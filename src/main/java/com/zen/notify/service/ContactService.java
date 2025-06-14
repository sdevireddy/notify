package com.zen.notify.service;

import com.zen.notify.dto.ContactDTO;
import com.zen.notify.entity.Account;
import com.zen.notify.entity.Contact;
import com.zen.notify.entity.Deal;
import com.zen.notify.entity.Lead;
import com.zen.notify.entity.LeadSource;
import com.zen.notify.repository.ContactRepository;
import com.zen.notify.search.ContactSearchCriteria;
import com.zen.notify.search.ContactSpecification;
import com.zen.notify.search.LeadSearchCriteria;
import com.zen.notify.search.LeadSpecification;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public Contact createContact(Contact contact) {
        Optional<Contact> existing = contactRepository.findByEmail(contact.getEmail());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Contact with this email already exists.");
        }
        return contactRepository.save(contact);
    }
    
    public Page<Contact> getContactsPaginated(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return contactRepository.findAll(pageable);
    }


    // Retrieve all contacts
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    // Retrieve a single contact by ID
    public Optional<Contact> getContactById(Long contactId) {
        return contactRepository.findById(contactId);
         
    }

    // Update an existing contact
    @Transactional
    public Contact updateContact(Long contactId, Contact updatedContact) {
    	 Contact contact = contactRepository.findById(contactId)
    		        .orElseThrow(() -> new EntityNotFoundException("Contact with ID " + contactId + " not found"));
        contact.setContactOwner(updatedContact.getContactOwner());
        contact.setLeadSource(updatedContact.getLeadSource());
        contact.setFirstName(updatedContact.getFirstName());
        contact.setLastName(updatedContact.getLastName());
        contact.setAccount(updatedContact.getAccount());
        contact.setVendorName(updatedContact.getVendorName());
        contact.setEmail(updatedContact.getEmail());
        contact.setTitle(updatedContact.getTitle());
        contact.setPhone(updatedContact.getPhone());
        contact.setDepartment(updatedContact.getDepartment());
        contact.setOtherPhone(updatedContact.getOtherPhone());
        contact.setHomePhone(updatedContact.getHomePhone());
        contact.setMobile(updatedContact.getMobile());
        contact.setDateOfBirth(updatedContact.getDateOfBirth());
        contact.setAssistant(updatedContact.getAssistant());
        contact.setAsstPhone(updatedContact.getAsstPhone());
        contact.setEmailOptOut(updatedContact.getEmailOptOut());
        contact.setSkypeId(updatedContact.getSkypeId());
        contact.setSecondaryEmail(updatedContact.getSecondaryEmail());
        contact.setTwitterHandle(updatedContact.getTwitterHandle());
        contact.setReportingTo(updatedContact.getReportingTo());

        // System-managed fields
        contact.setUpdatedAt(new Date());

        return contactRepository.save(contact);
    }

    @Transactional
    public void deleteContact(Long contactId) {
        Contact contact = contactRepository.findById(contactId)
            .orElseThrow(() -> new EntityNotFoundException("Contact with ID " + contactId + " not found"));

        contactRepository.delete(contact);
    }
    
    public Page<Contact> searchContacts(ContactSearchCriteria criteria, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Contact> spec = new ContactSpecification(criteria);
        return contactRepository.findAll(spec, pageable);
    }


	public Optional<Contact> findById(Long contactId) {
		return contactRepository.findById(contactId);
	}



    
}
