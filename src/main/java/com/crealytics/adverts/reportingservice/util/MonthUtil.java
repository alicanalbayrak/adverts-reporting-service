package com.crealytics.adverts.reportingservice.util;

import java.text.DateFormatSymbols;

/**
 * @author alican.albayrak
 */
public class MonthUtil {

    private static String[] monthNames = new DateFormatSymbols().getMonths();

    private static String[] monthShortNames = new DateFormatSymbols().getShortMonths();


    public static String getMonthNameById(int monthId) throws IllegalArgumentException {
        if (monthId < 0 || monthId > 11){
            throw new IllegalArgumentException("monthId must be between 1-12");
        }
        return monthNames[monthId];
    }

}
