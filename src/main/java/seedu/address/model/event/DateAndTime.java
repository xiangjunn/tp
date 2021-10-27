package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import seedu.address.commons.util.StringUtil;

/**
 * Represents time of an event in the event list.
 * Guarantees: immutable;
 */

public class DateAndTime implements Comparable<DateAndTime> {
    public static final String MESSAGE_CONSTRAINTS =
            "Time format should be in  dd-MM-yyyy HH:mm format and start time should not be blank";

    public static final String DATE_FORMAT = "(0[1-9]|[12][0-9]|3[01])"; // date range from 1 to 31

    public static final String MONTH_FORMAT = "(0[0-9]|1[0-2])"; // month range from 1 to 12

    public static final String YEAR_FORMAT = "([12][0-9][0-9][0-9])"; // year range from 1000 to 2999

    public static final String HOUR_FORMAT = "(0[0-9]|1[0-9]|2[0-3])"; // hour range from 00 to 23

    public static final String MINUTE_FORMAT = "(0[0-9]|[1-5][0-9])"; // minute range from 00 to 59

    public static final String VALIDATION_REGEX = DATE_FORMAT + "-" + MONTH_FORMAT + "-" + YEAR_FORMAT
            + " " + HOUR_FORMAT + ":" + MINUTE_FORMAT;

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    public final LocalDateTime time;

    /**
     * Constructs an {@code DateAndTime}
     *
     * @param time A valid start DateAndTime
     */
    public DateAndTime(String time) {
        requireNonNull(time);
        checkArgument(isValidDateTime(time), MESSAGE_CONSTRAINTS);
        this.time = LocalDateTime.parse(time, DATE_TIME_FORMATTER);
    }

    /**
     * Returns true if a given string is a valid DateAndTime.
     */
    public static boolean isValidDateTime(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Return LocalDateTime of a DateAndTime object
     */
    public LocalDateTime getDateTime() {
        return time;
    }

    public boolean isBefore(DateAndTime d) {
        return this.time.isBefore(d.getDateTime());
    }

    public boolean isNotBeforeNow() {
        return !this.time.isBefore(LocalDateTime.now());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateAndTime // instanceof handles nulls
                && time.equals(((DateAndTime) other).time)); // state check
    }

    /**
     * Returns a string representing a DateAndTime object.
     * @return A string representation of a DateAndTime object.
     */
    @Override
    public String toString() {
        return this.time.format(DATE_TIME_FORMATTER);
    }

    @Override
    public int compareTo(DateAndTime other) {
        return this.time.compareTo(other.time);
    }


    /**
     * Checks if this {@code time string} contains any keywords in {@code strings}
     */
    public boolean containsString(List<String> strings) {
        requireNonNull(strings);
        assert time != null : "time must not be null";
        return strings.stream().anyMatch(string ->
                StringUtil.containsWordIgnoreCase(toString(), string));
    }

}
