package lv.javaguru.cms.services.course.bills;

import lv.javaguru.cms.model.entities.BillEntity;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.repositories.BillRepository;
import lv.javaguru.cms.rest.controllers.course.model.bill.UpdateBillRequest;
import lv.javaguru.cms.rest.dto.BillDTO;
import lv.javaguru.cms.rest.dto.converters.BillDtoConverter;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UpdateBillService {

    @Autowired private SystemUserRightsChecker systemUserRightsChecker;
    @Autowired private BillRepository repository;
    @Autowired private BillDtoConverter converter;

    public BillDTO update(UpdateBillRequest request) {
        systemUserRightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN, SystemUserRole.BILL_MANAGER);
        BillEntity bill = repository.getById(request.getBillId());
        bill.setStatus(request.getBillStatus());
        return converter.convert(bill);
    }

}
