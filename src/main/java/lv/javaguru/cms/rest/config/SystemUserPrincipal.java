package lv.javaguru.cms.rest.config;

import lv.javaguru.cms.model.entities.SystemUserEntity;
import lv.javaguru.cms.model.entities.SystemUserRoleEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SystemUserPrincipal implements UserDetails {

    private SystemUserEntity systemUser;
    private List<SystemUserRoleEntity> systemUserRoles;

    public SystemUserPrincipal(SystemUserEntity systemUser,
                               List<SystemUserRoleEntity> systemUserRoles) {
        this.systemUser = systemUser;
        this.systemUserRoles = systemUserRoles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return systemUserRoles.stream()
                .map(SystemUserRoleEntity::getSystemUserRole)
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return systemUser.getPassword();
    }

    @Override
    public String getUsername() {
        return systemUser.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
