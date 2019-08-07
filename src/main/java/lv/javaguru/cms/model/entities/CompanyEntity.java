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
@Table(name = "company")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CompanyEntity extends BaseEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "registration_number", nullable = false)
    private String registrationNumber;

    @Column(name = "legal_address", nullable = false)
    private String legalAddress;

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "bank_account", nullable = false)
    private String bankAccount;

    @Column(name = "member_of_the_board", nullable = false)
    private String memberOfTheBoard;

    @Column(name = "pvn_payer", nullable = false)
    private boolean pvnPayer;

}
