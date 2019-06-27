package lv.javaguru.cms.services.client;

import lv.javaguru.cms.model.entities.ClientEntity;
import lv.javaguru.cms.model.entities.SystemUserRole;
import lv.javaguru.cms.model.entities.search.client.ClientSpecification;
import lv.javaguru.cms.model.repositories.ClientRepository;
import lv.javaguru.cms.rest.controllers.client.model.SearchClientsRequest;
import lv.javaguru.cms.rest.controllers.client.model.SearchClientsResponse;
import lv.javaguru.cms.rest.controllers.search.SearchCondition;
import lv.javaguru.cms.rest.dto.ClientDTO;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SearchClientsService {

    @Autowired private SystemUserRightsChecker rightsChecker;
    @Autowired private ClientRepository clientRepository;
    @Autowired private ClientEntityToDTOConverter clientEntityToDTOConverter;

    public SearchClientsResponse get(SearchClientsRequest request) {
        rightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN, SystemUserRole.CLIENT_MANAGER);

        Optional<Specification> specification = buildSpecification(request.getSearchConditions());
        Sort sort = buildSort(request);
        PageRequest pageRequest = buildPageRequest(request, sort);

        Page<ClientEntity> page = specification.isPresent() ? clientRepository.findAll(specification.get(), pageRequest) : clientRepository.findAll(pageRequest);

        return SearchClientsResponse.builder()
                                    .totalElements(page.getTotalElements())
                                    .totalPages(page.getTotalPages())
                                    .clients(convert(page.getContent()))
                                    .build();
    }

    private PageRequest buildPageRequest(SearchClientsRequest request, Sort sort) {
        if (request.getPageNumber() == null || request.getPageSize() == null) {
            return PageRequest.of(0, 20, sort);
        } else {
            return PageRequest.of(request.getPageNumber(), request.getPageSize(), sort);
        }
    }

    private Sort buildSort(SearchClientsRequest request) {
        if (request.getOrderBy() == null || request.getOrderDirection() == null) {
            return Sort.by(Sort.Direction.ASC, "id");
        } else {
            Sort.Direction sortDirection = Sort.Direction.valueOf(
                    request.getOrderDirection()
            );
            return Sort.by(sortDirection, request.getOrderBy());
        }
    }

    private Optional<Specification> buildSpecification(List<SearchCondition> searchConditions) {
        if (searchConditions == null || searchConditions.isEmpty()) {
            return Optional.empty();
        } else {
            List<ClientSpecification> specs = searchConditions.stream()
                    .map(searchCondition -> new ClientSpecification(
                            new SearchCondition(searchCondition.getField(), searchCondition.getOperation(), searchCondition.getValue())))
                    .collect(Collectors.toList());

            Specification spec = Specification.where(specs.get(0));
            for (int i = 1; i < specs.size(); i++) {
                spec = spec.and(specs.get(i));
            }
            return Optional.of(spec);
        }

    }

    private List<ClientDTO> convert(List<ClientEntity> clients) {
        return clients.stream()
                      .map(client -> clientEntityToDTOConverter.convert(client))
                      .collect(Collectors.toList());
    }

}
