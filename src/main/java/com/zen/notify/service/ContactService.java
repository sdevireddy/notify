package com.zen.notify.service;

import com.zen.notify.dto.ContactDTO;
import com.zen.notify.entity.Contact;
import com.zen.notify.repository.ContactRepository;
import com.zen.notify.search.ContactSearchCriteria;
import com.zen.notify.search.ContactSpecification;

import jakarta.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger log = LoggerFactory.getLogger(ContactService.class);

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    // Create Contact
    public Contact createContact(Contact contact) {
        Optional<Contact> existing = contactRepository.findByEmail(contact.getEmail());
        if (existing.isPresent()) {
            log.warn("⚠️ Attempted to create duplicate contact with email: {}", contact.getEmail());
            throw new IllegalArgumentException("Contact with this email already exists.");
        }
        Contact saved = contactRepository.save(contact);
        log.info("✅ Contact created with ID={}, email={}", saved.getContactId(), saved.getEmail());
        return saved;
    }

    // Get Paginated Contacts
    public Page<Contact> getContactsPaginated(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Contact> result = contactRepository.findAll(pageable);
        log.info("📄 Fetched paginated contacts: Page={}, Size={}, TotalElements={}", page, pageSize, result.getTotalElements());
        return result;
    }

    // Retrieve all contacts
    public List<Contact> getAllContacts() {
        List<Contact> contacts = contactRepository.findAll();
        log.info("📦 Retrieved all contacts: Count={}", contacts.size());
        return contacts;
    }

    // Retrieve a contact by ID
    public Optional<Contact> getContactById(Long contactId) {
        Optional<Contact> contactOpt = contactRepository.findById(contactId);
        if (contactOpt.isPresent()) {
            log.info("🔍 Contact found with ID={}", contactId);
        } else {
            log.warn("⚠️ Contact not found with ID={}", contactId);
        }
        return contactOpt;
    }

    public Optional<Contact> findById(Long contactId) {
        return getContactById(contactId); // reuse logging above
    }

    // Update Contact
    @Transactional
    public Contact updateContact(Long contactId, Contact updatedContact) {
        log.info("🛠️ Updating contact ID={}", contactId);
        Contact contact = contactRepository.findById(contactId)
            .orElseThrow(() -> {
                log.error("❌ Contact not found for update: ID={}", contactId);
                return new EntityNotFoundException("Contact with ID " + contactId + " not found");
            });

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
        contact.setUpdatedAt(new Date());

        Contact saved = contactRepository.save(contact);
        log.info("✅ Contact updated: ID={}", saved.getContactId());
        return saved;
    }

    // Delete Contact
    @Transactional
    public void deleteContact(Long contactId) {
        log.info("🗑️ Deleting contact ID={}", contactId);
        Contact contact = contactRepository.findById(contactId)
            .orElseThrow(() -> {
                log.error("❌ Cannot delete - Contact not found with ID={}", contactId);
                return new EntityNotFoundException("Contact with ID " + contactId + " not found");
            });

        contactRepository.delete(contact);
        log.info("✅ Contact deleted successfully ID={}", contactId);
    }

    // Search Contacts
    public Page<Contact> searchContacts(ContactSearchCriteria criteria, int page, int size) {
        log.info("🔎 Searching contacts with criteria={}, page={}, size={}", criteria, page, size);
        Pageable pageable = PageRequest.of(page, size);
        Specification<Contact> spec = new ContactSpecification(criteria);
        Page<Contact> results = contactRepository.findAll(spec, pageable);
        log.info("✅ Contact search returned {} results", results.getTotalElements());
        return results;
    }
}
