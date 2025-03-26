package com.doctorAppointmentSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doctorAppointmentSystem.model.admin;
import com.doctorAppointmentSystem.repository.adminRepository;

@Service
public class adminService {

	@Autowired private adminRepository adminRepo;
	
	public void save(admin admin) {
		adminRepo.save(admin);
	}
}
