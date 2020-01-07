package lv.javaguru.cms.rest.controllers.company.model;

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
public class CreateCompanyRequest extends BaseRequest {

    @NonNull
    private String title;

    @NonNull
    private String registrationNumber;

    @NonNull
    private String legalAddress;

    @NonNull
    private String bankName;

    @NonNull
    private String bankBicSwift;

    @NonNull
    private String bankAccount;

    @NonNull
    private String memberOfTheBoard;

    @NonNull
    private Boolean pvnPayer;

}
