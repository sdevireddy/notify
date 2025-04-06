package com.zen.notify.service;

import com.zen.notify.entity.Contact;
import com.zen.notify.entity.Deal;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class ContactToDealConverter {

    public Deal convertContactToDeal(Contact contact, Double amount, Date closingDate, String stage) {
        if (contact == null) {
            throw new IllegalArgumentException("Contact cannot be null");
        }

        Deal deal = new Deal();
        deal.setDealOwner(contact.getContactOwner());
        deal.setDealName("Deal with " + contact.getFirstName() + " " + contact.getLastName());
        deal.setContact(contact);
        deal.setAccount(contact.getAccount());
        deal.setLeadSource(contact.getLeadSource());
        deal.setAmount(amount != null ? amount : BigDecimal.ZERO);
        deal.setClosingDate(closingDate != null ? closingDate : new Date());
       // deal.setStage(stage != null ? stage : Deal.Stage.PROSPECTING);
        deal.setProbability(10);  // Default probability for a new deal
        deal.setExpectedRevenue(deal.getAmount().multiply(new BigDecimal(deal.getProbability()).divide(new BigDecimal(100))));
        deal.setCreatedAt(new Date());

        return deal;
    }
}
