package com.zen.notify.dto;

import lombok.Data;
import java.util.Date;

import com.zen.notify.entity.Contact;

@Data
public class ContactDTO {

    private Long contactId;
    private String contactOwner;
    private String leadSource; // Enum as String

    private String firstName;
    private String lastName;
    private Long accountId; // Avoid full Account object to prevent nesting

    private String vendorName;
    private String email;
    private String title;
    private String phone;
    private String department;
    private String otherPhone;
    private String homePhone;
    private String mobile;

    private Date dateOfBirth;
    private String assistant;
    private String asstPhone;
    private Boolean emailOptOut;
    private String skypeId;
    private String secondaryEmail;
    private String twitterHandle;

    private Long reportingToId; // Avoid circular reference with full Contact

    private Date createdAt;
    private Date updatedAt;
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
	public String getLeadSource() {
		return leadSource;
	}
	public void setLeadSource(String leadSource) {
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
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
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
	public Long getReportingToId() {
		return reportingToId;
	}
	public void setReportingToId(Long reportingToId) {
		this.reportingToId = reportingToId;
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

