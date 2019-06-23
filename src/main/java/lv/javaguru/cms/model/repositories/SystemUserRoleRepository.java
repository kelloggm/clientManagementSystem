package lv.javaguru.cms.model.repositories;

import lv.javaguru.cms.model.entities.SystemUserEntity;
import lv.javaguru.cms.model.entities.SystemUserRole;
import lv.javaguru.cms.model.entities.SystemUserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SystemUserRoleRepository extends JpaRepository<SystemUserRoleEntity, Long> {

    @Query("select systemUserRole.systemUserRole from SystemUserRoleEntity systemUserRole "
            + " left join SystemUserEntity as systemUser on systemUser.id = systemUserRole.systemUser "
            + " where systemUser.login = :login ")
    List<SystemUserRole> findAllBySystemUserLogin(@Param("login") String login);

    List<SystemUserRoleEntity> findAllBySystemUser(SystemUserEntity systemUser);

}
