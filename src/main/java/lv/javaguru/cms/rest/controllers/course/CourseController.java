package lv.javaguru.cms.rest.controllers.course;

import lv.javaguru.cms.rest.controllers.course.model.CourseRegistrationRequest;
import lv.javaguru.cms.rest.controllers.course.model.CourseRegistrationResponse;
import lv.javaguru.cms.rest.controllers.course.model.GetCourseRequest;
import lv.javaguru.cms.rest.controllers.course.model.GetCourseResponse;
import lv.javaguru.cms.rest.controllers.course.model.SearchCoursesRequest;
import lv.javaguru.cms.rest.controllers.course.model.SearchCoursesResponse;
import lv.javaguru.cms.rest.controllers.course.model.UpdateCourseRequest;
import lv.javaguru.cms.rest.controllers.course.model.UpdateCourseResponse;
import lv.javaguru.cms.rest.dto.CourseDTO;
import lv.javaguru.cms.services.course.GetCourseService;
import lv.javaguru.cms.services.course.RegisterCourseService;
import lv.javaguru.cms.services.course.SearchCoursesService;
import lv.javaguru.cms.services.course.UpdateCourseService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CourseController {

    @Autowired private RegisterCourseService registerCourseService;
    @Autowired private GetCourseService getCourseService;
    @Autowired private UpdateCourseService updateCourseService;
    @Autowired private SearchCoursesService searchCoursesService;

    @PostMapping(path = "/course", consumes = "application/json", produces = "application/json")
    public CourseRegistrationResponse register(@Valid @RequestBody CourseRegistrationRequest request, Principal principal) {
        request.setSystemUserLogin(principal.getName());
        CourseDTO course = registerCourseService.register(request);
        return CourseRegistrationResponse.builder().courseId(course.getId()).build();
    }

    @GetMapping(path = "/course/{courseId}", produces = "application/json")
    public GetCourseResponse get(@PathVariable("courseId") Long courseId, Principal principal) {
        GetCourseRequest request = GetCourseRequest.builder().courseId(courseId).build();
        request.setSystemUserLogin(principal.getName());
        CourseDTO course = getCourseService.get(request);
        return GetCourseResponse.builder().course(course).build();
    }

    @PutMapping(path = "/course/{courseId}", consumes = "application/json", produces = "application/json")
    public UpdateCourseResponse update(@PathVariable("courseId") Long courseId,
                                       @Valid @RequestBody UpdateCourseRequest request,
                                       Principal principal) {
        if (!Objects.equals(courseId, request.getCourseId())) {
            throw new IllegalArgumentException("courseId");
        }
        request.setSystemUserLogin(principal.getName());
        CourseDTO course = updateCourseService.update(request);
        return UpdateCourseResponse.builder().course(course).build();
    }

    @PostMapping(path = "/course/search", consumes = "application/json", produces = "application/json")
    public SearchCoursesResponse search(@Valid @RequestBody SearchCoursesRequest request, Principal principal) {
        request.setSystemUserLogin(principal.getName());
        return searchCoursesService.search(request);
    }

}
