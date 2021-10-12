package seedu.address.logic.parser.event;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.common.ZoomLink;
import seedu.address.model.event.DateAndTime;
import seedu.address.model.event.Description;
import seedu.address.model.event.EndDateTime;
import seedu.address.model.event.Name;
import seedu.address.model.event.StartDateTime;

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
     * Parses a {@code String  startDateTime} into a {@code StartDateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param startDateTime when the event starts
     * @throws ParseException if the given {@code startDateTime} is invalid.
     */
    public static StartDateTime parseStartDateTime(String startDateTime) throws ParseException {
        requireNonNull(startDateTime);
        String trimmedStartDateTime = startDateTime.trim();
        if (!StartDateTime.isValidDateTime(trimmedStartDateTime)) {
            throw new ParseException(DateAndTime.MESSAGE_CONSTRAINTS);
        }
        return new StartDateTime(trimmedStartDateTime);
    }

    /**
     * Parses a {@code String  endDateTime} into a {@code EndDateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param endDateTime when the event ends
     * @throws ParseException if the given {@code endDateTime} is invalid.
     */
    public static EndDateTime parseEndDateTime(String endDateTime) throws ParseException {
        requireNonNull(endDateTime);
        String trimmedEndDateTime = endDateTime.trim();
        if (!StartDateTime.isValidDateTime(trimmedEndDateTime)) {
            throw new ParseException(DateAndTime.MESSAGE_CONSTRAINTS);
        }
        return new EndDateTime(trimmedEndDateTime);
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
