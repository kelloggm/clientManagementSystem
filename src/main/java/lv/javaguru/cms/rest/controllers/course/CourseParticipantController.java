package lv.javaguru.cms.rest.controllers.course;

import lv.javaguru.cms.rest.controllers.course.model.participant.DeleteCourseParticipantRequest;
import lv.javaguru.cms.rest.controllers.course.model.participant.DeleteCourseParticipantResponse;
import lv.javaguru.cms.rest.controllers.course.model.participant.CreateCourseParticipantRequest;
import lv.javaguru.cms.rest.controllers.course.model.participant.CreateCourseParticipantResponse;
import lv.javaguru.cms.rest.controllers.course.model.participant.GetCourseParticipantResponse;
import lv.javaguru.cms.rest.controllers.course.model.participant.GetCourseParticipantRequest;
import lv.javaguru.cms.rest.controllers.course.model.participant.UpdateCourseParticipantRequest;
import lv.javaguru.cms.rest.controllers.course.model.participant.UpdateCourseParticipantResponse;
import lv.javaguru.cms.services.course.participants.CancelCourseParticipantService;
import lv.javaguru.cms.services.course.participants.CreateCourseParticipantService;
import lv.javaguru.cms.services.course.participants.GetCourseParticipantService;
import lv.javaguru.cms.services.course.participants.UpdateCourseParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Objects;

@RestController
public class CourseParticipantController {

    @Autowired private CreateCourseParticipantService createCourseParticipantService;
    @Autowired private GetCourseParticipantService getCourseParticipantService;
    @Autowired private CancelCourseParticipantService cancelCourseParticipantService;
    @Autowired private UpdateCourseParticipantService updateCourseParticipantService;

    @PostMapping(path = "/course/{courseId}/participant", produces = "application/json")
    public CreateCourseParticipantResponse createParticipant(@PathVariable("courseId") Long courseId,
                                                             @RequestBody @Valid CreateCourseParticipantRequest request,
                                                             Principal principal) {
        if (!Objects.equals(courseId, request.getCourseId())) {
            throw new IllegalArgumentException("courseId");
        }
        request.setSystemUserLogin(principal.getName());
        return createCourseParticipantService.create(request);
    }

    @GetMapping(path = "/course/{courseId}/participant/{participantId}", produces = "application/json")
    public GetCourseParticipantResponse getParticipant(@PathVariable("courseId") Long courseId,
                                                       @PathVariable("participantId") Long participantId,
                                                       Principal principal) {
        GetCourseParticipantRequest request = GetCourseParticipantRequest.builder()
                .courseId(courseId)
                .participantId(participantId)
                .build();
        request.setSystemUserLogin(principal.getName());
        return getCourseParticipantService.get(request);
    }

    @DeleteMapping(path = "/course/{courseId}/participant/{participantId}", produces = "application/json")
    public DeleteCourseParticipantResponse deleteParticipant(@PathVariable("courseId") Long courseId,
                                                             @PathVariable("participantId") Long participantId,
                                                             Principal principal) {
        DeleteCourseParticipantRequest request = DeleteCourseParticipantRequest.builder()
                .courseId(courseId)
                .participantId(participantId)
                .build();
        request.setSystemUserLogin(principal.getName());
        return cancelCourseParticipantService.cancel(request);
    }

    @PutMapping(path = "/course/{courseId}/participant/{participantId}", consumes = "application/json", produces = "application/json")
    public UpdateCourseParticipantResponse updateParticipant(@PathVariable("courseId") Long courseId,
                                                             @PathVariable("participantId") Long participantId,
                                                             @RequestBody UpdateCourseParticipantRequest request,
                                                             Principal principal) {
        if (!Objects.equals(courseId, request.getCourseId())) {
            throw new IllegalArgumentException("courseId");
        }
        if (!Objects.equals(participantId, request.getParticipantId())) {
            throw new IllegalArgumentException("participantId");
        }
        request.setSystemUserLogin(principal.getName());
        return updateCourseParticipantService.update(request);
    }

}
