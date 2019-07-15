package lv.javaguru.cms.rest.controllers.course.model.registration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lv.javaguru.cms.model.entities.enums.CourseRegistrationStatus;
import lv.javaguru.cms.rest.BaseRequest;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UpdateCourseRegistrationRequest extends BaseRequest {

    @NotNull
    private Long courseId;

    @NotNull
    private Long registrationId;

    @NotNull
    private CourseRegistrationStatus status;

}
