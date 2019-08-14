package lv.javaguru.cms.rest.controllers.systemuser.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lv.javaguru.cms.rest.BaseResponse;
import lv.javaguru.cms.rest.dto.SystemUserDTO;

import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SearchSystemUsersResponse extends BaseResponse {

    private Long totalElements;

    private Integer totalPages;

    private List<SystemUserDTO> systemUsers;

}
