package com.crealytics.adverts.reportingservice.domain.mapper;

import com.crealytics.adverts.reportingservice.domain.Report;
import com.crealytics.adverts.reportingservice.service.dto.ReportDTO;
import com.crealytics.adverts.reportingservice.util.MonthUtil;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * @author alican.albayrak
 */
@Primary
@Component
@Mapper(componentModel = "spring")
public abstract class ReportMapperDecorator implements ReportMapper {

    @Autowired
    @Qualifier("delegate")
    private ReportMapper delegate;

    @Override
    public ReportDTO toDto(Report entity) {
        ReportDTO dto = delegate.toDto(entity);
        Calendar cal = Calendar.getInstance();
        cal.setTime(entity.getReportDate());
        int monthIdx = cal.get(Calendar.MONTH);
        dto.setMonth(MonthUtil.getMonthNameById(monthIdx));
        return dto;
    }

}
