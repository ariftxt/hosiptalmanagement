package com.hospitalManagement.controller;

import com.hospitalManagement.entity.Patient;
import com.hospitalManagement.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PatientController {

    private final PatientService patientService;

    @GetMapping(value = "/get/all")
    public List<Patient> getAll(){
        return patientService.getAll();
    }
}
