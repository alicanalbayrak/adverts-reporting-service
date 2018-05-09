package com.crealytics.adverts.reportingservice.util;

import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;

/**
 * @author alican.albayrak
 */
public class ReportMetricCalculationUtilTest {

    private static final double EPSILON = 0.01d;

    @Test
    public void testClickThroughRateCalculation() {
        // 1% CTR means on every 100 impression there is one click.
        assertEquals(1d, ReportMetricCalculationUtil.calculateRatio(100L, 10_000L), EPSILON);
    }

    @Test
    public void testConversionRateCalculation() {
        assertEquals(2d, ReportMetricCalculationUtil.calculateRatio(20L, 1_000L), EPSILON);
    }

    @Test
    public void testFillRateCalculation() {

    }

    @Test
    public void testEffectiveCostCalculation() {
        // Example : An ad size of 728 has delivered 213456 impressions
        // and has also spent some $300 with CPM set as $1.5, what will be the eCPM ?

        assertEquals(1.40d, ReportMetricCalculationUtil.calculateECPM(new BigDecimal(300), 213_456), EPSILON);
    }
}
