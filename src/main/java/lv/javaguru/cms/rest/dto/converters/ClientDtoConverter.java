package lv.javaguru.cms.rest.dto.converters;

import lv.javaguru.cms.model.entities.ClientEntity;
import lv.javaguru.cms.rest.dto.ClientDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ClientDtoConverter {

    public ClientDTO convert(ClientEntity clientEntity) {
        ClientDTO client = ClientDTO.builder()
                .firstName(clientEntity.getFirstName())
                .lastName(clientEntity.getLastName())
                .phoneNumber(clientEntity.getPhoneNumber())
                .email(clientEntity.getEmail())
                .promoCode(clientEntity.getPromoCode())
                .personalCode(clientEntity.getPersonalCode())
                .comment(clientEntity.getComment())
                .schoolkid(clientEntity.getSchoolkid())
                .student(clientEntity.getStudent())
                .build();
        client.setId(clientEntity.getId());
        client.setCreatedAt(convert(clientEntity.getCreatedAt()));
        client.setModifiedAt(convert(clientEntity.getModifiedAt()));
        client.setModifiedBy(clientEntity.getModifiedBy());
        return client;
    }

    private String convert(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.toString() : null;
    }

}
