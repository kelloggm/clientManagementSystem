package lv.javaguru.cms.services.client;

import lv.javaguru.cms.model.entities.ClientEntity;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.repositories.BillRepository;
import lv.javaguru.cms.model.repositories.ClientRepository;
import lv.javaguru.cms.model.repositories.CourseParticipantRepository;
import lv.javaguru.cms.rest.controllers.client.model.GetClientBillsRequest;
import lv.javaguru.cms.rest.controllers.client.model.GetClientBillsResponse;
import lv.javaguru.cms.rest.dto.BillDTO;
import lv.javaguru.cms.rest.dto.converters.BillDtoConverter;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class GetClientBillsService {

    @Autowired private SystemUserRightsChecker rightsChecker;
    @Autowired private ClientRepository clientRepository;
    @Autowired private BillRepository billRepository;
    @Autowired private CourseParticipantRepository courseParticipantRepository;
    @Autowired private BillDtoConverter billConverter;

    public GetClientBillsResponse get(GetClientBillsRequest request) {
        rightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN, SystemUserRole.BILL_MANAGER);
        ClientEntity client = clientRepository.getById(request.getClientId());
        List<BillDTO> bills = courseParticipantRepository.findByClient(client).stream()
                .map(courseParticipant -> billRepository.findByCourseParticipant(courseParticipant))
                .flatMap(Collection::stream)
                .map(bill -> billConverter.convert(bill))
                .collect(Collectors.toList());
        return GetClientBillsResponse.builder().bills(bills).build();
    }

}
