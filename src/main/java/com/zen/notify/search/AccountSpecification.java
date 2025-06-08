package com.zen.notify.search;

//AccountSpecification.java
import org.springframework.data.jpa.domain.Specification;

import com.zen.notify.entity.Account;

import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class AccountSpecification implements Specification<Account> {

 private final AccountSearchCriteria criteria;

 public AccountSpecification(AccountSearchCriteria criteria) {
     this.criteria = criteria;
 }

 @Override
 public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
     List<Predicate> predicates = new ArrayList<>();

     if (criteria.getAccountOwner() != null)
         predicates.add(builder.like(builder.lower(root.get("accountOwner")), wrap(criteria.getAccountOwner())));

     if (criteria.getAccountName() != null)
         predicates.add(builder.like(builder.lower(root.get("accountName")), wrap(criteria.getAccountName())));

     if (criteria.getAccountSite() != null)
         predicates.add(builder.like(builder.lower(root.get("accountSite")), wrap(criteria.getAccountSite())));

     if (criteria.getAccountNumber() != null)
         predicates.add(builder.like(builder.lower(root.get("accountNumber")), wrap(criteria.getAccountNumber())));

     if (criteria.getAccountType() != null)
         predicates.add(builder.equal(root.get("accountType"), criteria.getAccountType()));

     if (criteria.getIndustry() != null)
         predicates.add(builder.like(builder.lower(root.get("industry")), wrap(criteria.getIndustry())));

     if (criteria.getAnnualRevenue() != null)
         predicates.add(builder.equal(root.get("annualRevenue"), criteria.getAnnualRevenue()));

     if (criteria.getRating() != null)
         predicates.add(builder.equal(root.get("rating"), criteria.getRating()));

     if (criteria.getPhone() != null)
         predicates.add(builder.like(builder.lower(root.get("phone")), wrap(criteria.getPhone())));

     if (criteria.getFax() != null)
         predicates.add(builder.like(builder.lower(root.get("fax")), wrap(criteria.getFax())));

     if (criteria.getWebsite() != null)
         predicates.add(builder.like(builder.lower(root.get("website")), wrap(criteria.getWebsite())));

     if (criteria.getTickerSymbol() != null)
         predicates.add(builder.like(builder.lower(root.get("tickerSymbol")), wrap(criteria.getTickerSymbol())));

     if (criteria.getOwnership() != null)
         predicates.add(builder.equal(root.get("ownership"), criteria.getOwnership()));

     if (criteria.getSicCode() != null)
         predicates.add(builder.like(builder.lower(root.get("sicCode")), wrap(criteria.getSicCode())));

     return builder.and(predicates.toArray(new Predicate[0]));
 }

 private String wrap(String input) {
     return "%" + input.toLowerCase().replace("%", "\\%").replace("_", "\\_") + "%";
 }
}
