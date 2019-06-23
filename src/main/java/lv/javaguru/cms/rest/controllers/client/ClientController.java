package lv.javaguru.cms.rest.controllers.client;

import lv.javaguru.cms.rest.controllers.client.model.GetClientRequest;
import lv.javaguru.cms.rest.controllers.client.model.GetClientResponse;
import lv.javaguru.cms.rest.controllers.client.model.RegisterClientRequest;
import lv.javaguru.cms.rest.controllers.client.model.RegisterClientResponse;
import lv.javaguru.cms.rest.dto.ClientDTO;
import lv.javaguru.cms.services.client.GetClientService;
import lv.javaguru.cms.services.client.RegisterClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
public class ClientController {

    @Autowired private RegisterClientService registerClientService;
    @Autowired private GetClientService getClientService;

    @PostMapping(path = "/client", consumes = "application/json", produces = "application/json")
    public RegisterClientResponse register(@Valid @RequestBody RegisterClientRequest request,
                                           Principal principal) {
        request.setSystemUserLogin(principal.getName());
        ClientDTO client = registerClientService.register(request);
        return RegisterClientResponse.builder().clientId(client.getId()).build();
    }

    @GetMapping(path = "/client/{clientId}", produces = "application/json")
    public GetClientResponse get(@PathVariable("clientId") Long clientId, Principal principal) {
        GetClientRequest request = GetClientRequest.builder().clientId(clientId).build();
        request.setSystemUserLogin(principal.getName());
        ClientDTO client = getClientService.get(request);
        return GetClientResponse.builder().client(client).build();
    }

}
