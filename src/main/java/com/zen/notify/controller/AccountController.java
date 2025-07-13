package com.zen.notify.controller;

import com.zen.notify.dto.AccountDTO;
import com.zen.notify.dto.AccountUpdateDTO;
import com.zen.notify.dto.ApiResponse;
import com.zen.notify.entity.Account;
import com.zen.notify.mapper.AccountMapper;
import com.zen.notify.search.AccountSearchCriteria;
import com.zen.notify.service.AccountService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/crm/accounts")
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<AccountDTO>> createAccount(@RequestBody AccountDTO accountDto, HttpServletRequest request) {
        log.info("📥 Creating account: {}", accountDto);
        try {
            Account account = AccountMapper.toEntity(accountDto);
            Account createdAccount = accountService.createAccount(account);
            AccountDTO responseDto = AccountMapper.toDTO(createdAccount);

            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<AccountDTO>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(HttpStatus.CREATED.value())
                    .path(request.getRequestURI())
                    .data(responseDto)
                    .build());

        } catch (DataIntegrityViolationException ex) {
            log.error("❌ Duplicate account error: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.<AccountDTO>builder()
                            .timestamp(ZonedDateTime.now())
                            .status(HttpStatus.CONFLICT.value())
                            .error("Account name already exists.")
                            .path(request.getRequestURI())
                            .build());
        } catch (Exception ex) {
            log.error("❌ Failed to create account: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<AccountDTO>builder()
                            .timestamp(ZonedDateTime.now())
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .error("Failed to create account: " + ex.getMessage())
                            .path(request.getRequestURI())
                            .build());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountDTO>> getAccountById(@PathVariable Long id, HttpServletRequest request) {
        log.info("🔍 Fetching account with ID: {}", id);

        Optional<Account> accountOpt = accountService.getAccountById(id);
        if (accountOpt.isPresent()) {
            AccountDTO dto = AccountMapper.toDTO(accountOpt.get());
            return ResponseEntity.ok(ApiResponse.<AccountDTO>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(HttpStatus.OK.value())
                    .path(request.getRequestURI())
                    .data(dto)
                    .build());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.<AccountDTO>builder()
                            .timestamp(ZonedDateTime.now())
                            .status(HttpStatus.NOT_FOUND.value())
                            .error("Account not found with ID: " + id)
                            .path(request.getRequestURI())
                            .build());
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AccountDTO>>> getAllAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int pageSize,
            HttpServletRequest request) {

        log.info("📄 Fetching paginated accounts - page: {}, pageSize: {}", page, pageSize);

        Page<Account> accountPage = accountService.getAccountsPaginated(page, pageSize);
        List<AccountDTO> dtoList = accountPage.getContent().stream()
                .map(AccountMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.<List<AccountDTO>>builder()
                .timestamp(ZonedDateTime.now())
                .status(HttpStatus.OK.value())
                .path(request.getRequestURI())
                .totalRecords(accountPage.getTotalElements())
                .pageSize(accountPage.getSize())
                .currentPage(accountPage.getNumber())
                .totalPages(accountPage.getTotalPages())
                .data(dtoList)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountDTO>> updateAccount(@PathVariable Long id, @RequestBody AccountUpdateDTO dto, HttpServletRequest request) {
        log.info("✏️ Updating account with ID: {}", id);
        try {
            Account updated = accountService.updateAccount(id, dto);
            AccountDTO response = AccountMapper.toDTO(updated);

            return ResponseEntity.ok(ApiResponse.<AccountDTO>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(HttpStatus.OK.value())
                    .path(request.getRequestURI())
                    .data(response)
                    .build());
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.<AccountDTO>builder()
                            .timestamp(ZonedDateTime.now())
                            .status(HttpStatus.NOT_FOUND.value())
                            .error(ex.getMessage())
                            .path(request.getRequestURI())
                            .build());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<AccountDTO>builder()
                            .timestamp(ZonedDateTime.now())
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .error("Failed to update account: " + ex.getMessage())
                            .path(request.getRequestURI())
                            .build());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteAccount(@PathVariable Long id, HttpServletRequest request) {
        try {
            accountService.deleteAccountById(id);
            return ResponseEntity.ok(ApiResponse.<String>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(HttpStatus.OK.value())
                    .path(request.getRequestURI())
                    .data("Account deleted successfully")
                    .build());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.<String>builder()
                            .timestamp(ZonedDateTime.now())
                            .status(HttpStatus.NOT_FOUND.value())
                            .error(e.getMessage())
                            .path(request.getRequestURI())
                            .build());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.<String>builder()
                            .timestamp(ZonedDateTime.now())
                            .status(HttpStatus.BAD_REQUEST.value())
                            .error(e.getMessage())
                            .path(request.getRequestURI())
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<String>builder()
                            .timestamp(ZonedDateTime.now())
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .error("Unexpected error occurred while deleting account.")
                            .path(request.getRequestURI())
                            .build());
        }
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<List<AccountDTO>>> searchAccounts(
            @RequestBody AccountSearchCriteria criteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        log.info("🔎 Searching accounts with criteria: {}, page: {}, size: {}", criteria, page, size);
        try {
            Page<Account> accountPage = accountService.searchAccounts(criteria, page, size);
            List<AccountDTO> dtoList = accountPage.getContent().stream()
                    .map(AccountMapper::toDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(ApiResponse.<List<AccountDTO>>builder()
                    .timestamp(ZonedDateTime.now())
                    .status(HttpStatus.OK.value())
                    .path(request.getRequestURI())
                    .totalRecords(accountPage.getTotalElements())
                    .pageSize(accountPage.getSize())
                    .currentPage(accountPage.getNumber())
                    .totalPages(accountPage.getTotalPages())
                    .data(dtoList)
                    .build());
        } catch (Exception ex) {
            log.error("❌ Account search failed: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<List<AccountDTO>>builder()
                            .timestamp(ZonedDateTime.now())
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .error("Account search failed: " + ex.getMessage())
                            .path(request.getRequestURI())
                            .build());
        }
    }
}