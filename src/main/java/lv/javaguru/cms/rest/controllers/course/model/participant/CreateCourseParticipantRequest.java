package lv.javaguru.cms.rest.controllers.course.model.participant;

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
public class CreateCourseParticipantRequest extends BaseRequest {

    @NonNull
    private Long courseId;

    @NonNull
    private Long clientId;

    @NonNull
    private Integer billCount;

    @NonNull
    private Integer oneBillAmount;

}
