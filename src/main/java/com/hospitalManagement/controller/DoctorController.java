package com.hospitalManagement.controller;

import com.hospitalManagement.dto.DoctorDTO;
import com.hospitalManagement.mapper.DoctorMapper;
import com.hospitalManagement.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/doctor")
public class DoctorController {

    private final DoctorRepository doctorRepository;

    @GetMapping(value = "/get/all")
    public Page<DoctorDTO> getAll(Pageable pageable){
        return doctorRepository.findAll(pageable).map(DoctorMapper::toDTO);
    }


}
