package com.zen.notify.service;


import com.zen.notify.entity.Deal;
import com.zen.notify.entity.Lead;
import com.zen.notify.mapper.DealMapper;
import com.zen.notify.repository.DealRepository;
import com.zen.notify.search.DealSearchCriteria;
import com.zen.notify.search.DealSpecification;
import com.zen.notify.search.LeadSearchCriteria;
import com.zen.notify.search.LeadSpecification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DealService {

    @Autowired
    private DealRepository dealRepository;

    // Create Deal
    public Deal createDeal(Deal deal) {
    	deal.setCreatedAt(new Date());
    	deal.setUpdatedAt(new Date());
            return dealRepository.save(deal);
        }
    // Get All Deals
    public List<Deal> getAllDeals() {
        return dealRepository.findAll();
    }
    
    public Page<Deal> getDealsPaginated(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return dealRepository.findAll(pageable);
    }

    // Get Deal by ID
    public Optional<Deal> getDealById(Long dealId) {
        return dealRepository.findById(dealId);
    }

    // Update Deal
    public Deal updateDeal(Long dealId, Deal dealDetails) {
        return dealRepository.findById(dealId).map(deal -> {
            deal.setDealOwner(dealDetails.getDealOwner());
            deal.setDealName(dealDetails.getDealName());
            deal.setAccount(dealDetails.getAccount());
            deal.setType(dealDetails.getType());
            deal.setNextStep(dealDetails.getNextStep());
            deal.setLeadSource(dealDetails.getLeadSource());
            deal.setContact(dealDetails.getContact());
            deal.setAmount(dealDetails.getAmount());
            deal.setClosingDate(dealDetails.getClosingDate());
            deal.setStage(dealDetails.getStage());
            deal.setQualification(dealDetails.getQualification());
            deal.setProbability(dealDetails.getProbability());
            deal.setExpectedRevenue(dealDetails.getExpectedRevenue());
            deal.setCampaignSource(dealDetails.getCampaignSource());
            return dealRepository.save(deal);
        }).orElseThrow(() -> new RuntimeException("Deal not found"));
    }

    // Delete Deal
    public void deleteDeal(Long dealId) {
        dealRepository.deleteById(dealId);
    }
    
    public Page<Deal> searchDeals(DealSearchCriteria criteria, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Deal> spec = new DealSpecification(criteria);
        return dealRepository.findAll(spec, pageable);
    }

}

