package lv.javaguru.cms.services.course.bills;

import lv.javaguru.cms.rest.util.RestIntegrationTest;
import lv.javaguru.cms.services.course.bills.generation.BillParameters;
import lv.javaguru.cms.services.course.bills.generation.PvnBillGenerationService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PvnBillGenerationServiceTest extends RestIntegrationTest {

    @Autowired
    private PvnBillGenerationService service;

    @Test
    public void shouldGenerateNewBill() {
        BillParameters billParameters = BillParameters.builder()
            .billPrefix("JA1-2019")
            .billNumber("JA1-2019-122")
            .billCreationDate("23.08.2019")
            .companyTitle("ITGuru Software Development")
            .companyAddress("S.Eizenšteina 59-12, Rīga, LV-1079")
            .companyBankName("Swedbank")
            .companyBankBicSwift("HABALV22")
            .companyBankAccount("LV46HABA0551043273217")
            .companyRegistrationNumber("40203054690")
            .courseParticipant("Jānis Aldiņš")
            .billDueDate("31.08.2019")
            .courseTitle("Java 1 – Introduction to Java")
            .courseStartDate("01.05.2019")
            .courseEndDate("31.08.2019")
            .courseAddress("Rīga, Skolas iela 21, 508c kabinets")
            .billPrice("120.00")
            .billPriceWithoutPvn("66.12")
            .pvn("12.37")
            .billPart("2")
            .billPartTotal("3")
            .companyMemberOfTheBoard("Viktors Savoņins")
            .build();
        service.generate(billParameters);
    }

}