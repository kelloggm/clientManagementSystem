package lv.javaguru.cms.rest.controllers.search;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchCondition {

    private String field;
    private SearchOperation operation;
    private Object value;

}
