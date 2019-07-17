package lv.javaguru.cms.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "payment_template")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PaymentTemplateEntity extends BaseEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "template_file_path", nullable = false)
    private String templateFilePath;

}
