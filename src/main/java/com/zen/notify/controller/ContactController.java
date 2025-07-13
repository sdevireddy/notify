package com.zen.notify.controller;

import com.zen.notify.dto.ContactDTO;
import com.zen.notify.dto.PaginatedResponse;
import com.zen.notify.entity.Contact;
import com.zen.notify.mapper.ContactMapper;
import com.zen.notify.search.ContactSearchCriteria;
import com.zen.notify.service.ContactService;
import com.zen.notify.service.ContactToDealConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/crm/contacts")
public class ContactController {

    private static final Logger log = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactToDealConverter converter;

    @GetMapping
    public ResponseEntity<PaginatedResponse<?>> getAllContacts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {

        log.info("📄 Fetching all contacts | page: {}, size: {}", page, pageSize);
        Page<Contact> contactPage = contactService.getContactsPaginated(page, pageSize);
        Page<ContactDTO> contactsDtoPage = contactPage.map(ContactMapper::toDto);

        PaginatedResponse<ContactDTO> response = new PaginatedResponse<>(
                contactPage.getTotalElements(),
                contactPage.getSize(),
                contactPage.getNumber(),
                contactPage.getTotalPages(),
                contactsDtoPage.getContent()
        );

        log.info("✅ Retrieved {} contacts", contactsDtoPage.getContent().size());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id) {
        log.info("🔍 Fetching contact with ID: {}", id);
        Optional<Contact> contact = contactService.getContactById(id);

        if (contact.isPresent()) {
            log.info("✅ Contact found for ID: {}", id);
            return ResponseEntity.ok(contact.get());
        } else {
            log.warn("⚠️ Contact not found for ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createContact(@RequestBody ContactDTO contactDto) {
        log.info("📝 Creating new contact: {}", contactDto);

        contactDto.setCreatedAt(new Date());
        contactDto.setUpdatedAt(new Date());

        Contact contact = ContactMapper.fromDto(contactDto);
        try {
            Contact saved = contactService.createContact(contact);
            ContactDTO savedContact = ContactMapper.toDto(saved);
            log.info("✅ Contact created successfully with ID: {}", savedContact.getAccountId());
            return ResponseEntity.ok(savedContact);
        } catch (IllegalArgumentException ex) {
            log.error("❌ Duplicate contact creation attempt: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "error", "Duplicate contact",
                    "message", ex.getMessage()
            ));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id, @RequestBody Contact contactDetails) {
        log.info("✏️ Updating contact with ID: {}", id);
        try {
            Contact updatedContact = contactService.updateContact(id, contactDetails);
            log.info("✅ Contact updated successfully for ID: {}", id);
            return ResponseEntity.ok(updatedContact);
        } catch (RuntimeException e) {
            log.error("❌ Failed to update contact with ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable Long id) {
        log.info("🗑️ Deleting contact with ID: {}", id);
        try {
            contactService.deleteContact(id);
            log.info("✅ Contact deleted successfully with ID: {}", id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                    "message", id + " got deleted"
            ));
        } catch (DataIntegrityViolationException ex) {
            log.error("❌ Cannot delete contact due to active deals or constraints: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "error", "Cannot delete contact",
                    "message", "This contact is associated with active deals. Please reassign or remove the deals before deleting."
            ));
        } catch (Exception e) {
            log.error("❌ Unexpected error while deleting contact: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "error", "Unexpected error",
                    "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchContacts(
            @RequestBody ContactSearchCriteria criteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        log.info("🔎 Searching contacts with criteria: {}, page: {}, size: {}", criteria, page, size);

        Page<Contact> contactPage = contactService.searchContacts(criteria, page, size);
        Page<ContactDTO> contactDTO = contactPage.map(ContactMapper::toDto);

        PaginatedResponse<ContactDTO> response = new PaginatedResponse<>(
                contactPage.getTotalElements(),
                contactPage.getSize(),
                contactPage.getNumber(),
                contactPage.getTotalPages(),
                contactDTO.getContent()
        );

        log.info("✅ Found {} contacts matching search criteria", contactDTO.getContent().size());
        return ResponseEntity.ok(response);
    }
} 
