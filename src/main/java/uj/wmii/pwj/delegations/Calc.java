package uj.wmii.pwj.delegations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Calc {

    BigDecimal calculate(String name, String start, String end, BigDecimal dailyRate) throws IllegalArgumentException {

        final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm z");
        BigDecimal total = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        ZonedDateTime startDateTime = ZonedDateTime.parse(start, DATE_PATTERN);
        ZonedDateTime endDateTime = ZonedDateTime.parse(end, DATE_PATTERN);
        Duration duration = Duration.between(startDateTime, endDateTime);

        if(duration.compareTo(Duration.ZERO) <= 0) { return total; }

        long fullDays = duration.toDays();
        int hours = duration.toHoursPart();

        if(fullDays > 0)
        {
            total = total.add(BigDecimal.valueOf(fullDays).multiply(dailyRate));
        }

        Duration remainder = duration.minusDays(fullDays);
        if(remainder.isZero()) {return total;}

        if(hours > 12)
        {
            total = total.add(dailyRate);
        } else if (hours > 8) {
            total = total.add(dailyRate.divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP));
        } else {
            total = total.add(dailyRate.divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP));
        }
        return total;
    }
}
