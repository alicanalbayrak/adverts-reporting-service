package com.crealytics.adverts.reportingservice.service;

import java.io.IOException;

import org.springframework.core.io.Resource;

/**
 * @author alican.albayrak
 */
public interface CSVReaderService {

    Resource[] getCSVResources() throws IOException;

    void deserializeFiles(Resource[] csvResources) throws IOException;
}
