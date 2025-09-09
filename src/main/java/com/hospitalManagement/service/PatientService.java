package com.hospitalManagement.service;

import com.hospitalManagement.entity.Patient;
import com.hospitalManagement.entity.User;
import com.hospitalManagement.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    public void create(String userName, Long userId) {
        Patient patient = new Patient();
        patient.setName(userName);
        patient.setEmail(userName);
        patient.setUser(User.builder().id(userId).build());
        patientRepository.save(patient);
    }
}
