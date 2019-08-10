package lv.javaguru.cms.services.client;

import lv.javaguru.cms.model.entities.ClientEntity;
import lv.javaguru.cms.model.entities.CourseParticipantEntity;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.repositories.ClientRepository;
import lv.javaguru.cms.model.repositories.CourseParticipantRepository;
import lv.javaguru.cms.rest.controllers.client.model.GetClientCoursesRequest;
import lv.javaguru.cms.rest.controllers.client.model.GetClientCoursesResponse;
import lv.javaguru.cms.rest.dto.CourseDTO;
import lv.javaguru.cms.rest.dto.converters.CourseDtoConverter;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class GetClientCoursesService {

    @Autowired private SystemUserRightsChecker rightsChecker;
    @Autowired private ClientRepository clientRepository;
    @Autowired private CourseParticipantRepository courseParticipantRepository;
    @Autowired private CourseDtoConverter courseConverter;

    public GetClientCoursesResponse get(GetClientCoursesRequest request) {
        rightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN, SystemUserRole.CLIENT_MANAGER);
        ClientEntity client = clientRepository.getById(request.getClientId());
        List<CourseDTO> courses = courseParticipantRepository.findByClient(client).stream()
                .map(CourseParticipantEntity::getCourse)
                .map(course -> courseConverter.convert(course))
                .collect(Collectors.toList());
        return GetClientCoursesResponse.builder().courses(courses).build();
    }

}
