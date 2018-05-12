package com.crealytics.adverts.reportingservice.web;

import com.crealytics.adverts.reportingservice.domain.Report;
import com.crealytics.adverts.reportingservice.service.ReportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author alican.albayrak
 */
@RestController
@RequestMapping("/reports")
public class ReportResource {

    private final ReportService reportService;

    public ReportResource(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<Page<Report>> getAllReports(Pageable pageable) {
        Page<Report> page = reportService.findAll(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }


}
