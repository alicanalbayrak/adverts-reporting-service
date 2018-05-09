package com.crealytics.adverts.reportingservice.service.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.crealytics.adverts.reportingservice.domain.ReportCSV;
import com.crealytics.adverts.reportingservice.service.CSVReaderService;
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
    private static final String CSV_LOCATION_PATTERN = "classpath*:/csv/*.csv";

    @Override
    public List<ReportCSV> deserializeFiles(Resource[] csvResources) {
        LOG.debug("deserializing report files in csv format...");

        ExecutorService executorService = Executors.newFixedThreadPool(8);
        ExecutorCompletionService<List<ReportCSV>> executorCompletionService = new ExecutorCompletionService<>(executorService);

        // submitting parsing tasks for each file
        Arrays.stream(csvResources)
                .forEach(resource -> executorCompletionService.submit(new ReportDeserializeCallable(resource)));

        executorService.shutdown();

        CopyOnWriteArrayList<ReportCSV> resultList = new CopyOnWriteArrayList<>();

        // collecting results of  submitted tasks.
        // number of submitted tasks is equal to csvResources.length
        for (int i = 0; i < csvResources.length; i++) {

            final Future<List<ReportCSV>> future;

            try {
                future = executorCompletionService.take();
                resultList.addAll(future.get());
            } catch (InterruptedException | ExecutionException e) {
                LOG.error("Task interrupted ", e);
            }
        }

        return resultList;
    }

    @Override
    public Resource[] getCSVResources() throws IOException {
        LOG.debug("Getting csv resources...");
        ClassLoader cl = this.getClass().getClassLoader();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
        return resolver.getResources(CSV_LOCATION_PATTERN);
    }
}
