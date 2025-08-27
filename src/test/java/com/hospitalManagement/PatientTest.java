package com.hospitalManagement;

import com.hospitalManagement.entity.Patient;
import com.hospitalManagement.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PatientTest {

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void PatientLoad(){
        List<Patient> all = patientRepository.findAll();
        System.out.println(all);
    }
}
