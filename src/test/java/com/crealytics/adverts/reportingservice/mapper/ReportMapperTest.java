package com.crealytics.adverts.reportingservice.mapper;

import com.crealytics.adverts.reportingservice.domain.Report;
import com.crealytics.adverts.reportingservice.domain.ReportMetric;
import com.crealytics.adverts.reportingservice.domain.enumaration.SiteEnum;
import com.crealytics.adverts.reportingservice.domain.mapper.ReportMapper;
import com.crealytics.adverts.reportingservice.service.dto.ReportDTO;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author alican.albayrak
 */
@RunWith(SpringRunner.class)
public class ReportMapperTest extends TestCase {


    private ReportMapper reportMapper = Mappers.getMapper(ReportMapper.class);

    @Test
    public void testDtoToEntity() {

        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setClicks(1L);
        reportDTO.setConversions(2L);
        reportDTO.setImpressions(3L);
        reportDTO.setRequests(3L);
        reportDTO.setRevenue(new BigDecimal(1234.56));
        reportDTO.setSite("desktop_web");
        reportDTO.setMonth("Jan");

        Report entity = reportMapper.toEntity(reportDTO);

        assertEquals(reportDTO.getSite(), entity.getSite().toString());
        assertEquals(reportDTO.getClicks(), entity.getClicks());
        assertEquals(reportDTO.getConversions(), entity.getConversions());
        assertEquals(reportDTO.getImpressions(), entity.getImpressions());
        assertEquals(reportDTO.getRequests(), entity.getRequests());
        assertEquals(reportDTO.getRevenue().doubleValue(), entity.getRevenue().doubleValue(), 0.01);

    }

    @Test
    public void testEntitiyToDTO() {

        ReportMetric reportMetric = new ReportMetric();
        reportMetric.setId(4132L);
        reportMetric.setFillRate(99.99d);
        reportMetric.setClickThroughRate(0.22d);
        reportMetric.setConversionRate(0.56d);
        reportMetric.setEffectiveCostPerThousand(2.05d);

        Report report = new Report();
        report.setId(1234L);
        report.setSite(SiteEnum.mobile_web);
        report.setClicks(1L);
        report.setConversions(2L);
        report.setImpressions(3L);
        report.setRequests(4L);
        report.setRevenue(new BigDecimal(123.45));
        report.setReportDate(new Date());
        report.setReportMetric(reportMetric);

        ReportDTO dto = reportMapper.toDto(report);

        assertEquals(report.getSite().toString(), dto.getSite());
        assertEquals(report.getClicks(), dto.getClicks());
        assertEquals(report.getConversions(), dto.getConversions());
        assertEquals(report.getImpressions(), dto.getImpressions());
        assertEquals(report.getRequests(), dto.getRequests());
        assertEquals(report.getRevenue(), dto.getRevenue());
    }
}
