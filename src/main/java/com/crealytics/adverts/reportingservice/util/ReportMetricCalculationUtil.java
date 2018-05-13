package com.crealytics.adverts.reportingservice.util;

import com.crealytics.adverts.reportingservice.domain.Report;
import com.crealytics.adverts.reportingservice.domain.ReportMetric;

import java.math.BigDecimal;

/**
 * This class contains common methods for various metrics calculation.
 *
 * @author alican.albayrak
 */
public class ReportMetricCalculationUtil {

    private static final int SCALE = 2;
    private static final double HUNDRED = 100d;
    private static final double THOUSAND = 1000d;

    /**
     * This is a generic method to calculate rate/ratio of given values.
     * This method can be used when calculating following metrics:
     * <p><ul>
     * <li> Click-through rate (clicks ÷ impressions) × 100%
     * <li> Conversion rate (conversions ÷ impressions) × 100%
     * <li> Fill rate (impressions ÷ requests) × 100%
     * </ul><p>
     *
     * @param numerator
     * @param denominator
     * @return
     */
    public static double calculateRatio(long numerator, long denominator) {

        return new BigDecimal(((double) numerator / denominator) * HUNDRED)
                .setScale(SCALE, BigDecimal.ROUND_HALF_EVEN)
                .doubleValue();

    }

    /**
     * Effective Cost Per Thousand
     *
     * @param revenue
     * @param impressions
     * @return
     */
    public static double calculateECPM(BigDecimal revenue, long impressions) {
        return (revenue.multiply(BigDecimal.valueOf(THOUSAND)))
                .divide(BigDecimal.valueOf(impressions), SCALE, BigDecimal.ROUND_HALF_EVEN)
                .doubleValue();
    }

    /**
     * Utility method to create report metric object from given report.
     * This method calculates CTR, CR, Fill_Rate and eCPM and encapsulates in a ReportMetric object.
     * @param report Report
     * @return ReportMetric object with calculated fields
     */
    public static ReportMetric createReportMetricFromReport(Report report){
        ReportMetric reportMetric = new ReportMetric();
        reportMetric.setClickThroughRate(ReportMetricCalculationUtil.calculateRatio(report.getClicks(), report.getImpressions()));
        reportMetric.setConversionRate(ReportMetricCalculationUtil.calculateRatio(report.getConversions(), report.getImpressions()));
        reportMetric.setFillRate(ReportMetricCalculationUtil.calculateRatio(report.getImpressions(), report.getRequests()));
        reportMetric.setEffectiveCostPerThousand(ReportMetricCalculationUtil.calculateECPM(report.getRevenue(), report.getImpressions()));
        return reportMetric;
    }

}
