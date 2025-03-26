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

public class doctorServiceTest {

    @InjectMocks
    private doctorService doctorSer;
	
    @Mock
    private doctorRepository doctorRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveDoctor() {
        doctor doctor = new doctor();
        doctor.setDoctorName("Dr. John Doe");

        doctorSer.save(doctor);
        verify(doctorRepo, times(1)).save(doctor);
    }

    @Test
    void testFindById_success() {
        int doctorId = 1;
        doctor doctor = new doctor();
        doctor.setDoctorId(doctorId);

        when(doctorRepo.findById(doctorId)).thenReturn(Optional.of(doctor));
        doctor foundDoctor = doctorSer.findById(doctorId);
        verify(doctorRepo, times(1)).findById(doctorId);
        assert(foundDoctor.getDoctorId() == doctorId);
    }

    @Test
    void testFindById_notFound() {
        int doctorId = 1;

        when(doctorRepo.findById(doctorId)).thenReturn(Optional.empty());
        try {
            doctorSer.findById(doctorId);
        } catch (NoSuchElementException e) {
            assert(e.getMessage().equals("No value present"));
        }
        verify(doctorRepo, times(1)).findById(doctorId);
    }

    @Test
    void testFindAllDoctors() {
        doctor doctor1 = new doctor();
        doctor1.setDoctorId(1);
        doctor doctor2 = new doctor();
        doctor2.setDoctorId(2);

        when(doctorRepo.findAll()).thenReturn(Arrays.asList(doctor1, doctor2));
        List<doctor> doctors = doctorSer.findAll();
        verify(doctorRepo, times(1)).findAll();
        assert(doctors.size() == 2);
    }

    @Test
    void testFindByKeyword() {
        String keyword = "Dr. John";
        doctor doctor1 = new doctor();
        doctor1.setDoctorName("Dr. John Doe");

        when(doctorRepo.findByKeyword(keyword)).thenReturn(Arrays.asList(doctor1));
        List<doctor> doctors = doctorSer.findByKeyword(keyword);
        verify(doctorRepo, times(1)).findByKeyword(keyword);
        assert(doctors.size() == 1);
        assert(doctors.get(0).getDoctorName().equals("Dr. John Doe"));
    }

    @Test
    void testVerifyDoctorProcess() {
        int doctorId = 1;
        doctor doctor = new doctor();
        doctor.setDoctorId(doctorId);
        doctor.setConfirmStatus("pending");

        when(doctorRepo.findById(doctorId)).thenReturn(Optional.of(doctor));
        doctorSer.verifyDoctorProcess(doctorId);
        verify(doctorRepo, times(1)).save(doctor);
        assert(doctor.getConfirmStatus().equals("confirmed"));
    }

    @Test
    void testUpdateUser() {
        int doctorId = 1;
        doctor existingDoctor = new doctor();
        existingDoctor.setDoctorId(doctorId);
        existingDoctor.setDoctorName("Old Name");

        doctor updatedDoctor = new doctor();
        updatedDoctor.setDoctorName("Updated Name");

        when(doctorRepo.findById(doctorId)).thenReturn(Optional.of(existingDoctor));
        doctorSer.updateUser(doctorId, updatedDoctor);
        verify(doctorRepo, times(1)).save(existingDoctor);
        assert(existingDoctor.getDoctorName().equals("Updated Name"));
    }

    @Test
    void testDeleteDoctorById() {
        int doctorId = 1;
        doctorSer.deleteDoctorById(doctorId);
        verify(doctorRepo, times(1)).deleteById(doctorId);
    }
}
