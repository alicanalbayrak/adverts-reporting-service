package com.crealytics.adverts.reportingservice.mapper;

import com.crealytics.adverts.reportingservice.domain.Report;
import com.crealytics.adverts.reportingservice.domain.ReportCSV;
import com.crealytics.adverts.reportingservice.domain.ReportCSVMapper;
import com.crealytics.adverts.reportingservice.domain.enumaration.SiteEnum;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;


/**
 * @author alican.albayrak
 */
public class ReportCSVMapperTest {

    private static final SiteEnum expectedSite = SiteEnum.android;

    ReportCSVMapper reportCSVMapper = ReportCSVMapper.INSTANCE;

    @Test
    public void testReportCSVMapper() throws ParseException {

        SimpleDateFormat reportDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date expectedDate = reportDateFormat.parse("2018-05-01");

        double expectedRevenue = new BigDecimal(1234.56).setScale(2, RoundingMode.HALF_UP).doubleValue();

        ReportCSV reportCSV = new ReportCSV();
        reportCSV.setSite(expectedSite);
        reportCSV.setClicks(1L);
        reportCSV.setConversions(2L);
        reportCSV.setImpressions(3L);
        reportCSV.setRequests(4L);
        reportCSV.setRevenue(expectedRevenue);
        reportCSV.setReportDate(expectedDate);


        Report report = reportCSVMapper.reportCsvToReportEntity(reportCSV);

        assertEquals(expectedSite, report.getSite());

    }
}
