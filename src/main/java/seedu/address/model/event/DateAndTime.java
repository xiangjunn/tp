package seedu.address.model.event;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents time of an event in the event list.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */

public class DateAndTime {
    public static final String MESSAGE_CONSTRAINTS =
            "Time format should be in  dd-MM-yyyy HH:mm format and start time should not be blank";

    /**
     * @// TODO: 10/5/2021 add requirement for time
     *
     */
    public static final String VALIDATION_REGEX = "";

    public final LocalDateTime time;

    /**
     * Constructs an {@code DateAndTime}
     *
     * @param time A valid start DateAndTime
     */
    public DateAndTime(String time) {
        checkArgument(isValidTime(time), MESSAGE_CONSTRAINTS);
        this.time = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    /**
     * Returns true if a given dateAndTime is valid
     */
    public static boolean isValidTime(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return time.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.event.DateAndTime // instanceof handles nulls
                && time.equals(((DateAndTime) other).time)); // state check
    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }


}
