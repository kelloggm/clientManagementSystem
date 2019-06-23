package lv.javaguru.cms.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ClientDTO extends BaseDTO {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String promoCode;
    private String personalCode;
    private String comment;
    private Boolean schoolkid;
    private Boolean student;

}
