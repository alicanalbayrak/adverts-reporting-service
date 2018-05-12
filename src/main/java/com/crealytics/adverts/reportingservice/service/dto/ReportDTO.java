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

    private Double clickThroughRate;

    private Double conversionRate;

    private Double fillRate;

    private Double effectiveCostPerThousand;

}
