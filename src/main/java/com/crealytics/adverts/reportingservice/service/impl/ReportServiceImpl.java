package com.crealytics.adverts.reportingservice.service.impl;

import com.crealytics.adverts.reportingservice.domain.Report;
import com.crealytics.adverts.reportingservice.domain.ReportCSV;
import com.crealytics.adverts.reportingservice.domain.ReportMetric;
import com.crealytics.adverts.reportingservice.domain.enumaration.SiteEnum;
import com.crealytics.adverts.reportingservice.domain.mapper.ReportCSVMapper;
import com.crealytics.adverts.reportingservice.domain.mapper.ReportMapper;
import com.crealytics.adverts.reportingservice.repositories.ReportRepository;
import com.crealytics.adverts.reportingservice.repositories.ReportSpecification;
import com.crealytics.adverts.reportingservice.service.ReportService;
import com.crealytics.adverts.reportingservice.service.dto.ReportDTO;
import com.crealytics.adverts.reportingservice.util.ReportMetricCalculationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

    private final ReportCSVMapper reportCSVMapper;
    private final ReportMapper reportMapper;
    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportCSVMapper reportCSVMapper, ReportMapper reportMapper, ReportRepository reportRepository) {
        this.reportCSVMapper = reportCSVMapper;
        this.reportMapper = reportMapper;
        this.reportRepository = reportRepository;
    }

    @Override
    public List<Report> saveReportCSVList(List<ReportCSV> deserializedReportCSV) {

        List<Report> reports = reportCSVMapper.toReportList(deserializedReportCSV);
        List<Report> enhancedReports = combineReportMetrics(reports);

        return reportRepository.saveAll(enhancedReports);
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

        executorService.shutdown();

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

    @Override
    public Page<ReportDTO> findAll(Pageable pageable) {
        return reportRepository.findAll(pageable).map(reportMapper::toDto);
    }

    @Override
    public ReportDTO getReportByMonthAndSite(Integer month, SiteEnum site) {
        LOG.debug(String.format("Received monthStr: %s", month));

        // Be defensive here!
        // at least one argument must be present!
        if(month == -1 && site == null){
            return null;
        }

        List<Report> resultList = reportRepository.findAll(ReportSpecification.findByMonthAndSite(month, site));
        Optional<Report> reducedReport = resultList.stream()
                .reduce((a, b) -> {

                    Report reportBuilder = new Report();
                    reportBuilder.setClicks(b.getClicks() + a.getClicks());
                    reportBuilder.setRequests(b.getRequests() + a.getRequests());
                    reportBuilder.setImpressions(b.getImpressions() + a.getImpressions());
                    reportBuilder.setConversions(b.getConversions() + a.getConversions());
                    reportBuilder.setRevenue(b.getRevenue().add(a.getRevenue()).setScale(2, BigDecimal.ROUND_HALF_EVEN));

                    if (month != -1) {
                        reportBuilder.setReportDate(a.getReportDate());
                    }

                    if (site != null) {
                        reportBuilder.setSite(a.getSite());
                    }
                    return reportBuilder;
                }).map(report -> {
                    ReportMetric reportMetric = ReportMetricCalculationUtil.createReportMetricFromReport(report);
                    report.setReportMetric(reportMetric);
                    return report;
                });

        return reportMapper.toDto(reducedReport.orElse(null));
    }


}
