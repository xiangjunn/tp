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

    // TODO: 10/6/2021 update test cases for isValidName()
    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && fullName.equals(((Name) other).fullName)); // state check
    }
}
