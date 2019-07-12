package lv.javaguru.cms.services.course;

import lv.javaguru.cms.model.entities.CourseRegistrationEntity;
import lv.javaguru.cms.rest.dto.CourseRegistrationDTO;
import lv.javaguru.cms.services.client.ClientEntityToDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CourseRegistrationEntityToDTOConverter {

    @Autowired private CourseEntityToDTOConverter courseConverter;
    @Autowired private ClientEntityToDTOConverter clientConverter;

    public CourseRegistrationDTO convert(CourseRegistrationEntity courseRegistration) {
        return CourseRegistrationDTO.builder()
                .course(courseConverter.convert(courseRegistration.getCourse()))
                .client(clientConverter.convert(courseRegistration.getClient()))
                .status(courseRegistration.getStatus().name())
                .build();
    }

}
