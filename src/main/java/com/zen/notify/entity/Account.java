package com.zen.notify.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "accounts", indexes = {@Index(name = "idx_account_name", columnList = "accountName")})
//@NoArgsConstructor
@ToString
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountId") 
    private Long accountId;

    @Column(nullable = true, length = 100)
    private String accountOwner;

    @Column(nullable = false, unique = true, length = 255)
    private String accountName;

    private String accountSite;

    @ManyToOne
    @JoinColumn(name = "parent_account_id", foreignKey = @ForeignKey(name = "fk_account_parent"), nullable=true)
    private Account parentAccount;

    @Column(unique = true, length = 50)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private AccountType accountType;

    private String industry;

    @Column(precision = 15, scale = 2)
    private BigDecimal annualRevenue;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Rating rating;

    private String phone;
    private String fax;
    private String website;
    private String tickerSymbol;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Ownership ownership;


    @Column(length = 50)
    private String sicCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();

    @PreUpdate
    public void setLastUpdated() {
        this.updatedAt = new Date();
    }

    public enum AccountType {
        CUSTOMER, PARTNER, VENDOR, PROSPECT, OTHER
    }

    public enum Rating {
        HOT, WARM, COLD
    }

    public enum Ownership {
        PUBLIC, PRIVATE, SUBSIDIARY, OTHER
    }

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getAccountOwner() {
		return accountOwner;
	}

	public void setAccountOwner(String accountOwner) {
		this.accountOwner = accountOwner;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountSite() {
		return accountSite;
	}

	public void setAccountSite(String accountSite) {
		this.accountSite = accountSite;
	}

	public Account getParentAccount() {
		return parentAccount;
	}

	public void setParentAccount(Account parentAccount) {
		this.parentAccount = parentAccount;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public BigDecimal getAnnualRevenue() {
		return annualRevenue;
	}

	public void setAnnualRevenue(BigDecimal annualRevenue) {
		this.annualRevenue = annualRevenue;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getTickerSymbol() {
		return tickerSymbol;
	}

	public void setTickerSymbol(String tickerSymbol) {
		this.tickerSymbol = tickerSymbol;
	}

	public Ownership getOwnership() {
		return ownership;
	}

	public void setOwnership(Ownership ownership) {
		this.ownership = ownership;
	}


	public String getSicCode() {
		return sicCode;
	}

	public void setSicCode(String sicCode) {
		this.sicCode = sicCode;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public Account() {
		
	}

	public Account(Long accountId, String accountOwner, String accountName, String accountSite, Account parentAccount,
			String accountNumber, AccountType accountType, String industry, BigDecimal annualRevenue, Rating rating,
			String phone, String fax, String website, String tickerSymbol, Ownership ownership, 
			String sicCode, Date createdAt, Date updatedAt) {
		super();
		this.accountId = accountId;
		this.accountOwner = accountOwner;
		this.accountName = accountName;
		this.accountSite = accountSite;
		this.parentAccount = parentAccount;
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.industry = industry;
		this.annualRevenue = annualRevenue;
		this.rating = rating;
		this.phone = phone;
		this.fax = fax;
		this.website = website;
		this.tickerSymbol = tickerSymbol;
		this.ownership = ownership;
		this.sicCode = sicCode;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
    
	
}

