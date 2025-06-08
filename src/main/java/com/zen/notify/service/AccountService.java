package com.zen.notify.service;



import com.zen.notify.entity.Account;
import com.zen.notify.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    // Create Account
    public Account createAccount(Account account) {
    	account.setCreatedAt(new Date());
    	account.setUpdatedAt(new Date());
    	
        return accountRepository.save(account);
    }

    // Get All Accounts
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Get Account by ID
    public Optional<Account> getAccountById(Long accountId) {
        return accountRepository.findById(accountId);
    }
    
    public Page<Account> getAccountsPaginated(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return accountRepository.findAll(pageable);
    }

    // Update Account
    public Account updateAccount(Long accountId, Account accountDetails) {
        return accountRepository.findById(accountId).map(account -> {
            account.setAccountOwner(accountDetails.getAccountOwner());
            account.setAccountName(accountDetails.getAccountName());
            account.setAccountSite(accountDetails.getAccountSite());
            account.setParentAccount(accountDetails.getParentAccount());
            account.setAccountNumber(accountDetails.getAccountNumber());
            account.setAccountType(accountDetails.getAccountType());
            account.setIndustry(accountDetails.getIndustry());
            account.setAnnualRevenue(accountDetails.getAnnualRevenue());
            account.setRating(accountDetails.getRating());
            account.setPhone(accountDetails.getPhone());
            account.setFax(accountDetails.getFax());
            account.setWebsite(accountDetails.getWebsite());
            account.setTickerSymbol(accountDetails.getTickerSymbol());
            account.setOwnership(accountDetails.getOwnership());
            account.setSicCode(accountDetails.getSicCode());
            return accountRepository.save(account);
        }).orElseThrow(() -> new RuntimeException("Account not found"));
    }

    // Delete Account
    public void deleteAccount(Long accountId) {
        accountRepository.deleteById(accountId);
    }
}
