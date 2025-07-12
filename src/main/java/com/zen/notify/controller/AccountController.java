package com.zen.notify.controller;

import com.zen.notify.dto.AccountDTO;
import com.zen.notify.dto.AccountUpdateDTO;
import com.zen.notify.dto.PaginatedResponse;
import com.zen.notify.entity.Account;
import com.zen.notify.mapper.AccountMapper;
import com.zen.notify.search.AccountSearchCriteria;
import com.zen.notify.service.AccountService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/crm/accounts")
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    // Create Account
    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody AccountDTO accountDto) {
        log.info("📥 Creating account: {}", accountDto);

        try {
            Account account = AccountMapper.toEntity(accountDto);
            Account createdAccount = accountService.createAccount(account);
            AccountDTO responseDto = AccountMapper.toDTO(createdAccount);

            log.info("✅ Account created successfully with ID: {}", responseDto.getAccountId());
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (Exception ex) {
            log.error("❌ Failed to create account: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create account: " + ex.getMessage()));
        }
    }

    // Get Account by ID
    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id) {
        log.info("🔍 Fetching account with ID: {}", id);

        Optional<Account> accountOpt = accountService.getAccountById(id);
        if (accountOpt.isPresent()) {
            AccountDTO dto = AccountMapper.toDTO(accountOpt.get());
            log.info("✅ Account found for ID: {}", id);
            return ResponseEntity.ok(dto);
        } else {
            log.warn("⚠️ Account not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    // Get All Accounts (Paginated)
    @GetMapping
    public ResponseEntity<PaginatedResponse<AccountDTO>> getAllAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int pageSize) {

        log.info("📄 Fetching paginated accounts - page: {}, pageSize: {}", page, pageSize);

        Page<Account> accountPage = accountService.getAccountsPaginated(page, pageSize);
        List<AccountDTO> dtoList = accountPage.getContent().stream()
                .map(AccountMapper::toDTO)
                .collect(Collectors.toList());

        PaginatedResponse<AccountDTO> response = new PaginatedResponse<>(
                accountPage.getTotalElements(),
                accountPage.getSize(),
                accountPage.getNumber(),
                accountPage.getTotalPages(),
                dtoList
        );

        log.info("✅ Retrieved {} accounts", dtoList.size());
        return ResponseEntity.ok(response);
    }

    // Update Account
    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long id, @RequestBody AccountUpdateDTO dto) {
        log.info("✏️ Updating account with ID: {}", id);

        Account updated = accountService.updateAccount(id, dto);
        AccountDTO response = AccountMapper.toDTO(updated);

        log.info("✅ Account updated successfully for ID: {}", id);
        return ResponseEntity.ok(response);
    }

    // Delete Account
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        log.info("🗑️ Deleting account with ID: {}", id);

        accountService.deleteAccount(id);

        log.info("✅ Account deleted successfully for ID: {}", id);
        return ResponseEntity.ok("Account deleted successfully");
    }

    // Search Accounts
    @PostMapping("/search")
    public ResponseEntity<?> searchLeads(
            @RequestBody AccountSearchCriteria criteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        log.info("🔎 Searching accounts with criteria: {}, page: {}, size: {}", criteria, page, size);

        try {
            Page<Account> accountPage = accountService.searchAccounts(criteria, page, size);

            PaginatedResponse<Account> response = new PaginatedResponse<>(
                    accountPage.getTotalElements(),
                    accountPage.getSize(),
                    accountPage.getNumber(),
                    accountPage.getTotalPages(),
                    accountPage.getContent()
            );

            log.info("✅ Account search returned {} results", response.getData().size());
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("❌ Account search failed: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Account search failed", "message", ex.getMessage()));
        }
    }
}
