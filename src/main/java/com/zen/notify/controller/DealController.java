package com.zen.notify.controller;


import com.zen.notify.dto.DealDTO;
import com.zen.notify.dto.LeadDTO;
import com.zen.notify.dto.PaginatedResponse;
import com.zen.notify.entity.Account;
import com.zen.notify.entity.Contact;
import com.zen.notify.entity.Deal;
import com.zen.notify.entity.Lead;
import com.zen.notify.mapper.DealMapper;
import com.zen.notify.mapper.LeadMapper;
import com.zen.notify.search.DealSearchCriteria;
import com.zen.notify.service.AccountService;
import com.zen.notify.service.ContactService;
import com.zen.notify.service.DealService;

import jakarta.persistence.EntityNotFoundException;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/deals")
public class DealController {

    @Autowired
    private DealService dealService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private AccountService accountService;


    // Create Deal
    @PostMapping
    public ResponseEntity<?> createDeal(@RequestBody DealDTO dealDto) {
    	
    	try {
    		 Deal deal = DealMapper.toEntity(dealDto);

    	     // Set Contact if ID is provided
    	        if (dealDto.getContactId() != null) {
    	            Optional<Contact> contact = contactService.findById(dealDto.getContactId());
    	            if (contact.get() == null) {
    	                throw new EntityNotFoundException("Contact not found with ID: " + dealDto.getContactId());
    	            }
    	            deal.setContact(contact.get());
    	        }

    	        // Set Account if ID is provided
    	        if (dealDto.getAccountId() != null) {
    	            Account account = accountService.findById(dealDto.getAccountId())
    	                .orElseThrow(() -> new EntityNotFoundException("Account not found with ID: " + dealDto.getAccountId()));
    	            deal.setAccount(account);
    	        }
    	        Deal savedDeal = dealService.createDeal(deal);
    	        DealDTO savedDealDTO = DealMapper.toDTO(savedDeal);

	        return savedDealDTO != null ? ResponseEntity.ok(savedDealDTO) : ResponseEntity.notFound().build();
   	}catch(Exception aEx) {
   		 return ResponseEntity.status(HttpStatus.SC_CONFLICT).body(Map.of(
                    "error", "Failed Deal to create",
                    "message", aEx.getMessage()
                ));
   	}
           }
    
	/*
	 * // Get All Deals
	 * 
	 * @GetMapping public ResponseEntity<List<Deal>> getAllDeals() { return
	 * ResponseEntity.ok(dealService.getAllDeals()); }
	 */
    
    @GetMapping
    public ResponseEntity<?> getAllDeals(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
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

            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            String errorMessage = "Failed to fetch deals. Reason: " + ex.getMessage();
            return ResponseEntity
                    .status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", errorMessage));
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getDealById(@PathVariable Long id) {
        Optional<Deal> dealOpt = dealService.getDealById(id);

        if (dealOpt.isPresent()) {
            DealDTO dealDTO = DealMapper.toDTO(dealOpt.get());
            return ResponseEntity.ok(dealDTO);
        } else {
            return ResponseEntity
                    .status(HttpStatus.SC_NOT_FOUND)
                    .body(Map.of("error", "Deal not found with ID: " + id));
        }
    }

    // Update Deal
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDeal(@PathVariable Long id, @RequestBody DealDTO dealDTO) {
        try {
            Deal dealEntity = DealMapper.toEntity(dealDTO); // Convert DTO to entity
            Deal updatedDeal = dealService.updateDeal(id, dealEntity); // Pass to service

            DealDTO responseDTO = DealMapper.toDTO(updatedDeal); // Convert updated entity back to DTO
            return ResponseEntity.ok(responseDTO);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND)
                    .body(Map.of("error", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to update deal"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDeal(@PathVariable Long id) {
        try {
            dealService.deleteDeal(id);
            return ResponseEntity.ok("Deal deleted successfully");
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND)
                    .body(Map.of("error", "Deal not found with ID: " + id));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to delete deal"));
        }
    }
    
    @PostMapping("/search")
    public ResponseEntity<?> searchDeals(
            @RequestBody DealSearchCriteria criteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<Deal> dealPage = dealService.searchDeals(criteria, page, size);

            Page<DealDTO> dealDTOPage = dealPage.map(DealMapper::toDTO); // Map to DTO

            PaginatedResponse<DealDTO> response = new PaginatedResponse<>(
                    dealDTOPage.getTotalElements(),
                    dealDTOPage.getSize(),
                    dealDTOPage.getNumber(),
                    dealDTOPage.getTotalPages(),
                    dealDTOPage.getContent()
            );

            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to search deals: " + ex.getMessage()));
        }
    }
    
}

