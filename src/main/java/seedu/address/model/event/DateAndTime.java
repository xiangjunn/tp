package seedu.address.model.event;

import java.time.LocalDateTime;

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
    public static final String VALIDATION_REGEX = "";

    public final LocalDateTime time;

    /**
     * Constructs an {@code DateAndTime}
     *
     * @param time A valid start DateAndTime
     */
    public DateAndTime(String time) {
        this.time = LocalDateTime.parse(time);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.event.DateAndTime // instanceof handles nulls
                && time.equals(((DateAndTime) other).time)); // state check
    }
}
