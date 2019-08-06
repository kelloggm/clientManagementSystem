package lv.javaguru.cms.services.course.bills;

import lv.javaguru.cms.model.entities.CourseEntity;
import lv.javaguru.cms.model.entities.CourseParticipantEntity;
import lv.javaguru.cms.model.entities.PaymentTemplateEntity;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.repositories.BillRepository;
import lv.javaguru.cms.model.repositories.CourseParticipantRepository;
import lv.javaguru.cms.model.repositories.CourseRepository;
import lv.javaguru.cms.rest.controllers.course.model.bill.CreateBillRequest;
import lv.javaguru.cms.rest.controllers.course.model.bill.CreateBillResponse;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CreateCourseParticipantBillService {

    @Autowired private SystemUserRightsChecker rightsChecker;
    @Autowired private CourseRepository courseRepository;
    @Autowired private CourseParticipantRepository courseParticipantRepository;
    @Autowired private BillRepository billRepository;

    public CreateBillResponse create(CreateBillRequest request) {
        rightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN, SystemUserRole.CLIENT_MANAGER);
        CourseEntity course = getCourse(request);
        CourseParticipantEntity courseParticipant = getCourseParticipant(request);

        if (!course.equals(courseParticipant.getCourse())) {
            throw new IllegalArgumentException("courseId");
        }

        PaymentTemplateEntity paymentTemplate = course.getPaymentTemplate();
        if (paymentTemplate == null) {
            throw new IllegalArgumentException("Payment Template not specified for course with id = " + course.getId());
        }

        billRepository.findByCourseAndCourseParticipantAndBillPart(
                course, courseParticipant, request.getBillPart()
        ).ifPresent(bill -> {
            // regenerate bill
        });

        // Calculate bill number
        //Optional<BillEntity> lastBillWithSamePrefix = billRepository.findFirstByBillPrefixOrderByBillNumberDesc(billPrefix);
        //int billNumber = lastBillWithSamePrefix.map(billEntity -> billEntity.getBillNumber() + 1).orElse(1);

        // Create bill word document using poi


        // Create BillEntity


        return CreateBillResponse.builder()
                //.billId()
                .build();
    }

    private CourseEntity getCourse(CreateBillRequest request) {
        return courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("courseId"));
    }

    private CourseParticipantEntity getCourseParticipant(CreateBillRequest request) {
        return courseParticipantRepository.findById(request.getParticipantId())
                .orElseThrow(() -> new IllegalArgumentException("participantId"));
    }

}
