package lv.javaguru.cms.services.systemuser;

import lv.javaguru.cms.model.entities.SystemUserEntity;
import lv.javaguru.cms.model.entities.SystemUserRoleEntity;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.repositories.SystemUserRepository;
import lv.javaguru.cms.model.repositories.SystemUserRoleRepository;
import lv.javaguru.cms.rest.controllers.systemuser.model.UpdateSystemUserRequest;
import lv.javaguru.cms.rest.dto.SystemUserDTO;
import lv.javaguru.cms.rest.dto.converters.SystemUserDtoConverter;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class UpdateSystemUserService {

    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private SystemUserRightsChecker systemUserRightsChecker;
    @Autowired private SystemUserRepository systemUserRepository;
    @Autowired private SystemUserRoleRepository systemUserRoleRepository;
    @Autowired private SystemUserDtoConverter systemUserDtoConverter;

    public SystemUserDTO update(UpdateSystemUserRequest request) {
        systemUserRightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN);
        SystemUserEntity systemUser = getSystemUser(request);
        updateSystemUser(systemUser, request);
        deleteAllSystemUserRoles(systemUser);
        createSystemUserRoles(request, systemUser);
        SystemUserDTO systemUserDTO = systemUserDtoConverter.convert(systemUser);
        systemUserDTO.setRoles(getSystemUserRoles(systemUser));
        return systemUserDTO;
    }

    private void deleteAllSystemUserRoles(SystemUserEntity systemUser) {
        systemUserRoleRepository.findAllBySystemUser(systemUser).forEach(role -> systemUserRoleRepository.delete(role));
    }

    private void createSystemUserRoles(UpdateSystemUserRequest request, SystemUserEntity systemUser) {
        request.getSystemUserRoles().forEach(role -> createSystemUserRole(systemUser, role));
    }

    private void updateSystemUser(SystemUserEntity systemUser, UpdateSystemUserRequest request) {
        systemUser.setFirstName(request.getFirstName());
        systemUser.setLastName(request.getLastName());
        systemUser.setPassword(passwordEncoder.encode(request.getPassword()));
        systemUser.setModifiedBy(request.getSystemUserLogin());
    }

    private SystemUserEntity getSystemUser(UpdateSystemUserRequest request) {
        return systemUserRepository.getById(request.getSystemUserId());
    }

    private void createSystemUserRole(SystemUserEntity systemUser, SystemUserRole role) {
        SystemUserRoleEntity systemUserRole = SystemUserRoleEntity.builder()
                .systemUser(systemUser)
                .systemUserRole(role)
                .build();
        systemUserRoleRepository.save(systemUserRole);
    }

    private List<SystemUserRole> getSystemUserRoles(SystemUserEntity systemUser) {
        return systemUserRoleRepository.findAllBySystemUser(systemUser).stream()
                .map(SystemUserRoleEntity::getSystemUserRole)
                .collect(Collectors.toList());
    }

}
