package lv.javaguru.cms.rest.dto.converters;

import lv.javaguru.cms.model.entities.CourseEntity;
import lv.javaguru.cms.rest.dto.CourseDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CourseDtoConverter {

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");

    public CourseDTO convert(CourseEntity entity) {
        CourseDTO dto = CourseDTO.builder()
                .title(entity.getTitle())
                .language(entity.getLanguage())
                .startDate(entity.getStartDate().format(dateFormatter))
                .endDate(entity.getEndDate().format(dateFormatter))
                .courseType(entity.getCourseType())
                .address(entity.getAddress())
                .lessonsTimeFrom(entity.getLessonsTimeFrom())
                .lessonsTimeTo(entity.getLessonsTimeTo())
                .dayOfTheWeek(entity.getDayOfTheWeek())
                .fullPrice(entity.getFullPrice())
                .build();
        dto.setId(entity.getId());
        dto.setCreatedAt(convert(entity.getCreatedAt()));
        dto.setModifiedAt(convert(entity.getModifiedAt()));
        dto.setModifiedBy(entity.getModifiedBy());
        return dto;
    }

    private String convert(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.toString() : null;
    }

}
