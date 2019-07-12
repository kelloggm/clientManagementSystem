package lv.javaguru.cms.model.repositories;

import lv.javaguru.cms.model.entities.ClientEntity;
import lv.javaguru.cms.model.entities.CourseEntity;
import lv.javaguru.cms.model.entities.CourseRegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface CourseRegistrationRepository
        extends JpaRepository<CourseRegistrationEntity, Long>,
        JpaSpecificationExecutor<CourseRegistrationEntity> {

    Optional<CourseRegistrationEntity> findByCourseAndClient(CourseEntity course, ClientEntity client);

    List<CourseRegistrationEntity> findByCourse(CourseEntity course);

}
