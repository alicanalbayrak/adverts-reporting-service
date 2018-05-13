package com.crealytics.adverts.reportingservice.domain.mapper;

import com.crealytics.adverts.reportingservice.domain.Report;
import com.crealytics.adverts.reportingservice.service.dto.ReportDTO;
import com.crealytics.adverts.reportingservice.util.MonthUtil;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Primary implementation of {@link ReportMapper} class.
 *
 * @author alican.albayrak
 */
@Primary
@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ReportMapperDecorator implements ReportMapper {

    private ReportMapper delegate;

    /**
     * This method is workaround to map ReportDTO's month field correctly.
     * Extracts month id from {@link Report#reportDate}, lookups month's
     * String value then injects it to returning DTO.
     *
     * @param entity Report
     * @return ReportDTO
     */
    @Override
    public ReportDTO toDto(Report entity) {
        ReportDTO dto = delegate.toDto(entity);
        if(dto == null){
            return null;
        }

        if(entity.getReportDate() != null){
            Calendar cal = Calendar.getInstance();
            cal.setTime(entity.getReportDate());
            int monthIdx = cal.get(Calendar.MONTH);
            dto.setMonth(MonthUtil.getMonthNameById(monthIdx));
        }

        return dto;
    }

    /**
     * This method overrided to prevent mapping operations on NULL
     * entityList.
     *
     * @param entityList
     * @return
     */
    @Override
    public List<ReportDTO> toDto(List<Report> entityList) {

        if ( entityList == null ) {
            return null;
        }

        List<ReportDTO> list = new ArrayList<ReportDTO>( entityList.size() );
        for ( Report report : entityList ) {
            list.add( toDto( report ) );
        }

        return list;

    }

    @Autowired
    @Qualifier("delegate")
    public void setDelegate(ReportMapper delegate) {
        this.delegate = delegate;
    }
}
