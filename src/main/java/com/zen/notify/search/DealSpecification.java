package com.zen.notify.search;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import com.zen.notify.entity.Deal;

import java.util.ArrayList;
import java.util.List;

public class DealSpecification implements Specification<Deal> {

    private final DealSearchCriteria criteria;

    public DealSpecification(DealSearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Deal> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getDealOwner() != null) {
            String value = escapeLikeSpecialChars(criteria.getDealOwner().toLowerCase());
            predicates.add(builder.like(builder.lower(root.get("dealOwner")), "%" + value + "%", '\\'));
        }

        if (criteria.getDealName() != null) {
            String value = escapeLikeSpecialChars(criteria.getDealName().toLowerCase());
            predicates.add(builder.like(builder.lower(root.get("dealName")), "%" + value + "%", '\\'));
        }

        if (criteria.getType() != null) {
            predicates.add(builder.equal(root.get("type"), criteria.getType()));
        }

        if (criteria.getNextStep() != null) {
            String value = escapeLikeSpecialChars(criteria.getNextStep().toLowerCase());
            predicates.add(builder.like(builder.lower(root.get("nextStep")), "%" + value + "%", '\\'));
        }

        if (criteria.getLeadSource() != null) {
            predicates.add(builder.equal(root.get("leadSource"), criteria.getLeadSource()));
        }

        if (criteria.getAmount() != null) {
            predicates.add(builder.equal(root.get("amount"), criteria.getAmount()));
        }

        if (criteria.getClosingDate() != null) {
            predicates.add(builder.equal(root.get("closingDate"), criteria.getClosingDate()));
        }

        if (criteria.getContactId() != null) {
            predicates.add(builder.equal(root.get("contact").get("contactId"), criteria.getContactId()));
        }

        if (criteria.getAccountId() != null) {
            predicates.add(builder.equal(root.get("account").get("accountId"), criteria.getAccountId()));
        }

        if (criteria.getStage() != null) {
            predicates.add(builder.equal(root.get("stage"), criteria.getStage()));
        }

        if (criteria.getQualification() != null) {
            String value = escapeLikeSpecialChars(criteria.getQualification().toLowerCase());
            predicates.add(builder.like(builder.lower(root.get("qualification")), "%" + value + "%", '\\'));
        }

        if (criteria.getProbability() != null) {
            predicates.add(builder.equal(root.get("probability"), criteria.getProbability()));
        }

        if (criteria.getExpectedRevenue() != null) {
            predicates.add(builder.equal(root.get("expectedRevenue"), criteria.getExpectedRevenue()));
        }

        if (criteria.getCampaignSource() != null) {
            String value = escapeLikeSpecialChars(criteria.getCampaignSource().toLowerCase());
            predicates.add(builder.like(builder.lower(root.get("campaignSource")), "%" + value + "%", '\\'));
        }

        if (criteria.getCreatedAt() != null) {
            predicates.add(builder.equal(root.get("createdAt"), criteria.getCreatedAt()));
        }

        if (criteria.getUpdatedAt() != null) {
            predicates.add(builder.equal(root.get("updatedAt"), criteria.getUpdatedAt()));
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }

    private String escapeLikeSpecialChars(String input) {
        return input
                .replace("\\", "\\\\")
                .replace("%", "\\%")
                .replace("_", "\\_");
    }
}

