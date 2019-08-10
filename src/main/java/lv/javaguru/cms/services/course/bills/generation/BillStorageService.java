package lv.javaguru.cms.services.course.bills.generation;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class BillStorageService {

    @Value("${bill.storage.micro}")
    public String billStorageMicro;


    public void saveNewBill(BillParameters billParameters, XWPFDocument document) throws IOException {
        String newBillFilePath = getNewBillFilePath(billParameters.getBillNumber());
        document.write(new FileOutputStream(newBillFilePath));
    }

    private String getNewBillFilePath(String billNumber) {
        return billStorageMicro + "\\" + billNumber + ".docx";
    }


}
