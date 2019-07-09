package lv.javaguru.cms.services.course;

import lv.javaguru.cms.model.entities.ClientEntity;
import lv.javaguru.cms.model.entities.CourseEntity;
import lv.javaguru.cms.model.entities.CourseRegistrationEntity;
import lv.javaguru.cms.model.entities.SystemUserRole;
import lv.javaguru.cms.model.repositories.ClientRepository;
import lv.javaguru.cms.model.repositories.CourseRegistrationRepository;
import lv.javaguru.cms.model.repositories.CourseRepository;
import lv.javaguru.cms.rest.controllers.course.model.ClientToCourseRegistrationRequest;
import lv.javaguru.cms.rest.controllers.course.model.ClientToCourseRegistrationResponse;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientToCourseRegistrationService {

    @Autowired private SystemUserRightsChecker rightsChecker;
    @Autowired private CourseRepository courseRepository;
    @Autowired private ClientRepository clientRepository;
    @Autowired private CourseRegistrationRepository courseRegistrationRepository;

    public ClientToCourseRegistrationResponse register(ClientToCourseRegistrationRequest request) {
        rightsChecker.checkAccessRights(request.getSystemUserLogin(),
                SystemUserRole.ADMIN, SystemUserRole.COURSE_MANAGER, SystemUserRole.CLIENT_MANAGER);
        CourseEntity course = getCourse(request);
        ClientEntity client = getClient(request);

        checkIfClientAlreadyRegisteredToThisCourse(course, client);

        CourseRegistrationEntity courseRegistration = CourseRegistrationEntity.builder()
                .course(course)
                .client(client)
                .build();
        courseRegistration = courseRegistrationRepository.save(courseRegistration);

        return ClientToCourseRegistrationResponse.builder().courseRegistrationId(courseRegistration.getId()).build();
    }

    private void checkIfClientAlreadyRegisteredToThisCourse(CourseEntity course, ClientEntity client) {
        courseRegistrationRepository.findByCourseAndClient(course, client)
                .ifPresent(courseRegistrationEntity -> {
                    throw new IllegalArgumentException("Client already registered to this course");
                });
    }

    private CourseEntity getCourse(ClientToCourseRegistrationRequest request) {
        return courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("courseId"));
    }

    private ClientEntity getClient(ClientToCourseRegistrationRequest request) {
        return clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("clientId"));
    }

}
