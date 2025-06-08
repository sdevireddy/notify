package com.zen.notify.search;

import java.math.BigDecimal;

import com.zen.notify.entity.Account;

public class AccountSearchCriteria {

	    private String accountOwner;
	    private String accountName;
	    private String accountSite;
	    private String accountNumber;
	    private Account.AccountType accountType;
	    private String industry;
	    private BigDecimal annualRevenue;
	    private Account.Rating rating;
	    private String phone;
	    private String fax;
	    private String website;
	    private String tickerSymbol;
	    private Account.Ownership ownership;
	    private String sicCode;
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
		public String getAccountNumber() {
			return accountNumber;
		}
		public void setAccountNumber(String accountNumber) {
			this.accountNumber = accountNumber;
		}
		public Account.AccountType getAccountType() {
			return accountType;
		}
		public void setAccountType(Account.AccountType accountType) {
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
		public Account.Rating getRating() {
			return rating;
		}
		public void setRating(Account.Rating rating) {
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
		public Account.Ownership getOwnership() {
			return ownership;
		}
		public void setOwnership(Account.Ownership ownership) {
			this.ownership = ownership;
		}
		public String getSicCode() {
			return sicCode;
		}
		public void setSicCode(String sicCode) {
			this.sicCode = sicCode;
		}
	    


}
