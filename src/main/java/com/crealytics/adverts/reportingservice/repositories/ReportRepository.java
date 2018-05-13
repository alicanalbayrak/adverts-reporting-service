package com.crealytics.adverts.reportingservice.repositories;

import com.crealytics.adverts.reportingservice.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author alican.albayrak
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, Long>, JpaSpecificationExecutor<Report> {

    @Query("select e from Report e where month(e.reportDate) = ?1")
    List<Report> getByMonth(int month);


}
