package com.doctorAppointmentSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doctorAppointmentSystem.model.*;

@Repository
public interface doctorAppointmentTimeRepository extends JpaRepository<doctorAppointmentTime, Integer>{

	List<doctorAppointmentTime> findByDoctor_DoctorId(Integer doctorId);
}
