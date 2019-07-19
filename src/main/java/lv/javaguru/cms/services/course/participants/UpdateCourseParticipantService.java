package lv.javaguru.cms.services.course.participants;

import lv.javaguru.cms.model.entities.CourseEntity;
import lv.javaguru.cms.model.entities.CourseParticipantEntity;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.repositories.CourseParticipantRepository;
import lv.javaguru.cms.model.repositories.CourseRepository;
import lv.javaguru.cms.rest.controllers.course.model.participant.UpdateCourseParticipantResponse;
import lv.javaguru.cms.rest.controllers.course.model.participant.UpdateCourseParticipantRequest;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UpdateCourseParticipantService {

    @Autowired private SystemUserRightsChecker rightsChecker;
    @Autowired private CourseRepository courseRepository;
    @Autowired private CourseParticipantRepository courseParticipantRepository;

    public UpdateCourseParticipantResponse update(UpdateCourseParticipantRequest request) {
        rightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN, SystemUserRole.CLIENT_MANAGER);
        CourseEntity course = courseRepository.getById(request.getCourseId());
        CourseParticipantEntity participant = courseParticipantRepository.getById(request.getParticipantId());
        if (!course.equals(participant.getCourse())) {
            throw new IllegalArgumentException("courseId");
        }
        participant.setStatus(request.getStatus());
        participant.setModifiedBy(request.getSystemUserLogin());
        return UpdateCourseParticipantResponse.builder().build();
    }

}
