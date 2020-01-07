package lv.javaguru.cms.rest.controllers.course.model.participant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import lv.javaguru.cms.model.entities.enums.CourseParticipantStatus;
import lv.javaguru.cms.rest.BaseRequest;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UpdateCourseParticipantRequest extends BaseRequest {

    @NonNull
    private Long courseId;

    @NonNull
    private Long participantId;

    @NonNull
    private CourseParticipantStatus status;

}
