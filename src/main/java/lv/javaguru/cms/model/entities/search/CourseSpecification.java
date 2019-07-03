package lv.javaguru.cms.model.entities.search;

import lombok.AllArgsConstructor;
import lv.javaguru.cms.model.entities.CourseEntity;
import lv.javaguru.cms.rest.controllers.search.SearchCondition;
import lv.javaguru.cms.rest.controllers.search.SearchOperation;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ReflectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;

@AllArgsConstructor
public class CourseSpecification implements Specification<CourseEntity> {

    private SearchCondition criteria;

    @Override
    public Predicate toPredicate(Root<CourseEntity> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder builder) {

        Field field = ReflectionUtils.findField(CourseEntity.class, criteria.getField());
        Class fieldType = field.getType();
        if (fieldType.isEnum()) {
            criteria.setValue(Enum.valueOf(fieldType, (String) criteria.getValue()));
        }

        if (SearchOperation.GREATER.equals(criteria.getOperation())) {
            return builder.greaterThan(
                    root.get(criteria.getField()), criteria.getValue().toString());
        }
        else if (SearchOperation.GREATER_OR_EQUALS.equals(criteria.getOperation())) {
            return builder.greaterThanOrEqualTo(
                    root.get(criteria.getField()), criteria.getValue().toString());
        }
        else if (SearchOperation.LESS.equals(criteria.getOperation())) {
            return builder.lessThan(
                    root.get(criteria.getField()), criteria.getValue().toString());
        }
        else if (SearchOperation.LESS_OR_EQUALS.equals(criteria.getOperation())) {
            return builder.lessThanOrEqualTo(
                    root.get(criteria.getField()), criteria.getValue().toString());
        }
        else if (SearchOperation.START_WITH.equals(criteria.getOperation())) {
            return builder.like(
                    root.get(criteria.getField()), "%" + criteria.getValue());
        }
        else if (SearchOperation.LIKE.equals(criteria.getOperation())) {
            return builder.like(
                    root.get(criteria.getField()), "%" + criteria.getValue() + "%");
        }
        else if (SearchOperation.NOT_EQUAL.equals(criteria.getOperation())) {
            return builder.notEqual(
                    root.get(criteria.getField()), criteria.getValue());
        }
        else if (SearchOperation.EQUAL.equals(criteria.getOperation())) {
            return builder.equal(
                    root.get(criteria.getField()), criteria.getValue());
        }
        return null;
    }

}
