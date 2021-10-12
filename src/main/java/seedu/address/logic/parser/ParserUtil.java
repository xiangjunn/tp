package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.core.range.Range;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.common.Address;
import seedu.address.model.common.ZoomLink;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Phone;
import seedu.address.model.contact.TelegramHandle;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_RANGE = "Range given is invalid";

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
            return new Range(index, index);
        } catch (ParseException pe) {
            return parseRange(args);
        }
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
        return new TelegramHandle(telegramHandle);
    }

    /**
     * Parses a {@code String zoomLink} into an {@code ZoomLink}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code zoomLink} is invalid.
     */
    public static ZoomLink parseZoom(String zoomLink) throws ParseException {
        requireNonNull(zoomLink);
        String trimmedZoomLink = zoomLink.trim();
        if (!ZoomLink.isValidZoomLink(trimmedZoomLink)) {
            throw new ParseException(TelegramHandle.MESSAGE_CONSTRAINTS);
        }
        return new ZoomLink(zoomLink);
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
