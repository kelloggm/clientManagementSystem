package lv.javaguru.cms.services.course;

import lv.javaguru.cms.model.entities.CourseRegistrationEntity;
import lv.javaguru.cms.model.entities.SystemUserRole;
import lv.javaguru.cms.model.repositories.CourseRegistrationRepository;
import lv.javaguru.cms.model.repositories.CourseRepository;
import lv.javaguru.cms.rest.controllers.course.model.GetCourseRegistrationsRequest;
import lv.javaguru.cms.rest.controllers.course.model.GetCourseRegistrationsResponse;
import lv.javaguru.cms.rest.dto.CourseRegistrationDTO;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import lv.javaguru.cms.services.client.ClientEntityToDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class GetCourseRegistrationsService {

    @Autowired private SystemUserRightsChecker rightsChecker;
    @Autowired private CourseRepository courseRepository;
    @Autowired private CourseRegistrationRepository courseRegistrationRepository;
    @Autowired private CourseEntityToDTOConverter courseConverter;
    @Autowired private ClientEntityToDTOConverter clientConverter;

    public GetCourseRegistrationsResponse get(GetCourseRegistrationsRequest request) {
        rightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN, SystemUserRole.CLIENT_MANAGER);
        return courseRepository.findById(request.getCourseId())
                .map(courseEntity -> courseRegistrationRepository.findByCourse(courseEntity))
                .map(courseRegistrations -> buildResponse(courseRegistrations))
                .orElseThrow(() -> new IllegalArgumentException("courseId"));

    }

    private GetCourseRegistrationsResponse buildResponse(List<CourseRegistrationEntity> courseRegistrations) {
        List<CourseRegistrationDTO> registrations = courseRegistrations.stream()
                .map(this::convert)
                .collect(toList());
        return GetCourseRegistrationsResponse.builder().registrations(registrations).build();
    }

    private CourseRegistrationDTO convert(CourseRegistrationEntity courseRegistration) {
        return CourseRegistrationDTO.builder()
                .course(courseConverter.convert(courseRegistration.getCourse()))
                .client(clientConverter.convert(courseRegistration.getClient()))
                .build();
    }

}
