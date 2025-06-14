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
    public ResponseEntity<PaginatedResponse<?>> getAllDeals(
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
    	}
        catch(Exception aEx) {
        	 return null;
    	}
        
    }
    
    // Get Deal by ID
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Deal>> getDealById(@PathVariable Long id) {
        return ResponseEntity.ok(dealService.getDealById(id));
    }

    // Update Deal
    @PutMapping("/{id}")
    public ResponseEntity<Deal> updateDeal(@PathVariable Long id, @RequestBody Deal dealDetails) {
        return ResponseEntity.ok(dealService.updateDeal(id, dealDetails));
    }

    // Delete Deal
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDeal(@PathVariable Long id) {
        dealService.deleteDeal(id);
        return ResponseEntity.ok("Deal deleted successfully");
    }
    
    @PostMapping("/search")
    public ResponseEntity<?> searchDeals(
            @RequestBody DealSearchCriteria criteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Deal> dealPage = dealService.searchDeals(criteria, page, size);
        PaginatedResponse<Deal> response = new PaginatedResponse<>(
        		dealPage.getTotalElements(),
        		dealPage.getSize(),
        		dealPage.getNumber(),
        		dealPage.getTotalPages(),
        		dealPage.getContent()
        );
        return ResponseEntity.ok(response);
    }
    
}

