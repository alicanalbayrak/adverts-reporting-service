package com.crealytics.adverts.reportingservice.service.impl;

import java.io.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import com.crealytics.adverts.reportingservice.domain.ReportCSV;
import com.crealytics.adverts.reportingservice.util.CSVParserUtil;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

/**
 * @author alican.albayrak
 */
public class ReportDeserializeCallable implements Callable<List<ReportCSV>> {

    private static final Logger LOG = LoggerFactory.getLogger(ReportDeserializeCallable.class);
    private final Resource resource;

    ReportDeserializeCallable(Resource resource) {
        this.resource = resource;
    }

    /**
     * Parses and deserializes report rows then adds date information
     * from given cvs file (a.k.a resource)
     *
     * @return Returns {@link Collections#emptyList()} if date information cannot be extracted from filename
     * or any exception thrown, otherwise returns deserialized report rows.
     */
    @Override
    public List<ReportCSV> call() {
        LOG.debug(String.format("Deserializing file: %s", resource.getFilename()));

        Optional<Date> reportDateOptional = CSVParserUtil.extractDateInfoFromFilename(resource.getFilename());

        if (!reportDateOptional.isPresent()) {
            return Collections.emptyList();
        }

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(ReportCSV.class).withColumnReordering(false).withHeader();
        ObjectReader oReader = mapper.readerFor(ReportCSV.class).with(schema);

        try (Reader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            MappingIterator<ReportCSV> csvMappingIterator = oReader.readValues(reader);
            return csvMappingIterator.readAll().stream()
                    .peek(f -> f.setReportDate(reportDateOptional.get()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOG.error("Exception thrown while ");
        }

        return Collections.emptyList();
    }
}
