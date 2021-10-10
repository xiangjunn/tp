package seedu.address.logic.parser.event;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.common.ZoomLink;
import seedu.address.model.event.DateAndTime;
import seedu.address.model.event.Description;
import seedu.address.model.event.Name;

public class EventParserUtil extends ParserUtil {

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String  dateAndTime} into a {@code DateAndTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dateAndTime} is invalid.
     */
    public static DateAndTime parseDateAndTime(String dateAndTime) throws ParseException {
        requireNonNull(dateAndTime);
        String trimmedDateAndTime = dateAndTime.trim();
        if (!DateAndTime.isValidDateTime(trimmedDateAndTime)) {
            throw new ParseException(DateAndTime.MESSAGE_CONSTRAINTS);
        }

        return new DateAndTime(trimmedDateAndTime);

    }

    /**
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     */
    public static Description parseDescription(String description) {
        requireNonNull(description);
        String trimmedDescription = description.trim();

        return new Description(trimmedDescription);
    }

    /**
     * Parses a {@code String  zoomLink} into a {@code ZoomLink}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code zoomLink} is invalid.
     */
    public static ZoomLink parseZoomLink(String zoomLink) throws ParseException {
        requireNonNull(zoomLink);
        String trimmedZoomLink = zoomLink.trim();
        if (!ZoomLink.isValidZoomLink(trimmedZoomLink)) {
            throw new ParseException(ZoomLink.MESSAGE_CONSTRAINTS);
        }

        return new ZoomLink(trimmedZoomLink);

    }

}
