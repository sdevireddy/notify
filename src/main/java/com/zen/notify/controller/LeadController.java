package com.zen.notify.controller;

import java.util.List;


import lombok.RequiredArgsConstructor;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zen.notify.dto.LeadDTO;
import com.zen.notify.dto.PaginatedResponse;
import com.zen.notify.entity.Contact;
import com.zen.notify.entity.Lead;
import com.zen.notify.mapper.LeadMapper;
import com.zen.notify.search.LeadSearchCriteria;
import com.zen.notify.service.LeadConversionService;
import com.zen.notify.service.LeadService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/leads")
@RequiredArgsConstructor
public class LeadController {

	    @Autowired
	    private LeadService leadService;
	    
	    @Autowired
	    private LeadConversionService leadConversionService;

		/*
		 * @GetMapping public List<Lead> getAllLeads() { return
		 * leadService.getAllLeads(); }
		 */
	    
	    @GetMapping
	    public ResponseEntity<PaginatedResponse<Lead>> getAllLeads(
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "10") int pageSize) {

	        Page<Lead> leadsPage = leadService.getLeadsPaginated(page, pageSize);


	        PaginatedResponse<Lead> response = new PaginatedResponse<>(
	        		leadsPage.getTotalElements(),
	        		leadsPage.getSize(),
	        		leadsPage.getNumber(),
	        		leadsPage.getTotalPages(),
	        		leadsPage.getContent()
	        );


	        return ResponseEntity.ok(response);
	    }
	    
	    @GetMapping("/{id}")
	    public ResponseEntity<Lead> getLeadById(@PathVariable Long id) {
	        Lead lead = leadService.getLeadById(id);
	        return lead != null ? ResponseEntity.ok(lead) : ResponseEntity.notFound().build();
	    }

	    @PostMapping
	    public ResponseEntity<?> createLead(@RequestBody LeadDTO leadDto) {
	    	 try {
	    		 System.out.println("Controller lead " + leadDto.toString());
	    		  Lead lead = LeadMapper.toEntity(leadDto);
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

	    // Search leads
	    
	    @PostMapping("/search")
	    public ResponseEntity<?> searchLeads(
	            @RequestBody LeadSearchCriteria criteria,
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "10") int size) {

	        Page<Lead> leadPage = leadService.searchLeads(criteria, page, size);

	        PaginatedResponse<Lead> response = new PaginatedResponse<>(
	        		leadPage.getTotalElements(),
	        		leadPage.getSize(),
	        		leadPage.getNumber(),
	        		leadPage.getTotalPages(),
	        		leadPage.getContent()
	        );
	        return ResponseEntity.ok(response);
	    }
	    
	    
    
    
}
