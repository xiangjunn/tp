package seedu.address.model.history;

/**
 * Represents an exception when there is an issue with the {@code ModelHistory}.
 */
public class ModelHistoryException extends RuntimeException {
    public ModelHistoryException(String message) {
        super(message);
    }
}
