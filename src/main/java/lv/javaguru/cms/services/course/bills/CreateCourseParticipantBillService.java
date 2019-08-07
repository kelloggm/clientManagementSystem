package lv.javaguru.cms.services.course.bills;

import lv.javaguru.cms.model.entities.BillEntity;
import lv.javaguru.cms.model.entities.CompanyEntity;
import lv.javaguru.cms.model.entities.CourseEntity;
import lv.javaguru.cms.model.entities.CourseParticipantEntity;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.repositories.BillRepository;
import lv.javaguru.cms.model.repositories.CompanyRepository;
import lv.javaguru.cms.model.repositories.CourseParticipantRepository;
import lv.javaguru.cms.model.repositories.CourseRepository;
import lv.javaguru.cms.rest.controllers.course.model.bill.CreateBillRequest;
import lv.javaguru.cms.rest.controllers.course.model.bill.CreateBillResponse;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Transactional
public class CreateCourseParticipantBillService {

    @Value("${bill.validDays:10}")
    public Integer validDays;

    @Autowired private SystemUserRightsChecker rightsChecker;
    @Autowired private CourseRepository courseRepository;
    @Autowired private CourseParticipantRepository courseParticipantRepository;
    @Autowired private CompanyRepository companyRepository;
    @Autowired private MicroBillGenerationService microBillGenerationService;
    @Autowired private PvnBillGenerationService pvnBillGenerationService;
    @Autowired private BillRepository billRepository;

    public CreateBillResponse create(CreateBillRequest request) {
        rightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN, SystemUserRole.CLIENT_MANAGER);
        CompanyEntity company = companyRepository.getById(request.getCompanyId());
        CourseEntity course = courseRepository.getById(request.getCourseId());
        CourseParticipantEntity courseParticipant = courseParticipantRepository.getById(request.getParticipantId());
        checkParticipantRegistrationToCourse(course, courseParticipant);

        BillEntity bill = billRepository.findByCourseAndCourseParticipantAndBillPart(course, courseParticipant, request.getBillPart())
                .map(b -> regenerateAlreadyExistingBill(request, course, courseParticipant, company, b))
                .orElseGet(() -> generateNewBill(request, course, courseParticipant, company));

        return CreateBillResponse.builder().billId(bill.getId()).build();
    }

    private void checkParticipantRegistrationToCourse(CourseEntity course, CourseParticipantEntity courseParticipant) {
        if (!course.equals(courseParticipant.getCourse())) {
            throw new IllegalArgumentException("courseId");
        }
    }

    private BillEntity generateNewBill(CreateBillRequest request, CourseEntity course, CourseParticipantEntity courseParticipant, CompanyEntity company) {
        Integer billNumber = findNewBillNumber(course);
        BillParameters billParameters = prepareBillParameters(company, course, courseParticipant, billNumber, request.getBillPart());
        generateBillFile(company, billParameters);
        BillEntity bill = createBillEntity(company, course, courseParticipant, billNumber, request.getBillPart());
        return billRepository.save(bill);
    }

    private Integer findNewBillNumber(CourseEntity course) {
        return billRepository.findFirstByBillPrefixOrderByBillNumberDesc(course.getBillPrefix())
                .map(billEntity -> billEntity.getBillNumber() + 1).orElse(1);
    }

    private BillEntity regenerateAlreadyExistingBill(CreateBillRequest request,
                                                     CourseEntity course,
                                                     CourseParticipantEntity courseParticipant,
                                                     CompanyEntity company,
                                                     BillEntity bill) {
        BillParameters billParameters = prepareBillParameters(company, course, courseParticipant, bill.getBillNumber(), request.getBillPart());
        generateBillFile(company, billParameters);
        bill.setPayTo(LocalDateTime.now().plusDays(validDays));
        bill.setBillSum(courseParticipant.getOneBillAmount());
        bill.setCompany(company);
        return bill;
    }

    private void generateBillFile(CompanyEntity company, BillParameters billParameters) {
        if (!company.isPvnPayer()) {
            microBillGenerationService.generate(billParameters);
        } else {
            pvnBillGenerationService.generate(billParameters);
        }
    }

    private BillEntity createBillEntity(CompanyEntity company,
                                        CourseEntity course,
                                        CourseParticipantEntity courseParticipant,
                                        Integer billNumber,
                                        Integer billPart) {
        return BillEntity.builder()
                .course(course)
                .courseParticipant(courseParticipant)
                .company(company)
                .billPrefix(course.getBillPrefix())
                .billNumber(billNumber)
                .billPart(billPart)
                .payTo(LocalDateTime.now().plusDays(validDays))
                .billSum(courseParticipant.getOneBillAmount())
                .build();
    }

    private BillParameters prepareBillParameters(CompanyEntity company,
                                                 CourseEntity course,
                                                 CourseParticipantEntity courseParticipant,
                                                 Integer billNumber,
                                                 Integer billPart) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");
        return BillParameters.builder()
                .billNumber(course.getBillPrefix() + "-" + billNumber)
                .billCreationDate(LocalDate.now().format(dateFormatter))
                .companyTitle(company.getTitle())
                .companyAddress(company.getLegalAddress())
                .companyBankName(company.getBankName())
                .companyBankAccount(company.getBankAccount())
                .companyRegistrationNumber(company.getRegistrationNumber())
                .courseParticipant(courseParticipant.getClient().getFirstName() + courseParticipant.getClient().getLastName())
                .billDueDate(LocalDate.now().plusDays(validDays).format(dateFormatter))
                .courseTitle(course.getTitle())
                .courseStartDate(course.getStartDate().format(dateFormatter))
                .courseEndDate(course.getEndDate().format(dateFormatter))
                .courseAddress(course.getAddress())
                .billPrice(courseParticipant.getOneBillAmount().toString() + ".00")
                .billPart(billPart.toString())
                .billPartTotal(courseParticipant.getBillCount().toString())
                .companyMemberOfTheBoard(company.getMemberOfTheBoard())
                .build();
    }

}
