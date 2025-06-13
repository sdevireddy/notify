
package com.zen.notify.mapper;

import com.zen.notify.dto.LeadDTO;
import com.zen.notify.entity.Lead;

public class LeadMapper {

    public static LeadDTO toDto(Lead lead) {
        if (lead == null) return null;

        LeadDTO dto = new LeadDTO();
        dto.setId(lead.getId());
        dto.setLeadOwner(lead.getLeadOwner());
        dto.setCompany(lead.getCompany());
        dto.setFirstName(lead.getFirstName());
        dto.setLastName(lead.getLastName());
        dto.setTitle(lead.getTitle());
        dto.setEmail(lead.getEmail());
        dto.setFax(lead.getFax());
        dto.setMobile(lead.getMobile());
        dto.setWebsite(lead.getWebsite());
        dto.setLeadSource(lead.getLeadSource());
        dto.setLeadStatus(lead.getLeadStatus());
        dto.setIndustry(lead.getIndustry());
        dto.setNoOfEmployees(lead.getNoOfEmployees());
        dto.setAnnualRevenue(lead.getAnnualRevenue());
        dto.setRating(lead.getRating());
        dto.setEmailOptOut(lead.getEmailOptOut());
        dto.setSkypeId(lead.getSkypeId());
        dto.setSecondaryEmail(lead.getSecondaryEmail());
        dto.setTwitter(lead.getTwitter());
        dto.setDescription(lead.getDescription());
        dto.setConverted(lead.getConverted());

        return dto;
    }

    public static Lead toEntity(LeadDTO lead2) {
        if (lead2 == null) return null;

        Lead lead = new Lead();
        lead.setId(lead2.getId());
        lead.setLeadOwner(lead2.getLeadOwner());
        lead.setCompany(lead2.getCompany());
        lead.setFirstName(lead2.getFirstName());
        lead.setLastName(lead2.getLastName());
        lead.setTitle(lead2.getTitle());
        lead.setEmail(lead2.getEmail());
        lead.setFax(lead2.getFax());
        lead.setMobile(lead2.getMobile());
        lead.setWebsite(lead2.getWebsite());
        lead.setLeadSource(lead2.getLeadSource());
        lead.setLeadStatus(lead2.getLeadStatus());
        lead.setIndustry(lead2.getIndustry());
        lead.setNoOfEmployees(lead2.getNoOfEmployees());
        lead.setAnnualRevenue(lead2.getAnnualRevenue());
        lead.setRating(lead2.getRating());
        lead.setEmailOptOut(lead2.getEmailOptOut());
        lead.setSkypeId(lead2.getSkypeId());
        lead.setSecondaryEmail(lead2.getSecondaryEmail());
        lead.setTwitter(lead2.getTwitter());
        lead.setDescription(lead2.getDescription());
        lead.setConverted(lead2.getConverted());

        return lead;
    }
}
