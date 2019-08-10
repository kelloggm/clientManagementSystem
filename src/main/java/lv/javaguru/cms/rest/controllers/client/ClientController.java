package lv.javaguru.cms.rest.controllers.client;

import lv.javaguru.cms.rest.controllers.client.model.ClientRegistrationRequest;
import lv.javaguru.cms.rest.controllers.client.model.ClientRegistrationResponse;
import lv.javaguru.cms.rest.controllers.client.model.GetClientBillsRequest;
import lv.javaguru.cms.rest.controllers.client.model.GetClientBillsResponse;
import lv.javaguru.cms.rest.controllers.client.model.GetClientCoursesRequest;
import lv.javaguru.cms.rest.controllers.client.model.GetClientCoursesResponse;
import lv.javaguru.cms.rest.controllers.client.model.GetClientRequest;
import lv.javaguru.cms.rest.controllers.client.model.GetClientResponse;
import lv.javaguru.cms.rest.controllers.client.model.SearchClientsRequest;
import lv.javaguru.cms.rest.controllers.client.model.SearchClientsResponse;
import lv.javaguru.cms.rest.controllers.client.model.UpdateClientRequest;
import lv.javaguru.cms.rest.controllers.client.model.UpdateClientResponse;
import lv.javaguru.cms.rest.dto.ClientDTO;
import lv.javaguru.cms.services.client.ClientRegistrationService;
import lv.javaguru.cms.services.client.GetClientBillsService;
import lv.javaguru.cms.services.client.GetClientCoursesService;
import lv.javaguru.cms.services.client.GetClientService;
import lv.javaguru.cms.services.client.SearchClientsService;
import lv.javaguru.cms.services.client.UpdateClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Objects;

@RestController
public class ClientController {

    @Autowired private ClientRegistrationService registerClientService;
    @Autowired private GetClientService getClientService;
    @Autowired private GetClientCoursesService getClientCoursesService;
    @Autowired private GetClientBillsService getClientBillsService;
    @Autowired private UpdateClientService updateClientService;
    @Autowired private SearchClientsService searchClientsService;

    @PostMapping(path = "/client", consumes = "application/json", produces = "application/json")
    public ClientRegistrationResponse register(@Valid @RequestBody ClientRegistrationRequest request, Principal principal) {
        request.setSystemUserLogin(principal.getName());
        ClientDTO client = registerClientService.register(request);
        return ClientRegistrationResponse.builder().clientId(client.getId()).build();
    }

    @GetMapping(path = "/client/{clientId}", produces = "application/json")
    public GetClientResponse get(@PathVariable("clientId") Long clientId, Principal principal) {
        GetClientRequest request = GetClientRequest.builder().clientId(clientId).build();
        request.setSystemUserLogin(principal.getName());
        ClientDTO client = getClientService.get(request);
        return GetClientResponse.builder().client(client).build();
    }

    @GetMapping(path = "/client/{clientId}/courses", produces = "application/json")
    public GetClientCoursesResponse getCourses(@PathVariable("clientId") Long clientId, Principal principal) {
        GetClientCoursesRequest request = GetClientCoursesRequest.builder().clientId(clientId).build();
        request.setSystemUserLogin(principal.getName());
        return getClientCoursesService.get(request);
    }

    @GetMapping(path = "/client/{clientId}/bills", produces = "application/json")
    public GetClientBillsResponse getBills(@PathVariable("clientId") Long clientId, Principal principal) {
        GetClientBillsRequest request = GetClientBillsRequest.builder().clientId(clientId).build();
        request.setSystemUserLogin(principal.getName());
        return getClientBillsService.get(request);
    }

    @PutMapping(path = "/client/{clientId}", consumes = "application/json", produces = "application/json")
    public UpdateClientResponse update(@PathVariable("clientId") Long clientId,
                                       @Valid @RequestBody UpdateClientRequest request,
                                       Principal principal) {
        if (!Objects.equals(clientId, request.getClientId())) {
            throw new IllegalArgumentException("clientId");
        }
        request.setSystemUserLogin(principal.getName());
        ClientDTO client = updateClientService.update(request);
        return UpdateClientResponse.builder().client(client).build();
    }

    @PostMapping(path = "/client/search", consumes = "application/json", produces = "application/json")
    public SearchClientsResponse search(@Valid @RequestBody SearchClientsRequest request, Principal principal) {
        request.setSystemUserLogin(principal.getName());
        return searchClientsService.search(request);
    }

}
