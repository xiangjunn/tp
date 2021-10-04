package seedu.address.model.event;

/**
 * Represents start time of an event in the event list.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class StartDateTime extends DateAndTime {

    /**
     * Constructs an {@code EndDateTime}
     *
     * @param time A valid DateAndTime
     */
    public StartDateTime(String time) {
        super(time);
    }
}
