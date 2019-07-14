package lv.javaguru.cms.services.client;

import lv.javaguru.cms.model.entities.ClientEntity;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.repositories.ClientRepository;
import lv.javaguru.cms.rest.controllers.client.model.ClientRegistrationRequest;
import lv.javaguru.cms.rest.dto.ClientDTO;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Component
@Transactional
public class ClientRegistrationService {

    @Autowired private SystemUserRightsChecker systemUserRightsChecker;
    @Autowired private ClientRepository clientRepository;
    @Autowired private ClientEntityToDTOConverter clientEntityToDTOConverter;

    public ClientDTO register(ClientRegistrationRequest request) {
        systemUserRightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN, SystemUserRole.CLIENT_MANAGER);
        checkIfClientHavePhoneOrEmail(request);
        checkIfClientAlreadyExist(request);
        ClientEntity client = buildClientEntity(request);
        client = clientRepository.save(client);
        return clientEntityToDTOConverter.convert(client);
    }

    private ClientEntity buildClientEntity(ClientRegistrationRequest request) {
        ClientEntity client = ClientEntity.builder()
                                          .firstName(request.getFirstName())
                                          .lastName(request.getLastName())
                                          .phoneNumber(request.getPhoneNumber())
                                          .email(request.getEmail())
                                          .promoCode(request.getPromoCode())
                                          .personalCode(request.getPersonalCode())
                                          .comment(request.getComment())
                                          .schoolkid(request.getSchoolkid() == null ? Boolean.FALSE : request.getSchoolkid())
                                          .student(request.getStudent() == null ? Boolean.FALSE : request.getStudent())
                                          .build();
        client.setModifiedBy(request.getSystemUserLogin());
        return client;
    }

    private void checkIfClientHavePhoneOrEmail(ClientRegistrationRequest request) {
        if (StringUtils.isEmpty(request.getPhoneNumber())
            && StringUtils.isEmpty(request.getEmail())) {
            throw new IllegalArgumentException("Phone or email must be present");
        }
    }

    private void checkIfClientAlreadyExist(ClientRegistrationRequest request) {
        clientRepository.findByFirstNameAndLastNameAndPhoneNumberAndEmail(
                request.getFirstName(), request.getLastName(), request.getPhoneNumber(), request.getEmail()
        ).ifPresent(clientEntity -> {
            throw new IllegalArgumentException("Client with same [firstName, lastName, phoneNumber, email] already exist");
        });
    }

}
