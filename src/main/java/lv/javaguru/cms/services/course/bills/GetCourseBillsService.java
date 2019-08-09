package lv.javaguru.cms.services.course.bills;

import lv.javaguru.cms.model.entities.BillEntity;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.repositories.BillRepository;
import lv.javaguru.cms.model.repositories.CourseRepository;
import lv.javaguru.cms.rest.controllers.course.model.bill.GetCourseBillsRequest;
import lv.javaguru.cms.rest.controllers.course.model.bill.GetCourseBillsResponse;
import lv.javaguru.cms.rest.dto.BillDTO;
import lv.javaguru.cms.rest.dto.converters.BillDtoConverter;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class GetCourseBillsService {

    @Autowired private SystemUserRightsChecker rightsChecker;
    @Autowired private CourseRepository courseRepository;
    @Autowired private BillRepository billRepository;
    @Autowired private BillDtoConverter converter;

    public GetCourseBillsResponse get(GetCourseBillsRequest request) {
        rightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN, SystemUserRole.BILL_MANAGER);
        return courseRepository.findById(request.getCourseId())
                .map(courseEntity -> billRepository.findByCourseOrderByBillPartAsc(courseEntity))
                .map(this::buildResponse)
                .orElseThrow(() -> new IllegalArgumentException("courseId"));
    }

    private GetCourseBillsResponse buildResponse(List<BillEntity> courseBills) {
        List<BillDTO> bills = courseBills.stream()
                .map(bill -> converter.convert(bill))
                .collect(toList());
        return GetCourseBillsResponse.builder().bills(bills).build();
    }

}
