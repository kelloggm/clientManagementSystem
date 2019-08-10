package lv.javaguru.cms.services.course.bills.generation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class BillParameters {

    private String billPrefix;
    private String billNumber;
    private String billCreationDate;
    private String companyTitle;
    private String companyAddress;
    private String companyBankName;
    private String companyBankAccount;
    private String companyRegistrationNumber;
    private String courseParticipant;
    private String billDueDate;
    private String courseTitle;
    private String courseStartDate;
    private String courseEndDate;
    private String courseAddress;
    private String billPrice;
    private String billPart;
    private String billPartTotal;
    private String companyMemberOfTheBoard;

}
