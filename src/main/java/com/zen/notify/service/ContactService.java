package com.zen.notify.service;

import com.zen.notify.entity.Contact;
import com.zen.notify.repository.ContactRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    // Create a new contact
    public Contact createContact(Contact contact) {
        return contactRepository.save(contact);
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
        Contact contact = contactRepository.getById(contactId);

        contact.setFirstName(updatedContact.getFirstName());
        contact.setLastName(updatedContact.getLastName());
        contact.setEmail(updatedContact.getEmail());
        contact.setPhone(updatedContact.getPhone());
        contact.setLeadSource(updatedContact.getLeadSource());
        contact.setAccount(updatedContact.getAccount());
        contact.setUpdatedAt(new java.util.Date());

        return contactRepository.save(contact);
    }

    // Delete a contact
    public void deleteContact(Long contactId) {
        Contact contact = contactRepository.getById(contactId);
        contactRepository.delete(contact);
    }
}
