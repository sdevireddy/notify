package com.zen.notify.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.zen.notify.dto.AccountDTO;
import com.zen.notify.dto.ContactDTO;
import com.zen.notify.entity.Account;
import com.zen.notify.entity.Contact;
import com.zen.notify.entity.LeadSource;

public class AccountMapper {

    public static AccountDTO toDTO(Account account) {
        if (account == null) return null;

        AccountDTO dto = new AccountDTO();
        dto.setAccountId(account.getAccountId());
        dto.setAccountOwner(account.getAccountOwner());
        dto.setAccountName(account.getAccountName());
        dto.setAccountSite(account.getAccountSite());
        dto.setParentAccountId(account.getParentAccount() != null ? account.getParentAccount().getAccountId() : null);
        dto.setAccountNumber(account.getAccountNumber());
        dto.setAccountType(account.getAccountType() != null ? account.getAccountType().name() : null);
        dto.setIndustry(account.getIndustry());
        dto.setAnnualRevenue(account.getAnnualRevenue());
        dto.setRating(account.getRating() != null ? account.getRating().name() : null);
        dto.setPhone(account.getPhone());
        dto.setFax(account.getFax());
        dto.setWebsite(account.getWebsite());
        dto.setTickerSymbol(account.getTickerSymbol());
        dto.setOwnership(account.getOwnership() != null ? account.getOwnership().name() : null);
        dto.setSicCode(account.getSicCode());
        dto.setCreatedAt(account.getCreatedAt());
        dto.setUpdatedAt(account.getUpdatedAt());

        if (account.getContacts() != null) {
            List<ContactDTO> contactDTOs = account.getContacts().stream()
                    .map(AccountMapper::toDTO)
                    .collect(Collectors.toList());
            dto.setContacts(contactDTOs);
        }

        return dto;
    }

    public static ContactDTO toDTO(Contact contact) {
        if (contact == null) return null;

        ContactDTO dto = new ContactDTO();
        dto.setContactId(contact.getContactId());
        dto.setContactOwner(contact.getContactOwner());
        dto.setFirstName(contact.getFirstName());
        dto.setLastName(contact.getLastName());
        dto.setEmail(contact.getEmail());
        dto.setPhone(contact.getPhone());
        dto.setDepartment(contact.getDepartment());
        dto.setMobile(contact.getMobile());
        dto.setTitle(contact.getTitle());
        dto.setVendorName(contact.getVendorName());
        dto.setOtherPhone(contact.getOtherPhone());
        dto.setHomePhone(contact.getHomePhone());
        dto.setDateOfBirth(contact.getDateOfBirth());
        dto.setAssistant(contact.getAssistant());
        dto.setAsstPhone(contact.getAsstPhone());
        dto.setEmailOptOut(contact.getEmailOptOut());
        dto.setSkypeId(contact.getSkypeId());
        dto.setSecondaryEmail(contact.getSecondaryEmail());
        dto.setTwitterHandle(contact.getTwitterHandle());
        dto.setLeadSource(contact.getLeadSource() != null ? contact.getLeadSource().name() : null);
        dto.setReportingToId(contact.getReportingTo() != null ? contact.getReportingTo().getContactId() : null);

        return dto;
    }

    public static Account toEntity(AccountDTO dto) {
        if (dto == null) return null;

        Account entity = new Account();
        entity.setAccountId(dto.getAccountId());
        entity.setAccountOwner(dto.getAccountOwner());
        entity.setAccountName(dto.getAccountName());
        entity.setAccountSite(dto.getAccountSite());
        entity.setAccountNumber(dto.getAccountNumber());
        entity.setAccountType(dto.getAccountType() != null ? Account.AccountType.valueOf(dto.getAccountType()) : null);
        entity.setIndustry(dto.getIndustry());
        entity.setAnnualRevenue(dto.getAnnualRevenue());
        entity.setRating(dto.getRating() != null ? Account.Rating.valueOf(dto.getRating()) : null);
        entity.setPhone(dto.getPhone());
        entity.setFax(dto.getFax());
        entity.setWebsite(dto.getWebsite());
        entity.setTickerSymbol(dto.getTickerSymbol());
        entity.setOwnership(dto.getOwnership() != null ? Account.Ownership.valueOf(dto.getOwnership()) : null);
        entity.setSicCode(dto.getSicCode());

        if (dto.getContacts() != null) {
            List<Contact> contacts = dto.getContacts().stream()
                    .map(ContactMapper::fromDto)
                    .collect(Collectors.toList());
            entity.setContacts(contacts); // bidirectional
        }

        return entity;
    }

    public static Contact toEntity(ContactDTO dto) {
        if (dto == null) return null;

        Contact entity = new Contact();
        entity.setContactId(dto.getContactId());
        entity.setContactOwner(dto.getContactOwner());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setDepartment(dto.getDepartment());
        entity.setMobile(dto.getMobile());
        entity.setTitle(dto.getTitle());
        entity.setVendorName(dto.getVendorName());
        entity.setOtherPhone(dto.getOtherPhone());
        entity.setHomePhone(dto.getHomePhone());
        entity.setDateOfBirth(dto.getDateOfBirth());
        entity.setAssistant(dto.getAssistant());
        entity.setAsstPhone(dto.getAsstPhone());
        entity.setEmailOptOut(dto.getEmailOptOut());
        entity.setSkypeId(dto.getSkypeId());
        entity.setSecondaryEmail(dto.getSecondaryEmail());
        entity.setTwitterHandle(dto.getTwitterHandle());
        entity.setLeadSource(dto.getLeadSource() != null ? LeadSource.valueOf(dto.getLeadSource()) : null);
        
        return entity;
    }
}

