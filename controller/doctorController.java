package com.doctorAppointmentSystem.controller;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.doctorAppointmentSystem.repository.*;
import com.doctorAppointmentSystem.service.*;
import com.doctorAppointmentSystem.model.*;

import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;

@Controller
@RequestMapping("/doctor")
public class doctorController {

    private final doctorService doctorSer;
    private final doctorAppointmentService doctorAppointmentSer;
    
    private final doctorRepository doctorRepo;
    private final patientRepository patientRepo;
    private final appointmentRepository appointmentRepo;
    private final doctorAppointmentTimeRepository doctorAppointmentTimeRepo;
    
	public doctorController(doctorService doctorSer, doctorAppointmentService doctorAppointmentSer,
							doctorRepository doctorRepo, patientRepository patientRepo,
							appointmentRepository appointmentRepo, doctorAppointmentTimeRepository doctorAppointmentTimeRepo) {
		super();
		this.doctorSer = doctorSer;
		this.doctorAppointmentSer = doctorAppointmentSer;
		this.doctorRepo = doctorRepo;
		this.patientRepo = patientRepo;
		this.appointmentRepo = appointmentRepo;
		this.doctorAppointmentTimeRepo = doctorAppointmentTimeRepo;
	}
    
    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("doctor", new doctor());
        return "doctorRegistration";
    }
    
    @PostMapping("/submit")
    public String saveDoctor(@ModelAttribute("doctor") doctor doctor,
            @RequestParam("doctorImageFile") MultipartFile doctorImage,
            RedirectAttributes redirectAttributes) {
        try {
            if (!doctorImage.isEmpty()) {
                System.out.println("Image uploaded: " + doctorImage.getOriginalFilename());
                String imagePath = saveImage(doctorImage);
                doctor.setDoctorImage(imagePath);
            }
            doctor.setConfirmStatus("unconfirmed");
            doctorSer.save(doctor);
            redirectAttributes.addFlashAttribute("success", "Doctor registered successfully!");
            return "redirect:/doctor/login";
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error");
            redirectAttributes.addFlashAttribute("error", "Failed to upload image.");
            return "redirect:/doctor/registration";
        }
    }
    
    private String saveImage(MultipartFile file) throws IOException {
        String uploadDir = "src/main/resources/static/images/";
        Path dir = Paths.get(uploadDir);
        Files.createDirectories(dir);
       
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = dir.resolve(fileName);

        Files.write(filePath, file.getBytes());
        return "/images/"+fileName;
    }
    
    @GetMapping("/login")
    public String login(Model model) {
        return "doctorLogin";
    }

    @PostMapping("/loginSubmit")
    public String loginSubmit(@RequestParam(name = "doctorEmail") String email,
                              @RequestParam(name = "doctorPassword") String password,
                              Model model) {
        doctor doctor = doctorRepo.findByDoctorEmail(email);
        if (doctor != null && password.equals(doctor.getPassword()) && doctor.getConfirmStatus().equalsIgnoreCase("confirmed")) {
            return "redirect:/doctor/index/" + doctor.getDoctorId();
        } else {

            model.addAttribute("error", "Invalid email or password");
            System.out.println("Error");
            return "redirect:/doctor/login";
        }
    }
    
    @GetMapping("/index/{id}")
    public String index(Model model,@PathVariable int id) {
        doctor foundDoctor = doctorRepo.findById(id).get();
        model.addAttribute("totalAppointmentList",doctorAppointmentTimeRepo.findByDoctor_DoctorId(foundDoctor.getDoctorId()));
        model.addAttribute("patientLists",patientRepo.findAll());
        model.addAttribute("doctor", foundDoctor);
        model.addAttribute("id",id);
        return "doctorIndex";
    }
    
    @GetMapping("/viewAppointments/{id}")
    public String checkappointment(Model model, @PathVariable int id) {
        doctor foundDoctor = doctorRepo.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Doctor not found with ID: " + id));
        List<appointment> appointmentList = appointmentRepo.findAppointmentByStatusAndDoctorId("Pending appointment", foundDoctor.getDoctorId());
        model.addAttribute("appointmentList", appointmentList);
        model.addAttribute("doctor", foundDoctor);
        model.addAttribute("id", id);
        return "doctorViewAppointments";
    }
    
    @PostMapping("/acceptBtn")
    @Transactional
    public String acceptBtn(@RequestParam("appointmentId") int appointmentId, RedirectAttributes redirectAttributes) {
        appointment foundAppointment = appointmentRepo.findById(appointmentId).orElseThrow(() ->
                new IllegalArgumentException("Appointment not found with ID: " + appointmentId));
        foundAppointment.setStatus("ACCEPTED");
        appointmentRepo.save(foundAppointment);
        redirectAttributes.addFlashAttribute("message", "Appointment accepted successfully!");
        return "redirect:/doctor/viewAppointments/" + foundAppointment.getDoctor().getDoctorId();
    }
    
    @GetMapping("/doctorAddTime/{id}")
    public String doctoraddtime(Model model,@PathVariable int id) {
        doctor foundDoctor = doctorRepo.findById(id).get();
        model.addAttribute("doctor", foundDoctor);
        model.addAttribute("timeSlots",doctorAppointmentTimeRepo.findByDoctor_DoctorId(id));
        model.addAttribute("id",id);
        return "addTimeSlot";
    }

    @PostMapping("/addTimeSlot")
    public String addTimeSlot(@RequestParam("id") int doctorId,
                              @RequestParam("date") String date,
                              @RequestParam("startTime") String startTime,
                              @RequestParam("endTime") String endTime,
                              @RequestParam("maxCount") int maxCount,
                              Model model) {
        try {
            LocalDate parsedDate = LocalDate.parse(date);
            LocalTime parsedStartTime = LocalTime.parse(startTime);
            LocalTime parsedEndTime = LocalTime.parse(endTime);

            doctorAppointmentSer.addTimeSlot(doctorId, parsedDate, parsedStartTime, parsedEndTime,maxCount);
            model.addAttribute("successMessage", "Time slot added successfully!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error adding time slot: " + e.getMessage());
        }
        return "redirect:/doctor/doctorAddTime/"+doctorId;
    }

    @GetMapping("/editTimeSlot/{doctorId}/{timeSlotId}")
    public String editTimeSlot(@PathVariable int doctorId, @PathVariable int timeSlotId, Model model) {
        doctorAppointmentTime timeSlot = doctorAppointmentTimeRepo.findById(timeSlotId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid time slot ID"));
        model.addAttribute("timeSlot", timeSlot);
        model.addAttribute("doctorId", doctorId);
        return "editTimeSlot";
    }

    @GetMapping("/deleteTimeSlot/{doctorId}/{timeSlotId}")
    public String deleteTimeSlot(@PathVariable int doctorId, @PathVariable int timeSlotId, Model model) {
        try {
        	doctorAppointmentSer.deleteTimeSlot(timeSlotId);
            model.addAttribute("successMessage", "Time slot deleted successfully!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error deleting time slot: " + e.getMessage());
        }
        return "redirect:/doctor/doctorAddTime/" + doctorId;
    }
    
    @PostMapping("/updateTimeSlot")
    public String updateTimeSlot(@RequestParam("timeSlotId") int timeSlotId,
                                 @RequestParam("date") String date,
                                 @RequestParam("startTime") String startTime,
                                 @RequestParam("endTime") String endTime,
                                 @RequestParam("maxCount") int maxCount,
                                 @RequestParam("doctorId") int doctorId, 
                                 Model model) {
        try {
            LocalDate parsedDate = LocalDate.parse(date);
            LocalTime parsedStartTime = LocalTime.parse(startTime);
            LocalTime parsedEndTime = LocalTime.parse(endTime);

            doctorAppointmentSer.updateTimeSlot(timeSlotId, parsedDate, parsedStartTime, parsedEndTime, maxCount);
            model.addAttribute("successMessage", "Time slot updated successfully!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error updating time slot: " + e.getMessage());
        }
        return "redirect:/doctor/doctorAddTime/" + doctorId;
    }
    
    @GetMapping("/viewCompletedAppointment/{id}")
    public String viewcompleteappointment(Model model,@PathVariable int id) {
        doctor foundDoctor = doctorRepo.findById(id).get();
        model.addAttribute("appointLists",appointmentRepo.findAppointmentByDoctorAndAdminAccept(foundDoctor,"Confirmed appointment"));
        model.addAttribute("doctor", foundDoctor);
        model.addAttribute("id",id);
        return "viewCompletedAppointments";
    }
    
    @GetMapping("/editProfile/{id}")
    public String editProfile(Model model,@PathVariable int id) {
        doctor foundDoctor = doctorRepo.findById(id).get();
        model.addAttribute("doctor", foundDoctor);
        model.addAttribute("id",id);
        return "doctorEditProfile";
    }

    @PostMapping("/updateProfile/{id}")
    public String updateProfile(@PathVariable int id, @ModelAttribute("doctor") doctor user, RedirectAttributes redirectAttributes) {
        try {
            doctorSer.updateUser(id, user);
            redirectAttributes.addAttribute("id", id);
            return "redirect:/doctor/index/"+id;
        } catch (Exception e) {
            e.printStackTrace();
            return "doctorEditProfile";
        }
    }
}
