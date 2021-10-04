package seedu.address.model.event;

/**
 * Represents end time of an event in the event list.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */

public class EndDateTime extends DateAndTime {

    /**
     * Constructs an {@code DateAndTime}
     *
     * @param time A valid DateAndTime
     */
    public EndDateTime(String time) {
        super(time);
    }
}
