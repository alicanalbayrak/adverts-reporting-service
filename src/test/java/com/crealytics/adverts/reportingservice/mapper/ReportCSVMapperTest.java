package com.crealytics.adverts.reportingservice.mapper;

import com.crealytics.adverts.reportingservice.domain.Report;
import com.crealytics.adverts.reportingservice.domain.ReportCSV;
import com.crealytics.adverts.reportingservice.domain.mapper.ReportCSVMapper;
import com.crealytics.adverts.reportingservice.domain.enumaration.SiteEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * @author alican.albayrak
 */
@RunWith(SpringRunner.class)
public class ReportCSVMapperTest {

    private static final SiteEnum expectedSite = SiteEnum.android;
    private static final Long expectedClicks = 1L;
    private static final Long expectedConversions = 2L;
    private static final Long expectedImpressions = 3L;
    private static final Long expectedRequests = 4L;
    private static final double expectedRevenue = new BigDecimal(1234.56).setScale(2, RoundingMode.HALF_UP).doubleValue();

    ReportCSVMapper reportCSVMapper = Mappers.getMapper(ReportCSVMapper.class);

    @Test
    public void testReportCSVMapper() throws ParseException {

        SimpleDateFormat reportDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date expectedDate = reportDateFormat.parse("2018-05-01");

        ReportCSV reportCSV = new ReportCSV();
        reportCSV.setSite(expectedSite);
        reportCSV.setClicks(expectedClicks);
        reportCSV.setConversions(expectedConversions);
        reportCSV.setImpressions(expectedImpressions);
        reportCSV.setRequests(expectedRequests);
        reportCSV.setRevenue(expectedRevenue);
        reportCSV.setReportDate(expectedDate);

        Report report = reportCSVMapper.reportCsvToReportEntity(reportCSV);

        assertEquals(expectedSite, report.getSite());
        assertEquals(expectedClicks, report.getClicks());
        assertEquals(expectedConversions, report.getConversions());
        assertEquals(expectedImpressions, report.getImpressions());
        assertEquals(expectedRequests, report.getRequests());
        assertEquals(expectedRevenue, report.getRevenue().doubleValue(), 0.001);
        assertEquals(expectedDate, report.getReportDate());

    }
}
