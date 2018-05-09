package com.crealytics.adverts.reportingservice.repositories;

import com.crealytics.adverts.reportingservice.domain.Report;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author alican.albayrak
 */
@Repository
public interface ReportRepository extends CrudRepository<Report, Long> {
}
