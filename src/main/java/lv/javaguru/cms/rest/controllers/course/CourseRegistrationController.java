package lv.javaguru.cms.rest.controllers.course;

import lv.javaguru.cms.rest.controllers.course.model.registration.CancelCourseRegistrationRequest;
import lv.javaguru.cms.rest.controllers.course.model.registration.CancelCourseRegistrationResponse;
import lv.javaguru.cms.rest.controllers.course.model.registration.CourseRegistrationRequest;
import lv.javaguru.cms.rest.controllers.course.model.registration.CourseRegistrationResponse;
import lv.javaguru.cms.rest.controllers.course.model.registration.GetCourseRegistrationRequest;
import lv.javaguru.cms.rest.controllers.course.model.registration.GetCourseRegistrationResponse;
import lv.javaguru.cms.rest.controllers.course.model.registration.UpdateCourseRegistrationRequest;
import lv.javaguru.cms.rest.controllers.course.model.registration.UpdateCourseRegistrationResponse;
import lv.javaguru.cms.services.course.registrations.CancelCourseRegistrationService;
import lv.javaguru.cms.services.course.registrations.CourseRegistrationFactory;
import lv.javaguru.cms.services.course.registrations.GetCourseRegistrationService;
import lv.javaguru.cms.services.course.registrations.UpdateCourseRegistrationService;
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
public class CourseRegistrationController {

    @Autowired private CourseRegistrationFactory courseRegistrationFactory;
    @Autowired private GetCourseRegistrationService getCourseRegistrationService;
    @Autowired private CancelCourseRegistrationService cancelCourseRegistrationService;
    @Autowired private UpdateCourseRegistrationService updateCourseRegistrationService;

    @PostMapping(path = "/course/{courseId}/registration", produces = "application/json")
    public CourseRegistrationResponse createRegistration(@PathVariable("courseId") Long courseId,
                                                         @Valid @RequestBody CourseRegistrationRequest request,
                                                         Principal principal) {
        if (!Objects.equals(courseId, request.getCourseId())) {
            throw new IllegalArgumentException("courseId");
        }
        request.setSystemUserLogin(principal.getName());
        return courseRegistrationFactory.create(request);
    }

    @GetMapping(path = "/course/{courseId}/registration/{registrationId}", produces = "application/json")
    public GetCourseRegistrationResponse getRegistration(@PathVariable("courseId") Long courseId,
                                                         @PathVariable("registrationId") Long registrationId,
                                                         Principal principal) {
        GetCourseRegistrationRequest request = GetCourseRegistrationRequest.builder()
                .courseId(courseId)
                .registrationId(registrationId)
                .build();
        request.setSystemUserLogin(principal.getName());
        return getCourseRegistrationService.get(request);
    }

    @DeleteMapping(path = "/course/{courseId}/registration/{registrationId}", produces = "application/json")
    public CancelCourseRegistrationResponse cancelRegistration(@PathVariable("courseId") Long courseId,
                                                               @PathVariable("registrationId") Long registrationId,
                                                               Principal principal) {
        CancelCourseRegistrationRequest request = CancelCourseRegistrationRequest.builder()
                .courseId(courseId)
                .registrationId(registrationId)
                .build();
        request.setSystemUserLogin(principal.getName());
        return cancelCourseRegistrationService.cancel(request);
    }

    @PutMapping(path = "/course/{courseId}/registration/{registrationId}", consumes = "application/json", produces = "application/json")
    public UpdateCourseRegistrationResponse updateRegistration(@PathVariable("courseId") Long courseId,
                                                               @PathVariable("registrationId") Long registrationId,
                                                               @RequestBody UpdateCourseRegistrationRequest request,
                                                               Principal principal) {
        request.setSystemUserLogin(principal.getName());
        return updateCourseRegistrationService.update(request);
    }

}
