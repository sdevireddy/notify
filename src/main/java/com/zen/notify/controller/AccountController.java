package com.zen.notify.controller;


import com.zen.notify.dto.AccountDTO;
import com.zen.notify.dto.AccountUpdateDTO;
import com.zen.notify.dto.PaginatedResponse;
import com.zen.notify.entity.Account;
import com.zen.notify.entity.Lead;
import com.zen.notify.mapper.AccountMapper;
import com.zen.notify.search.AccountSearchCriteria;
import com.zen.notify.search.LeadSearchCriteria;
import com.zen.notify.service.AccountService;
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

    @Autowired
    private AccountService accountService;

    // Create Account
    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody AccountDTO accountDto) {
        try {
            Account account = AccountMapper.toEntity(accountDto);
            Account createdAccount = accountService.createAccount(account);
            AccountDTO responseDto = AccountMapper.toDTO(createdAccount);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create account: " + ex.getMessage()));
        }
    }

	/*
	 * // Get All Accounts
	 * 
	 * @GetMapping public ResponseEntity<PaginatedResponse<Account>>
	 * getAllAccounts() { List<Account> accounts = accountService.getAllAccounts();
	 * PaginatedResponse<Account> response = new
	 * PaginatedResponse<>(accounts.size(), 20, accounts); return
	 * ResponseEntity.ok(response);
	 * 
	 * }
	 */

    // Get Account by ID
    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id) {
        Optional<Account> accountOpt = accountService.getAccountById(id);
        if (accountOpt.isPresent()) {
            AccountDTO dto = AccountMapper.toDTO(accountOpt.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping
    public ResponseEntity<PaginatedResponse<AccountDTO>> getAllAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int pageSize) {

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

        return ResponseEntity.ok(response);
    }


    // Update Account
    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long id, @RequestBody AccountUpdateDTO dto) {
        Account updated = accountService.updateAccount(id, dto);
        AccountDTO response = AccountMapper.toDTO(updated);
        return ResponseEntity.ok(response);
    }

    // Delete Account
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account deleted successfully");
    }
    
    @PostMapping("/search")
    public ResponseEntity<?> searchLeads(
            @RequestBody AccountSearchCriteria criteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Account> accountPage = accountService.searchAccounts(criteria, page, size);

        PaginatedResponse<Account> response = new PaginatedResponse<>(
        		accountPage.getTotalElements(),
        		accountPage.getSize(),
        		accountPage.getNumber(),
        		accountPage.getTotalPages(),
        		accountPage.getContent()
        );

        return ResponseEntity.ok(response);
    }
}

