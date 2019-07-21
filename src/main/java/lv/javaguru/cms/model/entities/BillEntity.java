package lv.javaguru.cms.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "bill")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BillEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    @ManyToOne
    @JoinColumn(name = "course_participant_id")
    private CourseParticipantEntity courseParticipant;

    @ManyToOne
    @JoinColumn(name = "payment_template_id")
    private PaymentTemplateEntity paymentTemplate;

    @Column(name = "bill_prefix", nullable = false)
    private String billPrefix;

    @Column(name = "bill_number", nullable = false)
    private Integer billNumber;

    @Column(name = "bill_part", nullable = false)
    private Integer billPart;

    @Column(name = "bill_file_path", nullable = false)
    private String billFilePath;

    @Column(name = "pay_to", nullable = false)
    private LocalDateTime payTo;

    @Column(name = "bill_sum", nullable = false)
    private Integer billSum;

}
