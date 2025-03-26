package com.doctorAppointmentSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.doctorAppointmentSystem.model.*;

@Repository
public interface appointmentRepository extends JpaRepository<appointment, Integer>{

    List<appointment> findByPatient(patient patient);

    @Query("SELECT a FROM appointment a WHERE (:status IS NULL AND a.status IS NULL) OR a.status = :status AND a.doctor.doctorId = :doctorId")
    List<appointment> findAppointmentByStatusAndDoctorId(@Param("status") String status, @Param("doctorId") int doctorId);

    List<appointment> findAppointmentByDoctorAndAdminAccept(doctor doctor, String adminAccept);
    List<appointment> findAppointmentByAdminAccept(String adminAccept);
    List<appointment> findAppointmentByStatusNull();
}
