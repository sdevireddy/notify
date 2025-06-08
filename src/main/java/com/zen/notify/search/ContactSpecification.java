package com.zen.notify.search;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import com.zen.notify.entity.Contact;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContactSpecification implements Specification<Contact> {

    private final ContactSearchCriteria criteria;

    public ContactSpecification(ContactSearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Contact> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getContactOwner() != null) {
            String value = escapeLikeSpecialChars(criteria.getContactOwner().toLowerCase());
            predicates.add(builder.like(builder.lower(root.get("contactOwner")), "%" + value + "%", '\\'));
        }

        if (criteria.getLeadSource() != null) {
            predicates.add(builder.equal(root.get("leadSource"), criteria.getLeadSource()));
        }

        if (criteria.getFirstName() != null) {
            String value = escapeLikeSpecialChars(criteria.getFirstName().toLowerCase());
            predicates.add(builder.like(builder.lower(root.get("firstName")), "%" + value + "%", '\\'));
        }

        if (criteria.getLastName() != null) {
            String value = escapeLikeSpecialChars(criteria.getLastName().toLowerCase());
            predicates.add(builder.like(builder.lower(root.get("lastName")), "%" + value + "%", '\\'));
        }

        if (criteria.getVendorName() != null) {
            String value = escapeLikeSpecialChars(criteria.getVendorName().toLowerCase());
            predicates.add(builder.like(builder.lower(root.get("vendorName")), "%" + value + "%", '\\'));
        }

        if (criteria.getEmail() != null) {
            String value = escapeLikeSpecialChars(criteria.getEmail().toLowerCase());
            predicates.add(builder.like(builder.lower(root.get("email")), "%" + value + "%", '\\'));
        }

        if (criteria.getTitle() != null) {
            String value = escapeLikeSpecialChars(criteria.getTitle().toLowerCase());
            predicates.add(builder.like(builder.lower(root.get("title")), "%" + value + "%", '\\'));
        }

        if (criteria.getPhone() != null) {
            String value = escapeLikeSpecialChars(criteria.getPhone().toLowerCase());
            predicates.add(builder.like(builder.lower(root.get("phone")), "%" + value + "%", '\\'));
        }

        if (criteria.getDepartment() != null) {
            String value = escapeLikeSpecialChars(criteria.getDepartment().toLowerCase());
            predicates.add(builder.like(builder.lower(root.get("department")), "%" + value + "%", '\\'));
        }

        if (criteria.getOtherPhone() != null) {
            String value = escapeLikeSpecialChars(criteria.getOtherPhone().toLowerCase());
            predicates.add(builder.like(builder.lower(root.get("otherPhone")), "%" + value + "%", '\\'));
        }

        if (criteria.getHomePhone() != null) {
            String value = escapeLikeSpecialChars(criteria.getHomePhone().toLowerCase());
            predicates.add(builder.like(builder.lower(root.get("homePhone")), "%" + value + "%", '\\'));
        }

        if (criteria.getMobile() != null) {
            String value = escapeLikeSpecialChars(criteria.getMobile().toLowerCase());
            predicates.add(builder.like(builder.lower(root.get("mobile")), "%" + value + "%", '\\'));
        }

        if (criteria.getDateOfBirth() != null) {
            // For exact date match; can be modified for range searches if needed
            predicates.add(builder.equal(root.get("dateOfBirth"), criteria.getDateOfBirth()));
        }

        if (criteria.getAssistant() != null) {
            String value = escapeLikeSpecialChars(criteria.getAssistant().toLowerCase());
            predicates.add(builder.like(builder.lower(root.get("assistant")), "%" + value + "%", '\\'));
        }

        if (criteria.getAsstPhone() != null) {
            String value = escapeLikeSpecialChars(criteria.getAsstPhone().toLowerCase());
            predicates.add(builder.like(builder.lower(root.get("asstPhone")), "%" + value + "%", '\\'));
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

        if (criteria.getTwitterHandle() != null) {
            String value = escapeLikeSpecialChars(criteria.getTwitterHandle().toLowerCase());
            predicates.add(builder.like(builder.lower(root.get("twitterHandle")), "%" + value + "%", '\\'));
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

