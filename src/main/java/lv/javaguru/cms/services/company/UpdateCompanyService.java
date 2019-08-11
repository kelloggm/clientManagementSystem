package lv.javaguru.cms.services.company;

import lv.javaguru.cms.model.entities.CompanyEntity;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.repositories.CompanyRepository;
import lv.javaguru.cms.rest.controllers.company.model.UpdateCompanyRequest;
import lv.javaguru.cms.rest.dto.CompanyDTO;
import lv.javaguru.cms.rest.dto.converters.CompanyDtoConverter;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UpdateCompanyService {

    @Autowired private SystemUserRightsChecker systemUserRightsChecker;
    @Autowired private CompanyRepository repository;
    @Autowired private CompanyDtoConverter converter;

    public CompanyDTO update(UpdateCompanyRequest request) {
        systemUserRightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN);
        CompanyEntity company = repository.getById(request.getCompanyId());
        updateCompany(company, request);
        return converter.convert(company);
    }

    private void updateCompany(CompanyEntity company, UpdateCompanyRequest request) {
        company.setTitle(request.getTitle());
        company.setRegistrationNumber(request.getRegistrationNumber());
        company.setLegalAddress(request.getLegalAddress());
        company.setBankName(request.getBankName());
        company.setBankBicSwift(request.getBankBicSwift());
        company.setBankAccount(request.getBankAccount());
        company.setMemberOfTheBoard(request.getMemberOfTheBoard());
        company.setPvnPayer(request.getPvnPayer());
        company.setModifiedBy(request.getSystemUserLogin());
    }

}
