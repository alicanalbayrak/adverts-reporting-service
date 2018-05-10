package com.crealytics.adverts.reportingservice.service;

import com.crealytics.adverts.reportingservice.domain.Report;
import com.crealytics.adverts.reportingservice.domain.ReportCSV;

import java.util.List;

/**
 * @author alican.albayrak
 */
public interface ReportService {

    List<Report> saveReportCSVList(List<ReportCSV> deserializedReportCSV);

    List<Report> combineReportMetrics(List<Report> reportList);

}
