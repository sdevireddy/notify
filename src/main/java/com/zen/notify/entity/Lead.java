package com.zen.notify.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
@Entity
@Table(name = "leads")

@NoArgsConstructor
@AllArgsConstructor

public class Lead {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String leadOwner;
    private String company;
    private String firstName;
    private String lastName;
    private String title;
    private String email;
    private String fax;
    private String mobile;
    private String website;
    private LeadSource leadSource;
    private String leadStatus;
    private String industry;
    private Integer noOfEmployees;
    private Double annualRevenue;
    private String rating;
    private Boolean emailOptOut;
    private String skypeId;
    private String secondaryEmail;
    private String twitter;
    private String description;
    private Boolean converted = false;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLeadOwner() {
		return leadOwner;
	}
	public void setLeadOwner(String leadOwner) {
		this.leadOwner = leadOwner;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public LeadSource getLeadSource() {
		return leadSource;
	}
	public void setLeadSource(LeadSource leadSource) {
		this.leadSource = leadSource;
	}
	public String getLeadStatus() {
		return leadStatus;
	}
	public void setLeadStatus(String leadStatus) {
		this.leadStatus = leadStatus;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public Integer getNoOfEmployees() {
		return noOfEmployees;
	}
	public void setNoOfEmployees(Integer noOfEmployees) {
		this.noOfEmployees = noOfEmployees;
	}
	public Double getAnnualRevenue() {
		return annualRevenue;
	}
	public void setAnnualRevenue(Double annualRevenue) {
		this.annualRevenue = annualRevenue;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public Boolean getEmailOptOut() {
		return emailOptOut;
	}
	public void setEmailOptOut(Boolean emailOptOut) {
		this.emailOptOut = emailOptOut;
	}
	public String getSkypeId() {
		return skypeId;
	}
	public void setSkypeId(String skypeId) {
		this.skypeId = skypeId;
	}
	public String getSecondaryEmail() {
		return secondaryEmail;
	}
	public void setSecondaryEmail(String secondaryEmail) {
		this.secondaryEmail = secondaryEmail;
	}
	public String getTwitter() {
		return twitter;
	}
	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public int hashCode() {
		return Objects.hash(annualRevenue, company, description, email, emailOptOut, fax, firstName, id, industry,
				lastName, leadOwner, leadSource, leadStatus, mobile, noOfEmployees, rating, secondaryEmail, skypeId,
				title, twitter, website);
	}
	
    
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lead other = (Lead) obj;
		return Objects.equals(annualRevenue, other.annualRevenue) && Objects.equals(company, other.company)
				&& Objects.equals(description, other.description) && Objects.equals(email, other.email)
				&& Objects.equals(emailOptOut, other.emailOptOut) && Objects.equals(fax, other.fax)
				&& Objects.equals(firstName, other.firstName) && Objects.equals(id, other.id)
				&& Objects.equals(industry, other.industry) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(leadOwner, other.leadOwner) && Objects.equals(leadSource, other.leadSource)
				&& Objects.equals(leadStatus, other.leadStatus) && Objects.equals(mobile, other.mobile)
				&& Objects.equals(noOfEmployees, other.noOfEmployees) && Objects.equals(rating, other.rating)
				&& Objects.equals(secondaryEmail, other.secondaryEmail) && Objects.equals(skypeId, other.skypeId)
				&& Objects.equals(title, other.title) && Objects.equals(twitter, other.twitter)
				&& Objects.equals(website, other.website);
	}
	public Boolean getConverted() {
		return converted;
	}
	public void setConverted(Boolean converted) {
		this.converted = converted;
	}

    
}