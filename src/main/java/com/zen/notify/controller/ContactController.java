package com.zen.notify.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zen.notify.dto.ContactDTO;
import com.zen.notify.dto.LeadDTO;
import com.zen.notify.dto.PaginatedResponse;
import com.zen.notify.entity.Account;
import com.zen.notify.entity.Contact;
import com.zen.notify.entity.Deal;
import com.zen.notify.entity.Lead;
import com.zen.notify.mapper.ContactMapper;
import com.zen.notify.mapper.LeadMapper;
import com.zen.notify.search.ContactSearchCriteria;
import com.zen.notify.service.ContactService;
import com.zen.notify.service.ContactToDealConverter;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/crm/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;
    
    @Autowired
    private ContactToDealConverter converter;

	/*
	 * @GetMapping public List<Contact> getAllContacts() { return
	 * contactService.getAllContacts(); }
	 */
    
    @GetMapping
    public ResponseEntity<PaginatedResponse<?>> getAllContacts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {

        Page<Contact> contactPage = contactService.getContactsPaginated(page, pageSize);
        Page<ContactDTO> contactsDtoPage = contactPage.map(ContactMapper::toDto);

        PaginatedResponse<ContactDTO> response = new PaginatedResponse<>(
        		contactPage.getTotalElements(),
        		contactPage.getSize(),
        		contactPage.getNumber(),
        		contactPage.getTotalPages(),
        		contactsDtoPage.getContent()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id) {
        Optional<Contact> contact = contactService.getContactById(id);
        return contact.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createContact(@RequestBody ContactDTO contact) {
        contact.setCreatedAt(new Date());
        contact.setUpdatedAt(new Date());
        Contact savedDTO = ContactMapper.fromDto(contact);

        try {
            Contact saved = contactService.createContact(savedDTO);
            ContactDTO savedContact = ContactMapper.toDto(saved);
            return ResponseEntity.ok(savedContact);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                "error", "Duplicate contact",
                "message", ex.getMessage()
            ));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id, @RequestBody Contact contactDetails) {
        try {
            Contact updatedContact = contactService.updateContact(id, contactDetails);
            ContactDTO savedContact = ContactMapper.toDto(updatedContact);
            return ResponseEntity.ok(updatedContact);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                "message", id +" got deleted "
            ));
       // return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/search")
    public ResponseEntity<?> searchContacts(
            @RequestBody ContactSearchCriteria criteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Contact> contactPage = contactService.searchContacts(criteria, page, size);


        Page<ContactDTO> contactDTO = contactPage.map(ContactMapper::toDto);

        PaginatedResponse<ContactDTO> response = new PaginatedResponse<>(
        		contactPage.getTotalElements(),
        		contactPage.getSize(),
        		contactPage.getNumber(),
        		contactPage.getTotalPages(),
        		contactDTO.getContent()
        );
        return ResponseEntity.ok(response);

    }
//    @PostMapping("/{contactId}/convertToDeal")
//    public ResponseEntity<Deal> convertToDeal(@PathVariable Long contactId) {
//        Deal deal = converter.convertContactToDeal(contactId);
//        return ResponseEntity.ok(deal);
//    }
}
