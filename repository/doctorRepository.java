package com.doctorAppointmentSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.doctorAppointmentSystem.model.doctor;

@Repository
public interface doctorRepository extends JpaRepository<doctor, Integer>{

    doctor findByDoctorEmail(String doctorEmail);

    @Query("SELECT d FROM doctor d WHERE d.doctorName LIKE %?1% OR d.specialization LIKE %?1% OR d.doctorEmail LIKE %?1%")
    List<doctor> findByKeyword(String keyword);

    List<doctor> findByConfirmStatus(String confirmStatus);
}
