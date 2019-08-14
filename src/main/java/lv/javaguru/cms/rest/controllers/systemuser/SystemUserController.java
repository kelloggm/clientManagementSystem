package lv.javaguru.cms.rest.controllers.systemuser;

import lv.javaguru.cms.model.entities.SystemUserEntity;
import lv.javaguru.cms.rest.controllers.systemuser.model.CreateSystemUserRequest;
import lv.javaguru.cms.rest.controllers.systemuser.model.CreateSystemUserResponse;
import lv.javaguru.cms.rest.controllers.systemuser.model.DeleteSystemUserRequest;
import lv.javaguru.cms.rest.controllers.systemuser.model.DeleteSystemUserResponse;
import lv.javaguru.cms.rest.controllers.systemuser.model.GetSystemUserRequest;
import lv.javaguru.cms.rest.controllers.systemuser.model.GetSystemUserResponse;
import lv.javaguru.cms.rest.controllers.systemuser.model.LoginSystemUserRequest;
import lv.javaguru.cms.rest.controllers.systemuser.model.LoginSystemUserResponse;
import lv.javaguru.cms.rest.controllers.systemuser.model.UpdateSystemUserRequest;
import lv.javaguru.cms.rest.controllers.systemuser.model.UpdateSystemUserResponse;
import lv.javaguru.cms.rest.dto.SystemUserDTO;
import lv.javaguru.cms.services.systemuser.CreateSystemUserService;
import lv.javaguru.cms.services.systemuser.DeleteSystemUserService;
import lv.javaguru.cms.services.systemuser.GetSystemUserService;
import lv.javaguru.cms.services.systemuser.LoginSystemUserService;
import lv.javaguru.cms.services.systemuser.UpdateSystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class SystemUserController {

    @Autowired private LoginSystemUserService loginSystemUserService;
    @Autowired private GetSystemUserService getSystemUserService;
    @Autowired private CreateSystemUserService createSystemUserService;
    @Autowired private UpdateSystemUserService updateSystemUserService;
    @Autowired private DeleteSystemUserService deleteSystemUserService;

    @PostMapping(path = "/system_user/login", consumes = "application/json", produces = "application/json")
    public LoginSystemUserResponse login(Principal principal) {
        LoginSystemUserRequest request = LoginSystemUserRequest.builder().build();
        request.setSystemUserLogin(principal.getName());
        SystemUserDTO systemUser = loginSystemUserService.get(request);
        return LoginSystemUserResponse.builder().systemUser(systemUser).build();
    }

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

    @PutMapping(path = "/system_user/{systemUserId}", consumes = "application/json", produces = "application/json")
    public UpdateSystemUserResponse update(@PathVariable("systemUserId") Long systemUserId,
                                           @Valid @RequestBody UpdateSystemUserRequest request, Principal principal) {
        if (!Objects.equals(systemUserId, request.getSystemUserId())) {
            throw new IllegalArgumentException("systemUserId");
        }
        request.setSystemUserLogin(principal.getName());
        SystemUserDTO systemUser = updateSystemUserService.update(request);
        return UpdateSystemUserResponse.builder().systemUser(systemUser).build();
    }

    @DeleteMapping(path = "/system_user/{systemUserId}", produces = "application/json")
    public DeleteSystemUserResponse update(@PathVariable("systemUserId") Long systemUserId, Principal principal) {
        DeleteSystemUserRequest request = DeleteSystemUserRequest.builder().systemUserId(systemUserId).build();
        request.setSystemUserLogin(principal.getName());
        deleteSystemUserService.delete(request);
        return DeleteSystemUserResponse.builder().build();
    }

    // search

}
