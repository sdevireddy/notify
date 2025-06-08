package com.zen.notify.search;

import org.springframework.data.jpa.domain.Specification;

import com.zen.notify.entity.Lead;

import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class LeadSpecification implements Specification<Lead> {

    private final LeadSearchCriteria criteria;

    public LeadSpecification(LeadSearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Lead> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

            if (criteria.getLeadOwner() != null) {
                String value = escapeLikeSpecialChars(criteria.getLeadOwner().toLowerCase());
                predicates.add(builder.like(builder.lower(root.get("leadOwner")), "%" + value + "%", '\\'));
            }

            if (criteria.getCompany() != null) {
                String value = escapeLikeSpecialChars(criteria.getCompany().toLowerCase());
                predicates.add(builder.like(builder.lower(root.get("company")), "%" + value + "%", '\\'));
            }

            if (criteria.getFirstName() != null) {
                String value = escapeLikeSpecialChars(criteria.getFirstName().toLowerCase());
                predicates.add(builder.like(builder.lower(root.get("firstName")), "%" + value + "%", '\\'));
            }

            if (criteria.getLastName() != null) {
                String value = escapeLikeSpecialChars(criteria.getLastName().toLowerCase());
                predicates.add(builder.like(builder.lower(root.get("lastName")), "%" + value + "%", '\\'));
            }

            if (criteria.getTitle() != null) {
                String value = escapeLikeSpecialChars(criteria.getTitle().toLowerCase());
                predicates.add(builder.like(builder.lower(root.get("title")), "%" + value + "%", '\\'));
            }

            if (criteria.getEmail() != null) {
                String value = escapeLikeSpecialChars(criteria.getEmail().toLowerCase());
                predicates.add(builder.like(builder.lower(root.get("email")), "%" + value + "%", '\\'));
            }

            if (criteria.getFax() != null) {
                String value = escapeLikeSpecialChars(criteria.getFax().toLowerCase());
                predicates.add(builder.like(builder.lower(root.get("fax")), "%" + value + "%", '\\'));
            }

            if (criteria.getMobile() != null) {
                String value = escapeLikeSpecialChars(criteria.getMobile().toLowerCase());
                predicates.add(builder.like(builder.lower(root.get("mobile")), "%" + value + "%", '\\'));
            }

            if (criteria.getWebsite() != null) {
                String value = escapeLikeSpecialChars(criteria.getWebsite().toLowerCase());
                predicates.add(builder.like(builder.lower(root.get("website")), "%" + value + "%", '\\'));
            }

            if (criteria.getLeadSource() != null) {
                predicates.add(builder.equal(root.get("leadSource"), criteria.getLeadSource()));
            }

            if (criteria.getLeadStatus() != null) {
                predicates.add(builder.equal(root.get("leadStatus"), criteria.getLeadStatus()));
            }

            if (criteria.getIndustry() != null) {
                String value = escapeLikeSpecialChars(criteria.getIndustry().toLowerCase());
                predicates.add(builder.like(builder.lower(root.get("industry")), "%" + value + "%", '\\'));
            }

            if (criteria.getNoOfEmployees() != null) {
                predicates.add(builder.equal(root.get("noOfEmployees"), criteria.getNoOfEmployees()));
            }

            if (criteria.getAnnualRevenue() != null) {
                predicates.add(builder.equal(root.get("annualRevenue"), criteria.getAnnualRevenue()));
            }

            if (criteria.getRating() != null) {
                String value = escapeLikeSpecialChars(criteria.getRating().toLowerCase());
                predicates.add(builder.like(builder.lower(root.get("rating")), "%" + value + "%", '\\'));
            }

            if (criteria.getEmailOptOut() != null) {
                predicates.add(builder.equal(root.get("emailOptOut"), criteria.getEmailOptOut()));
            }

            if (criteria.getSkypeId() != null) {
                String value = escapeLikeSpecialChars(criteria.getSkypeId().toLowerCase());
                predicates.add(builder.like(builder.lower(root.get("skypeId")), "%" + value + "%", '\\'));
            }

            if (criteria.getSecondaryEmail() != null) {
                String value = escapeLikeSpecialChars(criteria.getSecondaryEmail().toLowerCase());
                predicates.add(builder.like(builder.lower(root.get("secondaryEmail")), "%" + value + "%", '\\'));
            }

            if (criteria.getTwitter() != null) {
                String value = escapeLikeSpecialChars(criteria.getTwitter().toLowerCase());
                predicates.add(builder.like(builder.lower(root.get("twitter")), "%" + value + "%", '\\'));
            }

            if (criteria.getDescription() != null) {
                String value = escapeLikeSpecialChars(criteria.getDescription().toLowerCase());
                predicates.add(builder.like(builder.lower(root.get("description")), "%" + value + "%", '\\'));
            }

            if (criteria.getConverted() != null) {
                predicates.add(builder.equal(root.get("converted"), criteria.getConverted()));
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

