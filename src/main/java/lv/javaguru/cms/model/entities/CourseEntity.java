package lv.javaguru.cms.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lv.javaguru.cms.model.entities.enums.CourseType;
import lv.javaguru.cms.model.entities.enums.DayOfTheWeek;
import lv.javaguru.cms.model.entities.enums.Language;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "course")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CourseEntity extends BaseEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "language", nullable = false)
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "course_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseType courseType;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "lessons_time_from", nullable = false)
    private String lessonsTimeFrom;

    @Column(name = "lessons_time_to", nullable = false)
    private String lessonsTimeTo;

    @Column(name = "day_of_the_week", nullable = false)
    @Enumerated(EnumType.STRING)
    private DayOfTheWeek dayOfTheWeek;

    @Column(name = "full_price", nullable = false)
    private Integer fullPrice;

    @ManyToOne
    @JoinColumn(name = "payment_template_id")
    private PaymentTemplateEntity paymentTemplate;

}
