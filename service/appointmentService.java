package com.doctorAppointmentSystem.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doctorAppointmentSystem.model.*;
import com.doctorAppointmentSystem.repository.*;

@Service
public class appointmentService {

    @Autowired private appointmentRepository appointmentRepo;
    @Autowired private doctorRepository doctorRepo;
    @Autowired private patientRepository patientRepo;
    @Autowired private doctorAppointmentTimeRepository doctorAppointmentTimeRepo;
    
	public void addAppointment(int doctorId, int patientId, int timeSlotId) {
		Optional<doctorAppointmentTime> doctorAppointTimeOpt = doctorAppointmentTimeRepo.findById(timeSlotId);

		if (doctorAppointTimeOpt.isPresent()) {
			doctorAppointmentTime doctorAppointTime = doctorAppointTimeOpt.get();

			if (doctorAppointTime.getMaxCount() > 0) {
				appointment appointment = new appointment();
				appointment.setDoctorAppointmentTime(doctorAppointTime);
				appointment.setCreated_at(new Date());
				appointment.setPatient(patientRepo.findById(patientId).get());
				appointment.setDoctor(doctorRepo.findById(doctorId).get());
				appointment.setAppointmentTime("Created");
				appointment.setStatus("Pending appointment");
				appointment.setAppointmentDate(new Date());
				appointment.setAdminAccept("Pending appointment");
				appointmentRepo.save(appointment);

				doctorAppointTime.setMaxCount(doctorAppointTime.getMaxCount() - 1);

				if (doctorAppointTime.getMaxCount() == 0) {
					doctorAppointTime.setBooked(true);
				}
				doctorAppointmentTimeRepo.save(doctorAppointTime);
			} else {
				throw new IllegalStateException("No available slots for this time slot.");
			}
		} else {
			throw new IllegalArgumentException("Time slot with ID " + timeSlotId + " not found.");
		}
	}

    public void verifyAppointmentProcess(int id) {
		Optional<appointment> foundAppoint = appointmentRepo.findById(id);
        if (foundAppoint.isPresent()) {
            appointment appointment = foundAppoint.get();
            appointment.setAdminAccept("Confirmed appointment");
            appointmentRepo.save(appointment);
        }
    }
}
