package com.zen.notify.service;

import com.zen.notify.entity.Deal;
import com.zen.notify.repository.DealRepository;
import com.zen.notify.search.DealSearchCriteria;
import com.zen.notify.search.DealSpecification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger log = LoggerFactory.getLogger(DealService.class);

    @Autowired
    private DealRepository dealRepository;

    // Create Deal
    public Deal createDeal(Deal deal) {
        deal.setCreatedAt(new Date());
        deal.setUpdatedAt(new Date());
        Deal saved = dealRepository.save(deal);
        log.info("✅ Deal created: ID={}, Name={}", saved.getDealId(), saved.getDealName());
        return saved;
    }

    // Get All Deals
    public List<Deal> getAllDeals() {
        List<Deal> deals = dealRepository.findAll();
        log.info("📦 Retrieved all deals: Count={}", deals.size());
        return deals;
    }

    // Get Deals Paginated
    public Page<Deal> getDealsPaginated(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Deal> result = dealRepository.findAll(pageable);
        log.info("📄 Paginated deals fetched: Page={}, Size={}, Total={}", page, pageSize, result.getTotalElements());
        return result;
    }

    // Get Deal by ID
    public Optional<Deal> getDealById(Long dealId) {
        Optional<Deal> dealOpt = dealRepository.findById(dealId);
        if (dealOpt.isPresent()) {
            log.info("🔍 Deal found: ID={}", dealId);
        } else {
            log.warn("⚠️ Deal not found: ID={}", dealId);
        }
        return dealOpt;
    }

    // Update Deal
    public Deal updateDeal(Long dealId, Deal dealDetails) {
        log.info("🛠️ Updating deal: ID={}", dealId);
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
            deal.setUpdatedAt(new Date());

            Deal updated = dealRepository.save(deal);
            log.info("✅ Deal updated: ID={}, Name={}", updated.getDealId(), updated.getDealName());
            return updated;
        }).orElseThrow(() -> {
            log.error("❌ Update failed: Deal not found with ID={}", dealId);
            return new RuntimeException("Deal not found");
        });
    }

    // Delete Deal
    public void deleteDeal(Long dealId) {
        log.info("🗑️ Attempting to delete deal: ID={}", dealId);
        dealRepository.deleteById(dealId);
        log.info("✅ Deal deleted: ID={}", dealId);
    }

    // Search Deals
    public Page<Deal> searchDeals(DealSearchCriteria criteria, int page, int size) {
        log.info("🔎 Searching deals with criteria={}, page={}, size={}", criteria, page, size);
        Pageable pageable = PageRequest.of(page, size);
        Specification<Deal> spec = new DealSpecification(criteria);
        Page<Deal> result = dealRepository.findAll(spec, pageable);
        log.info("✅ Deal search returned {} results", result.getTotalElements());
        return result;
    }
}
