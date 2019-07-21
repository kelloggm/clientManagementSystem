package lv.javaguru.cms.rest.controllers.course;

import lv.javaguru.cms.rest.controllers.course.model.bill.CreateBillRequest;
import lv.javaguru.cms.rest.controllers.course.model.bill.CreateBillResponse;
import lv.javaguru.cms.services.course.bills.CreateCourseParticipantBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Objects;

@RestController
public class CourseBillController {

    @Autowired private CreateCourseParticipantBillService createCourseParticipantBillService;

    @PostMapping(path = "/course/{courseId}/bill", consumes = "application/json", produces = "application/json")
    public CreateBillResponse createBill(@Valid @RequestBody CreateBillRequest request,
                                         @PathVariable("courseId") Long courseId,
                                         Principal principal) {
        if (!Objects.equals(courseId, request.getCourseId())) {
            throw new IllegalArgumentException("courseId");
        }
        request.setSystemUserLogin(principal.getName());
        return createCourseParticipantBillService.create(request);
    }

}
