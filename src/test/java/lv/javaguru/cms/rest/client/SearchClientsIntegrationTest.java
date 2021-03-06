package lv.javaguru.cms.rest.client;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import lv.javaguru.cms.rest.CmsErrorCategory;
import lv.javaguru.cms.rest.CmsErrorCode;
import lv.javaguru.cms.rest.controllers.client.model.SearchClientsRequest;
import lv.javaguru.cms.rest.controllers.client.model.SearchClientsResponse;
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

public class SearchClientsIntegrationTest extends RestIntegrationTest {

    @Test
    @DatabaseSetup(value = "classpath:dbunit/client/search_client/searchClient-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/client/search_client/searchClient-Admin-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldSearchClientsByIdWithOrdering() {
        SearchClientsRequest request = SearchClientsRequest.builder()
                .searchConditions(Lists.newArrayList(new SearchCondition("id", SearchOperation.EQUAL, 1L)))
                .orderBy("id")
                .orderDirection("DESC")
                .pageNumber(0)
                .pageSize(10)
                .build();
        SearchClientsResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getClients().size(), is(1));
        assertThat(response.getClients().get(0).getId(), is(1L));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/client/search_client/searchClient-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/client/search_client/searchClient-Admin-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldSearchClientsByIdWithoutOrdering() {
        SearchClientsRequest request = SearchClientsRequest.builder()
                .searchConditions(Lists.newArrayList(new SearchCondition("id", SearchOperation.EQUAL, 1L)))
                .pageNumber(0)
                .pageSize(10)
                .build();
        SearchClientsResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getClients().size(), is(1));
        assertThat(response.getClients().get(0).getId(), is(1L));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/client/search_client/searchClient-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/client/search_client/searchClient-Admin-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldSearchClientsByIdWithoutOrderingAndPaging() {
        SearchClientsRequest request = SearchClientsRequest.builder()
                .searchConditions(Lists.newArrayList(new SearchCondition("id", SearchOperation.EQUAL, 1L)))
                .build();
        SearchClientsResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getClients().size(), is(1));
        assertThat(response.getClients().get(0).getId(), is(1L));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/client/search_client/searchClient-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/client/search_client/searchClient-Admin-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldSearchClientsByLastName() {
        SearchClientsRequest request = SearchClientsRequest.builder()
                .searchConditions(Lists.newArrayList(new SearchCondition("lastName", SearchOperation.EQUAL, "Pupkin")))
                .orderBy("id")
                .orderDirection("DESC")
                .build();
        SearchClientsResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getClients().size(), is(2));
        assertThat(response.getClients().get(0).getId(), is(2L));
        assertThat(response.getClients().get(1).getId(), is(1L));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/client/search_client/searchClient-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/client/search_client/searchClient-Admin-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldSearchClientsDefaultRequest() {
        SearchClientsRequest request = SearchClientsRequest.builder().build();
        SearchClientsResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getClients().size(), is(2));
        assertThat(response.getClients().get(0).getId(), is(1L));
        assertThat(response.getClients().get(1).getId(), is(2L));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/client/search_client/searchClient-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/client/search_client/searchClient-Admin-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldSearchClientsByLastNameAndPromoCode() {
        SearchClientsRequest request = SearchClientsRequest.builder()
                .searchConditions(
                        Lists.newArrayList(
                                new SearchCondition("lastName", SearchOperation.EQUAL, "Pupkin"),
                                new SearchCondition("promoCode", SearchOperation.EQUAL, "promoCode_1")
                        )
                )
                .build();
        SearchClientsResponse response = sendRequest(ADMIN_LOGIN, ADMIN_PASSWORD, request);
        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        assertThat(response.getClients().size(), is(1));
        assertThat(response.getClients().get(0).getId(), is(1L));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/client/search_client/searchClient-illegalAccessRights-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/client/search_client/searchClient-illegalAccessRights-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldReturnSecurityErrorWhenUserNotHaveAccessRights() {
        SearchClientsRequest request = SearchClientsRequest.builder().build();
        SearchClientsResponse response = sendRequest(BILL_MANAGER_LOGIN, BILL_MANAGER_PASSWORD, request);
        assertThat(response.isOk(), is(false));
        assertThat(response.getErrors().size(), is(1));
        assertThat(response.getErrors().get(0).getCategory(), is(CmsErrorCategory.WORKFLOW));
        assertThat(response.getErrors().get(0).getCode(), is(CmsErrorCode.UNAUTHORIZED));
        assertThat(response.getErrors().get(0).getDescription(), is("user = bill_manager must have role one of ADMIN,CLIENT_MANAGER"));
    }

    private SearchClientsResponse sendRequest(String userName,
                                              String password,
                                              SearchClientsRequest request) {
        return getRestTemplate(userName, password).postForObject(
                baseUrl() + "/client/search", request, SearchClientsResponse.class);
    }

}
