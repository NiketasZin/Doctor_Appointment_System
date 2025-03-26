package com.doctorAppointmentSystem.model;

import jakarta.persistence.*;

@Entity
@Table(name="admins")
public class admin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int adminId;
    @Column(nullable = false) private String adminName;
    @Column(nullable = false, unique = true) private String adminEmail;
    @Column(nullable = false) private String password;
    @Column(nullable = false) private String adminPhone;
    
    public admin() {
    	
    }

	public admin(String adminName, String adminEmail, String password, String adminPhone) {
		super();
		this.adminName = adminName;
		this.adminEmail = adminEmail;
		this.password = password;
		this.adminPhone = adminPhone;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAdminPhone() {
		return adminPhone;
	}

	public void setAdminPhone(String adminPhone) {
		this.adminPhone = adminPhone;
	}
	
    @Override
    public String toString() {
        return "Admin{" + "adminId=" + adminId + ", adminName='" + adminName + '\'' + ", password='" + password + '\'' +
                ", adminEmail='" + adminEmail + '\'' + ", adminPhone='" + adminPhone + '\'' + '}';
    }
}
