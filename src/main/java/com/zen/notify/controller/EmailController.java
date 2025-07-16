package com.zen.notify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zen.notify.dto.EmailTemplateDTO;
import com.zen.notify.email.EmailService;
import com.zen.notify.service.EmailTemplateService;

import jakarta.servlet.http.HttpServletRequest;




@RestController
@RequestMapping("/crm/email-templates")
public class EmailController {

    @Autowired
    private EmailTemplateService service;

    @PostMapping("create")
    public ResponseEntity<?> createTemplate(@RequestBody EmailTemplateDTO dto, HttpServletRequest request) {
        String createdBy = request.getUserPrincipal().getName(); // Or fetch user manually
        return ResponseEntity.ok(service.createTemplate(dto, createdBy));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTemplate(@PathVariable Long id, @RequestBody EmailTemplateDTO dto) {
        return ResponseEntity.ok(service.updateTemplate(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTemplate(@PathVariable Long id) {
        service.deleteTemplate(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<?> getTemplates(HttpServletRequest request) {
        String createdBy = request.getUserPrincipal().getName();
        return ResponseEntity.ok(service.getTemplates(createdBy));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTemplateById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getTemplateById(id));
    }
    
    
	/*
	 * @PostMapping("/send") public String sendEmail(
	 * 
	 * @RequestParam String to,
	 * 
	 * @RequestParam String subject,
	 * 
	 * @RequestParam String body) { service.sendSimpleEmail(to, subject, body);
	 * return "Email sent successfully!"; }
	 */
}
