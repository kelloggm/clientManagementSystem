package lv.javaguru.cms.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lv.javaguru.cms.model.entities.enums.CourseType;
import lv.javaguru.cms.model.entities.enums.DayOfTheWeek;
import lv.javaguru.cms.model.entities.enums.Language;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CourseDTO extends BaseDTO {

    private String title;
    private Language language;
    private String startDate;
    private String endDate;
    private CourseType courseType;
    private String address;
    private String lessonsTimeFrom;
    private String lessonsTimeTo;
    private DayOfTheWeek dayOfTheWeek;
    private Integer fullPrice;
    private PaymentTemplateDTO paymentTemplate;
    private String billPrefix;

}
