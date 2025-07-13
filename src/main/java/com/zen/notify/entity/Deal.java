package com.zen.notify.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "deals", indexes = {@Index(name = "idx_deal_name", columnList = "dealName")})


@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dealId;

    @Column(nullable = true, length = 100)
    private String dealOwner;

    @Column(nullable = false, unique = true, length = 255)
    private String dealName;


    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private DealType type;

    private String nextStep;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private LeadSource leadSource;


    @Column(precision = 15, scale = 2)
    private BigDecimal amount;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date closingDate;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contactID", foreignKey = @ForeignKey(name = "fk_deal_contact"))
    private Contact contact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountID", foreignKey = @ForeignKey(name = "fk_deal_account"))
    private Account account;



    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Stage stage;

    private String qualification;

    @Column(nullable = false)
    private Integer probability;

    @Column(precision = 15, scale = 2)
    private BigDecimal expectedRevenue;

    private String campaignSource;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();

    @PreUpdate
    public void setLastUpdated() {
        this.updatedAt = new Date();
    }

    public enum DealType {
        NEW_BUSINESS, EXISTING_BUSINESS, RENEWAL, OTHER, NEW
    }



    public enum Stage {
        PROSPECTING, QUALIFICATION, PROPOSAL, NEGOTIATION, CLOSED_WON, CLOSED_LOST
    }

	public Long getDealId() {
		return dealId;
	}

	public void setDealId(Long dealId) {
		this.dealId = dealId;
	}

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

	public DealType getType() {
		return type;
	}

	public void setType(DealType type) {
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

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
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

