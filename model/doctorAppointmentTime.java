package com.doctorAppointmentSystem.model;

import java.time.*;

import jakarta.persistence.*;

@Entity
@Table(name="doctorTimeSlot")
public class doctorAppointmentTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false) private int timeSlotId;
    @Column(nullable = false) private LocalDate date;
    @Column(nullable = false) private LocalTime startTime;
    @Column(nullable = false) private LocalTime endTime;
    @Column(nullable = false) private boolean isBooked = false;
    private int maxCount;
    
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private doctor doctor;
    
    public doctorAppointmentTime() {
    	
    }

	public doctorAppointmentTime(doctor doctor, LocalDate date, LocalTime startTime, LocalTime endTime, boolean isBooked, int maxCount
			) {
		super();
		this.doctor = doctor;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.isBooked = isBooked;
		this.maxCount = maxCount;
	}

	public int getTimeSlotId() {
		return timeSlotId;
	}

	public void setTimeSlotId(int timeSlotId) {
		this.timeSlotId = timeSlotId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public boolean isBooked() {
		return isBooked;
	}

	public void setBooked(boolean isBooked) {
		this.isBooked = isBooked;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	public doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(doctor doctor) {
		this.doctor = doctor;
	}
}
