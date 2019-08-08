package lv.javaguru.cms.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lv.javaguru.cms.model.entities.enums.BillStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BillDTO extends BaseDTO {

    private CourseDTO course;
    private CourseParticipantDTO courseParticipant;
    private CompanyDTO company;
    private String billPrefix;
    private Integer billNumber;
    private Integer billPart;
    private String payTo;
    private Integer billSum;
    private BillStatus billStatus;

}
