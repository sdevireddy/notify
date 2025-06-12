package com.zen.notify.controller;


import com.zen.notify.dto.PaginatedResponse;
import com.zen.notify.entity.Account;
import com.zen.notify.entity.Lead;
import com.zen.notify.search.AccountSearchCriteria;
import com.zen.notify.search.LeadSearchCriteria;
import com.zen.notify.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // Create Account
    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        return ResponseEntity.ok(accountService.createAccount(account));
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
    public ResponseEntity<Optional<Account>> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }
    
    @GetMapping
    public ResponseEntity<PaginatedResponse<Account>> getAllAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int pageSize) {

        Page<Account> accountPage = accountService.getAccountsPaginated(page, pageSize);
        PaginatedResponse<Account> response = new PaginatedResponse<>(
        		accountPage.getTotalElements(),
        		accountPage.getSize(),
        		accountPage.getNumber(),
        		accountPage.getTotalPages(),
        		accountPage.getContent()
        );

        return ResponseEntity.ok(response);
    }


    // Update Account
    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account accountDetails) {
        return ResponseEntity.ok(accountService.updateAccount(id, accountDetails));
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

