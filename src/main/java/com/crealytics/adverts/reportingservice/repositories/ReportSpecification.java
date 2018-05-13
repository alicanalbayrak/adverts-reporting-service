package com.crealytics.adverts.reportingservice.repositories;

import com.crealytics.adverts.reportingservice.domain.Report;
import com.crealytics.adverts.reportingservice.domain.enumaration.SiteEnum;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This specification contains predicate definitions to query Report table
 * in accordance with month and site column.
 *
 * @author alican.albayrak
 */
public class ReportSpecification {

    public static Specification<Report> findByMonthAndSite(Integer month, SiteEnum siteEnum) {
        return (Specification<Report>) (root, query, cb) -> {

            final Collection<Predicate> predicates = new ArrayList<>();

            // ignore month column if -1 provided
            if (month != -1) {
                // Run sql function to compare month of date!!!
                final Predicate monthPredicate = cb.equal(cb.function("month", Integer.class, root.get("reportDate")), month);
                predicates.add(monthPredicate);
            }

            if (siteEnum != null) {
                final Predicate sitePredicate = cb.equal(root.get("site"), siteEnum);
                predicates.add(sitePredicate);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

}
