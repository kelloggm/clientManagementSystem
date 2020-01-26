package lv.javaguru.cms.rest.controllers.company;

import lv.javaguru.cms.rest.controllers.company.model.CreateCompanyRequest;
import lv.javaguru.cms.rest.controllers.company.model.CreateCompanyResponse;
import lv.javaguru.cms.rest.controllers.company.model.GetCompanyRequest;
import lv.javaguru.cms.rest.controllers.company.model.GetCompanyResponse;
import lv.javaguru.cms.rest.controllers.company.model.SearchCompaniesRequest;
import lv.javaguru.cms.rest.controllers.company.model.SearchCompaniesResponse;
import lv.javaguru.cms.rest.controllers.company.model.UpdateCompanyRequest;
import lv.javaguru.cms.rest.controllers.company.model.UpdateCompanyResponse;
import lv.javaguru.cms.rest.dto.CompanyDTO;
import lv.javaguru.cms.services.company.CreateCompanyService;
import lv.javaguru.cms.services.company.GetCompanyService;
import lv.javaguru.cms.services.company.SearchCompaniesService;
import lv.javaguru.cms.services.company.UpdateCompanyService;
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
public class CompanyController {

    @Autowired private CreateCompanyService createCompanyService;
    @Autowired private GetCompanyService getCompanyService;
    @Autowired private UpdateCompanyService updateCompanyService;
    @Autowired private SearchCompaniesService searchCompaniesService;

    @PostMapping(path = "/company", consumes = "application/json", produces = "application/json")
    public CreateCompanyResponse register(@RequestBody @Valid CreateCompanyRequest request, Principal principal) {
        request.setSystemUserLogin(principal.getName());
        CompanyDTO companyDTO = createCompanyService.create(request);
        return CreateCompanyResponse.builder().companyId(companyDTO.getId()).build();
    }

    @GetMapping(path = "/company/{companyId}", produces = "application/json")
    public GetCompanyResponse get(@PathVariable("companyId") Long companyId, Principal principal) {
        GetCompanyRequest request = GetCompanyRequest.builder().companyId(companyId).build();
        request.setSystemUserLogin(principal.getName());
        CompanyDTO company = getCompanyService.get(request);
        return GetCompanyResponse.builder().company(company).build();
    }

    @PutMapping(path = "/company/{companyId}", consumes = "application/json", produces = "application/json")
    public UpdateCompanyResponse update(@PathVariable("companyId") Long companyId,
                                        @RequestBody @Valid UpdateCompanyRequest request,
                                        Principal principal) {
        if (!Objects.equals(companyId, request.getCompanyId())) {
            throw new IllegalArgumentException("companyId");
        }
        request.setSystemUserLogin(principal.getName());
        CompanyDTO company = updateCompanyService.update(request);
        return UpdateCompanyResponse.builder().company(company).build();
    }

    @PostMapping(path = "/company/search", consumes = "application/json", produces = "application/json")
    public SearchCompaniesResponse search(@RequestBody @Valid SearchCompaniesRequest request, Principal principal) {
        request.setSystemUserLogin(principal.getName());
        return searchCompaniesService.search(request);
    }

}
