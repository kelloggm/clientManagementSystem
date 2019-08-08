package lv.javaguru.cms.rest.controllers.company.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lv.javaguru.cms.rest.BaseRequest;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UpdateCompanyRequest extends BaseRequest {

    @NotNull
    private Long companyId;

    @NotNull
    private String title;

    @NotNull
    private String registrationNumber;

    @NotNull
    private String legalAddress;

    @NotNull
    private String bankName;

    @NotNull
    private String bankAccount;

    @NotNull
    private String memberOfTheBoard;

    @NotNull
    private Boolean pvnPayer;

}
