package lv.javaguru.cms.rest.course.registration;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import lv.javaguru.cms.model.entities.enums.CourseType;
import lv.javaguru.cms.model.entities.enums.DayOfTheWeek;
import lv.javaguru.cms.model.entities.enums.Language;
import lv.javaguru.cms.rest.CmsErrorCategory;
import lv.javaguru.cms.rest.CmsErrorCode;
import lv.javaguru.cms.rest.controllers.course.model.GetCourseRegistrationsResponse;
import lv.javaguru.cms.rest.util.RestIntegrationTest;
import org.hamcrest.Matchers;
import org.junit.Test;

import static com.github.springtestdbunit.annotation.DatabaseOperation.DELETE_ALL;
import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class GetCourseRegistrationsIntegrationTest extends RestIntegrationTest {

    @Test
    @DatabaseSetup(value = "classpath:dbunit/registration/get_course_registrations/getCourseRegistrations-ClientManager-sutupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/registration/get_course_registrations/getCourseRegistrations-ClientManager-sutupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldGetCourseRegistrationsWithClientManagerRole() {
        GetCourseRegistrationsResponse response = sendRequest(CLIENT_MANAGER_LOGIN, CLIENT_MANAGER_PASSWORD, 1L);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getRegistrations().size(), is(1));
        assertThat(response.getRegistrations().get(0).getStatus(), is("ACTIVE"));
        assertThat(response.getRegistrations().get(0).getCourse().getId(), is(1L));
        assertThat(response.getRegistrations().get(0).getCourse().getTitle(), is("Java 1 - Introduction to Java"));
        assertThat(response.getRegistrations().get(0).getCourse().getLanguage(), is(Language.RU));
        assertThat(response.getRegistrations().get(0).getCourse().getStartDate(), is("10.01.2019"));
        assertThat(response.getRegistrations().get(0).getCourse().getEndDate(), is("10.01.2020"));
        assertThat(response.getRegistrations().get(0).getCourse().getCourseType(), is(CourseType.IN_CLASS));
        assertThat(response.getRegistrations().get(0).getCourse().getAddress(), is("Skolas 21, cabinet 508c"));
        assertThat(response.getRegistrations().get(0).getCourse().getLessonsTimeFrom(), is("18:00"));
        assertThat(response.getRegistrations().get(0).getCourse().getLessonsTimeTo(), is("21:00"));
        assertThat(response.getRegistrations().get(0).getCourse().getDayOfTheWeek(), is(DayOfTheWeek.MONDAY));
        assertThat(response.getRegistrations().get(0).getCourse().getFullPrice(), is(400));
        assertThat(response.getRegistrations().get(0).getCourse().getModifiedBy(), is("client_manager_login"));
        assertThat(response.getRegistrations().get(0).getCourse().getCreatedAt(), is(Matchers.notNullValue()));
        assertThat(response.getRegistrations().get(0).getCourse().getModifiedAt(), is(Matchers.nullValue()));

        assertThat(response.getRegistrations().get(0).getClient().getId(), is(1L));
        assertThat(response.getRegistrations().get(0).getClient().getFirstName(), is("Vasja"));
        assertThat(response.getRegistrations().get(0).getClient().getLastName(), is("Pupkin"));
        assertThat(response.getRegistrations().get(0).getClient().getPhoneNumber(), is("+371222"));
        assertThat(response.getRegistrations().get(0).getClient().getEmail(), is("email@emal.lv"));
        assertThat(response.getRegistrations().get(0).getClient().getPromoCode(), is("promoCode"));
        assertThat(response.getRegistrations().get(0).getClient().getPersonalCode(), is("xxxxx"));
        assertThat(response.getRegistrations().get(0).getClient().getComment(), is("comment"));
        assertThat(response.getRegistrations().get(0).getClient().getSchoolkid(), is(Boolean.FALSE));
        assertThat(response.getRegistrations().get(0).getClient().getStudent(), is(Boolean.FALSE));
        assertThat(response.getRegistrations().get(0).getClient().getModifiedBy(), is("client_manager_login"));
        assertThat(response.getRegistrations().get(0).getClient().getCreatedAt(), is(Matchers.notNullValue()));
        assertThat(response.getRegistrations().get(0).getClient().getModifiedAt(), is(Matchers.nullValue()));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/registration/get_course_registrations/getCourseRegistrations-ClientManager-sutupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/registration/get_course_registrations/getCourseRegistrations-ClientManager-sutupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnValidationErrorWhenCourseNotFound() {
        GetCourseRegistrationsResponse response = sendRequest(CLIENT_MANAGER_LOGIN, CLIENT_MANAGER_PASSWORD, 9999L);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), Matchers.is(CmsErrorCategory.VALIDATION));
        assertThat(response.getErrors().get(0).getCode(), Matchers.is(CmsErrorCode.INVALID_FIELD_VALUE));
        assertThat(response.getErrors().get(0).getDescription(), is("courseId"));
    }

    private GetCourseRegistrationsResponse sendRequest(String userName,
                                                       String password,
                                                       Long courseId) {
        return getRestTemplate(userName, password).getForObject(
                baseUrl() + "/course/" + courseId + "/registration", GetCourseRegistrationsResponse.class);
    }

}
