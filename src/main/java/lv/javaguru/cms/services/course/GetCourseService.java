package lv.javaguru.cms.services.course;

import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.repositories.CourseRepository;
import lv.javaguru.cms.rest.controllers.course.model.GetCourseRequest;
import lv.javaguru.cms.rest.dto.CourseDTO;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class GetCourseService {

    @Autowired private SystemUserRightsChecker rightsChecker;
    @Autowired private CourseRepository repository;
    @Autowired private CourseEntityToDTOConverter converter;

    public CourseDTO get(GetCourseRequest request) {
        rightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN, SystemUserRole.COURSE_MANAGER);
        return repository.findById(request.getCourseId())
                .map(courseEntity -> converter.convert(courseEntity))
                .orElseThrow(() -> new IllegalArgumentException("courseId"));
    }

}
