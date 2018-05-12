package com.crealytics.adverts.reportingservice.service.dto;

import lombok.Data;

/**
 * @author alican.albayrak
 */
@Data
public class ReportMetricDTO {

    private Double clickThroughRate;

    private Double conversionRate;

    private Double fillRate;

    private Double effectiveCostPerThousand;

}
