package com.crealytics.adverts.reportingservice.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * @author alican.albayrak
 */
public class CSVParserUtil {

    private static final Logger LOG = LoggerFactory.getLogger(CSVParserUtil.class);
    private static final String FILENAME_DELIMITER = "_";

    public static Optional<Date> extractDateInfoFromFilename(String filename) {
        LOG.info(filename);

        String[] yearSplittedArr = StringUtils.split(filename, FILENAME_DELIMITER);
        String[] monthSplittedArr = StringUtils.split(yearSplittedArr[1], FILENAME_DELIMITER);

        LOG.debug(String.format("Report year:%s, month:%s", yearSplittedArr[0], monthSplittedArr[0]));

        try {
            // SimpleDateFormat is not thread safe
            // use a new instance each time (It is better than synchronizing, as synchronization can raise a bottleneck issue)
            SimpleDateFormat reportDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = reportDateFormat.parse(yearSplittedArr[0] + "-" + monthSplittedArr[0] + "-01");
            return Optional.of(date);
        } catch (ParseException e) {
            LOG.error(String.format("Exception thrown while parsing file: %s", filename));
        }

        return Optional.empty();
    }


}
