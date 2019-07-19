package lv.javaguru.cms.services.course.participants;

import lv.javaguru.cms.model.entities.CourseParticipantEntity;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.repositories.CourseParticipantRepository;
import lv.javaguru.cms.model.repositories.CourseRepository;
import lv.javaguru.cms.rest.controllers.course.model.participant.GetCourseParticipantsResponse;
import lv.javaguru.cms.rest.controllers.course.model.participant.GetCourseParticipantsRequest;
import lv.javaguru.cms.rest.dto.CourseParticipantDTO;
import lv.javaguru.cms.rest.dto.converters.CourseParticipantDtoConverter;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class GetCourseParticipantsService {

    @Autowired private SystemUserRightsChecker rightsChecker;
    @Autowired private CourseRepository courseRepository;
    @Autowired private CourseParticipantRepository courseParticipantRepository;
    @Autowired private CourseParticipantDtoConverter converter;

    public GetCourseParticipantsResponse get(GetCourseParticipantsRequest request) {
        rightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN, SystemUserRole.CLIENT_MANAGER);
        return courseRepository.findById(request.getCourseId())
                .map(courseEntity -> courseParticipantRepository.findByCourse(courseEntity))
                .map(this::buildResponse)
                .orElseThrow(() -> new IllegalArgumentException("courseId"));

    }

    private GetCourseParticipantsResponse buildResponse(List<CourseParticipantEntity> courseParticipants) {
        List<CourseParticipantDTO> participants = courseParticipants.stream()
                .map(participant -> converter.convert(participant))
                .collect(toList());
        return GetCourseParticipantsResponse.builder().participants(participants).build();
    }

}
