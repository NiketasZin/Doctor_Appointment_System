package com.doctorAppointmentSystem.model;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name="appointments")
public class appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false) private int appointmentId;
    @Column(nullable = false) private Date appointmentDate;
    @Column(nullable = false) private String appointmentTime;
    @Column(nullable = true) private String status;
    @Column(nullable = true) private String adminAccept;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id",referencedColumnName = "doctorId",nullable = false)
    private doctor doctor;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "time_slot_id", referencedColumnName = "timeSlotId", nullable = false)
    private doctorAppointmentTime doctorAppointmentTime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id", referencedColumnName = "patientId", nullable = false)
    private patient patient;

    public appointment() {
    	
    }

	public appointment(Date appointmentDate, String appointmentTime, String status, String adminAccept, Date created_at,
			com.doctorAppointmentSystem.model.doctor doctor,
			com.doctorAppointmentSystem.model.doctorAppointmentTime doctorAppointmentTime,
			com.doctorAppointmentSystem.model.patient patient) {
		super();
		this.appointmentDate = appointmentDate;
		this.appointmentTime = appointmentTime;
		this.status = status;
		this.adminAccept = adminAccept;
		this.created_at = created_at;
		this.doctor = doctor;
		this.doctorAppointmentTime = doctorAppointmentTime;
		this.patient = patient;
	}

	public int getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}

	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public String getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(String appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAdminAccept() {
		return adminAccept;
	}

	public void setAdminAccept(String adminAccept) {
		this.adminAccept = adminAccept;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(doctor doctor) {
		this.doctor = doctor;
	}

	public doctorAppointmentTime getDoctorAppointmentTime() {
		return doctorAppointmentTime;
	}

	public void setDoctorAppointmentTime(doctorAppointmentTime doctorAppointmentTime) {
		this.doctorAppointmentTime = doctorAppointmentTime;
	}

	public patient getPatient() {
		return patient;
	}

	public void setPatient(patient patient) {
		this.patient = patient;
	}
}
