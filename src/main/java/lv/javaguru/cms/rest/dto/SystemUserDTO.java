package lv.javaguru.cms.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SystemUserDTO extends BaseDTO {

    private String firstName;
    private String lastName;
    private String login;
    private List<SystemUserRole> roles;

}
