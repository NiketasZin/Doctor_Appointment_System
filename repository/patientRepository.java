package com.doctorAppointmentSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doctorAppointmentSystem.model.patient;

@Repository
public interface patientRepository extends JpaRepository<patient, Integer>{

    patient findByPatientEmail(String patientEmail);
}
