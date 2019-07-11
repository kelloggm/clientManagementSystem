package lv.javaguru.cms.rest.registration;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import lv.javaguru.cms.rest.CmsErrorCategory;
import lv.javaguru.cms.rest.CmsErrorCode;
import lv.javaguru.cms.rest.controllers.registration.model.RegistrationRequest;
import lv.javaguru.cms.rest.controllers.registration.model.RegistrationResponse;
import lv.javaguru.cms.rest.util.RestIntegrationTest;
import org.junit.Test;

import static com.github.springtestdbunit.annotation.DatabaseOperation.DELETE_ALL;
import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class RegistrationIntegrationTest extends RestIntegrationTest {

    @Test
    @DatabaseSetup(value = "classpath:dbunit/registration/create_registration/registration-ClientManager-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/registration/create_registration/registration-ClientManager-expectedDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldRegisterClientToCourseWithClientManagerRole() {
        RegistrationRequest request = RegistrationRequest.builder().courseId(1L).clientId(1L).build();
        RegistrationResponse response = sendRequest(CLIENT_MANAGER_LOGIN, CLIENT_MANAGER_PASSWORD, request);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getRegistrationId(), is(notNullValue()));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/registration/create_registration/registration-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/registration/create_registration/registration-Admin-expectedDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldRegisterClientToCourseWithAdminManagerRole() {
        RegistrationRequest request = RegistrationRequest.builder().courseId(1L).clientId(1L).build();
        RegistrationResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getRegistrationId(), is(notNullValue()));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/registration/create_registration/registration-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/registration/create_registration/registration-Admin-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnValidationErrorWhenCourseNotFound() {
        RegistrationRequest request = RegistrationRequest.builder().courseId(9999L).clientId(1L).build();
        RegistrationResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), is(CmsErrorCategory.VALIDATION));
        assertThat(response.getErrors().get(0).getCode(), is(CmsErrorCode.INVALID_FIELD_VALUE));
        assertThat(response.getErrors().get(0).getDescription(), is("courseId"));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/registration/create_registration/registration-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/registration/create_registration/registration-Admin-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnValidationErrorWhenClientNotFound() {
        RegistrationRequest request = RegistrationRequest.builder().courseId(1L).clientId(99999L).build();
        RegistrationResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), is(CmsErrorCategory.VALIDATION));
        assertThat(response.getErrors().get(0).getCode(), is(CmsErrorCode.INVALID_FIELD_VALUE));
        assertThat(response.getErrors().get(0).getDescription(), is("clientId"));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/registration/create_registration/registration-Admin-expectedDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/registration/create_registration/registration-Admin-expectedDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnValidationErrorWhenClientAlreadyRegisteredToCourse() {
        RegistrationRequest request = RegistrationRequest.builder().courseId(1L).clientId(1L).build();
        RegistrationResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), is(CmsErrorCategory.VALIDATION));
        assertThat(response.getErrors().get(0).getCode(), is(CmsErrorCode.INVALID_FIELD_VALUE));
        assertThat(response.getErrors().get(0).getDescription(), is("Client already registered to this course"));
    }

    private RegistrationResponse sendRequest(String userName,
                                             String password,
                                             RegistrationRequest request) {
        return getRestTemplate(userName, password).postForObject(
                baseUrl() + "/registration", request, RegistrationResponse.class);
    }

}
