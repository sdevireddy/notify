package com.zen.notify.entity;

import jakarta.persistence.*;
import lombok.*;
import com.zen.notify.entity.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "contacts", indexes = {@Index(name = "idx_email", columnList = "email")})
@ToString
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactId;

    @Column(nullable = true)
    private String contactOwner;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private LeadSource leadSource;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;
    @ManyToOne(fetch = FetchType.LAZY)
    
    @JoinColumn(name = "accountID", foreignKey = @ForeignKey(name = "fk_contact_account"))
    private Account account;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Deal> deals = new ArrayList<>();

    private String vendorName;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    private String title;
    private String phone;
    private String department;
    private String otherPhone;
    private String homePhone;
    private String mobile;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    private String assistant;
    @Column(name = "asstPhone")
    private String asstPhone;
    private Boolean emailOptOut = false;
    private String skypeId;
    private String secondaryEmail;
    private String twitterHandle;
    
    

    @ManyToOne
    @JoinColumn(name = "reporting_to", foreignKey = @ForeignKey(name = "fk_contact_reporting_to"))
    private Contact reportingTo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();

    @PreUpdate
    public void setLastUpdated() {
        this.updatedAt = new Date();
    }

	public Long getContactId() {
		return contactId;
	}

	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}

	public String getContactOwner() {
		return contactOwner;
	}

	public void setContactOwner(String contactOwner) {
		this.contactOwner = contactOwner;
	}

	public LeadSource getLeadSource() {
		return leadSource;
	}

	public void setLeadSource(LeadSource leadSource) {
		this.leadSource = leadSource;
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

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getOtherPhone() {
		return otherPhone;
	}

	public void setOtherPhone(String otherPhone) {
		this.otherPhone = otherPhone;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAssistant() {
		return assistant;
	}

	public void setAssistant(String assistant) {
		this.assistant = assistant;
	}

	public String getAsstPhone() {
		return asstPhone;
	}

	public void setAsstPhone(String asstPhone) {
		this.asstPhone = asstPhone;
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

	public String getTwitterHandle() {
		return twitterHandle;
	}

	public void setTwitterHandle(String twitterHandle) {
		this.twitterHandle = twitterHandle;
	}

	public Contact getReportingTo() {
		return reportingTo;
	}

	public void setReportingTo(Contact reportingTo) {
		this.reportingTo = reportingTo;
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
    
    
    
}
