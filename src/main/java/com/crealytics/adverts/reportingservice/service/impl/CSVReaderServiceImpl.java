package com.crealytics.adverts.reportingservice.service.impl;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import com.crealytics.adverts.reportingservice.model.ReportCSV;
import com.crealytics.adverts.reportingservice.service.CSVReaderService;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

/**
 * @author alican.albayrak
 */
@Service
public class CSVReaderServiceImpl implements CSVReaderService {

    private static final Logger LOG = LoggerFactory.getLogger(CSVReaderServiceImpl.class);

    @Override
    public void deserializeFiles(Resource[] csvResources) throws IOException {

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(ReportCSV.class).withColumnReordering(false).withHeader(); // schema from 'Pojo' definition
        ObjectReader oReader = mapper.readerFor(ReportCSV.class).with(schema);

        try (Reader reader = new FileReader(csvResources[0].getFile())) {
            MappingIterator<ReportCSV> mi = oReader.readValues(reader);
            List<ReportCSV> all = mi.readAll();
            LOG.info(all.toString());
        }
    }

    @Override
    public Resource[] getCSVResources() throws IOException {
        ClassLoader cl = this.getClass().getClassLoader();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
        return resolver.getResources("classpath*:/csv/*.csv");
    }
}
