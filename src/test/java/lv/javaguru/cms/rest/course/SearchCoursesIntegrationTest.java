package lv.javaguru.cms.rest.course;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import lv.javaguru.cms.model.entities.CourseType;
import lv.javaguru.cms.model.entities.DayOfTheWeek;
import lv.javaguru.cms.rest.CmsErrorCategory;
import lv.javaguru.cms.rest.CmsErrorCode;
import lv.javaguru.cms.rest.controllers.course.model.SearchCoursesRequest;
import lv.javaguru.cms.rest.controllers.course.model.SearchCoursesResponse;
import lv.javaguru.cms.rest.controllers.search.SearchCondition;
import lv.javaguru.cms.rest.controllers.search.SearchOperation;
import lv.javaguru.cms.rest.util.RestIntegrationTest;
import org.assertj.core.util.Lists;
import org.junit.Test;

import static com.github.springtestdbunit.annotation.DatabaseOperation.DELETE_ALL;
import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class SearchCoursesIntegrationTest extends RestIntegrationTest {

    @Test
    @DatabaseSetup(value = "classpath:dbunit/course/search_course/searchCourse-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/course/search_course/searchCourse-Admin-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldSearchCoursesByIdWithOrdering() {
        SearchCoursesRequest request = SearchCoursesRequest.builder()
                .searchConditions(Lists.newArrayList(new SearchCondition("id", SearchOperation.EQUAL, 1L)))
                .orderBy("id")
                .orderDirection("DESC")
                .pageNumber(0)
                .pageSize(10)
                .build();
        SearchCoursesResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getCourses().size(), is(1));
        assertThat(response.getCourses().get(0).getId(), is(1L));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/course/search_course/searchCourse-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/course/search_course/searchCourse-Admin-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldSearchCoursesByIdWithoutOrdering() {
        SearchCoursesRequest request = SearchCoursesRequest.builder()
                .searchConditions(Lists.newArrayList(new SearchCondition("id", SearchOperation.EQUAL, 1L)))
                .pageNumber(0)
                .pageSize(10)
                .build();
        SearchCoursesResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getCourses().size(), is(1));
        assertThat(response.getCourses().get(0).getId(), is(1L));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/course/search_course/searchCourse-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/course/search_course/searchCourse-Admin-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldSearchCoursesByIdWithoutOrderingAndPaging() {
        SearchCoursesRequest request = SearchCoursesRequest.builder()
                .searchConditions(Lists.newArrayList(new SearchCondition("id", SearchOperation.EQUAL, 1L)))
                .build();
        SearchCoursesResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getCourses().size(), is(1));
        assertThat(response.getCourses().get(0).getId(), is(1L));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/course/search_course/searchCourse-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/course/search_course/searchCourse-Admin-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldSearchCoursesByTitle() {
        SearchCoursesRequest request = SearchCoursesRequest.builder()
                .searchConditions(Lists.newArrayList(new SearchCondition("title", SearchOperation.EQUAL, "QA 1")))
                .build();
        SearchCoursesResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getCourses().size(), is(1));
        assertThat(response.getCourses().get(0).getId(), is(2L));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/course/search_course/searchCourse-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/course/search_course/searchCourse-Admin-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldSearchClientsDefaultRequest() {
        SearchCoursesRequest request = SearchCoursesRequest.builder().build();
        SearchCoursesResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getCourses().size(), is(2));
        assertThat(response.getCourses().get(0).getId(), is(1L));
        assertThat(response.getCourses().get(1).getId(), is(2L));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/course/search_course/searchCourse-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/course/search_course/searchCourse-Admin-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldSearchCoursesByCourseTypeAndDayOfTheWeek() {
        SearchCoursesRequest request = SearchCoursesRequest.builder()
                .searchConditions(
                        Lists.newArrayList(
                                new SearchCondition("courseType", SearchOperation.EQUAL, CourseType.ONLINE),
                                new SearchCondition("dayOfTheWeek", SearchOperation.EQUAL, DayOfTheWeek.MONDAY)
                        )
                )
                .build();
        SearchCoursesResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getCourses().size(), is(1));
        assertThat(response.getCourses().get(0).getId(), is(2L));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/course/search_course/searchCourse-illegalAccessRights-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/course/search_course/searchCourse-illegalAccessRights-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnSecurityErrorWhenUserNotHaveAccessRights() {
        SearchCoursesRequest request = SearchCoursesRequest.builder().build();
        SearchCoursesResponse response = sendRequest(BILL_MANAGER_LOGIN, BILL_MANAGER_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), is(CmsErrorCategory.WORKFLOW));
        assertThat(response.getErrors().get(0).getCode(), is(CmsErrorCode.UNAUTHORIZED));
        assertThat(response.getErrors().get(0).getDescription(), is("user = bill_manager must have role one of ADMIN,COURSE_MANAGER"));
    }

    private SearchCoursesResponse sendRequest(String userName,
                                              String password,
                                              SearchCoursesRequest request) {
        return getRestTemplate(userName, password).postForObject(
                baseUrl() + "/course/search", request, SearchCoursesResponse.class);
    }

}
