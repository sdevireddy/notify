package com.zen.notify.controller;

import com.zen.notify.dto.DealDTO;
import com.zen.notify.dto.PaginatedResponse;
import com.zen.notify.entity.Account;
import com.zen.notify.entity.Contact;
import com.zen.notify.entity.Deal;
import com.zen.notify.mapper.DealMapper;
import com.zen.notify.search.DealSearchCriteria;
import com.zen.notify.service.AccountService;
import com.zen.notify.service.ContactService;
import com.zen.notify.service.DealService;

import jakarta.persistence.EntityNotFoundException;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/crm/deals")
public class DealController {

    private static final Logger log = LoggerFactory.getLogger(DealController.class);

    @Autowired
    private DealService dealService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private AccountService accountService;

    // Create Deal
    @PostMapping
    public ResponseEntity<?> createDeal(@RequestBody DealDTO dealDto) {
        log.info("🔧 Creating new deal: {}", dealDto);

        try {
            Deal deal = DealMapper.toEntity(dealDto);

            // Set Contact
            if (dealDto.getContactId() != null) {
                Optional<Contact> contact = contactService.findById(dealDto.getContactId());
                if (contact.isEmpty()) {
                    throw new EntityNotFoundException("Contact not found with ID: " + dealDto.getContactId());
                }
                deal.setContact(contact.get());
            }

            // Set Account
            if (dealDto.getAccountId() != null) {
                Account account = accountService.findById(dealDto.getAccountId())
                        .orElseThrow(() -> new EntityNotFoundException("Account not found with ID: " + dealDto.getAccountId()));
                deal.setAccount(account);
            }

            Deal savedDeal = dealService.createDeal(deal);
            DealDTO savedDealDTO = DealMapper.toDTO(savedDeal);

            log.info("✅ Deal created successfully with ID: {}", savedDealDTO.getDealId());
            return ResponseEntity.ok(savedDealDTO);

        } catch (Exception ex) {
            log.error("❌ Failed to create deal: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.SC_CONFLICT).body(Map.of(
                    "error", "Failed to create deal",
                    "message", ex.getMessage()
            ));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllDeals(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        log.info("📥 Fetching all deals - Page: {}, Size: {}", page, pageSize);
        try {
            Page<Deal> dealPage = dealService.getDealsPaginated(page, pageSize);
            Page<DealDTO> deals = dealPage.map(DealMapper::toDTO);

            PaginatedResponse<?> response = new PaginatedResponse<>(
                    dealPage.getTotalElements(),
                    dealPage.getSize(),
                    dealPage.getNumber(),
                    dealPage.getTotalPages(),
                    deals.getContent()
            );

            log.info("✅ Retrieved {} deals", deals.getContent().size());
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("❌ Error fetching deals: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch deals. Reason: " + ex.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDealById(@PathVariable Long id) {
        log.info("🔍 Fetching deal with ID: {}", id);
        Optional<Deal> dealOpt = dealService.getDealById(id);

        if (dealOpt.isPresent()) {
            DealDTO dealDTO = DealMapper.toDTO(dealOpt.get());
            log.info("✅ Deal found for ID: {}", id);
            return ResponseEntity.ok(dealDTO);
        } else {
            log.warn("⚠️ Deal not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND)
                    .body(Map.of("error", "Deal not found with ID: " + id));
        }
    }

    // Update Deal
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDeal(@PathVariable Long id, @RequestBody DealDTO dealDTO) {
        log.info("✏️ Updating deal with ID: {}", id);
        try {
            Deal dealEntity = DealMapper.toEntity(dealDTO);
            Deal updatedDeal = dealService.updateDeal(id, dealEntity);

            DealDTO responseDTO = DealMapper.toDTO(updatedDeal);
            log.info("✅ Deal updated successfully for ID: {}", id);
            return ResponseEntity.ok(responseDTO);
        } catch (EntityNotFoundException ex) {
            log.warn("⚠️ Cannot update, deal not found: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND)
                    .body(Map.of("error", ex.getMessage()));
        } catch (Exception ex) {
            log.error("❌ Failed to update deal: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to update deal"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDeal(@PathVariable Long id) {
        log.info("🗑️ Deleting deal with ID: {}", id);
        try {
            dealService.deleteDeal(id);
            log.info("✅ Deal deleted successfully for ID: {}", id);
            return ResponseEntity.ok("Deal deleted successfully");
        } catch (EntityNotFoundException ex) {
            log.warn("⚠️ Cannot delete, deal not found: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND)
                    .body(Map.of("error", "Deal not found with ID: " + id));
        } catch (Exception ex) {
            log.error("❌ Failed to delete deal: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to delete deal"));
        }
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchDeals(
            @RequestBody DealSearchCriteria criteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        log.info("🔎 Searching deals with criteria: {}, page: {}, size: {}", criteria, page, size);
        try {
            Page<Deal> dealPage = dealService.searchDeals(criteria, page, size);
            Page<DealDTO> dealDTOPage = dealPage.map(DealMapper::toDTO);

            PaginatedResponse<DealDTO> response = new PaginatedResponse<>(
                    dealDTOPage.getTotalElements(),
                    dealDTOPage.getSize(),
                    dealDTOPage.getNumber(),
                    dealDTOPage.getTotalPages(),
                    dealDTOPage.getContent()
            );

            log.info("✅ Found {} deals matching search", dealDTOPage.getContent().size());
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("❌ Failed to search deals: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to search deals: " + ex.getMessage()));
        }
    }
}
