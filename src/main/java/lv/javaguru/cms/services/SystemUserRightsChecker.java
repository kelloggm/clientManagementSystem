package lv.javaguru.cms.services;

import lv.javaguru.cms.model.entities.SystemUserRole;
import lv.javaguru.cms.model.repositories.SystemUserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class SystemUserRightsChecker {

    @Autowired private SystemUserRoleRepository systemUserRoleRepository;

    public void checkAccessRights(String systemUserLogin, SystemUserRole ... allowedRoles) {
        systemUserRoleRepository.findAllBySystemUserLogin(systemUserLogin).stream()
                .filter(role -> isRoleAllowed(role, allowedRoles))
                .findFirst()
                .orElseThrow(() -> new SecurityException(
                        "user = " + systemUserLogin + " must have role one of " + buildListOfRoles(allowedRoles)));
    }

    private boolean isRoleAllowed(SystemUserRole role, SystemUserRole[] allowedRoles) {
        return Arrays.asList(allowedRoles).contains(role);
    }

    private String buildListOfRoles(SystemUserRole[] roles) {
        return Arrays.stream(roles)
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }

}
