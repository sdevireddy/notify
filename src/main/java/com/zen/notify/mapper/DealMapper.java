package com.zen.notify.mapper;

import com.zen.notify.dto.DealDTO;
import com.zen.notify.entity.Deal;

public class DealMapper {

    public static DealDTO toDTO(Deal deal) {
        DealDTO dto = new DealDTO();
        dto.setDealId(deal.getDealId());
        dto.setDealOwner(deal.getDealOwner());
        dto.setDealName(deal.getDealName());
        dto.setType(deal.getType());
        dto.setNextStep(deal.getNextStep());
        dto.setLeadSource(deal.getLeadSource());
        dto.setAmount(deal.getAmount());
        dto.setClosingDate(deal.getClosingDate());
        dto.setContactId(deal.getContact() != null ? deal.getContact().getContactId() : null);
        dto.setAccountId(deal.getAccount() != null ? deal.getAccount().getAccountId() : null);
        dto.setStage(deal.getStage());
        dto.setQualification(deal.getQualification());
        dto.setProbability(deal.getProbability());
        dto.setExpectedRevenue(deal.getExpectedRevenue());
        dto.setCampaignSource(deal.getCampaignSource());
        dto.setCreatedAt(deal.getCreatedAt());
        dto.setUpdatedAt(deal.getUpdatedAt());
        return dto;
    }

    public static Deal toEntity(DealDTO dto) {
        Deal deal = new Deal();
        deal.setDealId(dto.getDealId());
        deal.setDealOwner(dto.getDealOwner());
        deal.setDealName(dto.getDealName());
        deal.setType(dto.getType());
        deal.setNextStep(dto.getNextStep());
        deal.setLeadSource(dto.getLeadSource());
        deal.setAmount(dto.getAmount());
        deal.setClosingDate(dto.getClosingDate());
        deal.setStage(dto.getStage());
        deal.setQualification(dto.getQualification());
        deal.setProbability(dto.getProbability());
        deal.setExpectedRevenue(dto.getExpectedRevenue());
        deal.setCampaignSource(dto.getCampaignSource());
        // Note: Contact and Account should be set from service using their IDs if needed
        return deal;
    }
}
