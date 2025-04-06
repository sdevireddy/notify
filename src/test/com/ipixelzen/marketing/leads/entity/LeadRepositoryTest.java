package com.ipixelzen.marketing.leads.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest // For testing JPA repositories
public class LeadRepositoryTest {

    @Autowired
    private LeadRepository leadRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveAndFindLeadByEmail() {
        // Create a User for AssignedTo
        User user = new User();
        user.setUsername("sales_rep");
        user.setEmail("sales@example.com");
        userRepository.save(user);

        // Create a Lead
        Lead lead = new Lead();
        lead.setLeadName("John Doe");
        lead.setEmail("john.doe@example.com");
        lead.setPhoneNumber("+1234567890");
        lead.setCompanyName("Doe Industries");
        lead.setLeadStatus(LeadStatus.NEW);
        lead.setLeadSource(LeadSource.REFERRAL);
        lead.setAssignedTo(user);
        lead.setNotes("Interested in our product.");

        // Save the lead
        Lead savedLead = leadRepository.save(lead);

        // Find the lead by email
        Optional<Lead> retrievedLead = leadRepository.findByEmail("john.doe@example.com");

        // Assertions
        assertThat(retrievedLead).isPresent();
        assertThat(retrievedLead.get().getLeadName()).isEqualTo("John Doe");
        assertThat(retrievedLead.get().getLeadStatus()).isEqualTo(LeadStatus.NEW);
        assertThat(retrievedLead.get().getAssignedTo().getUsername()).isEqualTo("sales_rep");
    }

    @Test
    public void testUpdateLeadStatus() {
        // Create and Save a Lead
        Lead lead = new Lead();
        lead.setLeadName("Jane Smith");
        lead.setEmail("jane.smith@example.com");
        lead.setPhoneNumber("+9876543210");
        lead.setCompanyName("Smith Corp");
        lead.setLeadStatus(LeadStatus.NEW);
        lead.setLeadSource(LeadSource.WEBSITE);
        lead.setNotes("Requested a demo.");
        leadRepository.save(lead);

        // Retrieve and update the lead
        Optional<Lead> retrievedLead = leadRepository.findByEmail("jane.smith@example.com");
        assertThat(retrievedLead).isPresent();

        Lead updatedLead = retrievedLead.get();
        updatedLead.setLeadStatus(LeadStatus.CONTACTED);
        leadRepository.save(updatedLead);

        // Fetch again and verify
        Optional<Lead> modifiedLead = leadRepository.findByEmail("jane.smith@example.com");
        assertThat(modifiedLead).isPresent();
        assertThat(modifiedLead.get().getLeadStatus()).isEqualTo(LeadStatus.CONTACTED);
    }

    @Test
    public void testDeleteLead() {
        // Create and Save a Lead
        Lead lead = new Lead();
        lead.setLeadName("Alice Johnson");
        lead.setEmail("alice.johnson@example.com");
        lead.setPhoneNumber("+1122334455");
        lead.setCompanyName("Johnson Ltd.");
        lead.setLeadStatus(LeadStatus.QUALIFIED);
        lead.setLeadSource(LeadSource.ADVERTISEMENT);
        lead.setNotes("Needs further discussion.");
        leadRepository.save(lead);

        // Retrieve and delete the lead
        Optional<Lead> retrievedLead = leadRepository.findByEmail("alice.johnson@example.com");
        assertThat(retrievedLead).isPresent();
        leadRepository.delete(retrievedLead.get());

        // Verify deletion
        Optional<Lead> deletedLead = leadRepository.findByEmail("alice.johnson@example.com");
        assertThat(deletedLead).isEmpty();
    }
}
