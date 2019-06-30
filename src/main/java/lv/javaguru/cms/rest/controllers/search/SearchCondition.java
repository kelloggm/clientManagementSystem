package lv.javaguru.cms.rest.controllers.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCondition {

    private String field;
    private SearchOperation operation;
    private Object value;

}
