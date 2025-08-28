package com.hospitalManagement;

import com.hospitalManagement.entity.Insurance;
import com.hospitalManagement.entity.Patient;
import com.hospitalManagement.service.InsuranceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class InsuranceTest {

    @Autowired
    private InsuranceService insuranceService;

    @Test
    public void testInsurance(){
        Insurance insurance = Insurance.builder()
                .policyNumber("HDFC-001")
                .provider("HDFC")
                .validUntil(LocalDate.now())
                .build();
        Patient patient = insuranceService.assignInsuranceToPatient(insurance, 3L);
        System.out.println(patient);// error : failed to lazily initialize a collection of role:
        // com.hospitalManagement.entity.Patient.appointments: could not initialize proxy - no Session
        // because relation b/w patient and appointment is OneToMany(having FetchType lazy as a default)
        // Fix : Either exclude from ToString or make it FetchType.EAGER
    }

    @Test
    public void disaccociateInsuranceFromPatientTest(){
        Patient patient = insuranceService.disaccociateInsuranceFromPatient(3L);
        System.out.println(patient);
    }
}
