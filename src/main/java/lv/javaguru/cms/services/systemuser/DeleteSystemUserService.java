package lv.javaguru.cms.services.systemuser;

import lv.javaguru.cms.model.entities.SystemUserEntity;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.repositories.SystemUserRepository;
import lv.javaguru.cms.model.repositories.SystemUserRoleRepository;
import lv.javaguru.cms.rest.controllers.systemuser.model.DeleteSystemUserRequest;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DeleteSystemUserService {

    @Autowired private SystemUserRightsChecker rightsChecker;
    @Autowired private SystemUserRepository systemUserRepository;
    @Autowired private SystemUserRoleRepository systemUserRoleRepository;

    public void delete(DeleteSystemUserRequest request) {
        rightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN);
        SystemUserEntity systemUser = systemUserRepository.getById(request.getSystemUserId());

        if (request.getSystemUserLogin().equals(systemUser.getLogin())) {
            throw new IllegalArgumentException("Admin can not delete itself = " + request.getSystemUserLogin());
        }

        deleteAllSystemUserRoles(systemUser);
    }

    private void deleteAllSystemUserRoles(SystemUserEntity systemUser) {
        systemUserRoleRepository.findAllBySystemUser(systemUser).forEach(role -> systemUserRoleRepository.delete(role));
    }

}
