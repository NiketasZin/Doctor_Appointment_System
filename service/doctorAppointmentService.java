package com.doctorAppointmentSystem.service;

import java.time.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doctorAppointmentSystem.model.*;
import com.doctorAppointmentSystem.repository.*;

@Service
public class doctorAppointmentService {

	@Autowired private doctorRepository doctorRepo;
	@Autowired private doctorAppointmentTimeRepository doctorAppointmentTimeRepo;

	public void addTimeSlot(int doctorId, LocalDate date, LocalTime startTime, LocalTime endTime, int maxCount) {
		doctor doctor = doctorRepo.findById(doctorId)
				.orElseThrow(() -> new IllegalArgumentException("Doctor not found with id: " + doctorId));
		doctorAppointmentTime timeSlot = new doctorAppointmentTime(doctor, date, startTime, endTime, false, maxCount);
		doctorAppointmentTimeRepo.save(timeSlot);
	}

	public void updateTimeSlot(int timeSlotId, LocalDate date, LocalTime startTime, LocalTime endTime, int maxCount) {
		doctorAppointmentTime timeSlot = doctorAppointmentTimeRepo.findById(timeSlotId)
				.orElseThrow(() -> new IllegalArgumentException("Time slot not found for ID: " + timeSlotId));
		timeSlot.setDate(date);
		timeSlot.setStartTime(startTime);
		timeSlot.setEndTime(endTime);
		timeSlot.setMaxCount(maxCount);

		doctorAppointmentTimeRepo.save(timeSlot);
	}

	public void deleteTimeSlot(int timeSlotId) {
		if (!doctorAppointmentTimeRepo.existsById(timeSlotId)) {
			throw new IllegalArgumentException("Time slot not found for ID: " + timeSlotId);
		}
		doctorAppointmentTimeRepo.deleteById(timeSlotId);
	}
}
