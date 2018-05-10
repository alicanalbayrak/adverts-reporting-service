package com.crealytics.adverts.reportingservice.domain;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

import com.crealytics.adverts.reportingservice.domain.enumaration.SiteEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author alican.albayrak
 */
@Getter
@Setter
@Entity(name = "Report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "site", length = 11)
    private SiteEnum site;

    @NotNull
    @Column(name = "requests")
    private Long requests;

    @NotNull
    @Column(name = "impressions")
    private Long impressions;

    @NotNull
    @Column(name = "clicks")
    private Long clicks;

    @NotNull
    @Column(name = "conversions")
    private Long conversions;

    @NotNull
    @Digits(integer = 15, fraction = 2)
    @Column(name = "revenue")
    private BigDecimal revenue;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "report_date")
    private Date reportDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "report_metric_id")
    private ReportMetric reportMetric;

}
