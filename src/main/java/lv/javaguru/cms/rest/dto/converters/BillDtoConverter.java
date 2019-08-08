package lv.javaguru.cms.rest.dto.converters;

import lv.javaguru.cms.model.entities.BillEntity;
import lv.javaguru.cms.rest.dto.BillDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BillDtoConverter {

    @Autowired private CourseDtoConverter courseConverter;
    @Autowired private CourseParticipantDtoConverter courseParticipantDtoConverter;
    @Autowired private CompanyDtoConverter companyDtoConverter;

    public BillDTO convert(BillEntity entity) {
        BillDTO dto = BillDTO.builder()
                .course(courseConverter.convert(entity.getCourse()))
                .courseParticipant(courseParticipantDtoConverter.convert(entity.getCourseParticipant()))
                .company(companyDtoConverter.convert(entity.getCompany()))
                .billPrefix(entity.getBillPrefix())
                .billNumber(entity.getBillNumber())
                .billPart(entity.getBillPart())
                .billSum(entity.getBillSum())
                .payTo(convert(entity.getPayTo()))
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
