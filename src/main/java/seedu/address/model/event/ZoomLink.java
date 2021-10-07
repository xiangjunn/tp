package seedu.address.model.event;

import static java.util.Objects.requireNonNull;


/**
 * Represents zoom link of an event.
 * Guarantees: immutable}
 */
public class ZoomLink {

    public static final String MESSAGE_CONSTRAINTS = "Event zoom links should not be blank";

    // TODO: 10/6/2021 add validation
    public static final String VALIDATION_REGEX = ".*";

    public final String value;

    /**
     * Constructs a {@code ZoomLink}.
     *
     * @param link A valid zoom link.
     */
    public ZoomLink(String link) {
        requireNonNull(link);
        value = link;
    }

    // TODO: 10/6/2021 add test cases for invalid zoom link
    /**
     * Returns true if a given string is a valid zoom link.
     */
    public static boolean isValidZoomLink(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ZoomLink // instanceof handles nulls
                && value.equals(((ZoomLink) other).value)); // state check
    }
}
