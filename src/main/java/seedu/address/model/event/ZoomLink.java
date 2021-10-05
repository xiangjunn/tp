package seedu.address.model.event;

import static java.util.Objects.requireNonNull;


/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable}
 */
public class ZoomLink {

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

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ZoomLink // instanceof handles nulls
                && value.equals(((ZoomLink) other).value)); // state check
    }
}
