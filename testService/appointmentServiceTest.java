package com.doctorAppointmentSystem.testService;

import com.doctorAppointmentSystem.model.*;
import com.doctorAppointmentSystem.repository.*;
import com.doctorAppointmentSystem.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.mockito.Mockito.*;

public class appointmentServiceTest {

	@Mock
	private appointmentRepository appointmentRepo;

	@Mock
	private doctorRepository doctorRepo;

	@Mock
	private patientRepository patientRepo;

	@Mock
	private doctorAppointmentTimeRepository doctorAppointmentTimeRepo;

	@InjectMocks
	private appointmentService appointmentSer;

	private doctorAppointmentTime timeSlot;
	private doctor doctor;
	private patient patient;
	private appointment appointment;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		timeSlot = new doctorAppointmentTime();
		timeSlot.setMaxCount(5);
		timeSlot.setBooked(false);

		doctor = new doctor();
		doctor.setDoctorId(1);

		patient = new patient();
		patient.setPatientId(1);

		appointment = new appointment();
		appointment.setAppointmentId(1);
		appointment.setAdminAccept("Pending appointment");
	}

	@Test
	void testAddAppointment_successful() {
		int doctorId = 1;
		int patientId = 1;
		int timeSlotId = 1;

		when(doctorAppointmentTimeRepo.findById(timeSlotId)).thenReturn(Optional.of(timeSlot));
		when(doctorRepo.findById(doctorId)).thenReturn(Optional.of(doctor));
		when(patientRepo.findById(patientId)).thenReturn(Optional.of(patient));

		appointmentSer.addAppointment(doctorId, patientId, timeSlotId);

		verify(appointmentRepo, times(1)).save(any(appointment.class));
		verify(doctorAppointmentTimeRepo, times(1)).save(timeSlot);
	}

	@Test
	void testAddAppointment_noAvailableSlots() {
		int doctorId = 1;
		int patientId = 1;
		int timeSlotId = 1;

		timeSlot.setMaxCount(0);
		timeSlot.setBooked(true);

		when(doctorAppointmentTimeRepo.findById(timeSlotId)).thenReturn(Optional.of(timeSlot));
		try {
			appointmentSer.addAppointment(doctorId, patientId, timeSlotId);
		} catch (IllegalStateException e) {
			assert (e.getMessage().equals("No available slots for this time slot."));
		}
		verify(appointmentRepo, times(0)).save(any(appointment.class));
	}

	@Test
	void testVerifyAppointmentProcess() {
		int appointmentId = 1;
		
		when(appointmentRepo.findById(appointmentId)).thenReturn(Optional.of(appointment));
		appointmentSer.verifyAppointmentProcess(appointmentId);
		assert (appointment.getAdminAccept().equals("Confirmed appointment"));
		verify(appointmentRepo, times(1)).save(appointment);
	}
}
