package com.doctorAppointmentSystem.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="patients")
public class patient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int patientId;

    @Column(nullable = false) private String firstName;
    @Column(nullable = false) private String lastName;
    @Column private String patientName;
    @Column(nullable = false, unique = true) private String patientEmail;
    @Column(nullable = false) private String patientPhone;
    @Column(nullable = false) private String address;
    @Column(nullable = false) private String password;
    
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<appointment> patientAppointments;

    @PrePersist
    @PreUpdate
    private void generateFullName() {
        this.patientName = firstName + " " + lastName;
    }

    public enum Gender {
        MALE, FEMALE
    }
    
    public patient() {
    	
    }

	public patient(String firstName, String lastName, String patientName, String patientEmail, String patientPhone,
			String address, String password, Gender gender, List<appointment> patientAppointments) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.patientName = patientName;
		this.patientEmail = patientEmail;
		this.patientPhone = patientPhone;
		this.address = address;
		this.password = password;
		this.gender = gender;
		this.patientAppointments = patientAppointments;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getPatientEmail() {
		return patientEmail;
	}

	public void setPatientEmail(String patientEmail) {
		this.patientEmail = patientEmail;
	}

	public String getPatientPhone() {
		return patientPhone;
	}

	public void setPatientPhone(String patientPhone) {
		this.patientPhone = patientPhone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public List<appointment> getPatientAppointments() {
		return patientAppointments;
	}

	public void setPatientAppointments(List<appointment> patientAppointments) {
		this.patientAppointments = patientAppointments;
	}

    @Override
    public String toString() {
        return "Patient{" + "patientId=" + patientId + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' +
                ", patientName='" + patientName + '\'' + ", email='" + patientEmail + '\'' + ", phone='" + patientPhone + '\'' +
                ", address='" + address + '\'' + ", gender=" + gender + ", password='" + password + '\'' +
                ", PatientAppointments=" + patientAppointments + '}';
    }
}
