package com.crealytics.adverts.reportingservice.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * @author alican.albayrak
 */
public class CSVParserUtilTests {

    @Test
    public void testDatePresent() {
        Optional<Date> date = CSVParserUtil.extractDateInfoFromFilename("2018_01_somestring.csv");
        assertTrue(date.isPresent());
    }

    @Test
    public void testExpectedDateSatisfied() throws ParseException {

        Optional<Date> date = CSVParserUtil.extractDateInfoFromFilename("2017_09_somestring.csv");
        SimpleDateFormat reportDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date expectedDate = reportDateFormat.parse("2017-09-01");
        assertEquals(expectedDate, date.get());
    }
}
