package com.hospitalManagement.mapper;

import com.hospitalManagement.dto.DoctorDTO;
import com.hospitalManagement.entity.Doctor;

public class DoctorMapper {

    public static DoctorDTO toDTO(Doctor doctor) {
        return DoctorDTO.builder()
                .email(doctor.getEmail())
                .name(doctor.getName())
                .specialization(doctor.getSpecialization())
                .build();
    }
}
