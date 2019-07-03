package lv.javaguru.cms.rest.course;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import lv.javaguru.cms.model.entities.CourseType;
import lv.javaguru.cms.model.entities.DayOfTheWeek;
import lv.javaguru.cms.model.entities.Language;
import lv.javaguru.cms.rest.CmsErrorCategory;
import lv.javaguru.cms.rest.CmsErrorCode;
import lv.javaguru.cms.rest.controllers.course.model.UpdateCourseRequest;
import lv.javaguru.cms.rest.controllers.course.model.UpdateCourseResponse;
import lv.javaguru.cms.rest.util.RestIntegrationTest;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import static com.github.springtestdbunit.annotation.DatabaseOperation.DELETE_ALL;
import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class UpdateCourseIntegrationTest extends RestIntegrationTest {

    @Test
    @DatabaseSetup(value = "classpath:dbunit/course/update_course/updateCourse-CourseManager-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/course/update_course/updateCourse-CourseManager-expectedDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldUpdateCourseWithCourseManagerRole() {
        UpdateCourseRequest request = buildRequest();
        UpdateCourseResponse response = sendRequest(COURSE_MANAGER_LOGIN, COURSE_MANAGER_PASSWORD, request);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getCourse(), is(notNullValue()));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/course/update_course/updateCourse-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/course/update_course/updateCourse-Admin-expectedDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldUpdateCourseWithAdminRole() {
        UpdateCourseRequest request = buildRequest();
        UpdateCourseResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getCourse(), is(notNullValue()));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/course/update_course/updateCourse-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/course/update_course/updateCourse-Admin-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnValidationErrorWhenTitleIsNull() {
        UpdateCourseRequest request = buildRequest();
        request.setTitle(null);
        UpdateCourseResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), Matchers.is(CmsErrorCategory.VALIDATION));
        assertThat(response.getErrors().get(0).getCode(), Matchers.is(CmsErrorCode.MISSING_FIELD));
        assertThat(response.getErrors().get(0).getDescription(), is("title"));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/course/update_course/updateCourse-illegalAccessRights-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/course/update_course/updateCourse-illegalAccessRights-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnSecurityErrorWhenUserNotHaveAccessRights() {
        UpdateCourseRequest request = buildRequest();
        UpdateCourseResponse response = sendRequest(BILL_MANAGER_LOGIN, BILL_MANAGER_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), is(CmsErrorCategory.WORKFLOW));
        assertThat(response.getErrors().get(0).getCode(), is(CmsErrorCode.UNAUTHORIZED));
        assertThat(response.getErrors().get(0).getDescription(), is("user = bill_manager must have role one of ADMIN,COURSE_MANAGER"));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/course/update_course/updateCourse-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/course/update_course/updateCourse-Admin-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnValidationErrorWhenClientNotExist() {
        UpdateCourseRequest request = buildRequest();
        request.setCourseId(9999L);
        UpdateCourseResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), is(CmsErrorCategory.VALIDATION));
        assertThat(response.getErrors().get(0).getCode(), is(CmsErrorCode.INVALID_FIELD_VALUE));
        assertThat(response.getErrors().get(0).getDescription(), is("courseId"));
    }

    private UpdateCourseRequest buildRequest() {
        return UpdateCourseRequest.builder()
                .courseId(1L)
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

    private UpdateCourseResponse sendRequest(String userName,
                                             String password,
                                             UpdateCourseRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonString = mapper.writeValueAsString(request);
            HttpEntity requestEntity = new HttpEntity(jsonString, headers);
            return getRestTemplate(userName, password).exchange(
                    baseUrl() + "/course/" + request.getCourseId(), HttpMethod.PUT, requestEntity, UpdateCourseResponse.class).getBody();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
