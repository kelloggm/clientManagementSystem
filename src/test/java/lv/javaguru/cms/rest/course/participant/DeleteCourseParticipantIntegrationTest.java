package lv.javaguru.cms.rest.course.participant;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import lv.javaguru.cms.rest.util.RestIntegrationTest;
import org.junit.Test;

import static com.github.springtestdbunit.annotation.DatabaseOperation.DELETE_ALL;
import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT;

public class DeleteCourseParticipantIntegrationTest extends RestIntegrationTest {

    @Test
    @DatabaseSetup(value = "classpath:dbunit/participant/cancel_course_participant/cancelCourseParticipant-ClientManager-sutupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/participant/cancel_course_participant/cancelCourseParticipant-ClientManager-expectedDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldGetCourseParticipantsWithClientManagerRole() {
        sendRequest(CLIENT_MANAGER_LOGIN, CLIENT_MANAGER_PASSWORD, 1L, 1L);
    }

    private void sendRequest(String userName,
                             String password,
                             Long courseId,
                             Long participantId) {
        getRestTemplate(userName, password).delete(
                baseUrl() + "/course/" + courseId + "/participant/" + participantId);
    }

}
