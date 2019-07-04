package lv.javaguru.cms.model.repositories;

import lv.javaguru.cms.model.entities.CourseRegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CourseRegistrationRepository
        extends JpaRepository<CourseRegistrationEntity, Long>,
        JpaSpecificationExecutor<CourseRegistrationEntity> {

}
