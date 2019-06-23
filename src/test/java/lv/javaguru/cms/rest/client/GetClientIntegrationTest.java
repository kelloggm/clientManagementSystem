package lv.javaguru.cms.rest.client;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import lv.javaguru.cms.rest.CmsErrorCategory;
import lv.javaguru.cms.rest.CmsErrorCode;
import lv.javaguru.cms.rest.controllers.client.model.GetClientResponse;
import lv.javaguru.cms.rest.util.RestIntegrationTest;
import org.hamcrest.Matchers;
import org.junit.Test;

import static com.github.springtestdbunit.annotation.DatabaseOperation.DELETE_ALL;
import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class GetClientIntegrationTest extends RestIntegrationTest {

    @Test
    @DatabaseSetup(value = "classpath:dbunit/client/get_client/getClient-ClientManager-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/client/get_client/getClient-ClientManager-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldGetClientWithClientManagerRole() {
        GetClientResponse response = sendRequest(CLIENT_MANAGER_LOGIN, CLIENT_MANAGER_PASSWORD, 1L);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getClient().getId(), is(1L));
        assertThat(response.getClient().getFirstName(), is("Vasja"));
        assertThat(response.getClient().getLastName(), is("Pupkin"));
        assertThat(response.getClient().getPhoneNumber(), is("+371222"));
        assertThat(response.getClient().getEmail(), is("email@emal.lv"));
        assertThat(response.getClient().getPromoCode(), is("promoCode"));
        assertThat(response.getClient().getPersonalCode(), is("xxxxx"));
        assertThat(response.getClient().getComment(), is("comment"));
        assertThat(response.getClient().getSchoolkid(), is(Boolean.FALSE));
        assertThat(response.getClient().getStudent(), is(Boolean.FALSE));
        assertThat(response.getClient().getModifiedBy(), is("client_manager_login"));
        assertThat(response.getClient().getCreatedAt(), is(Matchers.notNullValue()));
        assertThat(response.getClient().getModifiedAt(), is(Matchers.nullValue()));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/client/get_client/getClient-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/client/get_client/getClient-Admin-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldGetClientWithAdminRole() {
        GetClientResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, 1L);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getClient().getId(), is(1L));
        assertThat(response.getClient().getFirstName(), is("Vasja"));
        assertThat(response.getClient().getLastName(), is("Pupkin"));
        assertThat(response.getClient().getPhoneNumber(), is("+371222"));
        assertThat(response.getClient().getEmail(), is("email@emal.lv"));
        assertThat(response.getClient().getPromoCode(), is("promoCode"));
        assertThat(response.getClient().getPersonalCode(), is("xxxxx"));
        assertThat(response.getClient().getComment(), is("comment"));
        assertThat(response.getClient().getSchoolkid(), is(Boolean.FALSE));
        assertThat(response.getClient().getStudent(), is(Boolean.FALSE));
        assertThat(response.getClient().getModifiedBy(), is("client_manager_login"));
        assertThat(response.getClient().getCreatedAt(), is(Matchers.notNullValue()));
        assertThat(response.getClient().getModifiedAt(), is(Matchers.nullValue()));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/client/get_client/getClient-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/client/get_client/getClient-Admin-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnValidationErrorWhenClientNotFound() {
        GetClientResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, 2L);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), Matchers.is(CmsErrorCategory.VALIDATION));
        assertThat(response.getErrors().get(0).getCode(), Matchers.is(CmsErrorCode.INVALID_FIELD_VALUE));
        assertThat(response.getErrors().get(0).getDescription(), is("clientId"));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/client/get_client/getClient-illegalAccessRights-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/client/get_client/getClient-illegalAccessRights-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnSecurityErrorWhenSystemUserNotHaveAccessRights() {
        GetClientResponse response = sendRequest(PAYMENT_MANAGER_LOGIN, PAYMENT_MANAGER_PASSWORD, 1L);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), is(CmsErrorCategory.WORKFLOW));
        assertThat(response.getErrors().get(0).getCode(), is(CmsErrorCode.UNAUTHORIZED));
        assertThat(response.getErrors().get(0).getDescription(), is("user = payment_manager must have role one of ADMIN,CLIENT_MANAGER"));
    }

    private GetClientResponse sendRequest(String userName,
                                          String password,
                                          Long clientId) {
        return getRestTemplate(userName, password).getForObject(
                baseUrl() + "/client/" + clientId, GetClientResponse.class);
    }

}
