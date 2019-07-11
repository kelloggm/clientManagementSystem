package lv.javaguru.cms.model.repositories;

import lv.javaguru.cms.model.entities.ClientEntity;
import lv.javaguru.cms.model.entities.CourseEntity;
import lv.javaguru.cms.model.entities.RegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface RegistrationRepository
        extends JpaRepository<RegistrationEntity, Long>,
        JpaSpecificationExecutor<RegistrationEntity> {

    Optional<RegistrationEntity> findByCourseAndClient(CourseEntity course, ClientEntity client);

}
