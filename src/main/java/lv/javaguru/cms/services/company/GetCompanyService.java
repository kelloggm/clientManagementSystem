package lv.javaguru.cms.services.company;

import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.repositories.CompanyRepository;
import lv.javaguru.cms.rest.controllers.company.model.GetCompanyRequest;
import lv.javaguru.cms.rest.dto.CompanyDTO;
import lv.javaguru.cms.rest.dto.converters.CompanyDtoConverter;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class GetCompanyService {

    @Autowired private SystemUserRightsChecker rightsChecker;
    @Autowired private CompanyRepository repository;
    @Autowired private CompanyDtoConverter converter;

    public CompanyDTO get(GetCompanyRequest request) {
        rightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN);
        return repository.findById(request.getCompanyId())
                .map(companyEntity -> converter.convert(companyEntity))
                .orElseThrow(() -> new IllegalArgumentException("companyId"));
    }

}
