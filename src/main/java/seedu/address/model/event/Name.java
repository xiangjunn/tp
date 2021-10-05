package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

/**
 * Represents name of an event in the event list.
 * Guarantees: immutable;
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS = "Event names should not be blank";

    /*
     * The first character of the event must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */

    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        fullName = name;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.event.Name // instanceof handles nulls
                && fullName.equals(((seedu.address.model.event.Name) other).fullName)); // state check
    }
}
