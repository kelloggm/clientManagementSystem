package lv.javaguru.cms.services.course;

import lv.javaguru.cms.model.entities.CourseEntity;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.repositories.CourseRepository;
import lv.javaguru.cms.rest.controllers.course.model.UpdateCourseRequest;
import lv.javaguru.cms.rest.dto.CourseDTO;
import lv.javaguru.cms.rest.dto.converters.CourseDtoConverter;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@Transactional
public class UpdateCourseService {

    @Autowired private SystemUserRightsChecker systemUserRightsChecker;
    @Autowired private CourseRepository courseRepository;
    @Autowired private CourseDtoConverter courseEntityToDTOConverter;

    public CourseDTO update(UpdateCourseRequest request) {
        systemUserRightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN, SystemUserRole.COURSE_MANAGER);
        CourseEntity course = getCourse(request);
        updateCourseEntity(course, request);
        return courseEntityToDTOConverter.convert(course);
    }

    private void updateCourseEntity(CourseEntity courseEntity, UpdateCourseRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        courseEntity.setTitle(request.getTitle());
        courseEntity.setLanguage(request.getLanguage());
        courseEntity.setStartDate(LocalDate.parse(request.getStartDate(), formatter));
        courseEntity.setEndDate(LocalDate.parse(request.getEndDate(), formatter));
        courseEntity.setCourseType(request.getCourseType());
        courseEntity.setAddress(request.getAddress());
        courseEntity.setLessonsTimeFrom(request.getLessonsTimeFrom());
        courseEntity.setLessonsTimeTo(request.getLessonsTimeTo());
        courseEntity.setDayOfTheWeek(request.getDayOfTheWeek());
        courseEntity.setFullPrice(request.getFullPrice());
        courseEntity.setModifiedBy(request.getSystemUserLogin());
    }

    private CourseEntity getCourse(UpdateCourseRequest request) {
        return courseRepository.findById(request.getCourseId())
                                .orElseThrow(() -> new IllegalArgumentException("courseId"));
    }

}
