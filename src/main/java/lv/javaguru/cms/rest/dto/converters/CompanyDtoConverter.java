package lv.javaguru.cms.rest.dto.converters;

import lv.javaguru.cms.model.entities.CompanyEntity;
import lv.javaguru.cms.rest.dto.CompanyDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CompanyDtoConverter {

    public CompanyDTO convert(CompanyEntity entity) {
        CompanyDTO dto = CompanyDTO.builder()
                .title(entity.getTitle())
                .registrationNumber(entity.getRegistrationNumber())
                .legalAddress(entity.getLegalAddress())
                .bankName(entity.getBankName())
                .bankBicSwift(entity.getBankBicSwift())
                .bankAccount(entity.getBankAccount())
                .memberOfTheBoard(entity.getMemberOfTheBoard())
                .pvnPayer(entity.isPvnPayer())
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
