package lv.javaguru.cms.services.registration;

import lv.javaguru.cms.model.entities.ClientEntity;
import lv.javaguru.cms.model.entities.CourseEntity;
import lv.javaguru.cms.model.entities.RegistrationEntity;
import lv.javaguru.cms.model.entities.SystemUserRole;
import lv.javaguru.cms.model.repositories.ClientRepository;
import lv.javaguru.cms.model.repositories.RegistrationRepository;
import lv.javaguru.cms.model.repositories.CourseRepository;
import lv.javaguru.cms.rest.controllers.registration.model.RegistrationRequest;
import lv.javaguru.cms.rest.controllers.registration.model.RegistrationResponse;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegistrationService {

    @Autowired private SystemUserRightsChecker rightsChecker;
    @Autowired private CourseRepository courseRepository;
    @Autowired private ClientRepository clientRepository;
    @Autowired private RegistrationRepository courseRegistrationRepository;

    public RegistrationResponse register(RegistrationRequest request) {
        rightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN, SystemUserRole.CLIENT_MANAGER);
        CourseEntity course = getCourse(request);
        ClientEntity client = getClient(request);

        checkIfClientAlreadyRegisteredToThisCourse(course, client);

        RegistrationEntity registration = RegistrationEntity.builder()
                .course(course)
                .client(client)
                .build();
        registration.setModifiedBy(request.getSystemUserLogin());
        registration = courseRegistrationRepository.save(registration);

        return RegistrationResponse.builder().registrationId(registration.getId()).build();
    }

    private void checkIfClientAlreadyRegisteredToThisCourse(CourseEntity course, ClientEntity client) {
        courseRegistrationRepository.findByCourseAndClient(course, client)
                .ifPresent(courseRegistrationEntity -> {
                    throw new IllegalArgumentException("Client already registered to this course");
                });
    }

    private CourseEntity getCourse(RegistrationRequest request) {
        return courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("courseId"));
    }

    private ClientEntity getClient(RegistrationRequest request) {
        return clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("clientId"));
    }

}
