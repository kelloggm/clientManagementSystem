package lv.javaguru.cms.rest.controllers.course;

import lv.javaguru.cms.rest.controllers.course.model.RegisterCourseRequest;
import lv.javaguru.cms.rest.controllers.course.model.RegisterCourseResponse;
import lv.javaguru.cms.rest.dto.CourseDTO;
import lv.javaguru.cms.services.course.RegisterCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
public class CourseController {

    @Autowired private RegisterCourseService registerCourseService;

    @PostMapping(path = "/course", consumes = "application/json", produces = "application/json")
    public RegisterCourseResponse register(@Valid @RequestBody RegisterCourseRequest request, Principal principal) {
        request.setSystemUserLogin(principal.getName());
        CourseDTO course = registerCourseService.register(request);
        return RegisterCourseResponse.builder().courseId(course.getId()).build();
    }

}
