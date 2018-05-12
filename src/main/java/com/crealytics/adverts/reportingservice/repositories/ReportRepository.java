package com.crealytics.adverts.reportingservice.repositories;

import com.crealytics.adverts.reportingservice.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author alican.albayrak
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
