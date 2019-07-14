package lv.javaguru.cms.services.systemuser;

import lv.javaguru.cms.model.entities.SystemUserEntity;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.entities.SystemUserRoleEntity;
import lv.javaguru.cms.model.repositories.SystemUserRepository;
import lv.javaguru.cms.model.repositories.SystemUserRoleRepository;
import lv.javaguru.cms.rest.controllers.systemuser.model.SystemUserRegistrationRequest;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class SystemUserRegistrationService {

    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private SystemUserRightsChecker systemUserRightsChecker;
    @Autowired private SystemUserRepository systemUserRepository;
    @Autowired private SystemUserRoleRepository systemUserRoleRepository;

    public SystemUserEntity register(SystemUserRegistrationRequest request) {
        systemUserRightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN);
        checkIfSystemUserWithSameLoginAlreadyExist(request);

        SystemUserEntity systemUser = SystemUserEntity.builder()
                                                      .firstName(request.getFirstName())
                                                      .lastName(request.getLastName())
                                                      .login(request.getLogin())
                                                      .password(passwordEncoder.encode(request.getPassword()))
                                                      .build();
        systemUser.setModifiedBy(request.getSystemUserLogin());
        systemUser = systemUserRepository.save(systemUser);

        SystemUserRoleEntity systemUserRole = SystemUserRoleEntity.builder()
                                                                  .systemUser(systemUser)
                                                                  .systemUserRole(request.getSystemUserRole())
                                                                  .build();
        systemUserRoleRepository.save(systemUserRole);

        return systemUser;
    }

    private void checkIfSystemUserWithSameLoginAlreadyExist(SystemUserRegistrationRequest request) {
        systemUserRepository.findByLogin(request.getLogin()).ifPresent(
                systemUserEntity -> {
                    throw new IllegalArgumentException("login");
                }
        );
    }

}
