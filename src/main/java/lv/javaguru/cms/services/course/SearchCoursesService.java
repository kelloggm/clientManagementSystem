package lv.javaguru.cms.services.course;

import lv.javaguru.cms.model.entities.CourseEntity;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.entities.search.CourseSpecification;
import lv.javaguru.cms.model.repositories.CourseRepository;
import lv.javaguru.cms.rest.controllers.course.model.SearchCoursesRequest;
import lv.javaguru.cms.rest.controllers.course.model.SearchCoursesResponse;
import lv.javaguru.cms.rest.controllers.search.SearchCondition;
import lv.javaguru.cms.rest.dto.CourseDTO;
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
public class SearchCoursesService {

    @Autowired private SystemUserRightsChecker rightsChecker;
    @Autowired private CourseRepository courseRepository;
    @Autowired private CourseEntityToDTOConverter courseEntityToDTOConverter;

    public SearchCoursesResponse search(SearchCoursesRequest request) {
        rightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN, SystemUserRole.COURSE_MANAGER);

        Optional<Specification> specification = buildSpecification(request.getSearchConditions());
        Sort sort = buildSort(request);
        PageRequest pageRequest = buildPageRequest(request, sort);

        Page<CourseEntity> page = specification.isPresent()
                ? courseRepository.findAll(specification.get(), pageRequest)
                : courseRepository.findAll(pageRequest);

        return SearchCoursesResponse.builder()
                                    .totalElements(page.getTotalElements())
                                    .totalPages(page.getTotalPages())
                                    .courses(convert(page.getContent()))
                                    .build();
    }

    private PageRequest buildPageRequest(SearchCoursesRequest request, Sort sort) {
        if (request.getPageNumber() == null || request.getPageSize() == null) {
            return PageRequest.of(0, 20, sort);
        } else {
            return PageRequest.of(request.getPageNumber(), request.getPageSize(), sort);
        }
    }

    private Sort buildSort(SearchCoursesRequest request) {
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
            List<CourseSpecification> specs = searchConditions.stream()
                    .map(searchCondition -> new CourseSpecification(
                            new SearchCondition(
                                    searchCondition.getField(),
                                    searchCondition.getOperation(),
                                    searchCondition.getValue()
                            ))
                    )
                    .collect(Collectors.toList());

            Specification spec = Specification.where(specs.get(0));
            for (int i = 1; i < specs.size(); i++) {
                spec = spec.and(specs.get(i));
            }
            return Optional.of(spec);
        }

    }

    private List<CourseDTO> convert(List<CourseEntity> clients) {
        return clients.stream()
                      .map(client -> courseEntityToDTOConverter.convert(client))
                      .collect(Collectors.toList());
    }

}
