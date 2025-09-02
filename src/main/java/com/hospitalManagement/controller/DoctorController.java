package com.hospitalManagement.controller;

import com.hospitalManagement.entity.Doctor;
import com.hospitalManagement.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/doctor")
public class DoctorController {

    private final DoctorRepository doctorRepository;

    @GetMapping(value = "/get/all")
    public List<Doctor> getAll(){
        return doctorRepository.findAll();
    }
}
