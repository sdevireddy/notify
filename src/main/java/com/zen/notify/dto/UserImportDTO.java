package com.zen.notify.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;



import  jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

public class UserImportDTO {

    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    private String email;

    private String firstName;
    private String lastName;

    @Pattern(regexp = "^[0-9\\-+() ]{7,20}$", message = "Invalid phone number")
    private String phoneNumber;

    private Boolean isActive;
    private Instant lastLogin;
    private Instant createdAt;
    private Instant updatedAt;

    @URL(message = "Invalid URL format")
    private String profilePictureUrl;

    private String timezone;
    private String languagePreference;
    private String department;
    private String jobTitle;
    private Long managerId;
    private String address;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female, or Other")
    private String gender;

    private Boolean accountLocked;
    private Boolean twoFactorEnabled;

    private String securityQuestion;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Instant getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Instant lastLogin) {
		this.lastLogin = lastLogin;
	}
	public Instant getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
	public Instant getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}
	public void setProfilePictureUrl(String profilePictureUrl) {
		this.profilePictureUrl = profilePictureUrl;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	public String getLanguagePreference() {
		return languagePreference;
	}
	public void setLanguagePreference(String languagePreference) {
		this.languagePreference = languagePreference;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public Long getManagerId() {
		return managerId;
	}
	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Boolean getAccountLocked() {
		return accountLocked;
	}
	public void setAccountLocked(Boolean accountLocked) {
		this.accountLocked = accountLocked;
	}
	public Boolean getTwoFactorEnabled() {
		return twoFactorEnabled;
	}
	public void setTwoFactorEnabled(Boolean twoFactorEnabled) {
		this.twoFactorEnabled = twoFactorEnabled;
	}
	public String getSecurityQuestion() {
		return securityQuestion;
	}
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}
	@Override
	public int hashCode() {
		return Objects.hash(accountLocked, address, createdAt, dateOfBirth, department, email, firstName, gender,
				isActive, jobTitle, languagePreference, lastLogin, lastName, managerId, phoneNumber, profilePictureUrl,
				securityQuestion, timezone, twoFactorEnabled, updatedAt, username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserImportDTO other = (UserImportDTO) obj;
		return Objects.equals(accountLocked, other.accountLocked) && Objects.equals(address, other.address)
				&& Objects.equals(createdAt, other.createdAt) && Objects.equals(dateOfBirth, other.dateOfBirth)
				&& Objects.equals(department, other.department) && Objects.equals(email, other.email)
				&& Objects.equals(firstName, other.firstName) && Objects.equals(gender, other.gender)
				&& Objects.equals(isActive, other.isActive) && Objects.equals(jobTitle, other.jobTitle)
				&& Objects.equals(languagePreference, other.languagePreference)
				&& Objects.equals(lastLogin, other.lastLogin) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(managerId, other.managerId) && Objects.equals(phoneNumber, other.phoneNumber)
				&& Objects.equals(profilePictureUrl, other.profilePictureUrl)
				&& Objects.equals(securityQuestion, other.securityQuestion) && Objects.equals(timezone, other.timezone)
				&& Objects.equals(twoFactorEnabled, other.twoFactorEnabled)
				&& Objects.equals(updatedAt, other.updatedAt) && Objects.equals(username, other.username);
	}
	@Override
	public String toString() {
		return "UserImportDTO [username=" + username + ", email=" + email + ", firstName=" + firstName + ", lastName="
				+ lastName + ", phoneNumber=" + phoneNumber + ", isActive=" + isActive + ", lastLogin=" + lastLogin
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", profilePictureUrl=" + profilePictureUrl
				+ ", timezone=" + timezone + ", languagePreference=" + languagePreference + ", department=" + department
				+ ", jobTitle=" + jobTitle + ", managerId=" + managerId + ", address=" + address + ", dateOfBirth="
				+ dateOfBirth + ", gender=" + gender + ", accountLocked=" + accountLocked + ", twoFactorEnabled="
				+ twoFactorEnabled + ", securityQuestion=" + securityQuestion + "]";
	}
    
    
}

