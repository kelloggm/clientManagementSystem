package lv.javaguru.cms.rest.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import lv.javaguru.cms.rest.CmsErrorCategory;
import lv.javaguru.cms.rest.CmsErrorCode;
import lv.javaguru.cms.rest.controllers.client.model.UpdateClientRequest;
import lv.javaguru.cms.rest.controllers.client.model.UpdateClientResponse;
import lv.javaguru.cms.rest.util.RestIntegrationTest;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import static com.github.springtestdbunit.annotation.DatabaseOperation.DELETE_ALL;
import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class UpdateClientIntegrationTest extends RestIntegrationTest {

    @Test
    @DatabaseSetup(value = "classpath:dbunit/client/update_client/updateClient-ClientManager-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/client/update_client/updateClient-ClientManager-expectedDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldUpdateClientWithClientManagerRole() {
        UpdateClientRequest request = buildRequest();
        UpdateClientResponse response = sendRequest(CLIENT_MANAGER_LOGIN, CLIENT_MANAGER_PASSWORD, request);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getClient(), is(notNullValue()));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/client/update_client/updateClient-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/client/update_client/updateClient-Admin-expectedDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldRegisterClientWithAdminRole() {
        UpdateClientRequest request = buildRequest();
        UpdateClientResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getClient(), is(notNullValue()));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/client/update_client/updateClient-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/client/update_client/updateClient-Admin-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnValidationErrorWhenFirstNameIsNull() {
        UpdateClientRequest request = buildRequest();
        request.setFirstName(null);
        UpdateClientResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), Matchers.is(CmsErrorCategory.VALIDATION));
        assertThat(response.getErrors().get(0).getCode(), Matchers.is(CmsErrorCode.MISSING_FIELD));
        assertThat(response.getErrors().get(0).getDescription(), is("firstName"));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/client/update_client/updateClient-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/client/update_client/updateClient-Admin-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnValidationErrorWhenNoPhoneOrEmailProvided() {
        UpdateClientRequest request = buildRequest();
        request.setPhoneNumber(null);
        request.setEmail(null);
        UpdateClientResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), is(CmsErrorCategory.VALIDATION));
        assertThat(response.getErrors().get(0).getCode(), is(CmsErrorCode.INVALID_FIELD_VALUE));
        assertThat(response.getErrors().get(0).getDescription(), is("Phone or email must be present"));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/client/update_client/updateClient-illegalAccessRights-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/client/update_client/updateClient-illegalAccessRights-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnSecurityErrorWhenUserNotHaveAccessRights() {
        UpdateClientRequest request = buildRequest();
        UpdateClientResponse response = sendRequest(BILL_MANAGER_LOGIN, BILL_MANAGER_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), is(CmsErrorCategory.WORKFLOW));
        assertThat(response.getErrors().get(0).getCode(), is(CmsErrorCode.UNAUTHORIZED));
        assertThat(response.getErrors().get(0).getDescription(), is("user = bill_manager must have role one of ADMIN,CLIENT_MANAGER"));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/client/update_client/updateClient-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/client/update_client/updateClient-Admin-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnValidationErrorWhenClientNotExist() {
        UpdateClientRequest request = buildRequest();
        request.setClientId(9999L);
        UpdateClientResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), is(CmsErrorCategory.VALIDATION));
        assertThat(response.getErrors().get(0).getCode(), is(CmsErrorCode.INVALID_FIELD_VALUE));
        assertThat(response.getErrors().get(0).getDescription(), is("clientId"));
    }

    private UpdateClientRequest buildRequest() {
        return UpdateClientRequest.builder()
                                        .clientId(1L)
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

    private UpdateClientResponse sendRequest(String userName,
                                             String password,
                                             UpdateClientRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonString = mapper.writeValueAsString(request);
            HttpEntity requestEntity = new HttpEntity(jsonString, headers);
            return getRestTemplate(userName, password).exchange(
                    baseUrl() + "/client/" + request.getClientId(), HttpMethod.PUT, requestEntity, UpdateClientResponse.class).getBody();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
