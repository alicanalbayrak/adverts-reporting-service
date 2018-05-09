package com.crealytics.adverts.reportingservice.bootstrap;

import java.io.IOException;
import java.util.List;

import com.crealytics.adverts.reportingservice.domain.ReportCSV;
import com.crealytics.adverts.reportingservice.service.CSVReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * @author alican.albayrak
 */
@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(Bootstrap.class);

    private CSVReaderService csvReaderService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        LOG.info("Context refreshed");
        initializeReportData();
    }

    private void initializeReportData() {

        try {
            Resource[] resources = csvReaderService.getCSVResources();
            List<ReportCSV> result = csvReaderService.deserializeFiles(resources);

            System.out.println(result);

        } catch (IOException e) {
            LOG.error("Something went wrong while getting csv resources... ", e);
        }

    }


    @Autowired
    public void setCsvReaderService(CSVReaderService csvReaderService) {
        this.csvReaderService = csvReaderService;
    }

}
