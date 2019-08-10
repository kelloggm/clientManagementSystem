package lv.javaguru.cms.rest.controllers.course;

import lv.javaguru.cms.rest.controllers.course.model.bill.CreateBillRequest;
import lv.javaguru.cms.rest.controllers.course.model.bill.CreateBillResponse;
import lv.javaguru.cms.rest.controllers.course.model.bill.DeleteBillRequest;
import lv.javaguru.cms.rest.controllers.course.model.bill.DeleteBillResponse;
import lv.javaguru.cms.rest.controllers.course.model.bill.GetBillRequest;
import lv.javaguru.cms.rest.controllers.course.model.bill.GetBillResponse;
import lv.javaguru.cms.rest.controllers.course.model.bill.SearchBillsRequest;
import lv.javaguru.cms.rest.controllers.course.model.bill.SearchBillsResponse;
import lv.javaguru.cms.rest.controllers.course.model.bill.UpdateBillRequest;
import lv.javaguru.cms.rest.controllers.course.model.bill.UpdateBillResponse;
import lv.javaguru.cms.rest.dto.BillDTO;
import lv.javaguru.cms.services.course.bills.CreateBillService;
import lv.javaguru.cms.services.course.bills.DeleteBillService;
import lv.javaguru.cms.services.course.bills.GetBillService;
import lv.javaguru.cms.services.course.bills.SearchBillsService;
import lv.javaguru.cms.services.course.bills.UpdateBillService;
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
public class BillController {

    @Autowired private CreateBillService createBillService;
    @Autowired private GetBillService getBillService;
    @Autowired private UpdateBillService updateBillService;
    @Autowired private DeleteBillService deleteBillService;
    @Autowired private SearchBillsService searchBillsService;

    @PostMapping(path = "/bill", consumes = "application/json", produces = "application/json")
    public CreateBillResponse createBill(@Valid @RequestBody CreateBillRequest request,
                                         Principal principal) {
        request.setSystemUserLogin(principal.getName());
        BillDTO bill = createBillService.create(request);
        return CreateBillResponse.builder().billId(bill.getId()).build();
    }

    @GetMapping(path = "/bill/{billId}", produces = "application/json")
    public GetBillResponse get(@PathVariable("billId") Long billId, Principal principal) {
        GetBillRequest request = GetBillRequest.builder().billId(billId).build();
        request.setSystemUserLogin(principal.getName());
        BillDTO bill = getBillService.get(request);
        return GetBillResponse.builder().bill(bill).build();
    }

    @PutMapping(path = "/bill/{billId}", consumes = "application/json", produces = "application/json")
    public UpdateBillResponse update(@PathVariable("billId") Long billId,
                                     @Valid @RequestBody UpdateBillRequest request,
                                     Principal principal) {
        if (!Objects.equals(billId, request.getBillId())) {
            throw new IllegalArgumentException("billId");
        }
        request.setSystemUserLogin(principal.getName());
        BillDTO bill = updateBillService.update(request);
        return UpdateBillResponse.builder().bill(bill).build();
    }

    @DeleteMapping(path = "/bill/{billId}", consumes = "application/json", produces = "application/json")
    public DeleteBillResponse delete(@PathVariable("billId") Long billId,
                                     @Valid @RequestBody DeleteBillRequest request,
                                     Principal principal) {
        if (!Objects.equals(billId, request.getBillId())) {
            throw new IllegalArgumentException("billId");
        }
        request.setSystemUserLogin(principal.getName());
        deleteBillService.delete(request);
        return DeleteBillResponse.builder().build();
    }

    @PostMapping(path = "/bill/search", consumes = "application/json", produces = "application/json")
    public SearchBillsResponse search(@Valid @RequestBody SearchBillsRequest request, Principal principal) {
        request.setSystemUserLogin(principal.getName());
        return searchBillsService.search(request);
    }

}
