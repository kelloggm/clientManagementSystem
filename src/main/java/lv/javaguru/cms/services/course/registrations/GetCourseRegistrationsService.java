package lv.javaguru.cms.services.course.registrations;

import lv.javaguru.cms.model.entities.CourseRegistrationEntity;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.repositories.CourseRegistrationRepository;
import lv.javaguru.cms.model.repositories.CourseRepository;
import lv.javaguru.cms.rest.controllers.course.model.registration.GetCourseRegistrationsRequest;
import lv.javaguru.cms.rest.controllers.course.model.registration.GetCourseRegistrationsResponse;
import lv.javaguru.cms.rest.dto.CourseRegistrationDTO;
import lv.javaguru.cms.rest.dto.converters.CourseRegistrationDtoConverter;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class GetCourseRegistrationsService {

    @Autowired private SystemUserRightsChecker rightsChecker;
    @Autowired private CourseRepository courseRepository;
    @Autowired private CourseRegistrationRepository courseRegistrationRepository;
    @Autowired private CourseRegistrationDtoConverter converter;

    public GetCourseRegistrationsResponse get(GetCourseRegistrationsRequest request) {
        rightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN, SystemUserRole.CLIENT_MANAGER);
        return courseRepository.findById(request.getCourseId())
                .map(courseEntity -> courseRegistrationRepository.findByCourse(courseEntity))
                .map(this::buildResponse)
                .orElseThrow(() -> new IllegalArgumentException("courseId"));

    }

    private GetCourseRegistrationsResponse buildResponse(List<CourseRegistrationEntity> courseRegistrations) {
        List<CourseRegistrationDTO> registrations = courseRegistrations.stream()
                .map(registration -> converter.convert(registration))
                .collect(toList());
        return GetCourseRegistrationsResponse.builder().registrations(registrations).build();
    }

}
