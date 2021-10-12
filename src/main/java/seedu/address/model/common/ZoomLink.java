package seedu.address.model.common;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents zoom link of a contact (usually a professor or tutor).
 * Guarantees: immutable
 */
public class ZoomLink {

    public static final String MESSAGE_CONSTRAINTS_1 =
            "Zoom link should be in (https://body.end) format";

    public static final String SPECIAL_CHARACTERS = "[\\/-]"; // body link can contain / or -

    public static final String MESSAGE_CONSTRAINTS = "Zoom link should be of the format (http(s)://)body.end "
            + "and adhere to the following constraints:\n"
            + "1. The header link http:// https:// and www. is optional. \n"
            + "2. The body of Zoom link should only contain alphanumeric characters and these special "
            + "characters, excluding the parentheses, (" + SPECIAL_CHARACTERS + "). "
            + "The body of Zoom link may not start or end with any special characters and "
            + "there may be multiple body parts. \n"
            + "3. This is followed by a '.' and then the end part. The end part may contain any characters except for "
            + "underscore.\n It must contain at least one alphanumerical and should end with with an alphanumerical.\n";

    public static final String ALPHANUMERIC_NO_UNDERSCORE = "[^\\W_]+";

    public static final String VALIDATION_HEADER = "(http:\\/\\/|https:\\/\\/)?(www.)?"; // optional header

    // body link should alphanumeric characters and may contain special characters but should not start with them
    public static final String VALIDATION_BODY_REPEATED =
            "(" + SPECIAL_CHARACTERS + "?" + ALPHANUMERIC_NO_UNDERSCORE + "+)*";

    // at least one repeated body part
    public static final String VALIDATION_BODY =
            ALPHANUMERIC_NO_UNDERSCORE + "(" + VALIDATION_BODY_REPEATED + "\\." + ")+";
    //body regex = [^\\W_]+(([\\-]?[^\\W_]+)*\\.)+

    public static final String VALIDATION_END = ALPHANUMERIC_NO_UNDERSCORE + "([^\\_]"
            + ALPHANUMERIC_NO_UNDERSCORE + "+)*";
    // end regex [^\W_]+([^\_][^\W_]+)*

    public static final String VALIDATION_REGEX = "^" + VALIDATION_HEADER + VALIDATION_BODY + VALIDATION_END;
    //"^(http:\/\/|https:\/\/)?(www.)?[^\W_]+(([\/-]?[^\W_]+)*\.)+[^\W_]+([^\_][^\W_])*$";

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
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ZoomLink // instanceof handles nulls
                && link.equals(((ZoomLink) other).link)); // state check
    }
}
