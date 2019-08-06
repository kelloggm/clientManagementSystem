package lv.javaguru.cms.services.course.bills;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;

@Component
public class GenerateBillService {

    @Autowired
    private TextReplacementService textReplacementService;

    public void generate(String templateFilePath,
                         String newBillFilePath) {
        try {
            XWPFDocument document = new XWPFDocument(OPCPackage.open(templateFilePath));
            textReplacementService.replace(document, "{full_bill_number_1}", "JA1-2019-777");
            textReplacementService.replace(document, "{full_bill_number_2}", "JA1-2019-777");
            document.write(new FileOutputStream(newBillFilePath));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
