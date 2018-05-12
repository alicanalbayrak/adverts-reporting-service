package com.crealytics.adverts.reportingservice.service;

import com.crealytics.adverts.reportingservice.domain.Report;
import com.crealytics.adverts.reportingservice.domain.ReportCSV;
import com.crealytics.adverts.reportingservice.service.dto.ReportDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author alican.albayrak
 */
public interface ReportService {

    List<Report> saveReportCSVList(List<ReportCSV> deserializedReportCSV);

    List<Report> combineReportMetrics(List<Report> reportList);

    Page<ReportDTO> findAll(Pageable pageable);
}
