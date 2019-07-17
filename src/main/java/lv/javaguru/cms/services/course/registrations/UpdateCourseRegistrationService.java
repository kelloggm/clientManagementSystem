package lv.javaguru.cms.services.course.registrations;

import lv.javaguru.cms.model.entities.CourseEntity;
import lv.javaguru.cms.model.entities.CourseRegistrationEntity;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.repositories.CourseRegistrationRepository;
import lv.javaguru.cms.model.repositories.CourseRepository;
import lv.javaguru.cms.rest.controllers.course.model.registration.UpdateCourseRegistrationRequest;
import lv.javaguru.cms.rest.controllers.course.model.registration.UpdateCourseRegistrationResponse;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UpdateCourseRegistrationService {

    @Autowired private SystemUserRightsChecker rightsChecker;
    @Autowired private CourseRepository courseRepository;
    @Autowired private CourseRegistrationRepository courseRegistrationRepository;

    public UpdateCourseRegistrationResponse update(UpdateCourseRegistrationRequest request) {
        rightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN, SystemUserRole.CLIENT_MANAGER);
        CourseEntity course = courseRepository.getById(request.getCourseId());
        CourseRegistrationEntity registration = courseRegistrationRepository.getById(request.getRegistrationId());
        if (!course.equals(registration.getCourse())) {
            throw new IllegalArgumentException("courseId");
        }
        registration.setStatus(request.getStatus());
        registration.setModifiedBy(request.getSystemUserLogin());
        return UpdateCourseRegistrationResponse.builder().build();
    }

}
