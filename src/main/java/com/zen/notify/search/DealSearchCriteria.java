package com.zen.notify.search;

import java.math.BigDecimal;
import java.util.Date;

import com.zen.notify.entity.Deal;
import com.zen.notify.entity.LeadSource;

public class DealSearchCriteria {

    private String dealOwner;
    private String dealName;
    private Deal.DealType type;
    private String nextStep;
    private LeadSource leadSource;
    private BigDecimal amount;
    private Date closingDate;
    private Long contactId;  // for filtering by related Contact entity
    private Long accountId;  // for filtering by related Account entity
    private Deal.Stage stage;
    private String qualification;
    private Integer probability;
    private BigDecimal expectedRevenue;
    private String campaignSource;
    private Date createdAt;
    private Date updatedAt;

    // Getters and Setters

    public String getDealOwner() {
        return dealOwner;
    }

    public void setDealOwner(String dealOwner) {
        this.dealOwner = dealOwner;
    }

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public Deal.DealType getType() {
        return type;
    }

    public void setType(Deal.DealType type) {
        this.type = type;
    }

    public String getNextStep() {
        return nextStep;
    }

    public void setNextStep(String nextStep) {
        this.nextStep = nextStep;
    }

    public LeadSource getLeadSource() {
        return leadSource;
    }

    public void setLeadSource(LeadSource leadSource) {
        this.leadSource = leadSource;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Deal.Stage getStage() {
        return stage;
    }

    public void setStage(Deal.Stage stage) {
        this.stage = stage;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public Integer getProbability() {
        return probability;
    }

    public void setProbability(Integer probability) {
        this.probability = probability;
    }

    public BigDecimal getExpectedRevenue() {
        return expectedRevenue;
    }

    public void setExpectedRevenue(BigDecimal expectedRevenue) {
        this.expectedRevenue = expectedRevenue;
    }

    public String getCampaignSource() {
        return campaignSource;
    }

    public void setCampaignSource(String campaignSource) {
        this.campaignSource = campaignSource;
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
