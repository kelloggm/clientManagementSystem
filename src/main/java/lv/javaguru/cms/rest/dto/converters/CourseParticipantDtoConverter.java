package lv.javaguru.cms.rest.dto.converters;

import lv.javaguru.cms.model.entities.CourseParticipantEntity;
import lv.javaguru.cms.rest.dto.CourseParticipantDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CourseParticipantDtoConverter {

    @Autowired private CourseDtoConverter courseConverter;
    @Autowired private ClientDtoConverter clientConverter;

    public CourseParticipantDTO convert(CourseParticipantEntity entity) {
        CourseParticipantDTO dto = CourseParticipantDTO.builder()
                .course(courseConverter.convert(entity.getCourse()))
                .client(clientConverter.convert(entity.getClient()))
                .status(entity.getStatus().name())
                .billCount(entity.getBillCount())
                .oneBillAmount(entity.getOneBillAmount())
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
