package com.zen.notify.controller;

import com.zen.notify.dto.DealDTO;
import com.zen.notify.dto.ApiResponse;
import com.zen.notify.entity.Account;
import com.zen.notify.entity.Contact;
import com.zen.notify.entity.Deal;
import com.zen.notify.mapper.DealMapper;
import com.zen.notify.search.DealSearchCriteria;
import com.zen.notify.service.AccountService;
import com.zen.notify.service.ContactService;
import com.zen.notify.service.DealService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.List;

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


    @PostMapping("/create")
    public ResponseEntity<ApiResponse<DealDTO>> createDeal(@RequestBody DealDTO dealDto, HttpServletRequest request) {
        log.info("🔧 Creating new deal: {}", dealDto);

        try {
            Deal deal = DealMapper.toEntity(dealDto);

            if (dealDto.getContactId() != null) {
                Optional<Contact> contact = contactService.findById(dealDto.getContactId());
                if (contact.isEmpty()) {
                    throw new EntityNotFoundException("Contact not found with ID: " + dealDto.getContactId());
                }
                deal.setContact(contact.get());
            }

            if (dealDto.getAccountId() != null) {
                Account account = accountService.findById(dealDto.getAccountId())
                        .orElseThrow(() -> new EntityNotFoundException("Account not found with ID: " + dealDto.getAccountId()));
                deal.setAccount(account);
            }

            Deal savedDeal = dealService.createDeal(deal);
            DealDTO savedDealDTO = DealMapper.toDTO(savedDeal);

            return ResponseEntity.ok(ApiResponse.<DealDTO>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(HttpStatus.SC_OK)
                    .path(request.getRequestURI())
                    .data(savedDealDTO)
                    .build());

        } catch (DataIntegrityViolationException ex) {
            log.error("❌ Duplicate deal name: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.SC_CONFLICT).body(ApiResponse.<DealDTO>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(HttpStatus.SC_CONFLICT)
                    .error("Deal name already exists. Please choose another name.")
                    .path(request.getRequestURI())
                    .build());
        } catch (Exception ex) {
            log.error("❌ Failed to create deal: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.SC_CONFLICT).body(ApiResponse.<DealDTO>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(HttpStatus.SC_CONFLICT)
                    .error(ex.getMessage())
                    .path(request.getRequestURI())
                    .build());
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DealDTO>>> getAllDeals(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            HttpServletRequest request) {

        log.info("📥 Fetching all deals - Page: {}, Size: {}", page, pageSize);
        try {
            Page<Deal> dealPage = dealService.getDealsPaginated(page, pageSize);
            List<DealDTO> deals = dealPage.map(DealMapper::toDTO).getContent();

            return ResponseEntity.ok(ApiResponse.<List<DealDTO>>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(HttpStatus.SC_OK)
                    .path(request.getRequestURI())
                    .totalRecords(dealPage.getTotalElements())
                    .pageSize(dealPage.getSize())
                    .currentPage(dealPage.getNumber())
                    .totalPages(dealPage.getTotalPages())
                    .data(deals)
                    .build());
        } catch (Exception ex) {
            log.error("❌ Error fetching deals: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<List<DealDTO>>builder()
                            .timestamp(ZonedDateTime.now())
                            .status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                            .error("Failed to fetch deals: " + ex.getMessage())
                            .path(request.getRequestURI())
                            .build());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DealDTO>> getDealById(@PathVariable Long id, HttpServletRequest request) {
        log.info("🔍 Fetching deal with ID: {}", id);
        Optional<Deal> dealOpt = dealService.getDealById(id);

        if (dealOpt.isPresent()) {
            DealDTO dealDTO = DealMapper.toDTO(dealOpt.get());
            return ResponseEntity.ok(ApiResponse.<DealDTO>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(HttpStatus.SC_OK)
                    .path(request.getRequestURI())
                    .data(dealDTO)
                    .build());
        } else {
            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(ApiResponse.<DealDTO>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(HttpStatus.SC_NOT_FOUND)
                    .error("Deal not found with ID: " + id)
                    .path(request.getRequestURI())
                    .build());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DealDTO>> updateDeal(@PathVariable Long id, @RequestBody DealDTO dealDTO, HttpServletRequest request) {
        log.info("✏️ Updating deal with ID: {}", id);
        try {
            Deal dealEntity = DealMapper.toEntity(dealDTO);
            Deal updatedDeal = dealService.updateDeal(id, dealEntity);

            DealDTO responseDTO = DealMapper.toDTO(updatedDeal);

            return ResponseEntity.ok(ApiResponse.<DealDTO>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(HttpStatus.SC_OK)
                    .path(request.getRequestURI())
                    .data(responseDTO)
                    .build());
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(ApiResponse.<DealDTO>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(HttpStatus.SC_NOT_FOUND)
                    .error(ex.getMessage())
                    .path(request.getRequestURI())
                    .build());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(ApiResponse.<DealDTO>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .error("Failed to update deal")
                    .path(request.getRequestURI())
                    .build());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteDeal(@PathVariable Long id, HttpServletRequest request) {
        log.info("🗑️ Deleting deal with ID: {}", id);
        try {
            dealService.deleteDeal(id);
            return ResponseEntity.ok(ApiResponse.<String>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(HttpStatus.SC_OK)
                    .path(request.getRequestURI())
                    .data("Deal deleted successfully")
                    .build());
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(ApiResponse.<String>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(HttpStatus.SC_NOT_FOUND)
                    .error(ex.getMessage())
                    .path(request.getRequestURI())
                    .build());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(ApiResponse.<String>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .error("Failed to delete deal")
                    .path(request.getRequestURI())
                    .build());
        }
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<List<DealDTO>>> searchDeals(
            @RequestBody DealSearchCriteria criteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        log.info("🔎 Searching deals with criteria: {}, page: {}, size: {}", criteria, page, size);
        try {
            Page<Deal> dealPage = dealService.searchDeals(criteria, page, size);
            List<DealDTO> deals = dealPage.map(DealMapper::toDTO).getContent();

            return ResponseEntity.ok(ApiResponse.<List<DealDTO>>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(HttpStatus.SC_OK)
                    .path(request.getRequestURI())
                    .totalRecords(dealPage.getTotalElements())
                    .pageSize(dealPage.getSize())
                    .currentPage(dealPage.getNumber())
                    .totalPages(dealPage.getTotalPages())
                    .data(deals)
                    .build());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<List<DealDTO>>builder()
                            .timestamp(ZonedDateTime.now())
                            .status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                            .error("Failed to search deals: " + ex.getMessage())
                            .path(request.getRequestURI())
                            .build());
        }
    }
}
