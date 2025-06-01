package com.zen.notify.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.zen.notify.entity.Account;
import com.zen.notify.entity.Contact;
import com.zen.notify.entity.Deal;
import com.zen.notify.service.ContactService;
import com.zen.notify.service.ContactToDealConverter;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;
    
    @Autowired
    private ContactToDealConverter converter;

    @GetMapping
    public List<Contact> getAllContacts() {
        return contactService.getAllContacts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id) {
        Optional<Contact> contact = contactService.getContactById(id);
        return contact.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Contact createContact(@RequestBody Contact contact) {
    	contact.setCreatedAt(new Date());
    	contact.setUpdatedAt(new Date());
    	
        return contactService.createContact(contact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id, @RequestBody Contact contactDetails) {
        try {
            Contact updatedContact = contactService.updateContact(id, contactDetails);
            return ResponseEntity.ok(updatedContact);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
    
//    @PostMapping("/{contactId}/convertToDeal")
//    public ResponseEntity<Deal> convertToDeal(@PathVariable Long contactId) {
//        Deal deal = converter.convertContactToDeal(contactId);
//        return ResponseEntity.ok(deal);
//    }
}
