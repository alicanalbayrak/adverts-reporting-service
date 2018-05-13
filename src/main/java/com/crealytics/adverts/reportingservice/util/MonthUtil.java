package com.crealytics.adverts.reportingservice.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.DateFormatSymbols;

/**
 * @author alican.albayrak
 */
public class MonthUtil {

    private static final Logger LOG = LoggerFactory.getLogger(MonthUtil.class);

    private static String[] MONTH_NAMES = new DateFormatSymbols().getMonths();
    private static String[] MONTH_SHORT_NAMES = new DateFormatSymbols().getShortMonths();
    private static final String MONTH_NUMBER_REGEX = "^(1[0-2]|[1-9])$";

    public static String getMonthNameById(int monthId) throws IllegalArgumentException {
        if (monthId < 0 || monthId > 11) {
            LOG.error("Invalid parameter: MonthId must be between 1-12");
            throw new IllegalArgumentException("monthId must be between 1-12");
        }

        LOG.debug(String.format("monthId:%s -> %s", monthId, MONTH_NAMES[monthId]));
        return MONTH_NAMES[monthId];
    }

    public static int getMonthByString(String monthStr) throws IllegalArgumentException {

        if (StringUtils.containsWhitespace(monthStr)) {
            throw new IllegalArgumentException("Month cannot contain whitespace!");
        }

        if (StringUtils.isEmpty(monthStr)) {
            throw new IllegalArgumentException("Month cannot be null or empty");
        }

        // if given string already is in integer format and between 1-12
        if (monthStr.matches(MonthUtil.MONTH_NUMBER_REGEX)) {
            LOG.debug("Provided month is in number form! ");
            return Integer.parseInt(monthStr);
        }

        // search in shortnames
        if (monthStr.length() == 3) {
            int tmpMonthIdx = findMonthInArray(MONTH_SHORT_NAMES, monthStr);
            if (tmpMonthIdx != -1) {
                return tmpMonthIdx;
            }
        }

        // lastly look for long names
        int tmpMonthIdx = findMonthInArray(MONTH_NAMES, monthStr);
        if (tmpMonthIdx != -1) {
            LOG.debug(String.format("monthName:%s -> %s", monthStr, 0));
            return tmpMonthIdx;
        } else {
            throw new IllegalArgumentException(String.format("Month [%s] is not valid!", monthStr));
        }
    }

    private static int findMonthInArray(String[] monthsInArr, String monthStr) {
        for (int i = 0; i < monthsInArr.length; i++) {
            if (monthsInArr[i].equalsIgnoreCase(monthStr)) {
                return i + 1;
            }
        }
        return -1;
    }

}
