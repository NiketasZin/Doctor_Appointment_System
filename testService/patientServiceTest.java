package com.doctorAppointmentSystem.testService;

import com.doctorAppointmentSystem.model.*;
import com.doctorAppointmentSystem.repository.*;
import com.doctorAppointmentSystem.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.mockito.Mockito.*;

public class patientServiceTest {

    @InjectMocks
    private patientService patientSer;
    
    @Mock
    private patientRepository patientRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateUser_success() {
        int patientId = 1;
        patient existingPatient = new patient();
        existingPatient.setPatientId(patientId);
        existingPatient.setFirstName("Old Name");
        existingPatient.setLastName("Doe");

        patient updatedPatient = new patient();
        updatedPatient.setFirstName("Updated Name");
        updatedPatient.setLastName("Smith");

        when(patientRepo.findById(patientId)).thenReturn(Optional.of(existingPatient));
        patientSer.updateUser(patientId, updatedPatient);
        verify(patientRepo, times(1)).save(existingPatient);
        assert(existingPatient.getFirstName().equals("Updated Name"));
        assert(existingPatient.getLastName().equals("Smith"));
    }

    @Test
    void testUpdateUser_notFound() {
        int patientId = 1;
        patient updatedPatient = new patient();
        updatedPatient.setFirstName("Updated Name");
        updatedPatient.setLastName("Smith");

        when(patientRepo.findById(patientId)).thenReturn(Optional.empty());
        try {
            patientSer.updateUser(patientId, updatedPatient);
        } catch (NoSuchElementException e) {
            assert(e.getMessage().equals("User not found"));
        }
        verify(patientRepo, times(0)).save(any(patient.class));
    }

    @Test
    void testDeletePatientById_success() {
        int patientId = 1;
        patientSer.deletePatientById(patientId);
        verify(patientRepo, times(1)).deleteById(patientId);
    }

    @Test
    void testDeletePatientById_notFound() {
        int patientId = 1;
        doThrow(new NoSuchElementException("User not found")).when(patientRepo).deleteById(patientId);
        try {
            patientSer.deletePatientById(patientId);
        } catch (NoSuchElementException e) {
            assert(e.getMessage().equals("User not found"));
        }
        verify(patientRepo, times(1)).deleteById(patientId);
    }
}
