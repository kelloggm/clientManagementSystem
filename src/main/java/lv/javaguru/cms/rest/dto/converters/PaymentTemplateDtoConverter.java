package lv.javaguru.cms.rest.dto.converters;

import lv.javaguru.cms.model.entities.PaymentTemplateEntity;
import lv.javaguru.cms.rest.dto.PaymentTemplateDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PaymentTemplateDtoConverter {

    public PaymentTemplateDTO convert(PaymentTemplateEntity entity) {
        if (entity == null) {
            return null;
        }

        PaymentTemplateDTO dto = PaymentTemplateDTO.builder()
                .title(entity.getTitle())
                .templateFilePath(entity.getTemplateFilePath())
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
