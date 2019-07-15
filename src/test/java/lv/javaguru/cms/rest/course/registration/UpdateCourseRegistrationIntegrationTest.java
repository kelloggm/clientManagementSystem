package lv.javaguru.cms.rest.course.registration;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import lv.javaguru.cms.model.entities.enums.CourseRegistrationStatus;
import lv.javaguru.cms.rest.controllers.course.model.registration.UpdateCourseRegistrationRequest;
import lv.javaguru.cms.rest.util.RestIntegrationTest;
import org.junit.Test;

import static com.github.springtestdbunit.annotation.DatabaseOperation.DELETE_ALL;
import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT;

public class UpdateCourseRegistrationIntegrationTest extends RestIntegrationTest {

    @Test
    @DatabaseSetup(value = "classpath:dbunit/registration/update_course_registration/updateCourseRegistration-ClientManager-sutupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/registration/update_course_registration/updateCourseRegistration-ClientManager-expectedDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldGetCourseRegistrationsWithClientManagerRole() {
        UpdateCourseRegistrationRequest request = UpdateCourseRegistrationRequest.builder()
                .courseId(1L)
                .registrationId(1L)
                .status(CourseRegistrationStatus.ACTIVE)
                .build();
        sendRequest(CLIENT_MANAGER_LOGIN, CLIENT_MANAGER_PASSWORD, request);
    }

    private void sendRequest(String userName,
                             String password,
                             UpdateCourseRegistrationRequest request) {
        getRestTemplate(userName, password).put(
                baseUrl() + "/course/" + request.getCourseId()
                        + "/registration/" + request.getRegistrationId(), request);
    }

}
