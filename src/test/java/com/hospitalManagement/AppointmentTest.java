package com.hospitalManagement;

import com.hospitalManagement.entity.Appointment;
import com.hospitalManagement.service.AppointmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class AppointmentTest {

    @Autowired
    private AppointmentService appointmentService;

    @Test
    public void appointmentTest(){
        Appointment appointment = Appointment.builder()
                        .appointmentTime(LocalDateTime.of(2025, 8, 29,10, 30))
                                .reason("Fever").build();
        Appointment savedAppointment = appointmentService.createAppointment(appointment, 2L, 6L);
        System.out.println(savedAppointment);
    }

    @Test
    public void reAssignAppointmentToAnotherDoctorTest(){
        Appointment appointment = appointmentService.reAssignAppointmentToAnotherDoctor(1L, 1L);
        System.out.println(appointment);
    }
}
