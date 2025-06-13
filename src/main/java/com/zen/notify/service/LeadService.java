package com.zen.notify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.zen.notify.entity.Lead;
import com.zen.notify.entity.ZenUser;
import com.zen.notify.repository.LeadRepository;
import com.zen.notify.repository.UserRepository;
import com.zen.notify.search.LeadSearchCriteria;
import com.zen.notify.search.LeadSpecification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LeadService {
	

        @Autowired
	    private LeadRepository leadRepository;

	    public List<Lead> getAllLeads() {
	        return leadRepository.findAll();
	    }

	    public Page<Lead> getLeadsPaginated(int page, int pageSize) {
	        Pageable pageable = PageRequest.of(page, pageSize);
	        return leadRepository.findAll(pageable);
	    }
	    
	    public Lead getLeadById(Long id) {
	        return leadRepository.findById(id).orElse(null);
	    }

	    public Lead createLead(Lead lead) {
	    	System.out.println("Saving lead");
	    	 Optional<Lead> existingContact = Optional.ofNullable(leadRepository.findByEmail(lead.getEmail()));

	         if (existingContact.isPresent()) {
	             throw new RuntimeException("Duplicate Lead: A contact with this email already exists.");
	         }
	         System.out.println("lead" + lead.toString());
	        return leadRepository.save(lead);
	    }

	    public Lead updateLead(Long id, Lead leadDetails) {
	        return leadRepository.findById(id).map(lead -> {
	            lead.setLeadOwner(leadDetails.getLeadOwner());
	            lead.setCompany(leadDetails.getCompany());
	            lead.setFirstName(leadDetails.getFirstName());
	            lead.setLastName(leadDetails.getLastName());
	            lead.setTitle(leadDetails.getTitle());
	            lead.setEmail(leadDetails.getEmail());
	            lead.setFax(leadDetails.getFax());
	            lead.setMobile(leadDetails.getMobile());
	            lead.setWebsite(leadDetails.getWebsite());
	            lead.setLeadSource(leadDetails.getLeadSource());
	            lead.setLeadStatus(leadDetails.getLeadStatus());
	            lead.setIndustry(leadDetails.getIndustry());
	            lead.setNoOfEmployees(leadDetails.getNoOfEmployees());
	            lead.setAnnualRevenue(leadDetails.getAnnualRevenue());
	            lead.setRating(leadDetails.getRating());
	            lead.setEmailOptOut(leadDetails.getEmailOptOut());
	            lead.setSkypeId(leadDetails.getSkypeId());
	            lead.setSecondaryEmail(leadDetails.getSecondaryEmail());
	            lead.setTwitter(leadDetails.getTwitter());
	            lead.setDescription(leadDetails.getDescription());
	            return leadRepository.save(lead);
	        }).orElse(null);
	    }

	    public void deleteLead(Long id) {
	        leadRepository.deleteById(id);
	    }
	    
	    public Page<Lead> searchLeads(LeadSearchCriteria criteria, int page, int size) {
	        Pageable pageable = PageRequest.of(page, size);
	        Specification<Lead> spec = new LeadSpecification(criteria);
	        return leadRepository.findAll(spec, pageable);
	    }

	}

