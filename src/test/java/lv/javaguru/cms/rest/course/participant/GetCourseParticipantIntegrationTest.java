package lv.javaguru.cms.rest.course.participant;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import lv.javaguru.cms.model.entities.enums.CourseType;
import lv.javaguru.cms.model.entities.enums.DayOfTheWeek;
import lv.javaguru.cms.model.entities.enums.Language;
import lv.javaguru.cms.rest.controllers.course.model.participant.GetCourseParticipantResponse;
import lv.javaguru.cms.rest.util.RestIntegrationTest;
import org.hamcrest.Matchers;
import org.junit.Test;

import static com.github.springtestdbunit.annotation.DatabaseOperation.DELETE_ALL;
import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class GetCourseParticipantIntegrationTest extends RestIntegrationTest {

    @Test
    @DatabaseSetup(value = "classpath:dbunit/participant/get_course_participant/getCourseParticipant-ClientManager-sutupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/participant/get_course_participant/getCourseParticipant-ClientManager-sutupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldGetCourseParticipantsWithClientManagerRole() {
        GetCourseParticipantResponse response = sendRequest(CLIENT_MANAGER_LOGIN, CLIENT_MANAGER_PASSWORD, 1L, 1L);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getParticipant().getStatus(), is("ACTIVE"));
        assertThat(response.getParticipant().getCourse().getId(), is(1L));
        assertThat(response.getParticipant().getCourse().getTitle(), is("Java 1 - Introduction to Java"));
        assertThat(response.getParticipant().getCourse().getLanguage(), is(Language.RU));
        assertThat(response.getParticipant().getCourse().getStartDate(), is("10.01.2019"));
        assertThat(response.getParticipant().getCourse().getEndDate(), is("10.01.2020"));
        assertThat(response.getParticipant().getCourse().getCourseType(), is(CourseType.IN_CLASS));
        assertThat(response.getParticipant().getCourse().getAddress(), is("Skolas 21, cabinet 508c"));
        assertThat(response.getParticipant().getCourse().getLessonsTimeFrom(), is("18:00"));
        assertThat(response.getParticipant().getCourse().getLessonsTimeTo(), is("21:00"));
        assertThat(response.getParticipant().getCourse().getDayOfTheWeek(), is(DayOfTheWeek.MONDAY));
        assertThat(response.getParticipant().getCourse().getFullPrice(), is(400));
        assertThat(response.getParticipant().getCourse().getModifiedBy(), is("client_manager_login"));
        assertThat(response.getParticipant().getCourse().getCreatedAt(), is(Matchers.notNullValue()));
        assertThat(response.getParticipant().getCourse().getModifiedAt(), is(Matchers.nullValue()));

        assertThat(response.getParticipant().getClient().getId(), is(1L));
        assertThat(response.getParticipant().getClient().getFirstName(), is("Vasja"));
        assertThat(response.getParticipant().getClient().getLastName(), is("Pupkin"));
        assertThat(response.getParticipant().getClient().getPhoneNumber(), is("+371222"));
        assertThat(response.getParticipant().getClient().getEmail(), is("email@emal.lv"));
        assertThat(response.getParticipant().getClient().getPromoCode(), is("promoCode"));
        assertThat(response.getParticipant().getClient().getPersonalCode(), is("xxxxx"));
        assertThat(response.getParticipant().getClient().getComment(), is("comment"));
        assertThat(response.getParticipant().getClient().getSchoolkid(), is(Boolean.FALSE));
        assertThat(response.getParticipant().getClient().getStudent(), is(Boolean.FALSE));
        assertThat(response.getParticipant().getClient().getModifiedBy(), is("client_manager_login"));
        assertThat(response.getParticipant().getClient().getCreatedAt(), is(Matchers.notNullValue()));
        assertThat(response.getParticipant().getClient().getModifiedAt(), is(Matchers.nullValue()));
    }


    private GetCourseParticipantResponse sendRequest(String userName,
                                                     String password,
                                                     Long courseId,
                                                     Long participantId) {
        return getRestTemplate(userName, password).getForObject(
                baseUrl() + "/course/" + courseId + "/participant/" + participantId, GetCourseParticipantResponse.class);
    }

}
