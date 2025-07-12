package com.zen.notify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.zen.notify.entity.Lead;
import com.zen.notify.repository.LeadRepository;
import com.zen.notify.search.LeadSearchCriteria;
import com.zen.notify.search.LeadSpecification;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LeadService {

    private static final Logger logger = LoggerFactory.getLogger(LeadService.class);

    @Autowired
    private LeadRepository leadRepository;

    public List<Lead> getAllLeads() {
        logger.info("Fetching all leads");
        return leadRepository.findAll();
    }

    public Page<Lead> getLeadsPaginated(int page, int pageSize) {
        logger.info("Fetching leads with pagination - page: {}, pageSize: {}", page, pageSize);
        Pageable pageable = PageRequest.of(page, pageSize);
        return leadRepository.findAll(pageable);
    }

    public Lead getLeadById(Long id) {
        logger.info("Fetching lead by ID: {}", id);
        return leadRepository.findById(id).orElse(null);
    }

    public Lead createLead(Lead lead) {
        logger.info("Creating new lead with email: {}", lead.getEmail());

        Optional<Lead> existingContact = Optional.ofNullable(leadRepository.findByEmail(lead.getEmail()));

        if (existingContact.isPresent()) {
            logger.warn("Duplicate lead creation attempt with email: {}", lead.getEmail());
            throw new RuntimeException("Duplicate Lead: A contact with this email already exists.");
        }

        logger.debug("Lead data: {}", lead);
        Lead savedLead = leadRepository.save(lead);
        logger.info("Lead created with ID: {}", savedLead.getId());
        return savedLead;
    }

    public Lead updateLead(Long id, Lead leadDetails) {
        logger.info("Updating lead with ID: {}", id);

        return leadRepository.findById(id).map(lead -> {
            logger.debug("Existing lead found. Updating fields.");

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

            Lead updatedLead = leadRepository.save(lead);
            logger.info("Lead with ID: {} successfully updated", id);
            return updatedLead;
        }).orElseGet(() -> {
            logger.warn("Lead with ID: {} not found for update", id);
            return null;
        });
    }

    public void deleteLead(Long id) {
        logger.info("Deleting lead with ID: {}", id);
        leadRepository.deleteById(id);
        logger.info("Lead with ID: {} deleted", id);
    }

    public Page<Lead> searchLeads(LeadSearchCriteria criteria, int page, int size) {
        logger.info("Searching leads with criteria: {}, page: {}, size: {}", criteria, page, size);
        Pageable pageable = PageRequest.of(page, size);
        Specification<Lead> spec = new LeadSpecification(criteria);
        return leadRepository.findAll(spec, pageable);
    }
}
