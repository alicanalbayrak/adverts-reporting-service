package com.crealytics.adverts.reportingservice.domain;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author alican.albayrak
 */
@Mapper
public interface ReportCSVMapper {

    ReportCSVMapper INSTANCE = Mappers.getMapper(ReportCSVMapper.class);

    Report reportCsvToReportEntity(ReportCSV reportCSV);

}
