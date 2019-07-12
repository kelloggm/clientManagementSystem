package lv.javaguru.cms.model.repositories;

import lv.javaguru.cms.model.entities.CourseEntity;
import lv.javaguru.cms.model.entities.enums.CourseType;
import lv.javaguru.cms.model.entities.enums.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface CourseRepository
        extends JpaRepository<CourseEntity, Long>,
                JpaSpecificationExecutor<CourseEntity> {

    Optional<CourseEntity> findByTitleAndLanguageAndAddressAndCourseTypeAndStartDateAndEndDate(
            @Param("title") String title,
            @Param("language") Language language,
            @Param("address") String address,
            @Param("courseType") CourseType courseType,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

}
