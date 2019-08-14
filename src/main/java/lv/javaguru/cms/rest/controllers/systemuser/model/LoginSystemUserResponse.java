package lv.javaguru.cms.rest.controllers.systemuser.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lv.javaguru.cms.rest.BaseResponse;
import lv.javaguru.cms.rest.dto.SystemUserDTO;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoginSystemUserResponse extends BaseResponse {

    private SystemUserDTO systemUser;

}
