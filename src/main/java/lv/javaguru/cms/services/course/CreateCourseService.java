package lv.javaguru.cms.services.course;

import lv.javaguru.cms.model.entities.CourseEntity;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.repositories.CourseRepository;
import lv.javaguru.cms.rest.controllers.course.model.CreateCourseRequest;
import lv.javaguru.cms.rest.dto.CourseDTO;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@Transactional
public class CreateCourseService {

    @Autowired private SystemUserRightsChecker systemUserRightsChecker;
    @Autowired private CourseRepository courseRepository;
    @Autowired private CourseEntityToDTOConverter courseEntityToDTOConverter;

    public CourseDTO create(CreateCourseRequest request) {
        systemUserRightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN, SystemUserRole.COURSE_MANAGER);
        checkIfCourseAlreadyExist(request);
        CourseEntity course = buildCourseEntity(request);
        course = courseRepository.save(course);
        return courseEntityToDTOConverter.convert(course);
    }

    private CourseEntity buildCourseEntity(CreateCourseRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        CourseEntity course = CourseEntity.builder()
                                          .title(request.getTitle())
                                          .language(request.getLanguage())
                                          .startDate(LocalDate.parse(request.getStartDate(), formatter))
                                          .endDate(LocalDate.parse(request.getEndDate(), formatter))
                                          .courseType(request.getCourseType())
                                          .address(request.getAddress())
                                          .lessonsTimeFrom(request.getLessonsTimeFrom())
                                          .lessonsTimeTo(request.getLessonsTimeTo())
                                          .dayOfTheWeek(request.getDayOfTheWeek())
                                          .fullPrice(request.getFullPrice())
                                          .build();
        course.setModifiedBy(request.getSystemUserLogin());
        return course;
    }

    private void checkIfCourseAlreadyExist(CreateCourseRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        courseRepository.findByTitleAndLanguageAndAddressAndCourseTypeAndStartDateAndEndDate(
                request.getTitle(),
                request.getLanguage(),
                request.getAddress(),
                request.getCourseType(),
                LocalDate.parse(request.getStartDate(), formatter),
                LocalDate.parse(request.getEndDate(), formatter)
        ).ifPresent(courseEntity -> {
            throw new IllegalArgumentException("Course already exist");
        });
    }

}
