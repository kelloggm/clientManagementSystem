package lv.javaguru.cms.services.course;

import lv.javaguru.cms.model.entities.CourseEntity;
import lv.javaguru.cms.model.entities.CourseRegistrationEntity;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.repositories.CourseRegistrationRepository;
import lv.javaguru.cms.model.repositories.CourseRepository;
import lv.javaguru.cms.rest.controllers.course.model.GetCourseRegistrationRequest;
import lv.javaguru.cms.rest.controllers.course.model.GetCourseRegistrationResponse;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetCourseRegistrationService {

    @Autowired private SystemUserRightsChecker rightsChecker;
    @Autowired private CourseRepository courseRepository;
    @Autowired private CourseRegistrationRepository courseRegistrationRepository;
    @Autowired private CourseRegistrationEntityToDTOConverter converter;

    public GetCourseRegistrationResponse get(GetCourseRegistrationRequest request) {
        rightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN, SystemUserRole.CLIENT_MANAGER);
        CourseEntity course = courseRepository.getById(request.getCourseId());
        CourseRegistrationEntity registration = courseRegistrationRepository.getById(request.getRegistrationId());
        if (!course.equals(registration.getCourse())) {
            throw new IllegalArgumentException("courseId");
        }
        return GetCourseRegistrationResponse.builder()
                .registration(converter.convert(registration))
                .build();
    }

}
