package com.doctorAppointmentSystem.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doctorAppointmentSystem.model.*;
import com.doctorAppointmentSystem.repository.*;

@Service
public class doctorService {

	@Autowired private doctorRepository doctorRepo;

	public void save(doctor doctor) {
		doctorRepo.save(doctor);
	}

	public doctor findById(Integer id) {
		return doctorRepo.findById(id).get();
	}

	public List<doctor> findAll() {
		return doctorRepo.findAll();
	}

	public List<doctor> findByKeyword(String keyword) {
		return doctorRepo.findByKeyword(keyword);
	}

	public void verifyDoctorProcess(int id) {
		Optional<doctor> userOpt = doctorRepo.findById(id);
		if (userOpt.isPresent()) {
			doctor doctor = userOpt.get();
			doctor.setConfirmStatus("confirmed");
			doctorRepo.save(doctor);
		}
	}

	public void updateUser(int id, doctor updatedUser) {
	    doctor existingUser = doctorRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Doctor not found"));
	    existingUser.setDoctorName(updatedUser.getDoctorName());
	    existingUser.setDoctorEmail(updatedUser.getDoctorEmail());
	    existingUser.setAbout(updatedUser.getAbout());
	    existingUser.setDoctorAge(updatedUser.getDoctorAge());
	    existingUser.setExperiencedYear(updatedUser.getExperiencedYear());
	    existingUser.setDoctorDegree(updatedUser.getDoctorDegree());
	    doctorRepo.save(existingUser);
	}

	public void deleteDoctorById(Integer id) {
		doctorRepo.deleteById(id);

	}
	
    public doctor findByDoctorEmail(String doctorEmail) {
        return doctorRepo.findByDoctorEmail(doctorEmail);
    }
}