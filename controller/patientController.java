package com.doctorAppointmentSystem.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.doctorAppointmentSystem.model.*;
import com.doctorAppointmentSystem.repository.*;
import com.doctorAppointmentSystem.service.*;

@Controller
@RequestMapping("/patient")
public class patientController {

    private final patientService patientSer;
    private final doctorService doctorSer;
    private final appointmentService appointmentSer;
    
    private final patientRepository patientRepo;
    private final appointmentRepository appointmentRepo;
    
	public patientController(patientService patientSer, doctorService doctorSer, appointmentService appointmentSer,
			patientRepository patientRepo, appointmentRepository appointmentRepo) {
		super();
		this.patientSer = patientSer;
		this.doctorSer = doctorSer;
		this.appointmentSer = appointmentSer;
		this.patientRepo = patientRepo;
		this.appointmentRepo = appointmentRepo;
	}
    
    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("patient", new patient());
        return "patientRegistration";
    }

	@PostMapping("/submit")
	public String submit(Model model, patient patient) {
	    patientRepo.save(patient);
	    return "redirect:/patient/login";
	}
	
    @GetMapping("/login")
    public String login(Model model) {
        return "patientLogin";
    }

    @PostMapping("/loginSubmit")
    public String loginSubmit(@RequestParam(name = "patientEmail") String email,
                              @RequestParam(name = "patientPassword") String password,
                              RedirectAttributes redirectAttributes, Model model) {
        patient patient = patientRepo.findByPatientEmail(email);
        if(patient != null) {
            return "redirect:/patient/index/"+patient.getPatientId();
        }
        else{
            return "redirect:/patient/login";
        }
    }
    
    @GetMapping("/index/{id}")
    public String index(Model model,@PathVariable int id) {
        patient foundPatient = patientRepo.findById(id).orElse(null);
        model.addAttribute("patient", foundPatient);
        model.addAttribute("id",id);
        return "patientIndex";
    }
    
    @GetMapping("/aboutUs/{id}")
    public String about(Model model,@PathVariable int id) {
        patient foundPatient = patientRepo.findById(id).orElse(null);
        model.addAttribute("patient", foundPatient);
        model.addAttribute("id",id);
        return "aboutUs";
    }
    
    @GetMapping("/medicalServices/{id}")
    public String treatment(Model model,@PathVariable int id) {
        patient foundPatient = patientRepo.findById(id).orElse(null);
        model.addAttribute("patient", foundPatient);
        model.addAttribute("id",id);
        return "medicalServices";
    }
    
    @GetMapping("/doctors/{id}")
    public String doctors(Model model,@PathVariable int id,@RequestParam(name = "keyword", required = false) String keyword) {
        List<doctor> doctorList;
        if(keyword != null) {
            doctorList = doctorSer.findByKeyword(keyword);
        } else {
            doctorList = doctorSer.findAll();
        }
        model.addAttribute("doctors", doctorList);
        patient foundPatient = patientRepo.findById(id).orElse(null);
        model.addAttribute("patient", foundPatient);
        model.addAttribute("id",id);
        return "doctors";
    }
    
    @GetMapping("/singleDoctor/{id}/{doctorId}")
    public String singlePage(Model model, @PathVariable("id")int patientId, @PathVariable("doctorId") int doctorId) {
        doctor doctor = doctorSer.findById(doctorId);
        patient foundPatient = patientRepo.findById(patientId).get();
        System.out.println(foundPatient.getPatientId());
        System.out.println(doctor.getDoctorId());
        model.addAttribute("doctor", doctor);
        model.addAttribute("patient", foundPatient);
        model.addAttribute("patientId", patientId);
        return "singleDoctor";
    }
    
    @PostMapping("/booking")
    public String bookAppointment(
            @RequestParam("patientId") int patientId,
            @RequestParam("doctorId") int doctorId,
            @RequestParam("timeSlotId") int timeSlotId,
            RedirectAttributes redirectAttributes) {
        try {
            appointmentSer.addAppointment(doctorId, patientId, timeSlotId);
            return "redirect:/patient/index/" + patientId;
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/patient/singleDoctor/" + patientId + "/" + doctorId;
        }
    }
    
    @GetMapping("/appointments/{id}")
    public String timetable(Model model,@PathVariable int id) {
        patient foundPatient = patientRepo.findById(id).orElse(null);
        model.addAttribute("patient", foundPatient);
        model.addAttribute("id",id);
        model.addAttribute("appointmentList",appointmentRepo.findByPatient(foundPatient));
        return "appointments";
    }
    
    @GetMapping("/contactUs/{id}")
    public String contactUs(Model model,@PathVariable int id) {
        patient contactUs = patientRepo.findById(id).orElse(null);
        model.addAttribute("patient", contactUs);
        model.addAttribute("id",id);
        return "contactUs";
    }
    
    @GetMapping("/editProfile/{id}")
    public String editProfile(Model model,@PathVariable int id) {
        patient foundPatient = patientRepo.findById(id).get();
        model.addAttribute("patient", foundPatient);
        model.addAttribute("id",id);
        return "profile";
    }

    @PostMapping("/updateProfile/{id}")
    public String updateProfile(@PathVariable int id, @ModelAttribute("patient") patient user, RedirectAttributes redirectAttributes) {
        try {
            patientSer.updateUser(id, user);
            redirectAttributes.addAttribute("id", id);
            return "redirect:/patient/index/"+id;
        } catch (Exception e) {
            e.printStackTrace();
            return "profile";
        }
    }
}
