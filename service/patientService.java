package com.doctorAppointmentSystem.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doctorAppointmentSystem.model.*;
import com.doctorAppointmentSystem.repository.*;

@Service
public class patientService {

	@Autowired private patientRepository patientRepo;

	public void updateUser(int id, patient updatedUser) {
		patient existingUser = patientRepo.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));
		existingUser.setFirstName(updatedUser.getFirstName());
		existingUser.setLastName(updatedUser.getLastName());
		existingUser.setAddress(updatedUser.getAddress());
		existingUser.setPatientEmail(updatedUser.getPatientEmail());
		existingUser.setPatientPhone(updatedUser.getPatientPhone());
		existingUser.setGender(updatedUser.getGender());
		patientRepo.save(existingUser);
	}

	public void deletePatientById(int patientId) {
		patientRepo.deleteById(patientId);
	}
}
