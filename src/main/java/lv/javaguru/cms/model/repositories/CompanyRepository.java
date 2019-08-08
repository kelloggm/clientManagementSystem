package lv.javaguru.cms.model.repositories;

import lv.javaguru.cms.model.entities.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long>,
                                           JpaSpecificationExecutor<CompanyEntity> {

    default CompanyEntity getById(Long companyId) {
        return findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("companyId"));
    }

    Optional<CompanyEntity> findByTitle(String title);

}
