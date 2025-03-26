package com.doctorAppointmentSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doctorAppointmentSystem.model.admin;

@Repository
public interface adminRepository extends JpaRepository<admin, Integer>{

	admin findByAdminEmail(String adminEmail);
}
