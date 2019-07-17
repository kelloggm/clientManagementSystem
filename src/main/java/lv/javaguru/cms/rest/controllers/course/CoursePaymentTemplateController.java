package lv.javaguru.cms.rest.controllers.course;

import lv.javaguru.cms.rest.controllers.course.model.paymenttemplate.UpdateCoursePaymentTemplateRequest;
import lv.javaguru.cms.rest.controllers.course.model.paymenttemplate.UpdateCoursePaymentTemplateResponse;
import lv.javaguru.cms.services.course.UpdateCoursePaymentTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
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

    // GET Course Payment Template

    // DELETE Course Payment Template


}
