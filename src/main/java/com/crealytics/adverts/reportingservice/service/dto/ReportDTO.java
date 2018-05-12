package com.crealytics.adverts.reportingservice.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author alican.albayrak
 */
@Data
public class ReportDTO implements Serializable {

    @JsonProperty("month")
    private String month;

    @JsonProperty("site")
    private String site;

    @JsonProperty("requests")
    private Long requests;

    @JsonProperty("impressions")
    private Long impressions;

    @JsonProperty("clicks")
    private Long clicks;

    @JsonProperty("conversions")
    private Long conversions;

    @JsonProperty("revenue")
    private BigDecimal revenue;

    @JsonProperty("CTR")
    private Double clickThroughRate;

    @JsonProperty("CR")
    private Double conversionRate;

    @JsonProperty("fill_rate")
    private Double fillRate;

    @JsonProperty("eCPM")
    private Double effectiveCostPerThousand;

}
