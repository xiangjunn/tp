package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

/**
 * Represents the description of an event in the address book.
 * Guarantees: immutable; is always valid
 */
public class Description {

    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param remark A valid address.
     */
    public Description(String remark) {
        requireNonNull(remark);
        value = remark;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && value.equals(((Description) other).value)); // state check
    }
}
