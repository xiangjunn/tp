package seedu.address.model.event.exceptions;

/**
 * Signal that operation has invalid input for start and end date time
 */
public class InvalidDateTimeRangeException extends RuntimeException {
    public InvalidDateTimeRangeException() {
        super("Start date time cannot be after end date time");
    }
}
