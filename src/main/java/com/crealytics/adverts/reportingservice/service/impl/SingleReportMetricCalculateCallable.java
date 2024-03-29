package com.crealytics.adverts.reportingservice.service.impl;

import com.crealytics.adverts.reportingservice.domain.Report;
import com.crealytics.adverts.reportingservice.domain.ReportMetric;
import com.crealytics.adverts.reportingservice.util.ReportMetricCalculationUtil;

import java.util.concurrent.Callable;

/**
 * @author alican.albayrak
 */
public class SingleReportMetricCalculateCallable implements Callable<Report> {

    private final Report report;

    SingleReportMetricCalculateCallable(Report report) {
        this.report = report;
    }

    @Override
    public Report call() {
        ReportMetric reportMetric = ReportMetricCalculationUtil.createReportMetricFromReport(report);
        report.setReportMetric(reportMetric);
        return report;
    }
}
