package lv.javaguru.cms.services.client;

import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.repositories.ClientRepository;
import lv.javaguru.cms.rest.controllers.client.model.GetClientRequest;
import lv.javaguru.cms.rest.dto.ClientDTO;
import lv.javaguru.cms.rest.dto.converters.ClientDtoConverter;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class GetClientService {

    @Autowired private SystemUserRightsChecker rightsChecker;
    @Autowired private ClientRepository repository;
    @Autowired private ClientDtoConverter converter;

    public ClientDTO get(GetClientRequest request) {
        rightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN, SystemUserRole.CLIENT_MANAGER);
        return repository.findById(request.getClientId())
                .map(clientEntity -> converter.convert(clientEntity))
                .orElseThrow(() -> new IllegalArgumentException("clientId"));
    }

}
