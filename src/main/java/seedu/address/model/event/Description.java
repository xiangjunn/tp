package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.List;

import seedu.address.commons.util.StringUtil;

/**
 * Represents the description of an event in the address book.
 * Guarantees: immutable; is always valid
 */
public class Description {

    public static final String MESSAGE_CONSTRAINTS = "Description can take any values, and it should not be blank";

    /*
     * The first character of the description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Description}.
     *
     * @param remark A valid description.
     */
    public Description(String remark) {
        requireNonNull(remark);
        checkArgument(isValidDescription(remark), MESSAGE_CONSTRAINTS);
        value = remark;
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && value.equals(((Description) other).value)); // state check
    }

    @Override
    public String toString() {
        return value;
    }

    /**
     * Checks if this {@code value} contains any keywords in {@code strings}
     */
    public boolean containsString(List<String> strings) {
        requireNonNull(strings);
        assert value != null : "the value should not be null";
        return strings.stream().anyMatch(string ->
                StringUtil.containsWordIgnoreCase(value, string));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
