package com.crealytics.adverts.reportingservice.service;

import com.crealytics.adverts.reportingservice.domain.enumaration.SiteEnum;
import com.crealytics.adverts.reportingservice.service.dto.ReportDTO;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * @author alican.albayrak
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class ReportServiceTests extends TestCase {

    private static final Logger LOG = LoggerFactory.getLogger(ReportServiceTests.class);

    @Autowired
    private ReportService reportService;

    @Test
    public void testDesktopWebReportOnJan() {

        ReportDTO reportDTO = reportService.getReportByMonthAndSite(1, SiteEnum.desktop_web);

        LOG.debug(reportDTO.toString());

        assertEquals("January", reportDTO.getMonth());
        assertEquals(SiteEnum.desktop_web.toString(), reportDTO.getSite());
        assertEquals(Long.valueOf(12483775L) , reportDTO.getRequests());
        assertEquals(Long.valueOf(11866157L), reportDTO.getImpressions());
        assertEquals(Long.valueOf(30965), reportDTO.getClicks());
        assertEquals(Long.valueOf(7608), reportDTO.getConversions());
        assertEquals(new BigDecimal(23555.46).setScale(2, BigDecimal.ROUND_HALF_DOWN), reportDTO.getRevenue());
        assertEquals(0.26d,reportDTO.getClickThroughRate());
        assertEquals(0.06d,reportDTO.getConversionRate());
        assertEquals(95.05d,reportDTO.getFillRate());
        assertEquals(1.99d,reportDTO.getEffectiveCostPerThousand());

    }


    @Test
    public void testMobileWebReportOnJan() {
        ReportDTO reportDTO = reportService.getReportByMonthAndSite(1, SiteEnum.mobile_web);

        LOG.debug(reportDTO.toString());

        assertEquals("January", reportDTO.getMonth());
        assertEquals(SiteEnum.mobile_web.toString(), reportDTO.getSite());
        assertEquals(Long.valueOf(9905942) , reportDTO.getRequests());
        assertEquals(Long.valueOf(9401153), reportDTO.getImpressions());
        assertEquals(Long.valueOf(25291), reportDTO.getClicks());
        assertEquals(Long.valueOf(6216), reportDTO.getConversions());
        assertEquals(new BigDecimal(19053.61).setScale(2, BigDecimal.ROUND_HALF_DOWN), reportDTO.getRevenue());
        assertEquals(0.27d,reportDTO.getClickThroughRate());
        assertEquals(0.07d,reportDTO.getConversionRate());
        assertEquals(94.90d,reportDTO.getFillRate());
        assertEquals(2.03d,reportDTO.getEffectiveCostPerThousand());
    }


    @Test
    public void testAggregateByFebruaryData() {

        ReportDTO reportDTO = reportService.getReportByMonthAndSite(2, null);

        assertEquals("February", reportDTO.getMonth());
        // aggregated object should not have site site field !
        assertNull(reportDTO.getSite());
        assertEquals(Long.valueOf(33969832) , reportDTO.getRequests());
        assertEquals(Long.valueOf(31322712), reportDTO.getImpressions());
        assertEquals(Long.valueOf(97742), reportDTO.getClicks());
        assertEquals(Long.valueOf(18071), reportDTO.getConversions());
        assertEquals(new BigDecimal(62940.15).setScale(2, BigDecimal.ROUND_HALF_DOWN), reportDTO.getRevenue());
        assertEquals(0.31d,reportDTO.getClickThroughRate());
        assertEquals(0.06d,reportDTO.getConversionRate());
        assertEquals(92.21d,reportDTO.getFillRate());
        assertEquals(2.01d,reportDTO.getEffectiveCostPerThousand());

    }


    @Test
    public void testAggregateByAndroidDate() {
        ReportDTO reportDTO = reportService.getReportByMonthAndSite(-1, SiteEnum.android);

        // aggregated object should not have site site field !
        assertNull(reportDTO.getMonth());
        assertEquals(SiteEnum.android.toString() ,reportDTO.getSite());
        assertEquals(Long.valueOf(18835321) , reportDTO.getRequests());
        assertEquals(Long.valueOf(17755397), reportDTO.getImpressions());
        assertEquals(Long.valueOf(47329), reportDTO.getClicks());
        assertEquals(Long.valueOf(11365), reportDTO.getConversions());
        assertEquals(new BigDecimal(35320.53).setScale(2, BigDecimal.ROUND_HALF_DOWN), reportDTO.getRevenue());
        assertEquals(0.27d,reportDTO.getClickThroughRate());
        assertEquals(0.06d,reportDTO.getConversionRate());
        assertEquals(94.27d,reportDTO.getFillRate());
        assertEquals(1.99d,reportDTO.getEffectiveCostPerThousand());
    }

    @Test
    public void testTwoInvalidInput(){

        ReportDTO reportDTO = reportService.getReportByMonthAndSite(-1, null);

        assertNull(reportDTO);

    }

}
