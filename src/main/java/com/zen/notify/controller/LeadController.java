package com.zen.notify.controller;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

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

import jakarta.persistence.EntityNotFoundException;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/crm/leads")
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
	    public ResponseEntity<PaginatedResponse<LeadDTO>> getAllLeads(
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "10") int pageSize) {

	        Page<Lead> leadsPage = leadService.getLeadsPaginated(page, pageSize);
	        Page<LeadDTO> leadsDtoPage = leadsPage.map(LeadMapper::toDto);


	        PaginatedResponse<LeadDTO> response = new PaginatedResponse<>(
	        		leadsPage.getTotalElements(),
	        		leadsPage.getSize(),
	        		leadsPage.getNumber(),
	        		leadsPage.getTotalPages(),
	        		leadsDtoPage.getContent()
	        );


	        return ResponseEntity.ok(response);
	    }
	    
	    @GetMapping("/{id}")
	    public ResponseEntity<?> getLeadById(@PathVariable Long id) {
	        System.out.println("Id is getting called");

	        try {
	            Lead lead = leadService.getLeadById(id);

	            if (lead == null) {
	                return ResponseEntity.status(HttpStatus.SC_NOT_FOUND)
	                    .body(Map.of("error", "Lead not found", "message", "No lead exists with id: " + id));
	            }

	            System.out.println("Lead service called");
	            LeadDTO leadDto = LeadMapper.toDto(lead);
	            return ResponseEntity.ok(leadDto);

	        } catch (NoSuchElementException | EntityNotFoundException ex) {
	            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND)
	                .body(Map.of("error", "Lead not found", "message", ex.getMessage()));
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
	                .body(Map.of("error", "Unexpected error", "message", ex.getMessage()));
	        }
	    }

	    @PostMapping("/create")
	    public ResponseEntity<?> createLead(@RequestBody LeadDTO leadDto) {
	    	 try {
	    		 System.out.println("Controller lead " + leadDto.toString());
	    		  Lead lead = LeadMapper.toEntity(leadDto);
	             Lead createdLead = leadService.createLead(lead);
	             LeadDTO savedLeadDto = LeadMapper.toDto(createdLead);
	             return ResponseEntity.status(HttpStatus.SC_CREATED).body(savedLeadDto);
	         } catch (RuntimeException e) {
	        	 return ResponseEntity.status(HttpStatus.SC_CONFLICT).body(Map.of(
	                     "error", "Duplicate Lead",
	                     "message", e.getMessage()
	                 ));
	             //return ResponseEntity.status(HttpStatus.SC_CONFLICT).body(e.getMessage()); // HTTP 409 Conflict
	         }
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<?> updateLead(@PathVariable Long id, @RequestBody Lead leadDetails) {
	        Lead updatedLead = leadService.updateLead(id, leadDetails);
	        try {
	        	  LeadDTO savedLeadDto = LeadMapper.toDto(updatedLead);
	  	        return updatedLead != null ? ResponseEntity.ok(savedLeadDto) : ResponseEntity.notFound().build();
	        }catch(Exception aEx) {
	        	 return ResponseEntity.status(HttpStatus.SC_CONFLICT).body(Map.of(
	                     "error", "Lead Update Failed!!!",
	                     "message", aEx.getMessage()
	                 ));
	        }
	      
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
	    	try {
	    		 Page<Lead> leadPage = leadService.searchLeads(criteria, page, size);
	    		   Page<LeadDTO> leadsDtoPage = leadPage.map(LeadMapper::toDto);

	 	        PaginatedResponse<LeadDTO> response = new PaginatedResponse<>(
	 	        		leadPage.getTotalElements(),
	 	        		leadPage.getSize(),
	 	        		leadPage.getNumber(),
	 	        		leadPage.getTotalPages(),
	 	        		leadsDtoPage.getContent()
	 	        );
	 	        return ResponseEntity.ok(response);
	    	}catch(Exception aEx) {
	    		return ResponseEntity.status(HttpStatus.SC_CONFLICT).body(Map.of(
	                     "error", "Lead Search Failed!!",
	                     "message", aEx.getMessage()));
	    	}

	       
	    }
	    
	    
    
    
}
