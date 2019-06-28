package lv.javaguru.cms.rest.controllers.course.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lv.javaguru.cms.model.entities.CourseType;
import lv.javaguru.cms.model.entities.DayOfTheWeek;
import lv.javaguru.cms.model.entities.Language;
import lv.javaguru.cms.rest.BaseRequest;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RegisterCourseRequest extends BaseRequest {

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
