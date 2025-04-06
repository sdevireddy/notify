package com.zen.notify.service;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zen.notify.entity.Account;
import com.zen.notify.entity.Contact;
import com.zen.notify.entity.Deal;
import com.zen.notify.entity.Deal.DealType;
import com.zen.notify.entity.Deal.Stage;
import com.zen.notify.entity.Lead;
import com.zen.notify.entity.LeadSource;
import com.zen.notify.repository.AccountRepository;
import com.zen.notify.repository.ContactRepository;
import com.zen.notify.repository.DealRepository;
import com.zen.notify.repository.LeadRepository;

@Service
public class LeadConversionService {
	    
@Autowired
private LeadRepository leadRepository;

@Autowired
private ContactRepository contactRepository;

@Autowired
private AccountRepository accountRepository;

@Autowired
private DealRepository dealRepository;

@Transactional
public Contact convertLeadToContact(Long leadId) {
    Lead lead = leadRepository.findByIdAndConvertedFalse(leadId)
                              .orElseThrow(() -> new RuntimeException("Lead not found or already converted"));

    // Create or get an existing account
    Account account = accountRepository.findByAccountName(lead.getCompany())
                                       .orElseGet(() -> {
                                           Account newAccount = new Account();
                                           newAccount.setAccountOwner(lead.getLeadOwner());
                                           newAccount.setAccountName(lead.getCompany());
                                           newAccount.setIndustry(lead.getIndustry());
                                           newAccount.setAnnualRevenue(lead.getAnnualRevenue() != null ? BigDecimal.valueOf(lead.getAnnualRevenue()) : BigDecimal.ZERO);
                                           return accountRepository.save(newAccount);
                                       });

    // Create Contact from Lead
    Contact contact = new Contact();
    contact.setContactOwner(lead.getLeadOwner());
    contact.setFirstName(lead.getFirstName());
    contact.setLastName(lead.getLastName());
    contact.setEmail(lead.getEmail());
    contact.setPhone(lead.getMobile());
    contact.setLeadSource(lead.getLeadSource()); // Enum conversion
    contact.setAccount(account);
    contactRepository.save(contact);

    return contact; // Return the created contact
    }

@Transactional
public Contact convertLeadToDeal(Long leadId) {
    Lead lead = leadRepository.findByIdAndConvertedFalse(leadId)
                              .orElseThrow(() -> new RuntimeException("Lead not found or already converted"));

    // Create or get an existing account
    Account account = accountRepository.findByAccountName(lead.getCompany())
                                       .orElseGet(() -> {
                                           Account newAccount = new Account();
                                           newAccount.setAccountOwner(lead.getLeadOwner());
                                           newAccount.setAccountName(lead.getCompany());
                                           newAccount.setIndustry(lead.getIndustry());
                                           newAccount.setAnnualRevenue(lead.getAnnualRevenue() != null ? BigDecimal.valueOf(lead.getAnnualRevenue()) : BigDecimal.ZERO);
                                           return accountRepository.save(newAccount);
                                       });

    // Create Contact from Lead
    Contact contact = new Contact();
    contact.setContactOwner(lead.getLeadOwner());
    contact.setFirstName(lead.getFirstName());
    contact.setLastName(lead.getLastName());
    contact.setEmail(lead.getEmail());
    contact.setPhone(lead.getMobile());
    contact.setLeadSource(lead.getLeadSource()); // Enum conversion
    contact.setAccount(account);
    contactRepository.save(contact);

    // Create a new Deal from Lead details
    Deal deal = new Deal();
    deal.setDealOwner(lead.getLeadOwner());
    deal.setDealName(lead.getCompany() + " Deal");
    deal.setType(DealType.NEW);
    deal.setLeadSource(contact.getLeadSource());
    deal.setAccount(account);
    deal.setContact(contact);
    deal.setStage(Stage.PROSPECTING);
    deal.setProbability(50);
    deal.setClosingDate(new Date()); // Default closing date
    deal.setAmount(lead.getAnnualRevenue() != null ? BigDecimal.valueOf(lead.getAnnualRevenue()) : BigDecimal.ZERO);
    dealRepository.save(deal);

    // Mark Lead as Converted
    lead.setConverted(true);
    leadRepository.save(lead);

    return contact; // Return the created contact
    }
}



