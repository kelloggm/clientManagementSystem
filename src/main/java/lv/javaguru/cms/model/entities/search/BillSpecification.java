package lv.javaguru.cms.model.entities.search;

import lombok.AllArgsConstructor;
import lv.javaguru.cms.model.entities.BillEntity;
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
public class BillSpecification implements Specification<BillEntity> {

    private SearchCondition criteria;

    @Override
    public Predicate toPredicate(Root<BillEntity> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder builder) {

        Field field = ReflectionUtils.findField(BillEntity.class, criteria.getField());
        Class fieldType = field.getType();
        if (fieldType.isEnum()) {
            criteria.setValue(Enum.valueOf(fieldType, (String) criteria.getValue()));
        }
        // TODO implement data conversion and Long/Boolean/Integer conversion from String

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
                    root.get(criteria.getField()), criteria.getValue().toString());
        }
        else if (SearchOperation.EQUAL.equals(criteria.getOperation())) {
            return builder.equal(
                    root.get(criteria.getField()), criteria.getValue().toString());
        }
        return null;
    }

}
