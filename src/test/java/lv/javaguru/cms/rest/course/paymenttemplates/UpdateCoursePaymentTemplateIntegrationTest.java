package lv.javaguru.cms.rest.course.paymenttemplates;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import lv.javaguru.cms.rest.controllers.course.model.paymenttemplate.UpdateCoursePaymentTemplateRequest;
import lv.javaguru.cms.rest.util.RestIntegrationTest;
import org.junit.Test;

import static com.github.springtestdbunit.annotation.DatabaseOperation.DELETE_ALL;
import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT;

public class UpdateCoursePaymentTemplateIntegrationTest extends RestIntegrationTest {

    @Test
    @DatabaseSetup(value = "classpath:dbunit/paymenttemplate/update_course_payment_template/updateCoursePaymentTemplate-CourseManager-sutupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/paymenttemplate/update_course_payment_template/updateCoursePaymentTemplate-CourseManager-expectedDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldUpdateCoursePaymentTemplateWithCourseManagerRole() {
        UpdateCoursePaymentTemplateRequest request = UpdateCoursePaymentTemplateRequest.builder()
                .courseId(1L)
                .paymentTemplateId(1L)
                .build();
        sendRequest(COURSE_MANAGER_LOGIN, COURSE_MANAGER_PASSWORD, request);
    }

    private void sendRequest(String userName,
                             String password,
                             UpdateCoursePaymentTemplateRequest request) {
        getRestTemplate(userName, password).put(
                baseUrl() + "/course/" + request.getCourseId()
                        + "/paymentTemplate/", request);
    }

}
