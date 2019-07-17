package lv.javaguru.cms.rest.dto.converters;

import lv.javaguru.cms.model.entities.SystemUserEntity;
import lv.javaguru.cms.rest.dto.SystemUserDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SystemUserDtoConverter {

    public SystemUserDTO convert(SystemUserEntity systemUserEntity) {
        SystemUserDTO systemUser = SystemUserDTO.builder()
                                                .firstName(systemUserEntity.getFirstName())
                                                .lastName(systemUserEntity.getLastName())
                                                .login(systemUserEntity.getLogin())
                                                .build();
        systemUser.setId(systemUserEntity.getId());
        systemUser.setCreatedAt(convert(systemUserEntity.getCreatedAt()));
        systemUser.setModifiedAt(convert(systemUserEntity.getModifiedAt()));
        systemUser.setModifiedBy(systemUserEntity.getModifiedBy());
        return systemUser;
    }

    private String convert(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.toString() : null;
    }

}
