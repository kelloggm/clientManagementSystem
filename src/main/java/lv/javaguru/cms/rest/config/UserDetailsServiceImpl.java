package lv.javaguru.cms.rest.config;

import lv.javaguru.cms.model.repositories.SystemUserRepository;
import lv.javaguru.cms.model.repositories.SystemUserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired private SystemUserRepository systemUserRepository;
    @Autowired private SystemUserRoleRepository systemUserRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return systemUserRepository.findByLogin(username)
                .map(systemUser -> new SystemUserPrincipal(
                        systemUser,
                        systemUserRoleRepository.findAllBySystemUser(systemUser)
                ))
        .orElseThrow(() -> new UsernameNotFoundException(username));
    }

}