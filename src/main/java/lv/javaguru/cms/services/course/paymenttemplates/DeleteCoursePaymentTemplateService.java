package lv.javaguru.cms.services.course.paymenttemplates;

import lv.javaguru.cms.model.entities.CourseEntity;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.repositories.CourseRepository;
import lv.javaguru.cms.rest.controllers.course.model.paymenttemplate.DeleteCoursePaymentTemplateRequest;
import lv.javaguru.cms.rest.controllers.course.model.paymenttemplate.DeleteCoursePaymentTemplateResponse;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DeleteCoursePaymentTemplateService {

    @Autowired private SystemUserRightsChecker rightsChecker;
    @Autowired private CourseRepository courseRepository;

    public DeleteCoursePaymentTemplateResponse delete(DeleteCoursePaymentTemplateRequest request) {
        rightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN, SystemUserRole.COURSE_MANAGER);
        CourseEntity course = getCourse(request);

        course.setPaymentTemplate(null);
        course.setModifiedBy(request.getSystemUserLogin());

        return DeleteCoursePaymentTemplateResponse.builder().build();
    }

    private CourseEntity getCourse(DeleteCoursePaymentTemplateRequest request) {
        return courseRepository.getById(request.getCourseId());
    }

}
