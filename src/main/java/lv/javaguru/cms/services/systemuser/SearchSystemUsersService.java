package lv.javaguru.cms.services.systemuser;

import lv.javaguru.cms.model.entities.SystemUserEntity;
import lv.javaguru.cms.model.entities.SystemUserRoleEntity;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.entities.search.SystemUserSpecification;
import lv.javaguru.cms.model.repositories.SystemUserRepository;
import lv.javaguru.cms.model.repositories.SystemUserRoleRepository;
import lv.javaguru.cms.rest.controllers.search.SearchCondition;
import lv.javaguru.cms.rest.controllers.systemuser.model.SearchSystemUsersRequest;
import lv.javaguru.cms.rest.controllers.systemuser.model.SearchSystemUsersResponse;
import lv.javaguru.cms.rest.dto.SystemUserDTO;
import lv.javaguru.cms.rest.dto.converters.SystemUserDtoConverter;
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
public class SearchSystemUsersService {

    @Autowired private SystemUserRightsChecker rightsChecker;
    @Autowired private SystemUserRepository repository;
    @Autowired private SystemUserRoleRepository systemUserRoleRepository;
    @Autowired private SystemUserDtoConverter converter;

    public SearchSystemUsersResponse search(SearchSystemUsersRequest request) {
        rightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN);

        Optional<Specification> specification = buildSpecification(request.getSearchConditions());
        Sort sort = buildSort(request);
        PageRequest pageRequest = buildPageRequest(request, sort);

        Page<SystemUserEntity> page = specification.isPresent()
                ? repository.findAll(specification.get(), pageRequest)
                : repository.findAll(pageRequest);

        return SearchSystemUsersResponse.builder()
                                      .totalElements(page.getTotalElements())
                                      .totalPages(page.getTotalPages())
                                      .systemUsers(convert(page.getContent()))
                                      .build();
    }

    private PageRequest buildPageRequest(SearchSystemUsersRequest request, Sort sort) {
        if (request.getPageNumber() == null || request.getPageSize() == null) {
            return PageRequest.of(0, 20, sort);
        } else {
            return PageRequest.of(request.getPageNumber(), request.getPageSize(), sort);
        }
    }

    private Sort buildSort(SearchSystemUsersRequest request) {
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
            List<SystemUserSpecification> specs = searchConditions.stream()
                    .map(searchCondition -> new SystemUserSpecification(
                            new SearchCondition(searchCondition.getField(), searchCondition.getOperation(), searchCondition.getValue())))
                    .collect(Collectors.toList());

            Specification spec = Specification.where(specs.get(0));
            for (int i = 1; i < specs.size(); i++) {
                spec = spec.and(specs.get(i));
            }
            return Optional.of(spec);
        }
    }

    private List<SystemUserDTO> convert(List<SystemUserEntity> systemUsers) {
        return systemUsers.stream()
                      .map(this::convert)
                      .collect(Collectors.toList());
    }

    private SystemUserDTO convert(SystemUserEntity systemUser) {
        SystemUserDTO systemUserDTO = converter.convert(systemUser);
        systemUserDTO.setRoles(getSystemUserRoles(systemUser));
        return systemUserDTO;
    }

    private List<SystemUserRole> getSystemUserRoles(SystemUserEntity systemUser) {
        return systemUserRoleRepository.findAllBySystemUser(systemUser).stream()
                .map(SystemUserRoleEntity::getSystemUserRole)
                .collect(Collectors.toList());
    }

}
