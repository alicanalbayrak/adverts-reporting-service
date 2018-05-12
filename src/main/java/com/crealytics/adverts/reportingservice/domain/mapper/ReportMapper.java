package com.crealytics.adverts.reportingservice.domain.mapper;

import com.crealytics.adverts.reportingservice.domain.Report;
import com.crealytics.adverts.reportingservice.service.dto.ReportDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author alican.albayrak
 */
@Component
@Mapper(componentModel = "spring")
public interface ReportMapper extends EntityMapper<ReportDTO , Report> {

}
