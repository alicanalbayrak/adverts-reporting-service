package com.crealytics.adverts.reportingservice.domain.mapper;

import com.crealytics.adverts.reportingservice.domain.Report;
import com.crealytics.adverts.reportingservice.domain.ReportCSV;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * {@see com.crealytics.adverts.reportingservice.domain.mapper.EntityMapper}
 *
 * @author alican.albayrak
 */
@Component
@Mapper(componentModel = "spring")
public interface ReportCSVMapper {

    Report reportCsvToReportEntity(ReportCSV reportCSV);

    List<Report> toReportList(List<ReportCSV> reportCSVList);
}
