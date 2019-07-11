package lv.javaguru.cms.rest.controllers.registration;

import lv.javaguru.cms.rest.controllers.registration.model.RegistrationRequest;
import lv.javaguru.cms.rest.controllers.registration.model.RegistrationResponse;
import lv.javaguru.cms.services.registration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
public class RegistrationController {

    @Autowired private RegistrationService registrationService;

    @PostMapping(path = "/registration", produces = "application/json")
    public RegistrationResponse registerClient(@Valid @RequestBody RegistrationRequest request,
                                               Principal principal) {
        request.setSystemUserLogin(principal.getName());
        return registrationService.register(request);
    }

}
