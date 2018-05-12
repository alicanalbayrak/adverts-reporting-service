package com.crealytics.adverts.reportingservice.domain.mapper;

import com.crealytics.adverts.reportingservice.domain.Report;
import com.crealytics.adverts.reportingservice.service.dto.ReportDTO;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author alican.albayrak
 */
@Component
@Qualifier("delegate")
@Mapper(componentModel = "spring")
@DecoratedWith(ReportMapperDecorator.class)
public interface ReportMapper extends EntityMapper<ReportDTO, Report> {

//    @Mappings({
//            @Mapping(target = "reportMetric.clickThroughRate", source = "clickThroughRate"),
//            @Mapping(target = "reportMetric.conversionRate", source = "conversionRate"),
//            @Mapping(target = "reportMetric.fillRate", source = "fillRate"),
//            @Mapping(target = "reportMetric.effectiveCostPerThousand", source = "effectiveCostPerThousand")
//    })
//    @Override
//    Report toEntity(ReportDTO dto);

    @Mappings({
            @Mapping(target = "clickThroughRate", source = "reportMetric.clickThroughRate"),
            @Mapping(target = "conversionRate", source = "reportMetric.conversionRate"),
            @Mapping(target = "fillRate", source = "reportMetric.fillRate"),
            @Mapping(target = "effectiveCostPerThousand", source = "reportMetric.effectiveCostPerThousand")
    })
    @Override
    ReportDTO toDto(Report entity);
}
