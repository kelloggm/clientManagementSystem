package lv.javaguru.cms.services.course.participants;

import lv.javaguru.cms.model.entities.ClientEntity;
import lv.javaguru.cms.model.entities.CourseEntity;
import lv.javaguru.cms.model.entities.CourseParticipantEntity;
import lv.javaguru.cms.model.entities.enums.CourseParticipantStatus;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.repositories.ClientRepository;
import lv.javaguru.cms.model.repositories.CourseParticipantRepository;
import lv.javaguru.cms.model.repositories.CourseRepository;
import lv.javaguru.cms.rest.controllers.course.model.participant.CreateCourseParticipantRequest;
import lv.javaguru.cms.rest.controllers.course.model.participant.CreateCourseParticipantResponse;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CreateCourseParticipantService {

    @Autowired private SystemUserRightsChecker rightsChecker;
    @Autowired private CourseRepository courseRepository;
    @Autowired private ClientRepository clientRepository;
    @Autowired private CourseParticipantRepository courseParticipantRepository;

    public CreateCourseParticipantResponse create(CreateCourseParticipantRequest request) {
        rightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN, SystemUserRole.CLIENT_MANAGER);
        CourseEntity course = getCourse(request);
        ClientEntity client = getClient(request);

        checkIfClientAlreadyRegisteredToThisCourse(course, client);

        CourseParticipantEntity participant = CourseParticipantEntity.builder()
                .course(course)
                .client(client)
                .status(CourseParticipantStatus.ACTIVE)
                .billCount(request.getBillCount())
                .oneBillAmount(request.getOneBillAmount())
                .build();
        participant.setModifiedBy(request.getSystemUserLogin());
        participant = courseParticipantRepository.save(participant);

        return CreateCourseParticipantResponse.builder().participantId(participant.getId()).build();
    }

    private void checkIfClientAlreadyRegisteredToThisCourse(CourseEntity course, ClientEntity client) {
        courseParticipantRepository.findByCourseAndClient(course, client)
                .ifPresent(courseParticipant -> {
                    throw new IllegalArgumentException("Client already registered to this course");
                });
    }

    private CourseEntity getCourse(CreateCourseParticipantRequest request) {
        return courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("courseId"));
    }

    private ClientEntity getClient(CreateCourseParticipantRequest request) {
        return clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("clientId"));
    }

}
