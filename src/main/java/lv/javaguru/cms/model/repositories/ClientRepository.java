package lv.javaguru.cms.model.repositories;

import lv.javaguru.cms.model.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClientRepository
        extends JpaRepository<ClientEntity, Long>,
                JpaSpecificationExecutor<ClientEntity> {

    default ClientEntity getById(Long clientId) {
        return findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("clientId"));
    }

    Optional<ClientEntity> findByFirstNameAndLastNameAndPhoneNumberAndEmail(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("phoneNumber") String phoneNumber,
            @Param("email") String email
    );

}
