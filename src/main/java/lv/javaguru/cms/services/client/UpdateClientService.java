package lv.javaguru.cms.services.client;

import lv.javaguru.cms.model.entities.ClientEntity;
import lv.javaguru.cms.model.entities.SystemUserRole;
import lv.javaguru.cms.model.repositories.ClientRepository;
import lv.javaguru.cms.rest.controllers.client.model.UpdateClientRequest;
import lv.javaguru.cms.rest.dto.ClientDTO;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Component
public class UpdateClientService {

    @Autowired private SystemUserRightsChecker systemUserRightsChecker;
    @Autowired private ClientRepository clientRepository;
    @Autowired private ClientEntityToDTOConverter clientEntityToDTOConverter;

    @Transactional
    public ClientDTO update(UpdateClientRequest request) {
        systemUserRightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN, SystemUserRole.CLIENT_MANAGER);
        checkIfClientHavePhoneOrEmail(request);
        ClientEntity client = getClient(request);
        updateClientEntity(client, request);
        return clientEntityToDTOConverter.convert(client);
    }

    private void updateClientEntity(ClientEntity clientEntity, UpdateClientRequest request) {
        clientEntity.setFirstName(request.getFirstName());
        clientEntity.setLastName(request.getLastName());
        clientEntity.setPhoneNumber(request.getPhoneNumber());
        clientEntity.setEmail(request.getEmail());
        clientEntity.setPromoCode(request.getPromoCode());
        clientEntity.setPersonalCode(request.getPersonalCode());
        clientEntity.setComment(request.getComment());
        clientEntity.setSchoolkid(request.getSchoolkid() == null ? Boolean.FALSE : request.getSchoolkid());
        clientEntity.setStudent(request.getStudent() == null ? Boolean.FALSE : request.getStudent());
        clientEntity.setModifiedBy(request.getSystemUserLogin());
    }

    private void checkIfClientHavePhoneOrEmail(UpdateClientRequest request) {
        if (StringUtils.isEmpty(request.getPhoneNumber())
                && StringUtils.isEmpty(request.getEmail())) {
            throw new IllegalArgumentException("Phone or email must be present");
        }
    }

    private ClientEntity getClient(UpdateClientRequest request) {
        return clientRepository.findById(request.getClientId())
                                .orElseThrow(() -> new IllegalArgumentException("clientId"));
    }

}
