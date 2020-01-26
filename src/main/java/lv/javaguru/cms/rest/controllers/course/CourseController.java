package lv.javaguru.cms.rest.controllers.course;

import lv.javaguru.cms.rest.controllers.course.model.CreateCourseRequest;
import lv.javaguru.cms.rest.controllers.course.model.CreateCourseResponse;
import lv.javaguru.cms.rest.controllers.course.model.GetCourseRequest;
import lv.javaguru.cms.rest.controllers.course.model.GetCourseResponse;
import lv.javaguru.cms.rest.controllers.course.model.SearchCoursesRequest;
import lv.javaguru.cms.rest.controllers.course.model.SearchCoursesResponse;
import lv.javaguru.cms.rest.controllers.course.model.UpdateCourseRequest;
import lv.javaguru.cms.rest.controllers.course.model.UpdateCourseResponse;
import lv.javaguru.cms.rest.controllers.course.model.bill.CreateCourseBillsRequest;
import lv.javaguru.cms.rest.controllers.course.model.bill.CreateCourseBillsResponse;
import lv.javaguru.cms.rest.controllers.course.model.bill.GetCourseBillsRequest;
import lv.javaguru.cms.rest.controllers.course.model.bill.GetCourseBillsResponse;
import lv.javaguru.cms.rest.controllers.course.model.participant.GetCourseParticipantsRequest;
import lv.javaguru.cms.rest.controllers.course.model.participant.GetCourseParticipantsResponse;
import lv.javaguru.cms.rest.dto.CourseDTO;
import lv.javaguru.cms.services.course.CreateCourseService;
import lv.javaguru.cms.services.course.GetCourseService;
import lv.javaguru.cms.services.course.SearchCoursesService;
import lv.javaguru.cms.services.course.UpdateCourseService;
import lv.javaguru.cms.services.course.bills.CreateCourseBillsService;
import lv.javaguru.cms.services.course.bills.GetCourseBillsService;
import lv.javaguru.cms.services.course.participants.GetCourseParticipantsService;
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

    @Autowired private CreateCourseService createCourseService;
    @Autowired private GetCourseService getCourseService;
    @Autowired private UpdateCourseService updateCourseService;
    @Autowired private SearchCoursesService searchCoursesService;
    @Autowired private GetCourseParticipantsService getCourseParticipantsService;
    @Autowired private GetCourseBillsService getCourseBillsService;
    @Autowired private CreateCourseBillsService createCourseBillsService;

    @PostMapping(path = "/course", consumes = "application/json", produces = "application/json")
    public CreateCourseResponse register(@RequestBody @Valid CreateCourseRequest request, Principal principal) {
        request.setSystemUserLogin(principal.getName());
        CourseDTO course = createCourseService.create(request);
        return CreateCourseResponse.builder().courseId(course.getId()).build();
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
                                       @RequestBody @Valid UpdateCourseRequest request,
                                       Principal principal) {
        if (!Objects.equals(courseId, request.getCourseId())) {
            throw new IllegalArgumentException("courseId");
        }
        request.setSystemUserLogin(principal.getName());
        CourseDTO course = updateCourseService.update(request);
        return UpdateCourseResponse.builder().course(course).build();
    }

    @PostMapping(path = "/course/search", consumes = "application/json", produces = "application/json")
    public SearchCoursesResponse search(@RequestBody @Valid SearchCoursesRequest request, Principal principal) {
        request.setSystemUserLogin(principal.getName());
        return searchCoursesService.search(request);
    }

    @GetMapping(path = "/course/{courseId}/participants", produces = "application/json")
    public GetCourseParticipantsResponse getParticipants(@PathVariable("courseId") Long courseId,
                                                         Principal principal) {
        GetCourseParticipantsRequest request = GetCourseParticipantsRequest.builder().courseId(courseId).build();
        request.setSystemUserLogin(principal.getName());
        return getCourseParticipantsService.get(request);
    }

    @GetMapping(path = "/course/{courseId}/bills", produces = "application/json")
    public GetCourseBillsResponse getBills(@PathVariable("courseId") Long courseId,
                                           Principal principal) {
        GetCourseBillsRequest request = GetCourseBillsRequest.builder().courseId(courseId).build();
        request.setSystemUserLogin(principal.getName());
        return getCourseBillsService.get(request);
    }

    @PostMapping(path = "/course/{courseId}/bills", consumes = "application/json", produces = "application/json")
    public CreateCourseBillsResponse createBills(@RequestBody @Valid CreateCourseBillsRequest request,
                                                Principal principal) {
        request.setSystemUserLogin(principal.getName());
        return createCourseBillsService.create(request);
    }

}
