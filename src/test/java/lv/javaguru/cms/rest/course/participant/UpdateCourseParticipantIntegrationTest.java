package lv.javaguru.cms.rest.course.participant;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import lv.javaguru.cms.model.entities.enums.CourseParticipantStatus;
import lv.javaguru.cms.rest.controllers.course.model.participant.UpdateCourseParticipantRequest;
import lv.javaguru.cms.rest.util.RestIntegrationTest;
import org.junit.Test;

import static com.github.springtestdbunit.annotation.DatabaseOperation.DELETE_ALL;
import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT;

public class UpdateCourseParticipantIntegrationTest extends RestIntegrationTest {

    @Test
    @DatabaseSetup(value = "classpath:dbunit/participant/update_course_participant/updateCourseParticipant-ClientManager-sutupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/participant/update_course_participant/updateCourseParticipant-ClientManager-expectedDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldUpdateCourseParticipantWithClientManagerRole() {
        UpdateCourseParticipantRequest request = UpdateCourseParticipantRequest.builder()
                .courseId(1L)
                .participantId(1L)
                .status(CourseParticipantStatus.ACTIVE)
                .build();
        sendRequest(CLIENT_MANAGER_LOGIN, CLIENT_MANAGER_PASSWORD, request);
    }

    private void sendRequest(String userName,
                             String password,
                             UpdateCourseParticipantRequest request) {
        getRestTemplate(userName, password).put(
                baseUrl() + "/course/" + request.getCourseId()
                        + "/participant/" + request.getParticipantId(), request);
    }

}
