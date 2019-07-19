package lv.javaguru.cms.rest.controllers.course.model.paymenttemplate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lv.javaguru.cms.rest.BaseResponse;
import lv.javaguru.cms.rest.dto.PaymentTemplateDTO;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GetCoursePaymentTemplateResponse extends BaseResponse {

    private PaymentTemplateDTO paymentTemplate;

}
