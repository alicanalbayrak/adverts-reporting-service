package com.crealytics.adverts.reportingservice.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author alican.albayrak
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportMetricDTO {

    private Double clickThroughRate;

    private Double conversionRate;

    private Double fillRate;

    private Double effectiveCostPerThousand;

}
