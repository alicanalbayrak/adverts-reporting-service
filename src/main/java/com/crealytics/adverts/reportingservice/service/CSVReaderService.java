package com.crealytics.adverts.reportingservice.service;

import java.io.IOException;
import java.util.List;

import com.crealytics.adverts.reportingservice.domain.ReportCSV;
import org.springframework.core.io.Resource;

/**
 * @author alican.albayrak
 */
public interface CSVReaderService {

    Resource[] getCSVResources() throws IOException;

    List<ReportCSV> deserializeFiles(Resource[] csvResources) throws IOException;
}
