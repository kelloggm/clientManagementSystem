package lv.javaguru.cms.services.course.bills;

import lv.javaguru.cms.model.entities.BillEntity;
import lv.javaguru.cms.model.entities.enums.BillStatus;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.repositories.BillRepository;
import lv.javaguru.cms.rest.controllers.course.model.bill.DeleteBillRequest;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DeleteBillService {

    @Autowired private SystemUserRightsChecker systemUserRightsChecker;
    @Autowired private BillRepository repository;

    public void delete(DeleteBillRequest request) {
        systemUserRightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN, SystemUserRole.BILL_MANAGER);
        BillEntity bill = repository.getById(request.getBillId());

        if (BillStatus.PAYED == bill.getStatus()) {
            throw new IllegalArgumentException("Bill already payed");
        }

        repository.delete(bill);
    }

}
