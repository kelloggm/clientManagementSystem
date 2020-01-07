package lv.javaguru.cms.rest.controllers.systemuser.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.rest.BaseRequest;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UpdateSystemUserRequest extends BaseRequest {

    @NonNull
    private Long systemUserId;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String password;

    @NotEmpty
    @NonNull
    private List<SystemUserRole> systemUserRoles;

}
