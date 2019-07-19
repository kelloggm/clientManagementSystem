package lv.javaguru.cms.services.course.paymenttemplates;

import lv.javaguru.cms.model.entities.CourseEntity;
import lv.javaguru.cms.model.entities.PaymentTemplateEntity;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.repositories.CourseRepository;
import lv.javaguru.cms.model.repositories.PaymentTemplateRepository;
import lv.javaguru.cms.rest.controllers.course.model.paymenttemplate.UpdateCoursePaymentTemplateRequest;
import lv.javaguru.cms.rest.controllers.course.model.paymenttemplate.UpdateCoursePaymentTemplateResponse;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UpdateCoursePaymentTemplateService {

    @Autowired private SystemUserRightsChecker rightsChecker;
    @Autowired private CourseRepository courseRepository;
    @Autowired private PaymentTemplateRepository paymentTemplateRepository;

    public UpdateCoursePaymentTemplateResponse update(UpdateCoursePaymentTemplateRequest request) {
        rightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN, SystemUserRole.COURSE_MANAGER);
        CourseEntity course = getCourse(request);
        PaymentTemplateEntity paymentTemplate = getPaymentTemplate(request);

        course.setPaymentTemplate(paymentTemplate);
        course.setModifiedBy(request.getSystemUserLogin());

        return UpdateCoursePaymentTemplateResponse.builder().build();
    }

    private CourseEntity getCourse(UpdateCoursePaymentTemplateRequest request) {
        return courseRepository.getById(request.getCourseId());
    }

    private PaymentTemplateEntity getPaymentTemplate(UpdateCoursePaymentTemplateRequest request) {
        return paymentTemplateRepository.getById(request.getPaymentTemplateId());
    }

}
