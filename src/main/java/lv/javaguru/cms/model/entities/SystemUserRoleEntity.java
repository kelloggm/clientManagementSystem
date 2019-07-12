package lv.javaguru.cms.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "system_user_role")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SystemUserRoleEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "system_user_id", nullable = false)
    private SystemUserEntity systemUser;

    @Column(name = "system_user_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private SystemUserRole systemUserRole;

}
