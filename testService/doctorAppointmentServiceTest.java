package com.doctorAppointmentSystem.testService;

import com.doctorAppointmentSystem.model.*;
import com.doctorAppointmentSystem.repository.*;
import com.doctorAppointmentSystem.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import static org.mockito.Mockito.*;

public class doctorAppointmentServiceTest {
    
    @InjectMocks
    private doctorAppointmentService doctorAppointmentSer;
    
    @Mock
    private doctorRepository doctorRepo;
    
    @Mock
    private doctorAppointmentTimeRepository doctorAppointmentTimeRepo;

    private doctor doctor;
    
    private doctorAppointmentTime doctorAppointmentTime;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        doctor = new doctor();
        doctor.setDoctorId(1);

        doctorAppointmentTime = new doctorAppointmentTime();
        doctorAppointmentTime.setTimeSlotId(1);
    }
    
    @Test
    void testAddTimeSlot_successful() {
        int doctorId = 1;
        LocalDate date = LocalDate.of(2025, 2, 6);
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(10, 0);
        int maxCount = 5;

        when(doctorRepo.findById(doctorId)).thenReturn(Optional.of(doctor));
        doctorAppointmentSer.addTimeSlot(doctorId, date, startTime, endTime, maxCount);
        verify(doctorAppointmentTimeRepo, times(1)).save(any(doctorAppointmentTime.class));
    }
    @Test
    void testAddTimeSlot_doctorNotFound() {
        int doctorId = 1;
        LocalDate date = LocalDate.of(2025, 2, 6);
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(10, 0);
        int maxCount = 5;

        when(doctorRepo.findById(doctorId)).thenReturn(Optional.empty());
        try {
            doctorAppointmentSer.addTimeSlot(doctorId, date, startTime, endTime, maxCount);
        } catch (IllegalArgumentException e) {
            assert(e.getMessage().equals("Doctor not found with id: " + doctorId));
        }
        verify(doctorAppointmentTimeRepo, times(0)).save(any(doctorAppointmentTime.class));
    }

    @Test
    void testUpdateTimeSlot_successful() {
        int timeSlotId = 1;
        LocalDate date = LocalDate.of(2025, 2, 6);
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(10, 0);
        int maxCount = 5;

        when(doctorAppointmentTimeRepo.findById(timeSlotId)).thenReturn(Optional.of(doctorAppointmentTime));
        doctorAppointmentSer.updateTimeSlot(timeSlotId, date, startTime, endTime, maxCount);
        verify(doctorAppointmentTimeRepo, times(1)).save(doctorAppointmentTime);
    }

    @Test
    void testUpdateTimeSlot_notFound() {
        int timeSlotId = 1;
        LocalDate date = LocalDate.of(2025, 2, 6);
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(10, 0);
        int maxCount = 5;

        when(doctorAppointmentTimeRepo.findById(timeSlotId)).thenReturn(Optional.empty());
        try {
            doctorAppointmentSer.updateTimeSlot(timeSlotId, date, startTime, endTime, maxCount);
        } catch (IllegalArgumentException e) {
            assert(e.getMessage().equals("Time slot not found for ID: " + timeSlotId));
        }
        verify(doctorAppointmentTimeRepo, times(0)).save(any(doctorAppointmentTime.class));
    }

    @Test
    void testDeleteTimeSlot_successful() {
        int timeSlotId = 1;
        when(doctorAppointmentTimeRepo.existsById(timeSlotId)).thenReturn(true);
        doctorAppointmentSer.deleteTimeSlot(timeSlotId);
        verify(doctorAppointmentTimeRepo, times(1)).deleteById(timeSlotId);
    }

    @Test
    void testDeleteTimeSlot_notFound() {
        int timeSlotId = 1;

        when(doctorAppointmentTimeRepo.existsById(timeSlotId)).thenReturn(false);

        try {
            doctorAppointmentSer.deleteTimeSlot(timeSlotId);
        } catch (IllegalArgumentException e) {
            assert(e.getMessage().equals("Time slot not found for ID: " + timeSlotId));
        }

        verify(doctorAppointmentTimeRepo, times(0)).deleteById(timeSlotId);
    }
}
