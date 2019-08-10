package lv.javaguru.cms.rest.controllers.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lv.javaguru.cms.rest.BaseResponse;
import lv.javaguru.cms.rest.dto.CourseDTO;

import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GetClientCoursesResponse extends BaseResponse {

    private List<CourseDTO> courses;

}
