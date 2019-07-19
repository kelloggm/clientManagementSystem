package lv.javaguru.cms.rest.course.paymenttemplates;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import lv.javaguru.cms.rest.controllers.course.model.paymenttemplate.GetCoursePaymentTemplateRequest;
import lv.javaguru.cms.rest.controllers.course.model.paymenttemplate.GetCoursePaymentTemplateResponse;
import lv.javaguru.cms.rest.dto.PaymentTemplateDTO;
import lv.javaguru.cms.rest.util.RestIntegrationTest;
import org.hamcrest.Matchers;
import org.junit.Test;

import static com.github.springtestdbunit.annotation.DatabaseOperation.DELETE_ALL;
import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class GetCoursePaymentTemplateIntegrationTest extends RestIntegrationTest {

    @Test
    @DatabaseSetup(value = "classpath:dbunit/paymenttemplate/get_course_payment_template/getCoursePaymentTemplate-CourseManager-setupDataset.xml")
    @ExpectedDatabase(value = "classpath:dbunit/paymenttemplate/get_course_payment_template/getCoursePaymentTemplate-CourseManager-setupDataset.xml", assertionMode= NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/database-cleanup.xml", type = DELETE_ALL)
    public void shouldGetCoursePaymentTemplateWithCourseManagerRole() {
        GetCoursePaymentTemplateRequest request = GetCoursePaymentTemplateRequest.builder()
                .courseId(1L)
                .paymentTemplateId(1L)
                .build();
        GetCoursePaymentTemplateResponse response = sendRequest(COURSE_MANAGER_LOGIN, COURSE_MANAGER_PASSWORD, request);

        assertThat(response.isOk(), is(true));
        assertThat(response.getErrors(), is(nullValue()));
        PaymentTemplateDTO paymentTemplate = response.getPaymentTemplate();
        assertThat(paymentTemplate.getId(), is(1L));
        assertThat(paymentTemplate.getTitle(), is("JavaGuru"));
        assertThat(paymentTemplate.getTemplateFilePath(), is("path"));
        assertThat(paymentTemplate.getModifiedBy(), is("course_manager_login"));
        assertThat(paymentTemplate.getCreatedAt(), is(Matchers.notNullValue()));
        assertThat(paymentTemplate.getModifiedAt(), is(Matchers.nullValue()));
    }

    private GetCoursePaymentTemplateResponse sendRequest(String userName,
                                                         String password,
                                                         GetCoursePaymentTemplateRequest request) {
        return getRestTemplate(userName, password).getForObject(
                baseUrl() + "/course/" + request.getCourseId()
                        + "/paymentTemplate/" + request.getPaymentTemplateId(), GetCoursePaymentTemplateResponse.class);
    }

}
