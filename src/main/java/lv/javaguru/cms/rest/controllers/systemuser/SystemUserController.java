package lv.javaguru.cms.rest.controllers.systemuser;

import lv.javaguru.cms.model.entities.SystemUserEntity;
import lv.javaguru.cms.rest.controllers.systemuser.model.CreateSystemUserRequest;
import lv.javaguru.cms.rest.controllers.systemuser.model.CreateSystemUserResponse;
import lv.javaguru.cms.rest.controllers.systemuser.model.GetSystemUserRequest;
import lv.javaguru.cms.rest.controllers.systemuser.model.GetSystemUserResponse;
import lv.javaguru.cms.rest.dto.SystemUserDTO;
import lv.javaguru.cms.services.systemuser.GetSystemUserService;
import lv.javaguru.cms.services.systemuser.CreateSystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
public class SystemUserController {

    @Autowired private CreateSystemUserService createSystemUserService;
    @Autowired private GetSystemUserService getSystemUserService;

    @PostMapping(path = "/system_user", consumes = "application/json", produces = "application/json")
    public CreateSystemUserResponse create(@Valid @RequestBody CreateSystemUserRequest request, Principal principal) {
        request.setSystemUserLogin(principal.getName());
        SystemUserEntity systemUser = createSystemUserService.register(request);
        return CreateSystemUserResponse.builder().systemUserId(systemUser.getId()).build();
    }

    @GetMapping(path = "/system_user/{systemUserId}", produces = "application/json")
    public GetSystemUserResponse get(@PathVariable("systemUserId") Long systemUserId, Principal principal) {
        GetSystemUserRequest request = GetSystemUserRequest.builder().systemUserId(systemUserId).build();
        request.setSystemUserLogin(principal.getName());
        SystemUserDTO systemUser = getSystemUserService.get(request);
        return GetSystemUserResponse.builder().systemUser(systemUser).build();
    }

}
