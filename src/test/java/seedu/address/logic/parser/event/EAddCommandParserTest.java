package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.general.CommandTestUtil.ADDRESS_DESC_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.ADDRESS_DESC_TUTORIAL;
import static seedu.address.logic.commands.general.CommandTestUtil.DESCRIPTION_DESC_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.END_DATE_TIME_DESC_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.END_DATE_TIME_DESC_TUTORIAL;
import static seedu.address.logic.commands.general.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.general.CommandTestUtil.INVALID_END_DATE_TIME_DESC;
import static seedu.address.logic.commands.general.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.general.CommandTestUtil.INVALID_START_DATE_TIME_DESC;
import static seedu.address.logic.commands.general.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.general.CommandTestUtil.NAME_DESC_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.NAME_DESC_TUTORIAL;
import static seedu.address.logic.commands.general.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.general.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.general.CommandTestUtil.START_DATE_TIME_DESC_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.START_DATE_TIME_DESC_TUTORIAL;
import static seedu.address.logic.commands.general.CommandTestUtil.TAG_DESC_COOL;
import static seedu.address.logic.commands.general.CommandTestUtil.TAG_DESC_EXAMS;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_ADDRESS_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_END_DATE_TIME_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_NAME_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_START_DATE_TIME_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_TAG_COOL;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_TAG_EXAMS;
import static seedu.address.logic.commands.general.CommandTestUtil.ZOOM_DESC_EXAM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalEvents.EXAM;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.event.EAddCommand;
import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.event.EndDateTime;
import seedu.address.model.event.Event;
import seedu.address.model.event.StartDateTime;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EventBuilder;

class EAddCommandParserTest {
    private EAddCommandParser parser = new EAddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedEvent = new EventBuilder(EXAM).withTags(VALID_TAG_EXAMS).withMarked(false).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_EXAM + START_DATE_TIME_DESC_EXAM
                        + END_DATE_TIME_DESC_EXAM + DESCRIPTION_DESC_EXAM + ADDRESS_DESC_EXAM + ZOOM_DESC_EXAM
                        + TAG_DESC_EXAMS,
                new EAddCommand(expectedEvent));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_TUTORIAL + NAME_DESC_EXAM + START_DATE_TIME_DESC_EXAM
                        + END_DATE_TIME_DESC_EXAM + DESCRIPTION_DESC_EXAM + ADDRESS_DESC_EXAM
                        + ZOOM_DESC_EXAM + TAG_DESC_EXAMS,
                new EAddCommand(expectedEvent));

        // multiple start time - last start time accepted
        assertParseSuccess(parser, NAME_DESC_EXAM + START_DATE_TIME_DESC_TUTORIAL + START_DATE_TIME_DESC_EXAM
                        + END_DATE_TIME_DESC_EXAM + DESCRIPTION_DESC_EXAM + ADDRESS_DESC_EXAM
                        + ZOOM_DESC_EXAM + TAG_DESC_EXAMS,
                new EAddCommand(expectedEvent));

        // multiple end time - last end time accepted
        assertParseSuccess(parser, NAME_DESC_EXAM + START_DATE_TIME_DESC_EXAM + END_DATE_TIME_DESC_TUTORIAL
                        + END_DATE_TIME_DESC_EXAM + DESCRIPTION_DESC_EXAM + ADDRESS_DESC_EXAM
                        + ZOOM_DESC_EXAM + TAG_DESC_EXAMS,
                new EAddCommand(expectedEvent));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_EXAM + START_DATE_TIME_DESC_EXAM + END_DATE_TIME_DESC_EXAM
                        + DESCRIPTION_DESC_EXAM + ADDRESS_DESC_TUTORIAL + ADDRESS_DESC_EXAM
                        + ZOOM_DESC_EXAM + TAG_DESC_EXAMS,
                new EAddCommand(expectedEvent));

        // multiple tags - all accepted
        Event expectedEventMultipleTags = new EventBuilder(EXAM).withTags(VALID_TAG_EXAMS, VALID_TAG_COOL)
                .withMarked(false).build();

        assertParseSuccess(parser, NAME_DESC_EXAM + START_DATE_TIME_DESC_EXAM + END_DATE_TIME_DESC_EXAM
                        + DESCRIPTION_DESC_EXAM + ADDRESS_DESC_EXAM
                        + ZOOM_DESC_EXAM + TAG_DESC_EXAMS + TAG_DESC_COOL,
                new EAddCommand(expectedEventMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Event expectedEvent = new EventBuilder(EXAM).withTags().withMarked(false).build();
        assertParseSuccess(parser, NAME_DESC_EXAM + START_DATE_TIME_DESC_EXAM + END_DATE_TIME_DESC_EXAM
                        + DESCRIPTION_DESC_EXAM + ADDRESS_DESC_EXAM + ZOOM_DESC_EXAM,
                new EAddCommand(expectedEvent));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EAddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_EXAM + START_DATE_TIME_DESC_EXAM + END_DATE_TIME_DESC_EXAM
                        + DESCRIPTION_DESC_EXAM + ADDRESS_DESC_EXAM + ZOOM_DESC_EXAM,
                expectedMessage);

        // missing start time prefix
        assertParseFailure(parser, NAME_DESC_EXAM + VALID_START_DATE_TIME_EXAM + END_DATE_TIME_DESC_EXAM
                        + DESCRIPTION_DESC_EXAM + ADDRESS_DESC_EXAM + ZOOM_DESC_EXAM,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_EXAM + VALID_START_DATE_TIME_EXAM + VALID_END_DATE_TIME_EXAM
                        + VALID_ADDRESS_EXAM + ZOOM_DESC_EXAM,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + START_DATE_TIME_DESC_EXAM + END_DATE_TIME_DESC_EXAM
                + ADDRESS_DESC_EXAM + TAG_DESC_EXAMS + TAG_DESC_COOL, Name.MESSAGE_CONSTRAINTS);

        // invalid start time
        assertParseFailure(parser, NAME_DESC_EXAM + INVALID_START_DATE_TIME_DESC + END_DATE_TIME_DESC_EXAM
                + ADDRESS_DESC_EXAM + TAG_DESC_EXAMS + TAG_DESC_COOL, StartDateTime.MESSAGE_CONSTRAINTS);

        // invalid end time
        assertParseFailure(parser, NAME_DESC_EXAM + START_DATE_TIME_DESC_EXAM + INVALID_END_DATE_TIME_DESC
                + ADDRESS_DESC_EXAM + TAG_DESC_EXAMS + TAG_DESC_COOL, EndDateTime.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_EXAM + START_DATE_TIME_DESC_EXAM + END_DATE_TIME_DESC_EXAM
                + DESCRIPTION_DESC_EXAM + INVALID_ADDRESS_DESC + TAG_DESC_EXAMS + TAG_DESC_COOL,
                Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_EXAM + START_DATE_TIME_DESC_EXAM + END_DATE_TIME_DESC_EXAM
                + ADDRESS_DESC_EXAM + INVALID_TAG_DESC + VALID_TAG_EXAMS, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + START_DATE_TIME_DESC_EXAM + END_DATE_TIME_DESC_EXAM
                        + INVALID_ADDRESS_DESC, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_EXAM + START_DATE_TIME_DESC_EXAM
                        + END_DATE_TIME_DESC_EXAM + ADDRESS_DESC_EXAM + TAG_DESC_EXAMS + TAG_DESC_COOL,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EAddCommand.MESSAGE_USAGE));
    }
}
