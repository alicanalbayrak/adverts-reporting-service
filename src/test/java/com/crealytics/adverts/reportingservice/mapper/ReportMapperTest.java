package com.crealytics.adverts.reportingservice.mapper;

import com.crealytics.adverts.reportingservice.domain.Report;
import com.crealytics.adverts.reportingservice.domain.mapper.ReportMapper;
import com.crealytics.adverts.reportingservice.service.dto.ReportDTO;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

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
}
