package lv.javaguru.cms.rest.course.participant;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import lv.javaguru.cms.rest.CmsErrorCategory;
import lv.javaguru.cms.rest.CmsErrorCode;
import lv.javaguru.cms.rest.controllers.course.model.participant.CreateCourseParticipantRequest;
import lv.javaguru.cms.rest.controllers.course.model.participant.CreateCourseParticipantResponse;
import lv.javaguru.cms.rest.util.RestIntegrationTest;
import org.junit.Test;

import static com.github.springtestdbunit.annotation.DatabaseOperation.DELETE_ALL;
import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class CreateCourseParticipantIntegrationTest extends RestIntegrationTest {

    @Test
    @DatabaseSetup(value = "classpath:dbunit/participant/create_participant/participant-ClientManager-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/participant/create_participant/participant-ClientManager-expectedDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldRegisterClientToCourseWithClientManagerRole() {
        CreateCourseParticipantRequest request = CreateCourseParticipantRequest.builder()
                .courseId(1L)
                .clientId(1L)
                .billCount(1)
                .oneBillAmount(100)
                .build();
        CreateCourseParticipantResponse response = sendRequest(CLIENT_MANAGER_LOGIN, CLIENT_MANAGER_PASSWORD, request);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getParticipantId(), is(notNullValue()));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/participant/create_participant/participant-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/participant/create_participant/participant-Admin-expectedDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldRegisterClientToCourseWithAdminManagerRole() {
        CreateCourseParticipantRequest request = CreateCourseParticipantRequest.builder()
                .courseId(1L)
                .clientId(1L)
                .billCount(1)
                .oneBillAmount(100)
                .build();
        CreateCourseParticipantResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getParticipantId(), is(notNullValue()));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/participant/create_participant/participant-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/participant/create_participant/participant-Admin-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnValidationErrorWhenCourseNotFound() {
        CreateCourseParticipantRequest request = CreateCourseParticipantRequest.builder()
                .courseId(9999L)
                .clientId(1L)
                .billCount(1)
                .oneBillAmount(100)
                .build();
        CreateCourseParticipantResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), is(CmsErrorCategory.VALIDATION));
        assertThat(response.getErrors().get(0).getCode(), is(CmsErrorCode.INVALID_FIELD_VALUE));
        assertThat(response.getErrors().get(0).getDescription(), is("courseId"));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/participant/create_participant/participant-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/participant/create_participant/participant-Admin-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnValidationErrorWhenClientNotFound() {
        CreateCourseParticipantRequest request = CreateCourseParticipantRequest.builder()
                .courseId(1L)
                .clientId(9999L)
                .billCount(1)
                .oneBillAmount(100)
                .build();
        CreateCourseParticipantResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), is(CmsErrorCategory.VALIDATION));
        assertThat(response.getErrors().get(0).getCode(), is(CmsErrorCode.INVALID_FIELD_VALUE));
        assertThat(response.getErrors().get(0).getDescription(), is("clientId"));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/participant/create_participant/participant-Admin-expectedDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/participant/create_participant/participant-Admin-expectedDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnValidationErrorWhenClientAlreadyRegisteredToCourse() {
        CreateCourseParticipantRequest request = CreateCourseParticipantRequest.builder()
                .courseId(1L)
                .clientId(1L)
                .billCount(1)
                .oneBillAmount(100)
                .build();
        CreateCourseParticipantResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), is(CmsErrorCategory.VALIDATION));
        assertThat(response.getErrors().get(0).getCode(), is(CmsErrorCode.INVALID_FIELD_VALUE));
        assertThat(response.getErrors().get(0).getDescription(), is("Client already registered to this course"));
    }

    private CreateCourseParticipantResponse sendRequest(String userName,
                                                        String password,
                                                        CreateCourseParticipantRequest request) {
        return getRestTemplate(userName, password).postForObject(
                baseUrl() + "/course/" + request.getCourseId() +"/participant", request, CreateCourseParticipantResponse.class);
    }

}
