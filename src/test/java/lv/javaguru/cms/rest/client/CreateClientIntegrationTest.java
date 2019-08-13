package lv.javaguru.cms.rest.client;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import lv.javaguru.cms.rest.CmsErrorCategory;
import lv.javaguru.cms.rest.CmsErrorCode;
import lv.javaguru.cms.rest.controllers.client.model.CreateClientRequest;
import lv.javaguru.cms.rest.controllers.client.model.CreateClientResponse;
import lv.javaguru.cms.rest.util.RestIntegrationTest;
import org.hamcrest.Matchers;
import org.junit.Test;

import static com.github.springtestdbunit.annotation.DatabaseOperation.DELETE_ALL;
import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class CreateClientIntegrationTest extends RestIntegrationTest {

    @Test
    @DatabaseSetup(value = "classpath:dbunit/client/create_client/createClient-ClientManager-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/client/create_client/createClient-ClientManager-expectedDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldCreateClientWithClientManagerRole() {
        CreateClientRequest request = buildRequest();
        CreateClientResponse response = sendRequest(CLIENT_MANAGER_LOGIN, CLIENT_MANAGER_PASSWORD, request);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getClientId(), is(notNullValue()));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/client/create_client/createClient-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/client/create_client/createClient-Admin-expectedDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldCreateClientWithAdminRole() {
        CreateClientRequest request = buildRequest();
        CreateClientResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getClientId(), is(notNullValue()));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/client/create_client/createClient-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/client/create_client/createClient-Admin-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnValidationErrorWhenFirstNameIsNull() {
        CreateClientRequest request = buildRequest();
        request.setFirstName(null);
        CreateClientResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), Matchers.is(CmsErrorCategory.VALIDATION));
        assertThat(response.getErrors().get(0).getCode(), Matchers.is(CmsErrorCode.MISSING_FIELD));
        assertThat(response.getErrors().get(0).getDescription(), is("firstName"));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/client/create_client/createClient-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/client/create_client/createClient-Admin-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnValidationErrorWhenNoPhoneOrEmailProvided() {
        CreateClientRequest request = buildRequest();
        request.setPhoneNumber(null);
        request.setEmail(null);
        CreateClientResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), is(CmsErrorCategory.VALIDATION));
        assertThat(response.getErrors().get(0).getCode(), is(CmsErrorCode.INVALID_FIELD_VALUE));
        assertThat(response.getErrors().get(0).getDescription(), is("Phone or email must be present"));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/client/create_client/createClient-illegalAccessRights-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/client/create_client/createClient-illegalAccessRights-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnSecurityErrorWhenUserNotHaveAccessRights() {
        CreateClientRequest request = buildRequest();
        CreateClientResponse response = sendRequest(BILL_MANAGER_LOGIN, BILL_MANAGER_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), is(CmsErrorCategory.WORKFLOW));
        assertThat(response.getErrors().get(0).getCode(), is(CmsErrorCode.UNAUTHORIZED));
        assertThat(response.getErrors().get(0).getDescription(), is("user = bill_manager must have role one of ADMIN,CLIENT_MANAGER"));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/client/create_client/createClient-ClientAlreadyExist-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/client/create_client/createClient-ClientAlreadyExist-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnValidationErrorWhenClientAlreadyExist() {
        CreateClientRequest request = buildRequest();
        CreateClientResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), is(CmsErrorCategory.VALIDATION));
        assertThat(response.getErrors().get(0).getCode(), is(CmsErrorCode.INVALID_FIELD_VALUE));
        assertThat(response.getErrors().get(0).getDescription(), is("Client with same [firstName, lastName, phoneNumber, email] already exist"));
    }

    private CreateClientRequest buildRequest() {
        return CreateClientRequest.builder()
                                        .firstName("Vasja")
                                        .lastName("Pupkin")
                                        .phoneNumber("+371222")
                                        .email("email@emal.lv")
                                        .promoCode("promoCode")
                                        .personalCode("xxxxx")
                                        .comment("comment")
                                        .schoolkid(Boolean.FALSE)
                                        .student(Boolean.FALSE)
                                        .build();
    }

    private CreateClientResponse sendRequest(String userName,
                                             String password,
                                             CreateClientRequest request) {
        return getRestTemplate(userName, password).postForObject(
                baseUrl() + "/client", request, CreateClientResponse.class);
    }

}
