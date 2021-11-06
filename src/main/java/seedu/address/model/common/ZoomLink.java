package seedu.address.model.common;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.List;

import seedu.address.commons.util.StringUtil;

/**
 * Represents zoom link of a contact (usually a professor or tutor).
 * Guarantees: immutable
 */
public class ZoomLink {

    public static final String MESSAGE_CONSTRAINTS = "Zoom link can take in any value and it should not be blank.";

    public static final String VALIDATION_REGEX = "^(?!\\s*$).+";

    public final String link;

    /**
    * Constructs a {@code ZoomLink}.
    *
    * @param link A valid zoom link.
    */
    public ZoomLink(String link) {
        requireNonNull(link);
        checkArgument(isValidZoomLink(link), MESSAGE_CONSTRAINTS);
        this.link = link;
    }

    /**
     * Returns true if a given string is a valid zoom link.
     */
    public static boolean isValidZoomLink(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return link;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ZoomLink // instanceof handles nulls
                && link.equals(((ZoomLink) other).link)); // state check
    }

    /**
     * Checks if this {@code link} contains any keywords in {@code strings}
     */
    public boolean containsString(List<String> strings) {
        requireNonNull(strings);
        assert link != null : "the link should not be null";
        return strings.stream().anyMatch(string ->
                StringUtil.containsWordIgnoreCase(link, string));
    }
}
