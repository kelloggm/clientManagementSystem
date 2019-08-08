package lv.javaguru.cms.rest.controllers.course;

import lv.javaguru.cms.rest.controllers.course.model.bill.CreateBillRequest;
import lv.javaguru.cms.rest.controllers.course.model.bill.CreateBillResponse;
import lv.javaguru.cms.rest.controllers.course.model.bill.GetBillRequest;
import lv.javaguru.cms.rest.controllers.course.model.bill.GetBillResponse;
import lv.javaguru.cms.rest.dto.BillDTO;
import lv.javaguru.cms.services.course.bills.CreateBillService;
import lv.javaguru.cms.services.course.bills.GetBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
public class BillController {

    @Autowired private CreateBillService createBillService;
    @Autowired private GetBillService getBillService;

    @PostMapping(path = "/bill", consumes = "application/json", produces = "application/json")
    public CreateBillResponse createBill(@Valid @RequestBody CreateBillRequest request,
                                         Principal principal) {
        request.setSystemUserLogin(principal.getName());
        return createBillService.create(request);
    }

    @GetMapping(path = "/bill/{billId}", produces = "application/json")
    public GetBillResponse get(@PathVariable("billId") Long billId, Principal principal) {
        GetBillRequest request = GetBillRequest.builder().billId(billId).build();
        request.setSystemUserLogin(principal.getName());
        BillDTO bill = getBillService.get(request);
        return GetBillResponse.builder().bill(bill).build();
    }

}