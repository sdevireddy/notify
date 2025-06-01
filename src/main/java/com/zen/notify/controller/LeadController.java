package com.zen.notify.controller;

import java.util.List;


import lombok.RequiredArgsConstructor;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.zen.notify.entity.Contact;
import com.zen.notify.entity.Lead;
import com.zen.notify.service.LeadConversionService;
import com.zen.notify.service.LeadService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/leads")
@RequiredArgsConstructor
public class LeadController {

	    @Autowired
	    private LeadService leadService;
	    
	    @Autowired
	    private LeadConversionService leadConversionService;

	    @GetMapping
	    public List<Lead> getAllLeads() {
	        return leadService.getAllLeads();
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Lead> getLeadById(@PathVariable Long id) {
	        Lead lead = leadService.getLeadById(id);
	        return lead != null ? ResponseEntity.ok(lead) : ResponseEntity.notFound().build();
	    }

	    @PostMapping
	    public ResponseEntity<?> createLead(@RequestBody Lead lead) {
	    	 try {
	             Lead createdContact = leadService.createLead(lead);
	             return ResponseEntity.status(HttpStatus.SC_CREATED).body(createdContact);
	         } catch (RuntimeException e) {
	             return ResponseEntity.status(HttpStatus.SC_CONFLICT).body(e.getMessage()); // HTTP 409 Conflict
	         }
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Lead> updateLead(@PathVariable Long id, @RequestBody Lead leadDetails) {
	        Lead updatedLead = leadService.updateLead(id, leadDetails);
	        return updatedLead != null ? ResponseEntity.ok(updatedLead) : ResponseEntity.notFound().build();
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteLead(@PathVariable Long id) {
	        leadService.deleteLead(id);
	        return ResponseEntity.noContent().build();
	    }
	
	    @PostMapping("/{leadId}/convert")
	    public ResponseEntity<Contact> convertLead(@PathVariable Long leadId) {
	        Contact convertedContact = leadConversionService.convertLeadToContact(leadId);
	        return ResponseEntity.ok(convertedContact);
	    }


    
    
}
