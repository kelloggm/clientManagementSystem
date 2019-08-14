package lv.javaguru.cms.rest.controllers.systemuser.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lv.javaguru.cms.rest.BaseRequest;

@Data
@Builder
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoginSystemUserRequest extends BaseRequest {

}
