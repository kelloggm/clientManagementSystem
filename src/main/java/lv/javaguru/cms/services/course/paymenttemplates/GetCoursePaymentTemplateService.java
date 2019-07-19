package lv.javaguru.cms.services.course.paymenttemplates;

import lv.javaguru.cms.model.entities.CourseEntity;
import lv.javaguru.cms.model.entities.PaymentTemplateEntity;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.repositories.CourseRepository;
import lv.javaguru.cms.rest.controllers.course.model.paymenttemplate.GetCoursePaymentTemplateRequest;
import lv.javaguru.cms.rest.controllers.course.model.paymenttemplate.GetCoursePaymentTemplateResponse;
import lv.javaguru.cms.rest.dto.converters.PaymentTemplateDtoConverter;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
@Transactional
public class GetCoursePaymentTemplateService {

    @Autowired private SystemUserRightsChecker rightsChecker;
    @Autowired private CourseRepository courseRepository;
    @Autowired private PaymentTemplateDtoConverter converter;

    public GetCoursePaymentTemplateResponse update(GetCoursePaymentTemplateRequest request) {
        rightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN, SystemUserRole.COURSE_MANAGER);
        CourseEntity course = getCourse(request);
        PaymentTemplateEntity paymentTemplate = course.getPaymentTemplate();
        if (!Objects.equals(paymentTemplate.getId(), request.getPaymentTemplateId())) {
            throw new IllegalArgumentException("paymentTemplateId");
        }
        return GetCoursePaymentTemplateResponse.builder()
                .paymentTemplate(converter.convert(paymentTemplate))
                .build();
    }

    private CourseEntity getCourse(GetCoursePaymentTemplateRequest request) {
        return courseRepository.getById(request.getCourseId());
    }

}
