package com.doctorAppointmentSystem.testService;

import com.doctorAppointmentSystem.model.*;
import com.doctorAppointmentSystem.repository.*;
import com.doctorAppointmentSystem.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

public class adminServiceTest {

    @InjectMocks
    private adminService adminSer;

    @Mock
    private adminRepository adminRepo;

    private admin admin;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        admin = new admin();
        admin.setAdminId(1);
        admin.setAdminName("Admin User");
        admin.setAdminEmail("admin@example.com");
    }

    @Test
    void testSaveAdmin() {
        adminSer.save(admin);
        verify(adminRepo, times(1)).save(admin);
    }
}
