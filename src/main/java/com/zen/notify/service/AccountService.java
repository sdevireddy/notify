package com.zen.notify.service;

import com.zen.notify.dto.AccountUpdateDTO;
import com.zen.notify.entity.Account;
import com.zen.notify.repository.AccountRepository;
import com.zen.notify.search.AccountSearchCriteria;
import com.zen.notify.search.AccountSpecification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.ResourceNotFoundException;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountRepository accountRepository;

    // Create Account
    public Account createAccount(Account account) {
        account.setCreatedAt(new Date());
        account.setUpdatedAt(new Date());

        Account savedAccount = accountRepository.save(account);
        log.info("✅ Account created: ID={}, Name={}", savedAccount.getAccountId(), savedAccount.getAccountName());

        return savedAccount;
    }

    // Get All Accounts
    public List<Account> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        log.info("📦 Retrieved all accounts, count={}", accounts.size());
        return accounts;
    }

    // Get Account by ID
    public Optional<Account> getAccountById(Long accountId) {
        Optional<Account> accountOpt = accountRepository.findById(accountId);
        if (accountOpt.isPresent()) {
            log.info("🔍 Found account with ID={}", accountId);
        } else {
            log.warn("⚠️ Account not found with ID={}", accountId);
        }
        return accountOpt;
    }

    public Optional<Account> findById(Long accountId) {
        return getAccountById(accountId); // log inside above method
    }

    // Get Paginated Accounts
    public Page<Account> getAccountsPaginated(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Account> pageResult = accountRepository.findAll(pageable);
        log.info("📄 Paginated accounts fetched: Page={}, Size={}, TotalElements={}",
                page, pageSize, pageResult.getTotalElements());
        return pageResult;
    }

    // Update Account
    public Account updateAccount(Long id, AccountUpdateDTO dto) {
        log.info("🛠️ Updating account ID={}", id);
        Account existing = accountRepository.findById(id)
            .orElseThrow(() -> {
                log.error("❌ Account not found for update ID={}", id);
                return new ResourceNotFoundException("Account not found");
            });

        existing.setAccountOwner(dto.getAccountOwner());
        existing.setAccountName(dto.getAccountName());
        existing.setAccountSite(dto.getAccountSite());
        existing.setPhone(dto.getPhone());
        existing.setFax(dto.getFax());
        existing.setWebsite(dto.getWebsite());
        existing.setIndustry(dto.getIndustry());
        existing.setAnnualRevenue(dto.getAnnualRevenue());
        existing.setAccountType(dto.getAccountType());
        existing.setRating(dto.getRating());
        existing.setOwnership(dto.getOwnership());
        existing.setTickerSymbol(dto.getTickerSymbol());
        existing.setSicCode(dto.getSicCode());
        existing.setAccountNumber(dto.getAccountNumber());

        if (dto.getParentAccountId() != null) {
            Account parent = accountRepository.findById(dto.getParentAccountId())
                .orElseThrow(() -> {
                    log.error("❌ Parent Account not found ID={}", dto.getParentAccountId());
                    return new ResourceNotFoundException("Parent Account not found");
                });
            existing.setParentAccount(parent);
        } else {
            existing.setParentAccount(null);
        }

        Account updatedAccount = accountRepository.save(existing);
        log.info("✅ Account updated: ID={}, Name={}", updatedAccount.getAccountId(), updatedAccount.getAccountName());
        return updatedAccount;
    }

    // Delete Account
    public void deleteAccount(Long accountId) {
        log.info("🗑️ Deleting account ID={}", accountId);
        accountRepository.deleteById(accountId);
        log.info("✅ Account deleted ID={}", accountId);
    }

    // Search Accounts with Criteria
    public Page<Account> searchAccounts(AccountSearchCriteria criteria, int page, int size) {
        log.info("🔎 Searching accounts - Page={}, Size={}, Criteria={}", page, size, criteria);
        Pageable pageable = PageRequest.of(page, size);
        Specification<Account> spec = new AccountSpecification(criteria);
        Page<Account> result = accountRepository.findAll(spec, pageable);
        log.info("✅ Search results - TotalElements={}", result.getTotalElements());
        return result;
    }
}
