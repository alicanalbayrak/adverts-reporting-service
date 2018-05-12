package com.crealytics.adverts.reportingservice.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author alican.albayrak
 */
@Data
public class ReportDTO implements Serializable {

    private String month;

    private String site;

    private Long requests;

    private Long impressions;

    private Long clicks;

    private Long conversions;

    private BigDecimal revenue;

// "CTR" : "some_value",
// "CR" : "some_value",
// "fill_rate" : "some_value",
// "eCPM" : "some_value"

}
