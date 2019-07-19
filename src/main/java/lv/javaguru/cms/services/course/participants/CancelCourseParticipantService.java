package lv.javaguru.cms.services.course.participants;

import lv.javaguru.cms.model.entities.CourseEntity;
import lv.javaguru.cms.model.entities.CourseParticipantEntity;
import lv.javaguru.cms.model.entities.enums.CourseParticipantStatus;
import lv.javaguru.cms.model.entities.enums.SystemUserRole;
import lv.javaguru.cms.model.repositories.CourseParticipantRepository;
import lv.javaguru.cms.model.repositories.CourseRepository;
import lv.javaguru.cms.rest.controllers.course.model.participant.DeleteCourseParticipantRequest;
import lv.javaguru.cms.rest.controllers.course.model.participant.DeleteCourseParticipantResponse;
import lv.javaguru.cms.services.SystemUserRightsChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CancelCourseParticipantService {

    @Autowired private SystemUserRightsChecker rightsChecker;
    @Autowired private CourseRepository courseRepository;
    @Autowired private CourseParticipantRepository courseParticipantRepository;

    public DeleteCourseParticipantResponse cancel(DeleteCourseParticipantRequest request) {
        rightsChecker.checkAccessRights(request.getSystemUserLogin(), SystemUserRole.ADMIN, SystemUserRole.CLIENT_MANAGER);
        CourseEntity course = courseRepository.getById(request.getCourseId());
        CourseParticipantEntity participant = courseParticipantRepository.getById(request.getParticipantId());
        if (!course.equals(participant.getCourse())) {
            throw new IllegalArgumentException("courseId");
        }
        participant.setStatus(CourseParticipantStatus.CANCELED);
        participant.setModifiedBy(request.getSystemUserLogin());
        return DeleteCourseParticipantResponse.builder().build();
    }

}
