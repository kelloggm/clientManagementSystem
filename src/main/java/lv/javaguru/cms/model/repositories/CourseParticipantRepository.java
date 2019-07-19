package lv.javaguru.cms.model.repositories;

import lv.javaguru.cms.model.entities.ClientEntity;
import lv.javaguru.cms.model.entities.CourseEntity;
import lv.javaguru.cms.model.entities.CourseParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface CourseParticipantRepository
        extends JpaRepository<CourseParticipantEntity, Long>,
        JpaSpecificationExecutor<CourseParticipantEntity> {

    Optional<CourseParticipantEntity> findByCourseAndClient(CourseEntity course, ClientEntity client);

    List<CourseParticipantEntity> findByCourse(CourseEntity course);

    default CourseParticipantEntity getById(Long participantId) {
        return findById(participantId)
                .orElseThrow(() -> new IllegalArgumentException("participantId"));
    }

}
