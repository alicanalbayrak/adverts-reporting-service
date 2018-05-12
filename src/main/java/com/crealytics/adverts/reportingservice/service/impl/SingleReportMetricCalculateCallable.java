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
        ReportMetric reportMetric = new ReportMetric();
        reportMetric.setClickThroughRate(ReportMetricCalculationUtil.calculateRatio(report.getClicks(), report.getImpressions()));
        reportMetric.setConversionRate(ReportMetricCalculationUtil.calculateRatio(report.getConversions(), report.getImpressions()));
        reportMetric.setFillRate(ReportMetricCalculationUtil.calculateRatio(report.getImpressions(), report.getRequests()));
        reportMetric.setEffectiveCostPerThousand(ReportMetricCalculationUtil.calculateECPM(report.getRevenue(), report.getImpressions()));
        report.setReportMetric(reportMetric);
        return report;
    }
}
