package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.general.CommandTestUtil.ADDRESS_DESC_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.ADDRESS_DESC_TUTORIAL;
import static seedu.address.logic.commands.general.CommandTestUtil.DELETE_TAG_DESC_COOL;
import static seedu.address.logic.commands.general.CommandTestUtil.DELETE_TAG_DESC_EXAMS;
import static seedu.address.logic.commands.general.CommandTestUtil.END_DATE_TIME_DESC_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.END_DATE_TIME_DESC_TUTORIAL;
import static seedu.address.logic.commands.general.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.general.CommandTestUtil.INVALID_END_DATE_TIME_DESC;
import static seedu.address.logic.commands.general.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.general.CommandTestUtil.INVALID_START_DATE_TIME_DESC;
import static seedu.address.logic.commands.general.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.general.CommandTestUtil.INVALID_ZOOM_DESC;
import static seedu.address.logic.commands.general.CommandTestUtil.NAME_DESC_TUTORIAL;
import static seedu.address.logic.commands.general.CommandTestUtil.START_DATE_TIME_DESC_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.START_DATE_TIME_DESC_TUTORIAL;
import static seedu.address.logic.commands.general.CommandTestUtil.TAG_DESC_COOL;
import static seedu.address.logic.commands.general.CommandTestUtil.TAG_DESC_DELETEALL;
import static seedu.address.logic.commands.general.CommandTestUtil.TAG_DESC_EXAMS;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_ADDRESS_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_ADDRESS_TUTORIAL;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_END_DATE_TIME_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_END_DATE_TIME_TUTORIAL;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_NAME_TUTORIAL;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_START_DATE_TIME_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_START_DATE_TIME_TUTORIAL;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_TAG_COOL;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_TAG_EXAMS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.EEditCommand;
import seedu.address.logic.commands.event.EEditCommand.EditEventDescriptor;
import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.common.ZoomLink;
import seedu.address.model.event.EndDateTime;
import seedu.address.model.event.StartDateTime;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditEventDescriptorBuilder;

class EEditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EEditCommand.MESSAGE_USAGE);

    private EEditCommandParser parser = new EEditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_TUTORIAL, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EEditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_TUTORIAL, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_TUTORIAL, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_START_DATE_TIME_DESC, StartDateTime.MESSAGE_CONSTRAINTS);
        // invalid start date time
        assertParseFailure(parser, "1" + INVALID_END_DATE_TIME_DESC, EndDateTime.MESSAGE_CONSTRAINTS);
        // invalid end date time
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "1" + INVALID_ZOOM_DESC, ZoomLink.MESSAGE_CONSTRAINTS); // invalid zoom link
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid start time followed by valid end time
        assertParseFailure(parser, "1" + INVALID_START_DATE_TIME_DESC + END_DATE_TIME_DESC_TUTORIAL,
                StartDateTime.MESSAGE_CONSTRAINTS);

        // valid start time followed by invalid start time. The test case for invalid start time followed by valid
        // start time
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + START_DATE_TIME_DESC_EXAM + INVALID_START_DATE_TIME_DESC,
                StartDateTime.MESSAGE_CONSTRAINTS);

        // parsing {@code PREFIX_TAG} alone will result in error
        assertParseFailure(parser, "1" + TAG_DESC_COOL + TAG_DESC_EXAMS + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_COOL + TAG_EMPTY + TAG_DESC_EXAMS, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_END_DATE_TIME_DESC + VALID_ADDRESS_TUTORIAL
                        + VALID_START_DATE_TIME_TUTORIAL, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + START_DATE_TIME_DESC_TUTORIAL
                + END_DATE_TIME_DESC_TUTORIAL + ADDRESS_DESC_TUTORIAL + NAME_DESC_TUTORIAL + TAG_DESC_COOL;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withName(VALID_NAME_TUTORIAL)
                .withStartDateTime(VALID_START_DATE_TIME_TUTORIAL).withEndDateTime(VALID_END_DATE_TIME_TUTORIAL)
                .withAddress(VALID_ADDRESS_TUTORIAL).withTags(VALID_TAG_COOL).build();
        EEditCommand expectedCommand = new EEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + START_DATE_TIME_DESC_EXAM + END_DATE_TIME_DESC_TUTORIAL;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withStartDateTime(VALID_START_DATE_TIME_EXAM)
                .withEndDateTime(VALID_END_DATE_TIME_TUTORIAL).build();
        EEditCommand expectedCommand = new EEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + NAME_DESC_TUTORIAL;
        EditEventDescriptor descriptor =
                new EditEventDescriptorBuilder().withName(VALID_NAME_TUTORIAL).build();
        EEditCommand expectedCommand = new EEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // start time
        userInput = targetIndex.getOneBased() + START_DATE_TIME_DESC_TUTORIAL;
        descriptor = new EditEventDescriptorBuilder().withStartDateTime(VALID_START_DATE_TIME_TUTORIAL).build();
        expectedCommand = new EEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // end time
        userInput = targetIndex.getOneBased() + END_DATE_TIME_DESC_TUTORIAL;
        descriptor = new EditEventDescriptorBuilder().withEndDateTime(VALID_END_DATE_TIME_TUTORIAL).build();
        expectedCommand = new EEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_TUTORIAL;
        descriptor = new EditEventDescriptorBuilder().withAddress(VALID_ADDRESS_TUTORIAL).build();
        expectedCommand = new EEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_COOL;
        descriptor = new EditEventDescriptorBuilder().withTags(VALID_TAG_COOL).build();
        expectedCommand = new EEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + START_DATE_TIME_DESC_TUTORIAL + ADDRESS_DESC_TUTORIAL
                + END_DATE_TIME_DESC_TUTORIAL + TAG_DESC_COOL + START_DATE_TIME_DESC_TUTORIAL + ADDRESS_DESC_TUTORIAL
                + END_DATE_TIME_DESC_TUTORIAL + TAG_DESC_COOL + START_DATE_TIME_DESC_EXAM + ADDRESS_DESC_EXAM
                + END_DATE_TIME_DESC_EXAM + TAG_DESC_EXAMS;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withStartDateTime(VALID_START_DATE_TIME_EXAM)
                .withEndDateTime(VALID_END_DATE_TIME_EXAM).withAddress(VALID_ADDRESS_EXAM)
                .withTags(VALID_TAG_COOL, VALID_TAG_EXAMS).build();
        EEditCommand expectedCommand = new EEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INVALID_START_DATE_TIME_DESC + START_DATE_TIME_DESC_EXAM;
        EditEventDescriptor descriptor =
                new EditEventDescriptorBuilder().withStartDateTime(VALID_START_DATE_TIME_EXAM).build();
        EEditCommand expectedCommand = new EEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + END_DATE_TIME_DESC_EXAM + INVALID_START_DATE_TIME_DESC
                + ADDRESS_DESC_EXAM + START_DATE_TIME_DESC_EXAM;
        descriptor = new EditEventDescriptorBuilder().withStartDateTime(VALID_START_DATE_TIME_EXAM)
                .withEndDateTime(VALID_END_DATE_TIME_EXAM).withAddress(VALID_ADDRESS_EXAM).build();
        expectedCommand = new EEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + TAG_DESC_DELETEALL;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withDeleteAllTags(true).build();
        EEditCommand expectedCommand = new EEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTagsThenAddTags_success() {
        Index targetIndex = INDEX_THIRD;
        // user types delete all tags before adding new tag
        String userInput = targetIndex.getOneBased() + TAG_DESC_DELETEALL + TAG_DESC_EXAMS;
        // user types add new tag before deleting all tag
        String altUserInput = targetIndex.getOneBased() + TAG_DESC_EXAMS + TAG_DESC_DELETEALL;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withDeleteAllTags(true).withTags(VALID_TAG_EXAMS).build();
        EEditCommand expectedCommand = new EEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
        assertParseSuccess(parser, altUserInput, expectedCommand);
    }

    @Test
    public void parse_deleteTags_success() {
        Index targetIndex = INDEX_SECOND;
        // user deletes cool tag
        String userDeleteOneTag = targetIndex.getOneBased() + DELETE_TAG_DESC_COOL;
        // user deletes cool tag, then deletes exams tag
        String userDeleteTwoTags = targetIndex.getOneBased() + DELETE_TAG_DESC_COOL + DELETE_TAG_DESC_EXAMS;

        EditEventDescriptor descriptorOneTagDeleted = new EditEventDescriptorBuilder()
                .withTagsToDelete(VALID_TAG_COOL).build();
        EEditCommand commandToDeleteOneTag = new EEditCommand(targetIndex, descriptorOneTagDeleted);

        assertParseSuccess(parser, userDeleteOneTag, commandToDeleteOneTag);

        EditEventDescriptor descriptorTwoTagsDeleted = new EditEventDescriptorBuilder()
                .withTagsToDelete(VALID_TAG_COOL, VALID_TAG_EXAMS).build();
        EEditCommand commandToDeleteTwoTags = new EEditCommand(targetIndex, descriptorTwoTagsDeleted);

        assertParseSuccess(parser, userDeleteTwoTags, commandToDeleteTwoTags);
    }
}
