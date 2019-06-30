package lv.javaguru.cms.rest.controllers.course;

import lv.javaguru.cms.rest.controllers.course.model.GetCourseRequest;
import lv.javaguru.cms.rest.controllers.course.model.GetCourseResponse;
import lv.javaguru.cms.rest.controllers.course.model.RegisterCourseRequest;
import lv.javaguru.cms.rest.controllers.course.model.RegisterCourseResponse;
import lv.javaguru.cms.rest.dto.CourseDTO;
import lv.javaguru.cms.services.course.GetCourseService;
import lv.javaguru.cms.services.course.RegisterCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
public class CourseController {

    @Autowired private RegisterCourseService registerCourseService;
    @Autowired private GetCourseService getCourseService;

    @PostMapping(path = "/course", consumes = "application/json", produces = "application/json")
    public RegisterCourseResponse register(@Valid @RequestBody RegisterCourseRequest request, Principal principal) {
        request.setSystemUserLogin(principal.getName());
        CourseDTO course = registerCourseService.register(request);
        return RegisterCourseResponse.builder().courseId(course.getId()).build();
    }

    @GetMapping(path = "/course/{courseId}", produces = "application/json")
    public GetCourseResponse get(@PathVariable("courseId") Long courseId, Principal principal) {
        GetCourseRequest request = GetCourseRequest.builder().courseId(courseId).build();
        request.setSystemUserLogin(principal.getName());
        CourseDTO course = getCourseService.get(request);
        return GetCourseResponse.builder().course(course).build();
    }

}
