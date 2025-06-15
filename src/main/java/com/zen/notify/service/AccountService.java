package com.zen.notify.service;



import com.zen.notify.dto.AccountUpdateDTO;
import com.zen.notify.entity.Account;
import com.zen.notify.entity.Contact;
import com.zen.notify.entity.Lead;
import com.zen.notify.repository.AccountRepository;
import com.zen.notify.search.AccountSearchCriteria;
import com.zen.notify.search.AccountSpecification;
import com.zen.notify.search.LeadSearchCriteria;
import com.zen.notify.search.LeadSpecification;

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

    public Account updateAccount(Long id, AccountUpdateDTO dto) {
        Account existing = accountRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

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

        // Set parent account if provided
        if (dto.getParentAccountId() != null) {
            Account parent = accountRepository.findById(dto.getParentAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Parent Account not found"));
            existing.setParentAccount(parent);
        } else {
            existing.setParentAccount(null);
        }

        return accountRepository.save(existing);
    }


    // Delete Account
    public void deleteAccount(Long accountId) {
        accountRepository.deleteById(accountId);
    }
    
    public Page<Account> searchAccounts(AccountSearchCriteria criteria, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Account> spec = new AccountSpecification(criteria);
        return accountRepository.findAll(spec, pageable);
    }

	public Optional<Account> findById(Long accountId) {
		return  accountRepository.findById(accountId);
	}

}
