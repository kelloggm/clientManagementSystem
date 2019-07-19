package lv.javaguru.cms.rest.controllers.course;

import lv.javaguru.cms.rest.controllers.course.model.paymenttemplate.GetCoursePaymentTemplateRequest;
import lv.javaguru.cms.rest.controllers.course.model.paymenttemplate.GetCoursePaymentTemplateResponse;
import lv.javaguru.cms.rest.controllers.course.model.paymenttemplate.UpdateCoursePaymentTemplateRequest;
import lv.javaguru.cms.rest.controllers.course.model.paymenttemplate.UpdateCoursePaymentTemplateResponse;
import lv.javaguru.cms.services.course.paymenttemplates.GetCoursePaymentTemplateService;
import lv.javaguru.cms.services.course.paymenttemplates.UpdateCoursePaymentTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Objects;

@RestController
public class CoursePaymentTemplateController {

    @Autowired private UpdateCoursePaymentTemplateService updateCoursePaymentTemplateService;
    @Autowired private GetCoursePaymentTemplateService getCoursePaymentTemplateService;

    @PutMapping(path = "/course/{courseId}/paymentTemplate", produces = "application/json")
    public UpdateCoursePaymentTemplateResponse updatePaymentTemplate(@PathVariable("courseId") Long courseId,
                                                                     @Valid @RequestBody UpdateCoursePaymentTemplateRequest request,
                                                                     Principal principal) {
        if (!Objects.equals(courseId, request.getCourseId())) {
            throw new IllegalArgumentException("courseId");
        }
        request.setSystemUserLogin(principal.getName());
        return updateCoursePaymentTemplateService.update(request);
    }

    @GetMapping(path = "/course/{courseId}/paymentTemplate/{paymentTemplateId}", produces = "application/json")
    public GetCoursePaymentTemplateResponse getPaymentTemplate(@PathVariable("courseId") Long courseId,
                                                               @PathVariable("paymentTemplateId") Long paymentTemplateId,
                                                               Principal principal) {
        GetCoursePaymentTemplateRequest request = GetCoursePaymentTemplateRequest.builder()
                .courseId(courseId)
                .paymentTemplateId(paymentTemplateId)
                .build();
        request.setSystemUserLogin(principal.getName());
        return getCoursePaymentTemplateService.update(request);
    }



    // DELETE Course Payment Template


}
