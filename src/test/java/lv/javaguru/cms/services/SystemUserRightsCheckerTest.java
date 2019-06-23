package lv.javaguru.cms.services;

import com.google.common.collect.Lists;
import lv.javaguru.cms.model.entities.SystemUserRole;
import lv.javaguru.cms.model.repositories.SystemUserRoleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SystemUserRightsCheckerTest {

    @Mock private SystemUserRoleRepository systemUserRoleRepository;

    @InjectMocks
    private SystemUserRightsChecker rightsChecker;

    private static final String SYSTEM_USER_LOGIN = "login";

    @Test
    public void shouldAllowOneRole(){
        when(systemUserRoleRepository.findAllBySystemUserLogin(SYSTEM_USER_LOGIN))
                .thenReturn(Lists.newArrayList(SystemUserRole.ADMIN));
        rightsChecker.checkAccessRights(SYSTEM_USER_LOGIN, SystemUserRole.ADMIN);
    }

    @Test
    public void shouldAllowMultipleRoles(){
        when(systemUserRoleRepository.findAllBySystemUserLogin(SYSTEM_USER_LOGIN))
                .thenReturn(Lists.newArrayList(SystemUserRole.CLIENT_MANAGER, SystemUserRole.ADMIN));
        rightsChecker.checkAccessRights(SYSTEM_USER_LOGIN, SystemUserRole.ADMIN);
    }

    @Test(expected = SecurityException.class)
    public void shouldNotAllow(){
        when(systemUserRoleRepository.findAllBySystemUserLogin(SYSTEM_USER_LOGIN))
                .thenReturn(Lists.newArrayList(SystemUserRole.CLIENT_MANAGER));
        rightsChecker.checkAccessRights(SYSTEM_USER_LOGIN, SystemUserRole.ADMIN);
    }

}