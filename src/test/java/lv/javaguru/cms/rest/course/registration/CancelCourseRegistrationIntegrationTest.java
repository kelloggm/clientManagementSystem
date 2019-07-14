package lv.javaguru.cms.rest.course.registration;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import lv.javaguru.cms.rest.util.RestIntegrationTest;
import org.junit.Test;

import static com.github.springtestdbunit.annotation.DatabaseOperation.DELETE_ALL;
import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT;

public class CancelCourseRegistrationIntegrationTest extends RestIntegrationTest {

    @Test
    @DatabaseSetup(value = "classpath:dbunit/registration/cancel_course_registration/cancelCourseRegistration-ClientManager-sutupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/registration/cancel_course_registration/cancelCourseRegistration-ClientManager-expectedDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldGetCourseRegistrationsWithClientManagerRole() {
        sendRequest(CLIENT_MANAGER_LOGIN, CLIENT_MANAGER_PASSWORD, 1L, 1L);
    }

    private void sendRequest(String userName,
                             String password,
                             Long courseId,
                             Long registrationId) {
        getRestTemplate(userName, password).delete(
                baseUrl() + "/course/" + courseId + "/registration/" + registrationId);
    }

}
