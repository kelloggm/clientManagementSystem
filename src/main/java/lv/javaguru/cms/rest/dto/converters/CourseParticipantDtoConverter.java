package lv.javaguru.cms.rest.dto.converters;

import lv.javaguru.cms.model.entities.CourseParticipantEntity;
import lv.javaguru.cms.rest.dto.CourseParticipantDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CourseParticipantDtoConverter {

    @Autowired private CourseDtoConverter courseConverter;
    @Autowired private ClientDtoConverter clientConverter;

    public CourseParticipantDTO convert(CourseParticipantEntity entity) {
        return CourseParticipantDTO.builder()
                .course(courseConverter.convert(entity.getCourse()))
                .client(clientConverter.convert(entity.getClient()))
                .status(entity.getStatus().name())
                .billCount(entity.getBillCount())
                .oneBillAmount(entity.getOneBillAmount())
                .build();
    }

}
