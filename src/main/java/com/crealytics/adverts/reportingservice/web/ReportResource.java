package com.crealytics.adverts.reportingservice.web;

import com.crealytics.adverts.reportingservice.domain.enumaration.SiteEnum;
import com.crealytics.adverts.reportingservice.service.ReportService;
import com.crealytics.adverts.reportingservice.service.dto.ReportDTO;
import com.crealytics.adverts.reportingservice.util.MonthUtil;
import com.crealytics.adverts.reportingservice.web.rest.exception.BadRequestException;
import com.crealytics.adverts.reportingservice.web.rest.exception.CommonExceptionResponse;
import com.crealytics.adverts.reportingservice.web.rest.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author alican.albayrak
 */
@RestController
@RequestMapping("/reports")
public class ReportResource {

    private static final Logger LOG = LoggerFactory.getLogger(ReportResource.class);
    private final ReportService reportService;

    public ReportResource(ReportService reportService) {
        this.reportService = reportService;
    }

    @ApiOperation(value = "This will get report by month and/or site")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ReportDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = CommonExceptionResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = CommonExceptionResponse.class)
    })
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<ReportDTO> getReport(
            @ApiParam(value = "Optional Month: 1, jan, january") @RequestParam(value = "month") Optional<String> month,
            @ApiParam(value = "Optional Month: [desktop_web, mobile_web, android, iOS]") @RequestParam(value = "site") Optional<String> site) throws BadRequestException {

        if (!month.isPresent() && !site.isPresent()) {
            LOG.error("Both month and site arguments are missing!");
            throw new BadRequestException("month and site values absent");
        }

        int monthIdx = -1;
        if (month.isPresent()) {
            monthIdx = checkMonthString(month.get());
        }

        SiteEnum siteEnum = null;
        if (site.isPresent()) {
            siteEnum = checkSiteEnum(site.get());
        }

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reportService.getReportByMonthAndSite(monthIdx, siteEnum)));
    }

    @ApiOperation(value = "This will get report by month and optional site")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ReportDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = CommonExceptionResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = CommonExceptionResponse.class)
    })
    @RequestMapping(value = "/{month}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<ReportDTO> getReportByMonthPath(@PathVariable String month, @RequestParam Optional<String> site) throws BadRequestException {
        int monthIdx = checkMonthString(month);

        SiteEnum siteEnum = null;
        if (site.isPresent()) {
            siteEnum = checkSiteEnum(site.get());
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reportService.getReportByMonthAndSite(monthIdx, siteEnum)));
    }


    @ApiOperation(value = "This will get report by month and site (both required)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ReportDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = CommonExceptionResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = CommonExceptionResponse.class)
    })
    @RequestMapping(value = "/{month}/{site}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<ReportDTO> getReportByMonthAndSitePathVar(@PathVariable String month, @PathVariable String site) throws BadRequestException {
        int monthIdx = checkMonthString(month);
        SiteEnum siteEnum = checkSiteEnum(site);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reportService.getReportByMonthAndSite(monthIdx, siteEnum)));
    }

    @ApiOperation(value = "This will get all report from database (paginated)")
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Page<ReportDTO>> getAllReports(Pageable pageable) {
        Page<ReportDTO> page = reportService.findAll(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    private static int checkMonthString(String month) throws BadRequestException {
        try {
            return MonthUtil.getMonthByString(month);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    private static SiteEnum checkSiteEnum(String siteString) throws BadRequestException {
        try {
            return SiteEnum.valueOf(siteString.toLowerCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(String.format("Unkown site type received:[%s] ! Accepted types: %s", siteString, Arrays.toString(SiteEnum.values())));
        }
    }

}
