package lv.javaguru.cms.services.company;

import lv.javaguru.cms.model.entities.CompanyEntity;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.repositories.CompanyRepository;
import lv.javaguru.cms.rest.controllers.company.model.CreateCompanyRequest;
import lv.javaguru.cms.rest.dto.CompanyDTO;
import lv.javaguru.cms.rest.dto.converters.CompanyDtoConverter;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CreateCompanyService {

    @Autowired private SystemUserRightsChecker systemUserRightsChecker;
    @Autowired private CompanyRepository companyRepository;
    @Autowired private CompanyDtoConverter companyEntityToDTOConverter;

    public CompanyDTO create(CreateCompanyRequest request) {
        systemUserRightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN);
        checkIfCompanyAlreadyExist(request);
        CompanyEntity company = buildCompanyEntity(request);
        company = companyRepository.save(company);
        return companyEntityToDTOConverter.convert(company);
    }

    private CompanyEntity buildCompanyEntity(CreateCompanyRequest request) {
        CompanyEntity company = CompanyEntity.builder()
                .title(request.getTitle())
                .registrationNumber(request.getRegistrationNumber())
                .legalAddress(request.getLegalAddress())
                .bankName(request.getBankName())
                .bankBicSwift(request.getBankBicSwift())
                .bankAccount(request.getBankAccount())
                .memberOfTheBoard(request.getMemberOfTheBoard())
                .pvnPayer(request.getPvnPayer())
                .build();
        company.setModifiedBy(request.getSystemUserLogin());
        return company;
    }

    private void checkIfCompanyAlreadyExist(CreateCompanyRequest request) {
        companyRepository.findByTitle(request.getTitle()).ifPresent(courseEntity -> {
            throw new IllegalArgumentException("Company already exist");
        });
    }

}
