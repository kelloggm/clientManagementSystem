package lv.javaguru.cms.rest.course.registration;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import lv.javaguru.cms.model.entities.enums.CourseType;
import lv.javaguru.cms.model.entities.enums.DayOfTheWeek;
import lv.javaguru.cms.model.entities.enums.Language;
import lv.javaguru.cms.rest.controllers.course.model.GetCourseRegistrationResponse;
import lv.javaguru.cms.rest.util.RestIntegrationTest;
import org.hamcrest.Matchers;
import org.junit.Test;

import static com.github.springtestdbunit.annotation.DatabaseOperation.DELETE_ALL;
import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class GetCourseRegistrationIntegrationTest extends RestIntegrationTest {

    @Test
    @DatabaseSetup(value = "classpath:dbunit/registration/get_course_registration/getCourseRegistration-ClientManager-sutupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/registration/get_course_registration/getCourseRegistration-ClientManager-sutupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldGetCourseRegistrationsWithClientManagerRole() {
        GetCourseRegistrationResponse response = sendRequest(CLIENT_MANAGER_LOGIN, CLIENT_MANAGER_PASSWORD, 1L, 1L);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getRegistration().getStatus(), is("ACTIVE"));
        assertThat(response.getRegistration().getCourse().getId(), is(1L));
        assertThat(response.getRegistration().getCourse().getTitle(), is("Java 1 - Introduction to Java"));
        assertThat(response.getRegistration().getCourse().getLanguage(), is(Language.RU));
        assertThat(response.getRegistration().getCourse().getStartDate(), is("10.01.2019"));
        assertThat(response.getRegistration().getCourse().getEndDate(), is("10.01.2020"));
        assertThat(response.getRegistration().getCourse().getCourseType(), is(CourseType.IN_CLASS));
        assertThat(response.getRegistration().getCourse().getAddress(), is("Skolas 21, cabinet 508c"));
        assertThat(response.getRegistration().getCourse().getLessonsTimeFrom(), is("18:00"));
        assertThat(response.getRegistration().getCourse().getLessonsTimeTo(), is("21:00"));
        assertThat(response.getRegistration().getCourse().getDayOfTheWeek(), is(DayOfTheWeek.MONDAY));
        assertThat(response.getRegistration().getCourse().getFullPrice(), is(400));
        assertThat(response.getRegistration().getCourse().getModifiedBy(), is("client_manager_login"));
        assertThat(response.getRegistration().getCourse().getCreatedAt(), is(Matchers.notNullValue()));
        assertThat(response.getRegistration().getCourse().getModifiedAt(), is(Matchers.nullValue()));

        assertThat(response.getRegistration().getClient().getId(), is(1L));
        assertThat(response.getRegistration().getClient().getFirstName(), is("Vasja"));
        assertThat(response.getRegistration().getClient().getLastName(), is("Pupkin"));
        assertThat(response.getRegistration().getClient().getPhoneNumber(), is("+371222"));
        assertThat(response.getRegistration().getClient().getEmail(), is("email@emal.lv"));
        assertThat(response.getRegistration().getClient().getPromoCode(), is("promoCode"));
        assertThat(response.getRegistration().getClient().getPersonalCode(), is("xxxxx"));
        assertThat(response.getRegistration().getClient().getComment(), is("comment"));
        assertThat(response.getRegistration().getClient().getSchoolkid(), is(Boolean.FALSE));
        assertThat(response.getRegistration().getClient().getStudent(), is(Boolean.FALSE));
        assertThat(response.getRegistration().getClient().getModifiedBy(), is("client_manager_login"));
        assertThat(response.getRegistration().getClient().getCreatedAt(), is(Matchers.notNullValue()));
        assertThat(response.getRegistration().getClient().getModifiedAt(), is(Matchers.nullValue()));
    }


    private GetCourseRegistrationResponse sendRequest(String userName,
                                                      String password,
                                                      Long courseId,
                                                      Long registrationId) {
        return getRestTemplate(userName, password).getForObject(
                baseUrl() + "/course/" + courseId + "/registration/" + registrationId, GetCourseRegistrationResponse.class);
    }

}
