package com.hospitalManagement.dto;

import com.hospitalManagement.entity.Appointment;
import com.hospitalManagement.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class DoctorDTO {

    private Long id;
    private String name;
    private String specialization;
    private String email;
    private List<Appointment> appointments;
    private Set<Department> departments = new HashSet<>();

}
