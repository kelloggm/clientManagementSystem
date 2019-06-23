package lv.javaguru.cms.rest.systemuser;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import lv.javaguru.cms.rest.CmsErrorCategory;
import lv.javaguru.cms.rest.CmsErrorCode;
import lv.javaguru.cms.rest.util.RestIntegrationTest;
import lv.javaguru.cms.model.entities.SystemUserRole;
import lv.javaguru.cms.rest.controllers.systemuser.model.RegisterSystemUserRequest;
import lv.javaguru.cms.rest.controllers.systemuser.model.RegisterSystemUserResponse;
import org.hamcrest.Matchers;
import org.junit.Test;

import static com.github.springtestdbunit.annotation.DatabaseOperation.DELETE_ALL;
import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class RegisterSystemUserIntegrationTest extends RestIntegrationTest {

    @Test
    @DatabaseSetup(value = "classpath:dbunit/system_user/register_system_user/registerSystemUser-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/system_user/register_system_user/registerSystemUser-expectedDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldRegisterSystemUser() {
        RegisterSystemUserRequest request = buildRequest();
        RegisterSystemUserResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getSystemUserId(), is(notNullValue()));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/system_user/register_system_user/registerSystemUser-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/system_user/register_system_user/registerSystemUser-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnValidationErrorWhenFirstNameIsNull() {
        RegisterSystemUserRequest request = buildRequest();
        request.setFirstName(null);
        RegisterSystemUserResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), Matchers.is(CmsErrorCategory.VALIDATION));
        assertThat(response.getErrors().get(0).getCode(), Matchers.is(CmsErrorCode.MISSING_FIELD));
        assertThat(response.getErrors().get(0).getDescription(), is("firstName"));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/system_user/register_system_user/registerSystemUser-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/system_user/register_system_user/registerSystemUser-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnValidationErrorWhenLastNameIsNull() {
        RegisterSystemUserRequest request = buildRequest();
        request.setLastName(null);
        RegisterSystemUserResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), is(CmsErrorCategory.VALIDATION));
        assertThat(response.getErrors().get(0).getCode(), is(CmsErrorCode.MISSING_FIELD));
        assertThat(response.getErrors().get(0).getDescription(), is("lastName"));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/system_user/register_system_user/registerSystemUser-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/system_user/register_system_user/registerSystemUser-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnValidationErrorWhenLoginIsNull() {
        RegisterSystemUserRequest request = buildRequest();
        request.setLogin(null);
        RegisterSystemUserResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), is(CmsErrorCategory.VALIDATION));
        assertThat(response.getErrors().get(0).getCode(), is(CmsErrorCode.MISSING_FIELD));
        assertThat(response.getErrors().get(0).getDescription(), is("login"));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/system_user/register_system_user/registerSystemUser-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/system_user/register_system_user/registerSystemUser-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnValidationErrorWhenPasswordIsNull() {
        RegisterSystemUserRequest request = buildRequest();
        request.setPassword(null);
        RegisterSystemUserResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), is(CmsErrorCategory.VALIDATION));
        assertThat(response.getErrors().get(0).getCode(), is(CmsErrorCode.MISSING_FIELD));
        assertThat(response.getErrors().get(0).getDescription(), is("password"));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/system_user/register_system_user/registerSystemUser-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/system_user/register_system_user/registerSystemUser-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnValidationErrorWhenUserRoleIsNull() {
        RegisterSystemUserRequest request = buildRequest();
        request.setSystemUserRole(null);
        RegisterSystemUserResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), is(CmsErrorCategory.VALIDATION));
        assertThat(response.getErrors().get(0).getCode(), is(CmsErrorCode.MISSING_FIELD));
        assertThat(response.getErrors().get(0).getDescription(), is("systemUserRole"));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/system_user/register_system_user/registerSystemUser-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/system_user/register_system_user/registerSystemUser-expectedDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnValidationErrorWhenLoginAlreadyExist() {
        RegisterSystemUserRequest request = buildRequest();
        RegisterSystemUserResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), is(CmsErrorCategory.VALIDATION));
        assertThat(response.getErrors().get(0).getCode(), is(CmsErrorCode.INVALID_FIELD_VALUE));
        assertThat(response.getErrors().get(0).getDescription(), is("login"));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/system_user/register_system_user/registerSystemUser-illegalAccessRights-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/system_user/register_system_user/registerSystemUser-illegalAccessRights-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnSecurityErrorWhenUserNotHaveAccessRights() {
        RegisterSystemUserRequest request = buildRequest();
        RegisterSystemUserResponse response = sendRequest(BILL_MANAGER_LOGIN, BILL_MANAGER_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), is(CmsErrorCategory.WORKFLOW));
        assertThat(response.getErrors().get(0).getCode(), is(CmsErrorCode.UNAUTHORIZED));
        assertThat(response.getErrors().get(0).getDescription(), is("user = bill_manager must have role one of ADMIN"));
    }

    private RegisterSystemUserRequest buildRequest() {
        return RegisterSystemUserRequest.builder()
                                        .firstName("Vasja")
                                        .lastName("Pupkin")
                                        .login("login")
                                        .password("password")
                                        .systemUserRole(SystemUserRole.ADMIN)
                                        .build();
    }

    private RegisterSystemUserResponse sendRequest(String userName,
                                                   String password,
                                                   RegisterSystemUserRequest request) {
        return getRestTemplate(userName, password).postForObject(
                baseUrl() + "/system_user", request, RegisterSystemUserResponse.class);
    }

}
