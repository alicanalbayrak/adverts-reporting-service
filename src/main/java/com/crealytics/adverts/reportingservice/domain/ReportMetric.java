package com.crealytics.adverts.reportingservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * This entity contains calculated metrics for Report object.
 *
 * @author alican.albayrak
 */
@Getter
@Setter
@Entity(name = "ReportMetric")
public class ReportMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "ctr")
    private Double clickThroughRate;

    @NotNull
    @Column(name = "cr")
    private Double conversionRate;

    @NotNull
    @Column(name = "fr")
    private Double fillRate;

    @NotNull
    @Column(name = "ecpm")
    private Double effectiveCostPerThousand;

    @JsonIgnore
    @OneToOne(mappedBy = "reportMetric")
    private Report report;

}
