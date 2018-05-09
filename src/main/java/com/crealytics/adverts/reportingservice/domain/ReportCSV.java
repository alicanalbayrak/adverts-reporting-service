package com.crealytics.adverts.reportingservice.domain;

import java.io.Serializable;
import java.util.Date;

import com.crealytics.adverts.reportingservice.domain.enumaration.SiteEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

/**
 * @author alican.albayrak
 */
@Data
@JsonPropertyOrder({"site", "requests", "impressions", "clicks", "conversions", "revenue"})
public class ReportCSV implements Serializable {

    @JsonProperty("site")
    private SiteEnum site;

    @JsonProperty("requests")
    private long requests;

    @JsonProperty("impressions")
    private long impressions;

    @JsonProperty("clicks")
    private long clicks;

    @JsonProperty("conversions")
    private long conversions;

    @JsonProperty("revenue (USD)")
    private double revenue;

    @JsonIgnore
    private Date reportDate;

}
