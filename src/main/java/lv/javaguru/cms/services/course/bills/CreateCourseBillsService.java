package lv.javaguru.cms.services.course.bills;

import lv.javaguru.cms.model.entities.CompanyEntity;
import lv.javaguru.cms.model.entities.CourseEntity;
import lv.javaguru.cms.model.entities.CourseParticipantEntity;
import lv.javaguru.cms.model.entities.enums.CourseParticipantStatus;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.repositories.CompanyRepository;
import lv.javaguru.cms.model.repositories.CourseParticipantRepository;
import lv.javaguru.cms.model.repositories.CourseRepository;
import lv.javaguru.cms.rest.controllers.course.model.bill.CreateBillRequest;
import lv.javaguru.cms.rest.controllers.course.model.bill.CreateCourseBillsRequest;
import lv.javaguru.cms.rest.controllers.course.model.bill.CreateCourseBillsResponse;
import lv.javaguru.cms.rest.dto.BillDTO;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class CreateCourseBillsService {

    @Value("${bill.validDays:10}")
    public Integer validDays;

    @Autowired private SystemUserRightsChecker rightsChecker;
    @Autowired private CourseRepository courseRepository;
    @Autowired private CourseParticipantRepository courseParticipantRepository;
    @Autowired private CompanyRepository companyRepository;
    @Autowired private CreateBillService createBillService;

    public CreateCourseBillsResponse create(CreateCourseBillsRequest request) {
        rightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN, SystemUserRole.BILL_MANAGER);
        CompanyEntity company = companyRepository.getById(request.getCompanyId());
        CourseEntity course = courseRepository.getById(request.getCourseId());

        List<BillDTO> bills = courseParticipantRepository.findByCourse(course).stream()
                .filter(courseParticipant -> CourseParticipantStatus.ACTIVE == courseParticipant.getStatus())
                .map(courseParticipant -> createBillRequest(
                        course, courseParticipant, company, request.getBillPart()
                ))
                .map(createBillRequest -> createBillService.create(createBillRequest))
                .collect(Collectors.toList());


        return CreateCourseBillsResponse.builder().bills(bills).build();
    }

    private CreateBillRequest createBillRequest(CourseEntity course,
                                                CourseParticipantEntity courseParticipant,
                                                CompanyEntity company,
                                                Integer billPart) {
        return CreateBillRequest.builder()
                .courseId(course.getId())
                .participantId(courseParticipant.getId())
                .companyId(company.getId())
                .billPart(billPart)
                .build();
    }

}
