package lv.javaguru.cms.rest.controllers.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import lv.javaguru.cms.rest.BaseRequest;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateClientRequest extends BaseRequest {

    @NonNull
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
