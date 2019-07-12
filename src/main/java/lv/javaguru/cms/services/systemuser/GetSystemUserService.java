package lv.javaguru.cms.services.systemuser;

import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.repositories.SystemUserRepository;
import lv.javaguru.cms.rest.controllers.systemuser.model.GetSystemUserRequest;
import lv.javaguru.cms.rest.dto.SystemUserDTO;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetSystemUserService {

    @Autowired private SystemUserRightsChecker rightsChecker;
    @Autowired private SystemUserRepository repository;
    @Autowired private SystemUserEntityToDTOConverter converter;

    public SystemUserDTO get(GetSystemUserRequest request) {
        rightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN);
        return repository.findById(request.getSystemUserId())
                .map(systemUserEntity -> converter.convert(systemUserEntity))
                .orElseThrow(() -> new IllegalArgumentException("systemUserId"));
    }

}
