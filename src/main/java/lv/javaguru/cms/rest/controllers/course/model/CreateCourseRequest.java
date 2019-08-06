package lv.javaguru.cms.rest.controllers.course.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lv.javaguru.cms.model.entities.enums.CourseType;
import lv.javaguru.cms.model.entities.enums.DayOfTheWeek;
import lv.javaguru.cms.model.entities.enums.Language;
import lv.javaguru.cms.rest.BaseRequest;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateCourseRequest extends BaseRequest {

    @NotNull
    private String title;

    @NotNull
    private Language language;

    @NotNull
    private String startDate;

    @NotNull
    private String endDate;

    @NotNull
    private CourseType courseType;

    @NotNull
    private String address;

    @NotNull
    private String lessonsTimeFrom;

    @NotNull
    private String lessonsTimeTo;

    @NotNull
    private DayOfTheWeek dayOfTheWeek;

    @NotNull
    private Integer fullPrice;

}
