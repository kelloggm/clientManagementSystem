package lv.javaguru.cms.rest.controllers.company;

import lv.javaguru.cms.rest.controllers.company.model.CreateCompanyRequest;
import lv.javaguru.cms.rest.controllers.company.model.CreateCompanyResponse;
import lv.javaguru.cms.rest.dto.CompanyDTO;
import lv.javaguru.cms.services.company.CreateCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
public class CompanyController {

    @Autowired private CreateCompanyService createCompanyService;

    @PostMapping(path = "/company", consumes = "application/json", produces = "application/json")
    public CreateCompanyResponse register(@Valid @RequestBody CreateCompanyRequest request, Principal principal) {
        request.setSystemUserLogin(principal.getName());
        CompanyDTO companyDTO = createCompanyService.create(request);
        return CreateCompanyResponse.builder().companyId(companyDTO.getId()).build();
    }

}
