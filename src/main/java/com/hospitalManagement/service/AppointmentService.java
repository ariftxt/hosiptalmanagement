package com.hospitalManagement.service;

import com.hospitalManagement.entity.Appointment;
import com.hospitalManagement.entity.Doctor;
import com.hospitalManagement.entity.Patient;
import com.hospitalManagement.repository.AppointmentRepository;
import com.hospitalManagement.repository.DoctorRepository;
import com.hospitalManagement.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public Appointment createAppointment(Appointment appointment, Long doctorId, Long patientId){
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() ->
                        new EntityNotFoundException("Doctor not found with id "+doctorId)
                );
        Patient patient = patientRepository.findById(patientId).orElseThrow(() ->
                new EntityNotFoundException("Patient not found with id "+patientId)
        );
        if(appointment.getId() != null)
            throw new IllegalArgumentException("Appointment already exist");

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        return appointmentRepository.save(appointment);
    }

    @Transactional
    public Appointment reAssignAppointmentToAnotherDoctor(Long appointmentId, Long doctorId){
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() ->
                new EntityNotFoundException("Doctor not found with id "+doctorId)
        );
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow();
        appointment.setDoctor(doctor);// auto call the update. Because it is dirty.
        return appointment;
    }
}
