package framework.helpers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateHelpers {
    /**
     * @param format - see https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
     * @return - now, reformatted so that can be used as a file name
     */
    public static String formattedNow(String format) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return now.format(formatter);
    }

    /**
     * @return - now as a date-time stamp to fractions of a second
     */
    public static String uniqueFileName() {
        return formattedNow("yyyy-MM-dd_kkmmss_SSS");
    }
}
