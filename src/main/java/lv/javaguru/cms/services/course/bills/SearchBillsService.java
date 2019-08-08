package lv.javaguru.cms.services.course.bills;

import lv.javaguru.cms.model.entities.BillEntity;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.entities.search.BillSpecification;
import lv.javaguru.cms.model.repositories.BillRepository;
import lv.javaguru.cms.rest.controllers.course.model.bill.SearchBillsRequest;
import lv.javaguru.cms.rest.controllers.course.model.bill.SearchBillsResponse;
import lv.javaguru.cms.rest.controllers.search.SearchCondition;
import lv.javaguru.cms.rest.dto.BillDTO;
import lv.javaguru.cms.rest.dto.converters.BillDtoConverter;
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
public class SearchBillsService {

    @Autowired private SystemUserRightsChecker rightsChecker;
    @Autowired private BillRepository repository;
    @Autowired private BillDtoConverter converter;

    public SearchBillsResponse search(SearchBillsRequest request) {
        rightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN, SystemUserRole.BILL_MANAGER);

        Optional<Specification> specification = buildSpecification(request.getSearchConditions());
        Sort sort = buildSort(request);
        PageRequest pageRequest = buildPageRequest(request, sort);

        Page<BillEntity> page = specification.isPresent()
                ? repository.findAll(specification.get(), pageRequest)
                : repository.findAll(pageRequest);

        return SearchBillsResponse.builder()
                                      .totalElements(page.getTotalElements())
                                      .totalPages(page.getTotalPages())
                                      .bills(convert(page.getContent()))
                                      .build();
    }

    private PageRequest buildPageRequest(SearchBillsRequest request, Sort sort) {
        if (request.getPageNumber() == null || request.getPageSize() == null) {
            return PageRequest.of(0, 20, sort);
        } else {
            return PageRequest.of(request.getPageNumber(), request.getPageSize(), sort);
        }
    }

    private Sort buildSort(SearchBillsRequest request) {
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
            List<BillSpecification> specs = searchConditions.stream()
                    .map(searchCondition -> new BillSpecification(
                            new SearchCondition(searchCondition.getField(), searchCondition.getOperation(), searchCondition.getValue())))
                    .collect(Collectors.toList());

            Specification spec = Specification.where(specs.get(0));
            for (int i = 1; i < specs.size(); i++) {
                spec = spec.and(specs.get(i));
            }
            return Optional.of(spec);
        }
    }

    private List<BillDTO> convert(List<BillEntity> bills) {
        return bills.stream()
                      .map(bill -> converter.convert(bill))
                      .collect(Collectors.toList());
    }

}
