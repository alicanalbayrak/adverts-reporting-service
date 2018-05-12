package com.crealytics.adverts.reportingservice.domain.mapper;

import com.crealytics.adverts.reportingservice.domain.ReportMetric;
import com.crealytics.adverts.reportingservice.service.dto.ReportMetricDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author alican.albayrak
 */
@Component
@Mapper(componentModel = "spring")
public interface ReportMetricMapper extends EntityMapper<ReportMetricDTO, ReportMetric> {

}
