package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.general.CommandTestUtil.EMPTY_PREFIX_ADDRESS;
import static seedu.address.logic.commands.general.CommandTestUtil.EMPTY_PREFIX_DESCRIPTION;
import static seedu.address.logic.commands.general.CommandTestUtil.EMPTY_PREFIX_END_DATE_TIME;
import static seedu.address.logic.commands.general.CommandTestUtil.EMPTY_PREFIX_START_DATE_TIME;
import static seedu.address.logic.commands.general.CommandTestUtil.EMPTY_PREFIX_TAG;
import static seedu.address.logic.commands.general.CommandTestUtil.EMPTY_PREFIX_ZOOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ZOOM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.event.EventDisplaySetting.DEFAULT_SETTING;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.event.EListCommand;
import seedu.address.model.event.EventDisplaySetting;

public class EListCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT = String.format(
        MESSAGE_INVALID_COMMAND_FORMAT,
        EListCommand.MESSAGE_USAGE);

    private final EListCommandParser parser = new EListCommandParser();

    @Test
    public void parse_invalidArguments_failure() {
        // Preamble provided
        assertParseFailure(parser, "Hello", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "Hello " + EMPTY_PREFIX_ADDRESS, MESSAGE_INVALID_FORMAT);

        // Invalid parameters
        assertParseFailure(parser, "g/", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "g/ " + PREFIX_ZOOM, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, PREFIX_ZOOM + "g/ ", MESSAGE_INVALID_FORMAT);

        // Additional arguments after prefix
        assertParseFailure(parser, EMPTY_PREFIX_START_DATE_TIME + "123", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, EMPTY_PREFIX_END_DATE_TIME + "123", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, EMPTY_PREFIX_ADDRESS + "123", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, EMPTY_PREFIX_DESCRIPTION + "123", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, EMPTY_PREFIX_ZOOM + "123", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, EMPTY_PREFIX_TAG + "123", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, EMPTY_PREFIX_ZOOM + " " + PREFIX_ADDRESS + "123", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_noArguments_success() {
        assertParseSuccess(parser, "", new EListCommand(DEFAULT_SETTING));
    }

    @Test
    public void parse_validArguments_success() {

        // only display start time
        EventDisplaySetting displaySetting1 = new EventDisplaySetting(true, false, false, false, false, false);
        assertParseSuccess(parser, EMPTY_PREFIX_START_DATE_TIME, new EListCommand(displaySetting1));

        // only display end time
        EventDisplaySetting displaySetting2 = new EventDisplaySetting(false, true, false, false, false, false);
        assertParseSuccess(parser, EMPTY_PREFIX_END_DATE_TIME, new EListCommand(displaySetting2));

        // only display address
        EventDisplaySetting displaySetting3 = new EventDisplaySetting(false, false, false, true, false, false);
        assertParseSuccess(parser, EMPTY_PREFIX_ADDRESS, new EListCommand(displaySetting3));

        //only display description
        EventDisplaySetting displaySetting4 = new EventDisplaySetting(false, false, true, false, false, false);
        assertParseSuccess(parser, EMPTY_PREFIX_DESCRIPTION, new EListCommand(displaySetting4));

        //only display zoom link
        EventDisplaySetting displaySetting5 = new EventDisplaySetting(false, false, false, false, true, false);
        assertParseSuccess(parser, EMPTY_PREFIX_ZOOM, new EListCommand(displaySetting5));

        //only display tags
        EventDisplaySetting displaySetting6 = new EventDisplaySetting(false, false, false, false, false, true);
        assertParseSuccess(parser, EMPTY_PREFIX_TAG, new EListCommand(displaySetting6));

        // display multiple fields
        EventDisplaySetting displaySetting7 = new EventDisplaySetting(true, false, false, false, false, true);
        assertParseSuccess(parser, EMPTY_PREFIX_TAG + EMPTY_PREFIX_START_DATE_TIME,
            new EListCommand(displaySetting7));
    }
}
