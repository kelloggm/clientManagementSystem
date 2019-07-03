package lv.javaguru.cms.model.repositories;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import lv.javaguru.cms.model.entities.ClientEntity;
import lv.javaguru.cms.rest.controllers.search.SearchOperation;
import lv.javaguru.cms.model.entities.search.ClientSpecification;
import lv.javaguru.cms.rest.controllers.search.SearchCondition;
import lv.javaguru.cms.rest.util.RestIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static com.github.springtestdbunit.annotation.DatabaseOperation.DELETE_ALL;
import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ClientRepositoryTest extends RestIntegrationTest {

    @Autowired private ClientRepository clientRepository;

    @Test
    @DatabaseSetup(value = "classpath:dbunit/client/search_client/searchClient-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/client/search_client/searchClient-Admin-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldSearchClientById() {
        ClientSpecification spec =
                new ClientSpecification(new SearchCondition("id", SearchOperation.EQUAL, 1L));

        List<ClientEntity> clients = clientRepository.findAll(spec);

        assertThat(clients.size(), is(1));
        assertThat(clients.get(0).getId(), is(1L));
    }

    @Test
    @DatabaseSetup(value = "classpath:dbunit/client/search_client/searchClient-Admin-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/client/search_client/searchClient-Admin-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldSearchLastNameWithOrdering() {
        ClientSpecification spec =
                new ClientSpecification(new SearchCondition("lastName", SearchOperation.EQUAL, "Pupkin"));

        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(0, 2, sort);

        Page<ClientEntity> page = clientRepository.findAll(spec, pageRequest);

        assertThat(page.getContent().size(), is(2));
        assertThat(page.getTotalElements(), is(2L));
        assertThat(page.getTotalPages(), is(1));
        assertThat(page.getContent().get(0).getId(), is(2L));
        assertThat(page.getContent().get(1).getId(), is(1L));
    }

}