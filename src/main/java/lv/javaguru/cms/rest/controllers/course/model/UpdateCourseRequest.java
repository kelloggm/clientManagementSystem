package lv.javaguru.cms.rest.controllers.course.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import lv.javaguru.cms.model.entities.enums.CourseType;
import lv.javaguru.cms.model.entities.enums.DayOfTheWeek;
import lv.javaguru.cms.model.entities.enums.Language;
import lv.javaguru.cms.rest.BaseRequest;


@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UpdateCourseRequest extends BaseRequest {

    @NonNull
    private Long courseId;

    @NonNull
    private String title;

    @NonNull
    private Language language;

    @NonNull
    private String startDate;

    @NonNull
    private String endDate;

    @NonNull
    private CourseType courseType;

    @NonNull
    private String address;

    @NonNull
    private String lessonsTimeFrom;

    @NonNull
    private String lessonsTimeTo;

    @NonNull
    private DayOfTheWeek dayOfTheWeek;

    @NonNull
    private Integer fullPrice;

}
