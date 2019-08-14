package lv.javaguru.cms.rest.systemuser;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import lv.javaguru.cms.rest.CmsErrorCategory;
import lv.javaguru.cms.rest.CmsErrorCode;
import lv.javaguru.cms.rest.controllers.systemuser.model.CreateSystemUserRequest;
import lv.javaguru.cms.rest.controllers.systemuser.model.CreateSystemUserResponse;
import lv.javaguru.cms.rest.util.RestIntegrationTest;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.junit.Test;

import static com.github.springtestdbunit.annotation.DatabaseOperation.DELETE_ALL;
import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class CreateSystemUserIntegrationTest extends RestIntegrationTest {

    @Test
    @DatabaseSetup(value = "classpath:dbunit/system_user/create_system_user/createSystemUser-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/system_user/create_system_user/createSystemUser-expectedDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldCreateSystemUser() {
        CreateSystemUserRequest request = buildRequest();
        CreateSystemUserResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getSystemUserId(), is(notNullValue()));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/system_user/create_system_user/createSystemUser-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/system_user/create_system_user/createSystemUser-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnValidationErrorWhenFirstNameIsNull() {
        CreateSystemUserRequest request = buildRequest();
        request.setFirstName(null);
        CreateSystemUserResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), Matchers.is(CmsErrorCategory.VALIDATION));
        assertThat(response.getErrors().get(0).getCode(), Matchers.is(CmsErrorCode.MISSING_FIELD));
        assertThat(response.getErrors().get(0).getDescription(), is("firstName"));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/system_user/create_system_user/createSystemUser-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/system_user/create_system_user/createSystemUser-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnValidationErrorWhenLastNameIsNull() {
        CreateSystemUserRequest request = buildRequest();
        request.setLastName(null);
        CreateSystemUserResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), is(CmsErrorCategory.VALIDATION));
        assertThat(response.getErrors().get(0).getCode(), is(CmsErrorCode.MISSING_FIELD));
        assertThat(response.getErrors().get(0).getDescription(), is("lastName"));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/system_user/create_system_user/createSystemUser-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/system_user/create_system_user/createSystemUser-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnValidationErrorWhenLoginIsNull() {
        CreateSystemUserRequest request = buildRequest();
        request.setLogin(null);
        CreateSystemUserResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), is(CmsErrorCategory.VALIDATION));
        assertThat(response.getErrors().get(0).getCode(), is(CmsErrorCode.MISSING_FIELD));
        assertThat(response.getErrors().get(0).getDescription(), is("login"));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/system_user/create_system_user/createSystemUser-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/system_user/create_system_user/createSystemUser-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnValidationErrorWhenPasswordIsNull() {
        CreateSystemUserRequest request = buildRequest();
        request.setPassword(null);
        CreateSystemUserResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), is(CmsErrorCategory.VALIDATION));
        assertThat(response.getErrors().get(0).getCode(), is(CmsErrorCode.MISSING_FIELD));
        assertThat(response.getErrors().get(0).getDescription(), is("password"));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/system_user/create_system_user/createSystemUser-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/system_user/create_system_user/createSystemUser-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnValidationErrorWhenUserRoleIsNull() {
        CreateSystemUserRequest request = buildRequest();
        request.setSystemUserRoles(null);
        CreateSystemUserResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), is(CmsErrorCategory.VALIDATION));
        assertThat(response.getErrors().get(0).getCode(), is(CmsErrorCode.MISSING_FIELD));
        assertThat(response.getErrors().get(0).getDescription(), is("systemUserRoles"));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/system_user/create_system_user/createSystemUser-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/system_user/create_system_user/createSystemUser-expectedDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnValidationErrorWhenLoginAlreadyExist() {
        CreateSystemUserRequest request = buildRequest();
        CreateSystemUserResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), is(CmsErrorCategory.VALIDATION));
        assertThat(response.getErrors().get(0).getCode(), is(CmsErrorCode.INVALID_FIELD_VALUE));
        assertThat(response.getErrors().get(0).getDescription(), is("login"));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/system_user/create_system_user/createSystemUser-illegalAccessRights-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/system_user/create_system_user/createSystemUser-illegalAccessRights-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnSecurityErrorWhenUserNotHaveAccessRights() {
        CreateSystemUserRequest request = buildRequest();
        CreateSystemUserResponse response = sendRequest(BILL_MANAGER_LOGIN, BILL_MANAGER_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), is(CmsErrorCategory.WORKFLOW));
        assertThat(response.getErrors().get(0).getCode(), is(CmsErrorCode.UNAUTHORIZED));
        assertThat(response.getErrors().get(0).getDescription(), is("user = bill_manager must have role one of ADMIN"));
    }

    private CreateSystemUserRequest buildRequest() {
        return CreateSystemUserRequest.builder()
                                        .firstName("Vasja")
                                        .lastName("Pupkin")
                                        .login("login")
                                        .password("password")
                                        .systemUserRoles(Lists.newArrayList(SystemUserRole.ADMIN))
                                        .build();
    }

    private CreateSystemUserResponse sendRequest(String userName,
                                                 String password,
                                                 CreateSystemUserRequest request) {
        return getRestTemplate(userName, password).postForObject(
                baseUrl() + "/system_user", request, CreateSystemUserResponse.class);
    }

}
