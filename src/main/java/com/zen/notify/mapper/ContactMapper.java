package com.zen.notify.mapper;

import java.util.Date;

import com.zen.notify.dto.ContactDTO;
import com.zen.notify.entity.Account;
import com.zen.notify.entity.Contact;
import com.zen.notify.entity.LeadSource;

public class ContactMapper {
	
	public static ContactDTO toDto(Contact contact) {
	    ContactDTO dto = new ContactDTO();
	    dto.setContactId(contact.getContactId());
	    dto.setContactOwner(contact.getContactOwner());
	    dto.setLeadSource(contact.getLeadSource() != null ? contact.getLeadSource().name() : null);
	    dto.setFirstName(contact.getFirstName());
	    dto.setLastName(contact.getLastName());
	    dto.setAccountId(contact.getAccount() != null ? contact.getAccount().getAccountId() : null);
	    dto.setVendorName(contact.getVendorName());
	    dto.setEmail(contact.getEmail());
	    dto.setTitle(contact.getTitle());
	    dto.setPhone(contact.getPhone());
	    dto.setDepartment(contact.getDepartment());
	    dto.setOtherPhone(contact.getOtherPhone());
	    dto.setHomePhone(contact.getHomePhone());
	    dto.setMobile(contact.getMobile());
	    dto.setDateOfBirth(contact.getDateOfBirth());
	    dto.setAssistant(contact.getAssistant());
	    dto.setAsstPhone(contact.getAsstPhone());
	    dto.setEmailOptOut(contact.getEmailOptOut());
	    dto.setSkypeId(contact.getSkypeId());
	    dto.setSecondaryEmail(contact.getSecondaryEmail());
	    dto.setTwitterHandle(contact.getTwitterHandle());
	    dto.setReportingToId(contact.getReportingTo() != null ? contact.getReportingTo().getContactId() : null);
	    dto.setCreatedAt(contact.getCreatedAt());
	    dto.setUpdatedAt(contact.getUpdatedAt());
	    return dto;
	}
    
	public static Contact fromDto(ContactDTO dto) {
	    Contact contact = new Contact();

	    contact.setContactId(dto.getContactId()); // For updates
	    contact.setContactOwner(dto.getContactOwner());

	    if (dto.getLeadSource() != null) {
	        contact.setLeadSource(LeadSource.valueOf(dto.getLeadSource()));
	    }

	    contact.setFirstName(dto.getFirstName());
	    contact.setLastName(dto.getLastName());

	    if (dto.getAccountId() != null) {
	        Account account = new Account();
	        account.setAccountId(dto.getAccountId());
	        contact.setAccount(account);
	    }

	    contact.setVendorName(dto.getVendorName());
	    contact.setEmail(dto.getEmail());
	    contact.setTitle(dto.getTitle());
	    contact.setPhone(dto.getPhone());
	    contact.setDepartment(dto.getDepartment());
	    contact.setOtherPhone(dto.getOtherPhone());
	    contact.setHomePhone(dto.getHomePhone());
	    contact.setMobile(dto.getMobile());
	    contact.setDateOfBirth(dto.getDateOfBirth());
	    contact.setAssistant(dto.getAssistant());
	    contact.setAsstPhone(dto.getAsstPhone());
	    contact.setEmailOptOut(dto.getEmailOptOut());
	    contact.setSkypeId(dto.getSkypeId());
	    contact.setSecondaryEmail(dto.getSecondaryEmail());
	    contact.setTwitterHandle(dto.getTwitterHandle());

	    if (dto.getReportingToId() != null) {
	        Contact reportingTo = new Contact();
	        reportingTo.setContactId(dto.getReportingToId());
	        contact.setReportingTo(reportingTo);
	    }

	    contact.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : new Date());
	    contact.setUpdatedAt(dto.getUpdatedAt() != null ? dto.getUpdatedAt() : new Date());

	    return contact;
	}
}
