package lv.javaguru.cms.services.course.bills.generation;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class PvnBillGenerationService {

    @Value("${bill.templates.pvn}")
    public String billTemplatesPvn;

    @Value("${bill.storage}")
    public String billStorage;

    @Autowired
    private TextReplacementService textReplacementService;

    public void generate(BillParameters billParameters) {
        try {
            XWPFDocument document = openBillTemplate();

            textReplacementService.replace(document, "{bill_number_1}", billParameters.getBillNumber());
            textReplacementService.replace(document, "{bill_creation_date}", billParameters.getBillCreationDate());
            textReplacementService.replace(document, "{company_title_1}", billParameters.getCompanyTitle());
            textReplacementService.replace(document, "{company_address}", billParameters.getCompanyAddress());
            textReplacementService.replace(document, "{company_bank_name}", billParameters.getCompanyBankName());
            textReplacementService.replace(document, "{company_bank_account}", billParameters.getCompanyBankAccount());
            textReplacementService.replace(document, "{company_rn}", billParameters.getCompanyRegistrationNumber());
            textReplacementService.replace(document, "{course_participant}", billParameters.getCourseParticipant());
            textReplacementService.replace(document, "{bill_due_date}", billParameters.getBillDueDate());
            textReplacementService.replace(document, "{course_title}", billParameters.getCourseTitle());
            textReplacementService.replace(document, "{course_start_date}", billParameters.getCourseStartDate());
            textReplacementService.replace(document, "{course_end_date}", billParameters.getCourseEndDate());
            textReplacementService.replace(document, "{course_address}", billParameters.getCourseAddress());
            textReplacementService.replace(document, "{bill_number_2}", billParameters.getBillNumber());
            textReplacementService.replace(document, "{bill_price}", billParameters.getBillPrice());
            textReplacementService.replace(document, "{bill_part}", billParameters.getBillPart());
            textReplacementService.replace(document, "{bill_part_total}", billParameters.getBillPartTotal());
            textReplacementService.replace(document, "{company_title_2}", billParameters.getCompanyTitle());
            textReplacementService.replace(document, "{company_member_of_the_board}", billParameters.getCompanyMemberOfTheBoard());

            saveNewBill(billParameters, document);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private XWPFDocument openBillTemplate() throws IOException, InvalidFormatException {
        Resource billTemplateResource = loadBillTemplate();
        File billTemplateFile = billTemplateResource.getFile();
        return new XWPFDocument(OPCPackage.open(billTemplateFile));
    }

    private void saveNewBill(BillParameters billParameters, XWPFDocument document) throws IOException {
        String newBillFilePath = getNewBillFilePath(billParameters.getBillNumber());
        document.write(new FileOutputStream(newBillFilePath));
    }

    private String getNewBillFilePath(String billNumber) {
        return billStorage + "\\" + billNumber + ".docx";
    }

    private Resource loadBillTemplate() {
        return new ClassPathResource(billTemplatesPvn);
    }

}
