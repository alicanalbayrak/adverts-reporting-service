package com.crealytics.adverts.reportingservice.util;

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

}
