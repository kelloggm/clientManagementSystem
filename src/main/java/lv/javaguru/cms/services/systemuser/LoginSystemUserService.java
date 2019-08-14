package lv.javaguru.cms.services.systemuser;

import lv.javaguru.cms.model.entities.SystemUserEntity;
import lv.javaguru.cms.model.entities.SystemUserRoleEntity;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.repositories.SystemUserRepository;
import lv.javaguru.cms.model.repositories.SystemUserRoleRepository;
import lv.javaguru.cms.rest.controllers.systemuser.model.LoginSystemUserRequest;
import lv.javaguru.cms.rest.dto.SystemUserDTO;
import lv.javaguru.cms.rest.dto.converters.SystemUserDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class LoginSystemUserService {

    @Autowired private SystemUserRepository systemUserRepository;
    @Autowired private SystemUserRoleRepository systemUserRoleRepository;
    @Autowired private SystemUserDtoConverter converter;

    public SystemUserDTO get(LoginSystemUserRequest request) {
        SystemUserEntity systemUser = systemUserRepository.getByLogin(request.getSystemUserLogin());
        SystemUserDTO systemUserDTO = converter.convert(systemUser);
        systemUserDTO.setRoles(getSystemUserRoles(systemUser));
        return systemUserDTO;
    }

    private List<SystemUserRole> getSystemUserRoles(SystemUserEntity systemUser) {
        return systemUserRoleRepository.findAllBySystemUser(systemUser).stream()
                    .map(SystemUserRoleEntity::getSystemUserRole)
                    .collect(Collectors.toList());
    }

}
