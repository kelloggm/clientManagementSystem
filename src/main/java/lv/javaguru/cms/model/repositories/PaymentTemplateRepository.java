package lv.javaguru.cms.model.repositories;

import lv.javaguru.cms.model.entities.PaymentTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTemplateRepository extends JpaRepository<PaymentTemplateEntity, Long> {

    default PaymentTemplateEntity getById(Long paymentTemplateId) {
        return findById(paymentTemplateId)
                .orElseThrow(() -> new IllegalArgumentException("paymentTemplateId"));
    }

}
