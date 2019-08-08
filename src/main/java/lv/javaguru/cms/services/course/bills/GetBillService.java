package lv.javaguru.cms.services.course.bills;

import lv.javaguru.cms.model.entities.BillEntity;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.repositories.BillRepository;
import lv.javaguru.cms.rest.controllers.course.model.bill.GetBillRequest;
import lv.javaguru.cms.rest.dto.BillDTO;
import lv.javaguru.cms.rest.dto.converters.BillDtoConverter;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class GetBillService {

    @Autowired private SystemUserRightsChecker rightsChecker;
    @Autowired private BillRepository repository;
    @Autowired private BillDtoConverter converter;

    public BillDTO get(GetBillRequest request) {
        rightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN, SystemUserRole.BILL_MANAGER);
        BillEntity bill = repository.getById(request.getBillId());
        return converter.convert(bill);
    }

}
