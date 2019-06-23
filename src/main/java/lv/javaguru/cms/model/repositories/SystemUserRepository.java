package lv.javaguru.cms.model.repositories;

import lv.javaguru.cms.model.entities.SystemUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SystemUserRepository extends JpaRepository<SystemUserEntity, Long> {

    Optional<SystemUserEntity> findByLogin(String login);

    default SystemUserEntity getByLogin(String login) {
        return findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException(
                        "unable to find SystemUserEntity by login = " + login));
    }


}
