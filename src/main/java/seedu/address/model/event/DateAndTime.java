package seedu.address.model.event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents time of an event in the event list.
 * Guarantees: immutable;
 */

public class DateAndTime {
    public static final String MESSAGE_CONSTRAINTS =
            "Time format should be in  dd-MM-yyyy HH:mm format and start time should not be blank";

    /**
     * @// TODO: 10/5/2021 add requirement for time
     *
     */
    public static final String VALIDATION_REGEX = ".*";

    public final LocalDateTime time;

    /**
     * Constructs an {@code DateAndTime}
     *
     * @param time A valid start DateAndTime
     */
    public DateAndTime(String time) {
        this.time = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    // TODO: 10/6/2021 add test case for invalid date and time
    /**
     * Returns true if a given string is a valid DateAndTime.
     */
    public static boolean isValidDateTime(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateAndTime // instanceof handles nulls
                && time.equals(((DateAndTime) other).time)); // state check
    }
}
