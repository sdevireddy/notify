package com.zen.notify.controller;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.NoSuchElementException;

import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zen.notify.dto.ApiResponse;
import com.zen.notify.dto.LeadDTO;
import com.zen.notify.dto.PaginatedResponse;
import com.zen.notify.entity.Contact;
import com.zen.notify.entity.Lead;
import com.zen.notify.mapper.LeadMapper;
import com.zen.notify.search.LeadSearchCriteria;
import com.zen.notify.service.LeadConversionService;
import com.zen.notify.service.LeadService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/crm/leads")
@RequiredArgsConstructor
public class LeadController {

    private static final Logger logger = LoggerFactory.getLogger(LeadController.class);

    @Autowired
    private LeadService leadService;

    @Autowired
    private LeadConversionService leadConversionService;

    @GetMapping
    public ResponseEntity<ApiResponse<PaginatedResponse<LeadDTO>>> getAllLeads(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            HttpServletRequest request) {

        logger.info("Fetching all leads - page: {}, size: {}", page, pageSize);

        Page<Lead> leadsPage = leadService.getLeadsPaginated(page, pageSize);
        Page<LeadDTO> leadsDtoPage = leadsPage.map(LeadMapper::toDto);

        PaginatedResponse<LeadDTO> responseData = new PaginatedResponse<>(
                leadsPage.getTotalElements(),
                leadsPage.getSize(),
                leadsPage.getNumber(),
                leadsPage.getTotalPages(),
                leadsDtoPage.getContent()
        );

        ApiResponse<PaginatedResponse<LeadDTO>> response = ApiResponse.<PaginatedResponse<LeadDTO>>builder()
                .timestamp(ZonedDateTime.now())
                .status(HttpStatus.SC_OK)
                .error("OK")
                .path(request.getRequestURI())
                .data(responseData)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LeadDTO>> getLeadById(@PathVariable Long id, HttpServletRequest request) {
        logger.info("Fetching lead with ID: {}", id);

        try {
            Lead lead = leadService.getLeadById(id);
            if (lead == null) {
                return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(
                        ApiResponse.<LeadDTO>builder()
                                .timestamp(ZonedDateTime.now())
                                .status(HttpStatus.SC_NOT_FOUND)
                                .error("Lead not found")
                                .path(request.getRequestURI())
                                .data(null)
                                .build());
            }

            LeadDTO leadDto = LeadMapper.toDto(lead);
            return ResponseEntity.ok(ApiResponse.<LeadDTO>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(HttpStatus.SC_OK)
                    .error("OK")
                    .path(request.getRequestURI())
                    .data(leadDto)
                    .build());

        } catch (NoSuchElementException | EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(
                    ApiResponse.<LeadDTO>builder()
                            .timestamp(ZonedDateTime.now())
                            .status(HttpStatus.SC_NOT_FOUND)
                            .error("Lead not found")
                            .path(request.getRequestURI())
                            .data(null)
                            .build());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(
                    ApiResponse.<LeadDTO>builder()
                            .timestamp(ZonedDateTime.now())
                            .status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                            .error("Unexpected error")
                            .path(request.getRequestURI())
                            .data(null)
                            .build());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<LeadDTO>> createLead(@RequestBody LeadDTO leadDto, HttpServletRequest request) {
        logger.info("Creating new lead: {}", leadDto);

        try {
            Lead lead = LeadMapper.toEntity(leadDto);
            Lead createdLead = leadService.createLead(lead);
            LeadDTO savedLeadDto = LeadMapper.toDto(createdLead);

            ApiResponse<LeadDTO> response = ApiResponse.<LeadDTO>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(HttpStatus.SC_CREATED)
                    .error("Created")
                    .path(request.getRequestURI())
                    .data(savedLeadDto)
                    .build();

            return ResponseEntity.status(HttpStatus.SC_CREATED).body(response);

        } catch (RuntimeException e) {
            logger.error("Failed to create lead: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.SC_CONFLICT).body(
                    ApiResponse.<LeadDTO>builder()
                            .timestamp(ZonedDateTime.now())
                            .status(HttpStatus.SC_CONFLICT)
                            .error("Duplicate Lead")
                            .path(request.getRequestURI())
                            .data(null)
                            .build());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<LeadDTO>> updateLead(@PathVariable Long id, @RequestBody Lead leadDetails, HttpServletRequest request) {
        logger.info("Updating lead with ID: {}", id);

        try {
            Lead updatedLead = leadService.updateLead(id, leadDetails);
            LeadDTO savedLeadDto = LeadMapper.toDto(updatedLead);

            return ResponseEntity.ok(ApiResponse.<LeadDTO>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(HttpStatus.SC_OK)
                    .error("OK")
                    .path(request.getRequestURI())
                    .data(savedLeadDto)
                    .build());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_CONFLICT).body(
                    ApiResponse.<LeadDTO>builder()
                            .timestamp(ZonedDateTime.now())
                            .status(HttpStatus.SC_CONFLICT)
                            .error("Lead Update Failed!!!")
                            .path(request.getRequestURI())
                            .data(null)
                            .build());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteLead(@PathVariable Long id, HttpServletRequest request) {
        logger.info("Deleting lead with ID: {}", id);
        leadService.deleteLead(id);

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .timestamp(ZonedDateTime.now())
                        .status(HttpStatus.OK.value())
                        .error(null)
                        .path(request.getRequestURI())
                        .data("Lead with ID " + id + " deleted successfully.")
                        .build()
        );
    }

    @PostMapping("/{leadId}/convert")
    public ResponseEntity<ApiResponse<Contact>> convertLead(@PathVariable Long leadId, HttpServletRequest request) {
        Contact convertedContact = leadConversionService.convertLeadToContact(leadId);
        return ResponseEntity.ok(ApiResponse.<Contact>builder()
                .timestamp(ZonedDateTime.now())
                .status(HttpStatus.SC_OK)
                .error("OK")
                .path(request.getRequestURI())
                .data(convertedContact)
                .build());
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<PaginatedResponse<LeadDTO>>> searchLeads(
            @RequestBody LeadSearchCriteria criteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        try {
            Page<Lead> leadPage = leadService.searchLeads(criteria, page, size);
            Page<LeadDTO> leadsDtoPage = leadPage.map(LeadMapper::toDto);

            PaginatedResponse<LeadDTO> responseData = new PaginatedResponse<>(
                    leadPage.getTotalElements(),
                    leadPage.getSize(),
                    leadPage.getNumber(),
                    leadPage.getTotalPages(),
                    leadsDtoPage.getContent()
            );

            return ResponseEntity.ok(ApiResponse.<PaginatedResponse<LeadDTO>>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(HttpStatus.SC_OK)
                    .error("OK")
                    .path(request.getRequestURI())
                    .data(responseData)
                    .build());

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.SC_CONFLICT).body(
                    ApiResponse.<PaginatedResponse<LeadDTO>>builder()
                            .timestamp(ZonedDateTime.now())
                            .status(HttpStatus.SC_CONFLICT)
                            .error("Lead Search Failed!!")
                            .path(request.getRequestURI())
                            .data(null)
                            .build());
        }
    }
}
