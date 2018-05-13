package com.crealytics.adverts.reportingservice.repositories;

import com.crealytics.adverts.reportingservice.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This class is responsible to all CRUD operations on Report table.
 * Also provides custom {@link org.springframework.data.jpa.domain.Specification} implementation
 * to provide flexible queries.
 *
 * @author alican.albayrak
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, Long>, JpaSpecificationExecutor<Report> {

    // TODO Not used, remove
    @Query("select e from Report e where month(e.reportDate) = ?1")
    List<Report> getByMonth(int month);


}
