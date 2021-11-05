package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.core.range.Range;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.common.ZoomLink;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Phone;
import seedu.address.model.contact.TelegramHandle;
import seedu.address.model.event.Description;
import seedu.address.model.event.EndDateTime;
import seedu.address.model.event.StartDateTime;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_START_DATE_TIME = "20/11/2021 11:00";
    private static final String INVALID_END_DATE_TIME = "20/11/2021 15:00";
    private static final String INVALID_DESCRIPTION = " ";
    private static final String INVALID_TELEGRAM = "my%Telegram";
    private static final String INVALID_ZOOM_LINK = "";
    private static final String INVALID_DELETE_ARGUMENT = "abc";
    private static final String INVALID_INDEX = "-1";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_START_DATE_TIME = "20-11-2021 11:00";
    private static final String VALID_END_DATE_TIME = "20-11-2021 15:00";
    private static final String VALID_TELEGRAM = "my_Telegram";
    private static final String VALID_ZOOM_LINK = "my-zoom-link/tutorial.nus.edu";
    private static final String VALID_DESCRIPTION = "This is a description!";
    private static final String VALID_INDEX = "1";
    private static final String VALID_RANGE = "1-2";
    private static final String VALID_TWO_DIGIT_INDEX = "11";
    private static final String VALID_THREE_DIGIT_INDEX = "111";
    private static final String INDEX_WITH_ZERO_INFRONT = "01";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseContactName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseContactName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseContactName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseContactName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseZoomLink_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseZoomLink((String) null));
    }

    @Test
    public void parseZoomLink_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseZoomLink(INVALID_ZOOM_LINK));
    }

    @Test
    public void parseZoomLink_validValueWithoutWhitespace_returnsZoomLink() throws Exception {
        ZoomLink expectedZoomLink = new ZoomLink(VALID_ZOOM_LINK);
        assertEquals(expectedZoomLink, ParserUtil.parseZoomLink(VALID_ZOOM_LINK));
    }

    @Test
    public void parseZoomLink_validValueWithWhitespace_returnsTrimmedZoomLink() throws Exception {
        String zoomLinkWithWhitespace = WHITESPACE + VALID_ZOOM_LINK + WHITESPACE;
        ZoomLink expectedZoomLink = new ZoomLink(VALID_ZOOM_LINK);
        assertEquals(expectedZoomLink, ParserUtil.parseZoomLink(zoomLinkWithWhitespace));
    }

    @Test
    public void parseTelegram_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTelegram((String) null));
    }

    @Test
    public void parseTelegram_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTelegram(INVALID_TELEGRAM));
    }

    @Test
    public void parseTelegram_validValueWithoutWhitespace_returnsTelegram() throws Exception {
        TelegramHandle expectedTelegramHandle = new TelegramHandle(VALID_TELEGRAM);
        assertEquals(expectedTelegramHandle, ParserUtil.parseTelegram(VALID_TELEGRAM));
    }

    @Test
    public void parseTelegram_validValueWithWhitespace_returnsTrimmedTelegram() throws Exception {
        String telegramWithWhitespace = WHITESPACE + VALID_TELEGRAM + WHITESPACE;
        TelegramHandle expectedTelegram = new TelegramHandle(VALID_TELEGRAM);
        assertEquals(expectedTelegram, ParserUtil.parseTelegram(telegramWithWhitespace));
    }

    @Test
    public void parseStartDateTime_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseStartDateTime((String) null));
    }

    @Test
    public void parseStartDateTime_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseStartDateTime(INVALID_START_DATE_TIME));
    }

    @Test
    public void parseStartDateTime_validValueWithoutWhitespace_returnsStartDateTime() throws Exception {
        StartDateTime expectedStartDateTime = new StartDateTime(VALID_START_DATE_TIME);
        assertEquals(expectedStartDateTime, ParserUtil.parseStartDateTime(VALID_START_DATE_TIME));
    }

    @Test
    public void parseStartDateTime_validValueWithWhitespace_returnsTrimmedStartDateTime() throws Exception {
        String startDateTimeWithWhitespace = WHITESPACE + VALID_START_DATE_TIME + WHITESPACE;
        StartDateTime expectedStartDateTime = new StartDateTime(VALID_START_DATE_TIME);
        assertEquals(expectedStartDateTime, ParserUtil.parseStartDateTime(startDateTimeWithWhitespace));
    }


    @Test
    public void parseEndDateTime_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEndDateTime((String) null));
    }

    @Test
    public void parseEndDateTime_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEndDateTime(INVALID_END_DATE_TIME));
    }

    @Test
    public void parseEndDateTime_validValueWithoutWhitespace_returnsEndDateTime() throws Exception {
        EndDateTime expectedEndDateTime = new EndDateTime(VALID_END_DATE_TIME);
        assertEquals(expectedEndDateTime, ParserUtil.parseEndDateTime(VALID_END_DATE_TIME));
    }

    @Test
    public void parseEndDateTime_validValueWithWhitespace_returnsTrimmedEndDateTime() throws Exception {
        String endDateTimeWithWhitespace = WHITESPACE + VALID_END_DATE_TIME + WHITESPACE;
        EndDateTime expectedEndDateTime = new EndDateTime(VALID_END_DATE_TIME);
        assertEquals(expectedEndDateTime, ParserUtil.parseEndDateTime(endDateTimeWithWhitespace));
    }

    @Test
    public void parseDescription_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDescription((String) null));
    }

    @Test
    public void parseDescription_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDescription(INVALID_DESCRIPTION));
    }

    @Test
    public void parseDescription_validValueWithoutWhitespace_returnsDescription() throws Exception {
        Description expectedDescription = new Description(VALID_DESCRIPTION);
        assertEquals(expectedDescription, ParserUtil.parseDescription(VALID_DESCRIPTION));
    }

    @Test
    public void parseDescription_validValueWithWhitespace_returnsTrimmedDescription() throws Exception {
        String descriptionWithWhitespace = WHITESPACE + VALID_DESCRIPTION + WHITESPACE;
        Description expectedDescription = new Description(VALID_DESCRIPTION);
        assertEquals(expectedDescription, ParserUtil.parseDescription(descriptionWithWhitespace));
    }

    @Test
    public void parseIndex_validArguementWithWhiteSpace_returnsIndex() throws Exception {
        Index expectedIndex = Index.fromOneBased(1);
        String indexWithWhiteSpace = WHITESPACE + VALID_INDEX + WHITESPACE;
        String indexWithZero = WHITESPACE + INDEX_WITH_ZERO_INFRONT;
        assertEquals(expectedIndex, ParserUtil.parseIndex(indexWithWhiteSpace));
        assertEquals(expectedIndex, ParserUtil.parseIndex(indexWithZero));
    }

    @Test
    public void parseIndex_invalidValue_throwsParseException() throws Exception {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex(INVALID_INDEX));
    }

    @Test
    public void parseDeleteArgument_validValueWithWhitespace_returnsRange() throws Exception {
        String indexWithWhitespace = WHITESPACE + VALID_INDEX + WHITESPACE;
        Range expectedRangeFromIndex = Range.convertFromIndex(Index.fromOneBased(1));
        assertEquals(expectedRangeFromIndex, ParserUtil.parseDeleteArgument(indexWithWhitespace));

        String rangeWithWhitespace = WHITESPACE + VALID_RANGE + WHITESPACE;
        Range expectedRange = new Range(Index.fromOneBased(1), Index.fromOneBased(2));
        assertEquals(expectedRange, ParserUtil.parseDeleteArgument(rangeWithWhitespace));
    }

    @Test
    public void parseDeleteArgument_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDeleteArgument(INVALID_DELETE_ARGUMENT));
    }

    @Test
    public void parseMarkIndexes_validValueWithWhiteSpace_returnsListOfIndex() throws Exception {
        String indexWithWhiteSpace = WHITESPACE + VALID_INDEX + WHITESPACE + WHITESPACE + VALID_TWO_DIGIT_INDEX
                + WHITESPACE + VALID_THREE_DIGIT_INDEX + WHITESPACE;
        List<Index> expectedIndexes = List.of(Index.fromOneBased(1), Index.fromOneBased(11), Index.fromOneBased(111));
        assertEquals(expectedIndexes, ParserUtil.parseMarkIndexes(indexWithWhiteSpace));
    }

    @Test
    public void parseMarkIndexes_invalidValueWithWhiteSpace_returnsListOfIndex() throws Exception {
        assertThrows(ParseException.class, () -> ParserUtil.parseDeleteArgument(INVALID_DELETE_ARGUMENT));
    }

}
