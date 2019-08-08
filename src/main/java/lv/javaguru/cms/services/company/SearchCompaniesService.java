package lv.javaguru.cms.services.company;

import lv.javaguru.cms.model.entities.CompanyEntity;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.entities.search.CompanySpecification;
import lv.javaguru.cms.model.repositories.CompanyRepository;
import lv.javaguru.cms.rest.controllers.company.model.SearchCompaniesRequest;
import lv.javaguru.cms.rest.controllers.company.model.SearchCompaniesResponse;
import lv.javaguru.cms.rest.controllers.search.SearchCondition;
import lv.javaguru.cms.rest.dto.CompanyDTO;
import lv.javaguru.cms.rest.dto.converters.CompanyDtoConverter;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Transactional
public class SearchCompaniesService {

    @Autowired private SystemUserRightsChecker rightsChecker;
    @Autowired private CompanyRepository repository;
    @Autowired private CompanyDtoConverter converter;

    public SearchCompaniesResponse search(SearchCompaniesRequest request) {
        rightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN);

        Optional<Specification> specification = buildSpecification(request.getSearchConditions());
        Sort sort = buildSort(request);
        PageRequest pageRequest = buildPageRequest(request, sort);

        Page<CompanyEntity> page = specification.isPresent()
                ? repository.findAll(specification.get(), pageRequest)
                : repository.findAll(pageRequest);

        return SearchCompaniesResponse.builder()
                                      .totalElements(page.getTotalElements())
                                      .totalPages(page.getTotalPages())
                                      .companies(convert(page.getContent()))
                                      .build();
    }

    private PageRequest buildPageRequest(SearchCompaniesRequest request, Sort sort) {
        if (request.getPageNumber() == null || request.getPageSize() == null) {
            return PageRequest.of(0, 20, sort);
        } else {
            return PageRequest.of(request.getPageNumber(), request.getPageSize(), sort);
        }
    }

    private Sort buildSort(SearchCompaniesRequest request) {
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
            List<CompanySpecification> specs = searchConditions.stream()
                    .map(searchCondition -> new CompanySpecification(
                            new SearchCondition(searchCondition.getField(), searchCondition.getOperation(), searchCondition.getValue())))
                    .collect(Collectors.toList());

            Specification spec = Specification.where(specs.get(0));
            for (int i = 1; i < specs.size(); i++) {
                spec = spec.and(specs.get(i));
            }
            return Optional.of(spec);
        }
    }

    private List<CompanyDTO> convert(List<CompanyEntity> companies) {
        return companies.stream()
                      .map(company -> converter.convert(company))
                      .collect(Collectors.toList());
    }

}
