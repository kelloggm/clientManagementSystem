package lv.javaguru.cms.model.repositories;

import lv.javaguru.cms.model.entities.BillEntity;
import lv.javaguru.cms.model.entities.CourseEntity;
import lv.javaguru.cms.model.entities.CourseParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BillRepository
        extends JpaRepository<BillEntity, Long>,
        JpaSpecificationExecutor<BillEntity> {

    Optional<BillEntity> findByCourseAndCourseParticipantAndBillPart(CourseEntity courseEntity,
                                                                     CourseParticipantEntity courseParticipant,
                                                                     Integer billPart);


    Optional<BillEntity> findFirstByBillPrefixOrderByBillNumberDesc(String billPrefix);

}
