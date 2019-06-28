package lv.javaguru.cms.services.course;

import lv.javaguru.cms.model.entities.CourseEntity;
import lv.javaguru.cms.rest.dto.CourseDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CourseEntityToDTOConverter {

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");

    public CourseDTO convert(CourseEntity courseEntity) {
        CourseDTO course = CourseDTO.builder()
                .title(courseEntity.getTitle())
                .language(courseEntity.getLanguage())
                .startDate(courseEntity.getStartDate().format(dateFormatter))
                .endDate(courseEntity.getEndDate().format(dateFormatter))
                .courseType(courseEntity.getCourseType())
                .address(courseEntity.getAddress())
                .lessonsTimeFrom(courseEntity.getLessonsTimeFrom())
                .lessonsTimeTo(courseEntity.getLessonsTimeTo())
                .dayOfTheWeek(courseEntity.getDayOfTheWeek())
                .fullPrice(courseEntity.getFullPrice())
                .build();
        course.setId(courseEntity.getId());
        course.setCreatedAt(convert(courseEntity.getCreatedAt()));
        course.setModifiedAt(convert(courseEntity.getModifiedAt()));
        course.setModifiedBy(courseEntity.getModifiedBy());
        return course;
    }

    private String convert(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.toString() : null;
    }

}
