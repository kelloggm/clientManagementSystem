package lv.javaguru.cms.rest.course;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import lv.javaguru.cms.model.entities.enums.CourseType;
import lv.javaguru.cms.model.entities.enums.DayOfTheWeek;
import lv.javaguru.cms.model.entities.enums.Language;
import lv.javaguru.cms.rest.CmsErrorCategory;
import lv.javaguru.cms.rest.CmsErrorCode;
import lv.javaguru.cms.rest.controllers.course.model.GetCourseResponse;
import lv.javaguru.cms.rest.util.RestIntegrationTest;
import org.hamcrest.Matchers;
import org.junit.Test;

import static com.github.springtestdbunit.annotation.DatabaseOperation.DELETE_ALL;
import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class GetCourseIntegrationTest extends RestIntegrationTest {

    @Test
    @DatabaseSetup(value = "classpath:dbunit/course/get_course/getCourse-CourseManager-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/course/get_course/getCourse-CourseManager-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldGetCourseWithCourseManagerRole() {
        GetCourseResponse response = sendRequest(COURSE_MANAGER_LOGIN, COURSE_MANAGER_PASSWORD, 1L);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getCourse().getId(), is(1L));
        assertThat(response.getCourse().getTitle(), is("Java 1 - Introduction to Java"));
        assertThat(response.getCourse().getLanguage(), is(Language.RU));
        assertThat(response.getCourse().getStartDate(), is("10.01.2019"));
        assertThat(response.getCourse().getEndDate(), is("10.01.2020"));
        assertThat(response.getCourse().getCourseType(), is(CourseType.IN_CLASS));
        assertThat(response.getCourse().getAddress(), is("Skolas 21, cabinet 508c"));
        assertThat(response.getCourse().getLessonsTimeFrom(), is("18:00"));
        assertThat(response.getCourse().getLessonsTimeTo(), is("21:00"));
        assertThat(response.getCourse().getDayOfTheWeek(), is(DayOfTheWeek.MONDAY));
        assertThat(response.getCourse().getFullPrice(), is(400));
        assertThat(response.getCourse().getModifiedBy(), is("course_manager_login"));
        assertThat(response.getCourse().getCreatedAt(), is(Matchers.notNullValue()));
        assertThat(response.getCourse().getModifiedAt(), is(Matchers.nullValue()));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/course/get_course/getCourse-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/course/get_course/getCourse-Admin-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldGetCourseWithAdminRole() {
        GetCourseResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, 1L);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getCourse().getId(), is(1L));
        assertThat(response.getCourse().getTitle(), is("Java 1 - Introduction to Java"));
        assertThat(response.getCourse().getLanguage(), is(Language.RU));
        assertThat(response.getCourse().getStartDate(), is("10.01.2019"));
        assertThat(response.getCourse().getEndDate(), is("10.01.2020"));
        assertThat(response.getCourse().getCourseType(), is(CourseType.IN_CLASS));
        assertThat(response.getCourse().getAddress(), is("Skolas 21, cabinet 508c"));
        assertThat(response.getCourse().getLessonsTimeFrom(), is("18:00"));
        assertThat(response.getCourse().getLessonsTimeTo(), is("21:00"));
        assertThat(response.getCourse().getDayOfTheWeek(), is(DayOfTheWeek.MONDAY));
        assertThat(response.getCourse().getFullPrice(), is(400));
        assertThat(response.getCourse().getModifiedBy(), is("admin"));
        assertThat(response.getCourse().getCreatedAt(), is(Matchers.notNullValue()));
        assertThat(response.getCourse().getModifiedAt(), is(Matchers.nullValue()));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/course/get_course/getCourse-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/course/get_course/getCourse-Admin-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnValidationErrorWhenCourseNotFound() {
        GetCourseResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, 2L);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), Matchers.is(CmsErrorCategory.VALIDATION));
        assertThat(response.getErrors().get(0).getCode(), Matchers.is(CmsErrorCode.INVALID_FIELD_VALUE));
        assertThat(response.getErrors().get(0).getDescription(), is("courseId"));
    }

    private GetCourseResponse sendRequest(String userName,
                                          String password,
                                          Long courseId) {
        return getRestTemplate(userName, password).getForObject(
                baseUrl() + "/course/" + courseId, GetCourseResponse.class);
    }

}
