package lv.javaguru.cms.rest.controllers.course;

import lv.javaguru.cms.rest.controllers.course.model.paymenttemplate.DeleteCoursePaymentTemplateRequest;
import lv.javaguru.cms.rest.controllers.course.model.paymenttemplate.DeleteCoursePaymentTemplateResponse;
import lv.javaguru.cms.rest.controllers.course.model.paymenttemplate.GetCoursePaymentTemplateRequest;
import lv.javaguru.cms.rest.controllers.course.model.paymenttemplate.GetCoursePaymentTemplateResponse;
import lv.javaguru.cms.rest.controllers.course.model.paymenttemplate.UpdateCoursePaymentTemplateRequest;
import lv.javaguru.cms.rest.controllers.course.model.paymenttemplate.UpdateCoursePaymentTemplateResponse;
import lv.javaguru.cms.services.course.paymenttemplates.DeleteCoursePaymentTemplateService;
import lv.javaguru.cms.services.course.paymenttemplates.GetCoursePaymentTemplateService;
import lv.javaguru.cms.services.course.paymenttemplates.UpdateCoursePaymentTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    @Autowired private DeleteCoursePaymentTemplateService deleteCoursePaymentTemplateService;

    @PutMapping(path = "/course/{courseId}/paymentTemplate", produces = "application/json")
    public UpdateCoursePaymentTemplateResponse update(@PathVariable("courseId") Long courseId,
                                                      @Valid @RequestBody UpdateCoursePaymentTemplateRequest request,
                                                      Principal principal) {
        if (!Objects.equals(courseId, request.getCourseId())) {
            throw new IllegalArgumentException("courseId");
        }
        request.setSystemUserLogin(principal.getName());
        return updateCoursePaymentTemplateService.update(request);
    }

    @DeleteMapping(path = "/course/{courseId}/paymentTemplate", produces = "application/json")
    public DeleteCoursePaymentTemplateResponse delete(@PathVariable("courseId") Long courseId,
                                                      Principal principal) {
        DeleteCoursePaymentTemplateRequest request = DeleteCoursePaymentTemplateRequest.builder()
                .courseId(courseId)
                .build();
        request.setSystemUserLogin(principal.getName());
        return deleteCoursePaymentTemplateService.delete(request);
    }

    @GetMapping(path = "/course/{courseId}/paymentTemplate/{paymentTemplateId}", produces = "application/json")
    public GetCoursePaymentTemplateResponse get(@PathVariable("courseId") Long courseId,
                                                @PathVariable("paymentTemplateId") Long paymentTemplateId,
                                                Principal principal) {
        GetCoursePaymentTemplateRequest request = GetCoursePaymentTemplateRequest.builder()
                .courseId(courseId)
                .paymentTemplateId(paymentTemplateId)
                .build();
        request.setSystemUserLogin(principal.getName());
        return getCoursePaymentTemplateService.update(request);
    }

}
