package lv.javaguru.cms.rest.controllers.course.model.registration;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lv.javaguru.cms.rest.BaseResponse;

@Data
@Builder
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UpdateCourseRegistrationResponse extends BaseResponse {

}
