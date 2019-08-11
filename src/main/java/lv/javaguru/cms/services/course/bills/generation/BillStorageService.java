package lv.javaguru.cms.services.course.bills.generation;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

@Component
public class BillStorageService {

    @Value("${bill.storage}")
    public String billStorage;


    public void storeNewBill(BillParameters billParameters,
                             XWPFDocument document) throws IOException {
        storeBillToAllBillFolder(billParameters, document);
        storeBillToCompanyFolder(billParameters, document);
    }

    private void storeBillToAllBillFolder(BillParameters billParameters, XWPFDocument document) throws IOException {
        String newBillFilePath = getAllBillFilePath(billParameters);
        document.write(new FileOutputStream(newBillFilePath));
    }

    private String getAllBillFilePath(BillParameters billParameters) {
        int currentYear = LocalDate.now().getYear();
        File yearDirectory = new File(billStorage + "\\" + currentYear);
        if (!yearDirectory.exists()) {
            yearDirectory.mkdirs();
        }

        File billPrefixDirectory = new File(
                billStorage
                        + "\\"
                        + currentYear
                        + "\\"
                        + billParameters.getBillPrefix()
        );
        if (!billPrefixDirectory.exists()) {
            billPrefixDirectory.mkdirs();
        }

        return billStorage
                + "\\"
                + currentYear
                + "\\"
                + billParameters.getBillPrefix()
                + "\\"
                + billParameters.getBillNumber() + ".docx";
    }

    private void storeBillToCompanyFolder(BillParameters billParameters, XWPFDocument document) throws IOException {
        String newBillFilePath = getCourseBillFilePath(billParameters);
        document.write(new FileOutputStream(newBillFilePath));
    }

    private String getCourseBillFilePath(BillParameters billParameters) {
        String companyTitle = billParameters.getCompanyTitle();
        File companyDirectory = new File(billStorage + "\\" + companyTitle);
        if (!companyDirectory.exists()) {
            companyDirectory.mkdirs();
        }

        int currentYear = LocalDate.now().getYear();
        File yearDirectory = new File(
                billStorage +
                        "\\"
                        + companyTitle
                        + "\\"
                        + currentYear);
        if (!yearDirectory.exists()) {
            yearDirectory.mkdirs();
        }

        File courseDirectory = new File(
                billStorage
                        + "\\"
                        + companyTitle
                        + "\\"
                        + currentYear
                        + "\\"
                        + billParameters.getCourseTitle() + " (" + billParameters.getCourseStartDate().replaceAll("\\.", "_") + " - " + billParameters.getCourseEndDate().replaceAll("\\.", "_") + ")"
        );
        if (!courseDirectory.exists()) {
            courseDirectory.mkdirs();
        }

        File billPartDirectory = new File(
                billStorage
                        + "\\"
                        + companyTitle
                        + "\\"
                        + currentYear
                        + "\\"
                        + billParameters.getCourseTitle() + " (" + billParameters.getCourseStartDate().replaceAll("\\.", "_") + " - " + billParameters.getCourseEndDate().replaceAll("\\.", "_") + ")"
                        + "\\"
                        + "part " + billParameters.getBillPart() + " of " + billParameters.getBillPartTotal()
        );
        if (!billPartDirectory.exists()) {
            billPartDirectory.mkdirs();
        }

        return billStorage
                + "\\"
                + companyTitle
                + "\\"
                + currentYear
                + "\\"
                + billParameters.getCourseTitle() + " (" + billParameters.getCourseStartDate().replaceAll("\\.", "_") + " - " + billParameters.getCourseEndDate().replaceAll("\\.", "_") + ")"
                + "\\"
                + "part " + billParameters.getBillPart() + " of " + billParameters.getBillPartTotal()
                + "\\"
                + billParameters.getBillNumber() + ".docx";
    }

}
