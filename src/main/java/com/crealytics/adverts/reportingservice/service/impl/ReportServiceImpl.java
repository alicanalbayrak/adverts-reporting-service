package com.crealytics.adverts.reportingservice.service.impl;

import com.crealytics.adverts.reportingservice.domain.Report;
import com.crealytics.adverts.reportingservice.domain.ReportCSV;
import com.crealytics.adverts.reportingservice.domain.mapper.ReportCSVMapper;
import com.crealytics.adverts.reportingservice.repositories.ReportRepository;
import com.crealytics.adverts.reportingservice.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author alican.albayrak
 */
@Service
public class ReportServiceImpl implements ReportService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportServiceImpl.class);
    private static final int COMBINE_UNIT_THREAD_LEVEL = 4;

    private ReportRepository reportRepository;

    @Override
    public List<Report> saveReportCSVList(List<ReportCSV> deserializedReportCSV) {

        List<Report> reports = ReportCSVMapper.INSTANCE.toReportList(deserializedReportCSV);
        List<Report> enhancedReports = combineReportMetrics(reports);

        return (List<Report>) reportRepository.saveAll(enhancedReports);
    }

    @Override
    public List<Report> combineReportMetrics(List<Report> reportList) {
        ExecutorService executorService = Executors.newFixedThreadPool(COMBINE_UNIT_THREAD_LEVEL);
        List<SingleReportMetricCalculateCallable> calculatorCallableList =
                reportList.stream()
                        .map(SingleReportMetricCalculateCallable::new)
                        .collect(Collectors.toList());

        List<Future<Report>> futureList = Collections.emptyList();
        try {
            futureList = executorService.invokeAll(calculatorCallableList);
        } catch (InterruptedException e) {
            LOG.error("Exception thrown while submitting calculation callables", e);
        }


        List<Report> result = new ArrayList<>();
        futureList.forEach(reportMetricFuture -> {
            try {
                result.add(reportMetricFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                LOG.error("Exception thrown while getting enhanced report object", e);
            }
        });

        return result;
    }

    @Autowired
    public void setReportRepository(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

}
