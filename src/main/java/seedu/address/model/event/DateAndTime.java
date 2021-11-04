package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;

import seedu.address.commons.util.StringUtil;

/**
 * Represents time of an event in the event list.
 * Guarantees: immutable;
 */

public class DateAndTime implements Comparable<DateAndTime> {
    public static final String MESSAGE_CONSTRAINTS =
            "Event date and time should be in  dd-MM-yyyy HH:mm format and should meet the following requirements: \n"
            + "1. Start dateTime should not be empty.\n"
            + "2. Input should not contain any leading or trailing white space.\n"
            + "3. Year input can be any 4 digits number. Month input ranges from 01 to 12. "
            + "Date input ranges from 01 to 31 but the range may vary according to the month input. \n"
            + "Example: 31-01-2021 is a valid date, but 31-02-2021 is invalid as February has only 28 or 29 days. \n"
            + "4. Hour input ranges from 00 to 23. Minute input ranges from 00 to 59. \n"
            + "5. Any single-digit input should start with a leading 0. \n"
            + "Example: 02-10-2021 09:00, 13-05-1999 12:05 are valid inputs but 2-10-2021 09:00, "
            + "13-05-1999 12:5 are invalid. \n";

    public static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm").withResolverStyle(ResolverStyle.STRICT);
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
        try {
            DATE_TIME_FORMATTER.parse(test);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
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
