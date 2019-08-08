package lv.javaguru.cms.rest.systemuser;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import lv.javaguru.cms.rest.CmsErrorCategory;
import lv.javaguru.cms.rest.CmsErrorCode;
import lv.javaguru.cms.rest.controllers.systemuser.model.GetSystemUserResponse;
import lv.javaguru.cms.rest.util.RestIntegrationTest;
import org.hamcrest.Matchers;
import org.junit.Test;

import static com.github.springtestdbunit.annotation.DatabaseOperation.DELETE_ALL;
import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class GetSystemUserIntegrationTest extends RestIntegrationTest {

    @Test
    @DatabaseSetup(value = "classpath:dbunit/system_user/get_system_user/getSystemUser-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/system_user/get_system_user/getSystemUser-Admin-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldGetClientWithAdminRole() {
        GetSystemUserResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, 1L);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getSystemUser().getId(), is(1L));
        assertThat(response.getSystemUser().getFirstName(), is("adminFirstName"));
        assertThat(response.getSystemUser().getLastName(), is("adminLastName"));
        assertThat(response.getSystemUser().getLogin(), is("admin"));
        assertThat(response.getSystemUser().getModifiedBy(), is("admin"));
        assertThat(response.getSystemUser().getCreatedAt(), is(Matchers.notNullValue()));
        assertThat(response.getSystemUser().getModifiedAt(), is(Matchers.nullValue()));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/system_user/get_system_user/getSystemUser-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/system_user/get_system_user/getSystemUser-Admin-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnValidationErrorWhenSystemUserNotFound() {
        GetSystemUserResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, 2L);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), Matchers.is(CmsErrorCategory.VALIDATION));
        assertThat(response.getErrors().get(0).getCode(), Matchers.is(CmsErrorCode.INVALID_FIELD_VALUE));
        assertThat(response.getErrors().get(0).getDescription(), is("systemUserId"));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/system_user/get_system_user/getSystemUser-illegalAccessRights-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/system_user/get_system_user/getSystemUser-illegalAccessRights-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnSecurityErrorWhenSystemUserNotHaveAccessRights() {
        GetSystemUserResponse response = sendRequest(BILL_MANAGER_LOGIN, BILL_MANAGER_PASSWORD, 1L);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), is(CmsErrorCategory.WORKFLOW));
        assertThat(response.getErrors().get(0).getCode(), is(CmsErrorCode.UNAUTHORIZED));
        assertThat(response.getErrors().get(0).getDescription(), is("user = bill_manager must have role one of ADMIN"));
    }

    private GetSystemUserResponse sendRequest(String userName,
                                              String password,
                                              Long systemUserId) {
        return getRestTemplate(userName, password).getForObject(
                baseUrl() + "/system_user/" + systemUserId, GetSystemUserResponse.class);
    }

}
