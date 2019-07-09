package lv.javaguru.cms.rest.course;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import lv.javaguru.cms.model.entities.CourseType;
import lv.javaguru.cms.model.entities.DayOfTheWeek;
import lv.javaguru.cms.model.entities.Language;
import lv.javaguru.cms.rest.CmsErrorCategory;
import lv.javaguru.cms.rest.CmsErrorCode;
import lv.javaguru.cms.rest.controllers.course.model.CourseRegistrationRequest;
import lv.javaguru.cms.rest.controllers.course.model.CourseRegistrationResponse;
import lv.javaguru.cms.rest.util.RestIntegrationTest;
import org.hamcrest.Matchers;
import org.junit.Test;

import static com.github.springtestdbunit.annotation.DatabaseOperation.DELETE_ALL;
import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class CourseRegistrationIntegrationTest extends RestIntegrationTest {

    @Test
    @DatabaseSetup(value = "classpath:dbunit/course/register_course/registerCourse-CourseManager-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/course/register_course/registerCourse-CourseManager-expectedDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldRegisterCourseWithClientManagerRole() {
        CourseRegistrationRequest request = buildRequest();
        CourseRegistrationResponse response = sendRequest(COURSE_MANAGER_LOGIN, COURSE_MANAGER_PASSWORD, request);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getCourseId(), is(notNullValue()));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/course/register_course/registerCourse-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/course/register_course/registerCourse-Admin-expectedDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldRegisterCourseWithAdminRole() {
        CourseRegistrationRequest request = buildRequest();
        CourseRegistrationResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getCourseId(), is(notNullValue()));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/course/register_course/registerCourse-illegalAccessRights-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/course/register_course/registerCourse-illegalAccessRights-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnSecurityErrorWhenUserNotHaveAccessRights() {
        CourseRegistrationRequest request = buildRequest();
        CourseRegistrationResponse response = sendRequest(BILL_MANAGER_LOGIN, BILL_MANAGER_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), is(CmsErrorCategory.WORKFLOW));
        assertThat(response.getErrors().get(0).getCode(), is(CmsErrorCode.UNAUTHORIZED));
        assertThat(response.getErrors().get(0).getDescription(), is("user = bill_manager must have role one of ADMIN,COURSE_MANAGER"));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/course/register_course/registerCourse-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/course/register_course/registerCourse-Admin-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnValidationErrorWhenTitleIsNull() {
        CourseRegistrationRequest request = buildRequest();
        request.setTitle(null);
        CourseRegistrationResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), Matchers.is(CmsErrorCategory.VALIDATION));
        assertThat(response.getErrors().get(0).getCode(), Matchers.is(CmsErrorCode.MISSING_FIELD));
        assertThat(response.getErrors().get(0).getDescription(), is("title"));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/course/register_course/registerCourse-CourseAlreadyExist-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/course/register_course/registerCourse-CourseAlreadyExist-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnValidationErrorWhenClientAlreadyExist() {
        CourseRegistrationRequest request = buildRequest();
        CourseRegistrationResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), is(CmsErrorCategory.VALIDATION));
        assertThat(response.getErrors().get(0).getCode(), is(CmsErrorCode.INVALID_FIELD_VALUE));
        assertThat(response.getErrors().get(0).getDescription(), is("Course already exist"));
    }

    private CourseRegistrationRequest buildRequest() {
        return CourseRegistrationRequest.builder()
                .title("Java 1 - Introduction to Java")
                .language(Language.RU)
                .startDate("01.10.2019")
                .endDate("01.10.2020")
                .courseType(CourseType.IN_CLASS)
                .address("Skolas 21, cabinet 508c")
                .lessonsTimeFrom("18:00")
                .lessonsTimeTo("21:00")
                .dayOfTheWeek(DayOfTheWeek.MONDAY)
                .fullPrice(400)
                .build();
    }

    private CourseRegistrationResponse sendRequest(String userName,
                                                   String password,
                                                   CourseRegistrationRequest request) {
        return getRestTemplate(userName, password).postForObject(
                baseUrl() + "/course", request, CourseRegistrationResponse.class);
    }

}
