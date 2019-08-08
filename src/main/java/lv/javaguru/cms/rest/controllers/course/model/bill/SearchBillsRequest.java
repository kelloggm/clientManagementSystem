package lv.javaguru.cms.rest.controllers.course.model.bill;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lv.javaguru.cms.rest.BaseRequest;
import lv.javaguru.cms.rest.controllers.search.SearchCondition;

import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SearchBillsRequest extends BaseRequest {

    private List<SearchCondition> searchConditions;

    private String orderBy;

    private String orderDirection;

    private Integer pageNumber;

    private Integer pageSize;

}
