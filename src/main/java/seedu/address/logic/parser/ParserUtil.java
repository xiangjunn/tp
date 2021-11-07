package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_RANGE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.core.range.Range;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.common.ZoomLink;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Phone;
import seedu.address.model.contact.TelegramHandle;
import seedu.address.model.event.DateAndTime;
import seedu.address.model.event.Description;
import seedu.address.model.event.EndDateTime;
import seedu.address.model.event.StartDateTime;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {



    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses parameter into a {@code Range} and returns it. Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the specified range is invalid.
     */
    public static Range parseRange(String range) throws ParseException {
        String trimmedRange = range.trim();
        if (!StringUtil.isValidRange(trimmedRange)) {
            throw new ParseException(MESSAGE_INVALID_RANGE);
        }
        String[] rangeArr = trimmedRange.split("-");
        Index start = parseIndex(rangeArr[0]);
        Index end = parseIndex(rangeArr[1]);
        return new Range(start, end);
    }

    /**
     * Parses parameter into a {@code Range} and returns it. Leading and trailing whitespaces will be trimmed.
     * First, it will try to parse into an {@code Index}. If successful, the index will be converted to a
     * Range from itself to itself. Else, it will parse into a Range.
     * @throws ParseException if specified argument can neither be parsed into an Index nor a Range.
     */
    public static Range parseDeleteArgument(String args) throws ParseException {
        try {
            Index index = parseIndex(args);
            return Range.convertFromIndex(index);
        } catch (ParseException pe) {
            return parseRange(args);
        }
    }

    /**
     * Parses parameter into a list of {@code Index} and returns it. Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if specified argument cannot be parsed into a Index.
     */
    public static List<Index> parseMarkIndexes(String args) throws ParseException {
        String[] stringIndexes = args.split("\\s+");
        List<Index> indexes = new ArrayList<>();
        for (String index : stringIndexes) {
            index = index.trim();
            if (index.equals("")) {
                continue;
            }
            Index parsedIndex = parseIndex(index.trim());
            if (indexes.contains(parsedIndex)) {
                throw new ParseException(Messages.MESSAGE_DUPLICATE_INDEXES_PROVIDED);
            }
            indexes.add(parsedIndex);
        }
        return indexes;
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseContactName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String telegramHandle} into an {@code TelegramHandle}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code telegramHandle} is invalid.
     */
    public static TelegramHandle parseTelegram(String telegramHandle) throws ParseException {
        requireNonNull(telegramHandle);
        String trimmedTelegram = telegramHandle.trim();
        if (!TelegramHandle.isValidHandle(trimmedTelegram)) {
            throw new ParseException(TelegramHandle.MESSAGE_CONSTRAINTS);
        }
        return new TelegramHandle(trimmedTelegram);
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseEventName(String name) throws ParseException {
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
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
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

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
