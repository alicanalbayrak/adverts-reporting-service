package com.crealytics.adverts.reportingservice.bootstrap;

import com.crealytics.adverts.reportingservice.domain.ReportCSV;
import com.crealytics.adverts.reportingservice.repositories.ReportRepository;
import com.crealytics.adverts.reportingservice.service.CSVReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author alican.albayrak
 */
@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(Bootstrap.class);

    private CSVReaderService csvReaderService;
    private ReportRepository reportRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        LOG.debug("Context refreshed");

        Optional<Resource[]> resourcesOptional = retrieveReports();

        // TODO refactor
        if (!resourcesOptional.isPresent()) {
            LOG.error("Seems CSV resources are not available!");
            return;
        }

        Resource[] resources = resourcesOptional.get();
        List<ReportCSV> deserializedReports = csvReaderService.deserializeFiles(resources);

        //        if (deserializedObjects.isEmpty()) {
//            LOG.error("There is no report to import");
//        }
//
//        List<Report> a = ReportCSVMapper.INSTANCE.toReportList(deserializedObjects);
//        reportRepository.saveAll(a);

    }

    private Optional<Resource[]> retrieveReports() {
        LOG.debug("Retrieving reports");

        try {
            return Optional.of(csvReaderService.getCSVResources());
        } catch (IOException e) {
            LOG.error("Something went wrong while getting csv resources... ", e);
        }

        return Optional.empty();
    }


    @Autowired
    public void setCsvReaderService(CSVReaderService csvReaderService) {
        this.csvReaderService = csvReaderService;
    }

    @Autowired
    public void setReportRepository(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }
}
