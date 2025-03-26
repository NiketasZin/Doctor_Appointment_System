package com.doctorAppointmentSystem.controller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.doctorAppointmentSystem.model.*;
import com.doctorAppointmentSystem.repository.*;
import com.doctorAppointmentSystem.service.*;

@Controller
@RequestMapping("/admin")
public class adminController {

    @Autowired private adminService adminSer;
    @Autowired private doctorService doctorSer;
    @Autowired private patientService patientSer;
    @Autowired private appointmentService appointmentSer;

    @Autowired private adminRepository adminRepo;
    @Autowired private doctorRepository doctorRepo;
    @Autowired private patientRepository patientRepo;
    @Autowired private appointmentRepository appointmentRepo;
 
    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("admin", new admin());
        return "adminRegistration";
    }

    @PostMapping("/submit")
    public String submit(Model model, admin admin) {
        adminSer.save(admin);
        return "redirect:/admin/login";
    }

    @GetMapping("/login")
    public String login() {
        return "adminLogin";
    }

    @PostMapping("/loginSubmit")
    public String loginSubmit(@RequestParam(name = "adminEmail") String adminEmail,
                              @RequestParam(name = "password") String password,
                              RedirectAttributes redirectAttributes, Model model) {
        System.out.println("Admin Email: " + adminEmail);
        System.out.println("Admin Password: " + password);

        admin admin = adminRepo.findByAdminEmail(adminEmail);
        if (admin != null && admin.getPassword().equals(password)) {
            return "redirect:/admin/index";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid email or password");
            return "redirect:/admin/login";
        }
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("appointmentList", appointmentRepo.findAll());
        model.addAttribute("doctorList", doctorRepo.findAll());
        model.addAttribute("patientList", patientRepo.findAll());
        return "adminIndex";
    }
    
    @GetMapping("/viewDoctors")
    public String showDoctors(Model model) {
        model.addAttribute("doctors", doctorRepo.findByConfirmStatus("confirmed"));
        return "viewDoctors";
    }

    @PostMapping("/submitConfirm")
    public String submitConfirm(@RequestParam(name = "doctorId") int doctorId){
        doctor doctor = doctorRepo.findById(doctorId).get();
        doctor.setConfirmStatus("confirmed");
        doctorRepo.save(doctor);
        return "redirect:/admin/viewDoctors";
    }
    
    @GetMapping("/adminEditDoctorProfile/{id}")
    public String editDoctorProfile(Model model, @PathVariable int id) {
        doctor foundDoctor = doctorRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Doctor not found"));
        model.addAttribute("doctor", foundDoctor);
        model.addAttribute("id", id);
        return "adminEditDoctorProfile";
    }
    
    @PostMapping("/doctorProfileSubmit/{id}")
    public String updateDoctorProfile(@PathVariable int id, @ModelAttribute("doctor") doctor updatedDoctor, RedirectAttributes redirectAttributes) {
        try {
            doctorSer.updateUser(id, updatedDoctor);
            redirectAttributes.addFlashAttribute("message", "Doctor profile updated successfully!");
            return "redirect:/admin/viewDoctors";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to update doctor profile. Please try again.");
            return "redirect:/admin/adminEditDoctorProfile/" + id;
        }
    }
    
    @GetMapping("/deleteDoctor/{id}")
    public String deleteDoctor(@PathVariable("id") int doctorId) {
        doctorSer.deleteDoctorById(doctorId);  
        return "redirect:/admin/viewDoctors";  
    }
    
    @GetMapping("/viewDoctorRequests")
    public String showRequestDoctors(Model model) {
        model.addAttribute("doctors", doctorRepo.findByConfirmStatus("unconfirmed"));
        return "viewDoctorRequests";
    }

    @PostMapping("/doctor/save")
    public String saveDoctor(doctor doctor){
        doctor.setConfirmStatus("confirmed");
        doctorRepo.save(doctor);
        return "redirect:/admin/viewDoctors";
    }
    
    @GetMapping("/verify/yes/{id}")
    public String verifyUser(@PathVariable("id") int id, Model model) {
        doctorSer.verifyDoctorProcess(id);
        return "redirect:/admin/viewDoctorRequests";
    }
    
    @GetMapping("/viewAppointments")
    public String viewAppointments(Model model) {
        model.addAttribute("appointmentList",appointmentRepo.findAppointmentByAdminAccept("Pending appointment"));
        return "adminViewAppointments";
    }
    
    @GetMapping("/verify/appointment/{id}")
    public String verifyAppointment(@PathVariable("id")int id, Model model) {
        appointmentSer.verifyAppointmentProcess(id);
        return "redirect:/admin/viewAppointments";
    }
    
    @GetMapping("/viewHistory")
    public String viewHistory(Model model) {
        model.addAttribute("appointmentLists",appointmentRepo.findAppointmentByAdminAccept("Confirmed appointment"));
        return "viewHistory";
    }
    
    @GetMapping("/viewPatients")
    public String viewPatient(Model model) {
        model.addAttribute("patientList",patientRepo.findAll());
        return "viewPatients";
    }
    
    @GetMapping("/deletePatient/{id}")
    public String deletePatient(@PathVariable("id") int patientId) {
        patientSer.deletePatientById(patientId);
        return "redirect:/admin/viewPatients";  
    }
}
