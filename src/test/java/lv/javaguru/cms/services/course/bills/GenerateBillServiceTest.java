package lv.javaguru.cms.services.course.bills;

import lv.javaguru.cms.rest.util.RestIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GenerateBillServiceTest extends RestIntegrationTest {

    @Autowired
    private GenerateBillService service;

    @Test
    public void shouldGenerateNewBill() {
        String templateFilePath = "C:\\work\\projects\\JavaGuruLV\\Client_Management_System\\src\\test\\resources\\payment_templates\\JavaGuru-Template.docx";
        String newBillFilePath = "C:\\work\\projects\\JavaGuruLV\\Client_Management_System\\src\\test\\resources\\payment_templates\\JavaGuru-JA1-2019.docx";

        service.generate(templateFilePath, newBillFilePath);
    }

}