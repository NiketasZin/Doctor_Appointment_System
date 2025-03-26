package com.doctorAppointmentSystem.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="doctors")
public class doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int doctorId;
    @Column(nullable = false) private String doctorName;
    @Column(nullable = false, unique = true) private String doctorEmail;
    @Column(nullable = false) private String doctorDegree;
    @Column(nullable = false) private String specialization;
    @Column(nullable = false) private String doctorAge;
    @Column(nullable = false) private String experiencedYear;
    @Column(nullable = false) private String licenseNumber;
    @Column private String about;
    @Column(nullable = false) private String password; 
    @Column private String confirmStatus;
    private String doctorImage;

    @OneToMany(mappedBy = "doctor",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<appointment> appointments;
    
    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<doctorAppointmentTime> appointmentTimes;
    
    public doctor() {
    	
    }

	public doctor(String doctorName, String doctorEmail, String doctorDegree, String specialization, String doctorAge,
			String experiencedYear, String licenseNumber, String about, String password, String confirmStatus,
			String doctorImage, List<appointment> appointments, List<doctorAppointmentTime> appointmentTimes) {
		super();
		this.doctorName = doctorName;
		this.doctorEmail = doctorEmail;
		this.doctorDegree = doctorDegree;
		this.specialization = specialization;
		this.doctorAge = doctorAge;
		this.experiencedYear = experiencedYear;
		this.licenseNumber = licenseNumber;
		this.about = about;
		this.password = password;
		this.confirmStatus = confirmStatus;
		this.doctorImage = doctorImage;
		this.appointments = appointments;
		this.appointmentTimes = appointmentTimes;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getDoctorEmail() {
		return doctorEmail;
	}

	public void setDoctorEmail(String doctorEmail) {
		this.doctorEmail = doctorEmail;
	}

	public String getDoctorDegree() {
		return doctorDegree;
	}

	public void setDoctorDegree(String doctorDegree) {
		this.doctorDegree = doctorDegree;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getDoctorAge() {
		return doctorAge;
	}

	public void setDoctorAge(String doctorAge) {
		this.doctorAge = doctorAge;
	}

	public String getExperiencedYear() {
		return experiencedYear;
	}

	public void setExperiencedYear(String experiencedYear) {
		this.experiencedYear = experiencedYear;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public String getDoctorImage() {
		return doctorImage;
	}

	public void setDoctorImage(String doctorImage) {
		this.doctorImage = doctorImage;
	}

	public List<appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<appointment> appointments) {
		this.appointments = appointments;
	}

	public List<doctorAppointmentTime> getAppointmentTimes() {
		return appointmentTimes;
	}

	public void setAppointmentTimes(List<doctorAppointmentTime> appointmentTimes) {
		this.appointmentTimes = appointmentTimes;
	}
}
