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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * This service contains business logic methods for report domain
 *
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

    /**
     * Persists Report objects to DB along with calculated metric information.
     *
     * @param deserializedReportCSV Deserialized data from CSV data
     * @return Persisted data list
     */
    @Override
    public List<Report> saveReportCSVList(List<ReportCSV> deserializedReportCSV) {

        // mapping CSV objects to Report entity,
        List<Report> reports = reportCSVMapper.toReportList(deserializedReportCSV);
        List<Report> enhancedReports = combineReportMetrics(reports);

        return reportRepository.saveAll(enhancedReports);
    }

    /**
     * Performs distributed metric calculations for each Report object.
     *
     * @param reportList
     * @return
     */
    @Override
    public List<Report> combineReportMetrics(List<Report> reportList) {

        ExecutorService executorService = Executors.newFixedThreadPool(COMBINE_UNIT_THREAD_LEVEL);

        // submit new calculator task for each report item
        List<SingleReportMetricCalculateCallable> calculatorCallableList =
                reportList.stream()
                        .map(SingleReportMetricCalculateCallable::new)
                        .collect(Collectors.toList());

        // List of future will be referenced each task result
        List<Future<Report>> futureList = Collections.emptyList();
        try {
            futureList = executorService.invokeAll(calculatorCallableList);
        } catch (InterruptedException e) {
            LOG.error("Exception thrown while submitting calculation callables", e);
        }

        executorService.shutdown();

        CopyOnWriteArrayList<Report> result = new CopyOnWriteArrayList<>();

        // wait for all future responses
        futureList.forEach(reportMetricFuture -> {
            try {
                // collect task results (sequentially) whenever they have done.
                result.add(reportMetricFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                LOG.error("Exception thrown while getting enhanced report object", e);
            }
        });

        return result;
    }

    // returns all reports that resides in database
    @Override
    public Page<ReportDTO> findAll(Pageable pageable) {
        return reportRepository.findAll(pageable).map(reportMapper::toDto);
    }

    /**
     * Provides report search/filter queries by month and site.
     *
     * @param month Month in numeric (between 1-12)
     * @param site  Site type {@see SiteEnum}
     * @return Returns corresponding (single) Report if month and site both provided,
     * otherwise returns aggregated Report in advance given month or site
     */
    @Override
    public ReportDTO getReportByMonthAndSite(Integer month, SiteEnum site) {
        LOG.debug(String.format("Received monthStr: %s", month));

        // Be defensive here!
        // at least one argument must be present!
        // FIXME I admit this is a ugly workaround to provide flexible api.
        if (month == -1 && site == null) {
            return null;
        }

        List<Report> resultList = reportRepository.findAll(ReportSpecification.findByMonthAndSite(month, site));

        // When resultList contains more than 1 items, this means calling function needs to have aggregated data.
        Optional<Report> reducedReport = resultList.stream()
                .reduce((a, b) -> {

                    // SUM related fields of consecutive fields
                    Report reportBuilder = new Report();
                    reportBuilder.setClicks(b.getClicks() + a.getClicks());
                    reportBuilder.setRequests(b.getRequests() + a.getRequests());
                    reportBuilder.setImpressions(b.getImpressions() + a.getImpressions());
                    reportBuilder.setConversions(b.getConversions() + a.getConversions());
                    reportBuilder.setRevenue(b.getRevenue().add(a.getRevenue()).setScale(2, BigDecimal.ROUND_HALF_EVEN));

                    // When creating aggregated data if user wants to get all reports that are have same site value,
                    // leave month field as NULL. This will prevent it to be serialized to JSON response.

                    if (month != -1) {
                        reportBuilder.setReportDate(a.getReportDate());
                    }

                    if (site != null) {
                        reportBuilder.setSite(a.getSite());
                    }

                    return reportBuilder;
                }).map(report -> {
                    // Recalculate metric for aggregated object
                    ReportMetric reportMetric = ReportMetricCalculationUtil.createReportMetricFromReport(report);
                    report.setReportMetric(reportMetric);
                    return report;
                });

        return reportMapper.toDto(reducedReport.orElse(null));
    }


}
