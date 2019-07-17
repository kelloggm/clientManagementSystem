package lv.javaguru.cms.rest.dto.converters;

import lv.javaguru.cms.model.entities.CourseRegistrationEntity;
import lv.javaguru.cms.rest.dto.CourseRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CourseRegistrationDtoConverter {

    @Autowired private CourseDtoConverter courseConverter;
    @Autowired private ClientDtoConverter clientConverter;

    public CourseRegistrationDTO convert(CourseRegistrationEntity courseRegistration) {
        return CourseRegistrationDTO.builder()
                .course(courseConverter.convert(courseRegistration.getCourse()))
                .client(clientConverter.convert(courseRegistration.getClient()))
                .status(courseRegistration.getStatus().name())
                .build();
    }

}
