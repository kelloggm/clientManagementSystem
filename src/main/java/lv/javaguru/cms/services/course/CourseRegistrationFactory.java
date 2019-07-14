package lv.javaguru.cms.services.course;

import lv.javaguru.cms.model.entities.ClientEntity;
import lv.javaguru.cms.model.entities.CourseEntity;
import lv.javaguru.cms.model.entities.CourseRegistrationEntity;
import lv.javaguru.cms.model.entities.enums.CourseRegistrationStatus;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.repositories.ClientRepository;
import lv.javaguru.cms.model.repositories.CourseRegistrationRepository;
import lv.javaguru.cms.model.repositories.CourseRepository;
import lv.javaguru.cms.rest.controllers.course.model.registration.CourseRegistrationRequest;
import lv.javaguru.cms.rest.controllers.course.model.registration.CourseRegistrationResponse;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CourseRegistrationFactory {

    @Autowired private SystemUserRightsChecker rightsChecker;
    @Autowired private CourseRepository courseRepository;
    @Autowired private ClientRepository clientRepository;
    @Autowired private CourseRegistrationRepository courseRegistrationRepository;

    public CourseRegistrationResponse create(CourseRegistrationRequest request) {
        rightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN, SystemUserRole.CLIENT_MANAGER);
        CourseEntity course = getCourse(request);
        ClientEntity client = getClient(request);

        checkIfClientAlreadyRegisteredToThisCourse(course, client);

        CourseRegistrationEntity registration = CourseRegistrationEntity.builder()
                .course(course)
                .client(client)
                .status(CourseRegistrationStatus.ACTIVE)
                .build();
        registration.setModifiedBy(request.getSystemUserLogin());
        registration = courseRegistrationRepository.save(registration);

        return CourseRegistrationResponse.builder().registrationId(registration.getId()).build();
    }

    private void checkIfClientAlreadyRegisteredToThisCourse(CourseEntity course, ClientEntity client) {
        courseRegistrationRepository.findByCourseAndClient(course, client)
                .ifPresent(courseRegistrationEntity -> {
                    throw new IllegalArgumentException("Client already registered to this course");
                });
    }

    private CourseEntity getCourse(CourseRegistrationRequest request) {
        return courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("courseId"));
    }

    private ClientEntity getClient(CourseRegistrationRequest request) {
        return clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("clientId"));
    }

}
