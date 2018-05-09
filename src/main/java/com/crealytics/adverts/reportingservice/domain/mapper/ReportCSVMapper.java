package com.crealytics.adverts.reportingservice.domain.mapper;

import com.crealytics.adverts.reportingservice.domain.Report;
import com.crealytics.adverts.reportingservice.domain.ReportCSV;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author alican.albayrak
 */
@Mapper
public interface ReportCSVMapper {

    ReportCSVMapper INSTANCE = Mappers.getMapper(ReportCSVMapper.class);

    Report reportCsvToReportEntity(ReportCSV reportCSV);

    List<Report> toReportList(List<ReportCSV> reportCSVList);
}
