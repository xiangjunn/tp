package seedu.address.logic.parser.event;

import static org.junit.jupiter.api.Assertions.assertTrue;
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

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.event.EListCommand;
import seedu.address.model.event.Event;

public class EListCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT = String.format(
        MESSAGE_INVALID_COMMAND_FORMAT,
        EListCommand.MESSAGE_USAGE);
    private static final EListCommand E_LIST_COMMAND = new EListCommand(); // same for all

    private final EListCommandParser parser = new EListCommandParser();

    static {
        Event.setAllDisplayToTrue();
    }

    @Test
    public void parse_invalidArguments_failure() {
        // Preamble provided
        assertParseFailure(parser, "Hello", MESSAGE_INVALID_FORMAT);

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
        assertParseSuccess(parser, "", E_LIST_COMMAND);
        assertTrue(Event.isWillDisplayStartDateTime()
            && Event.isWillDisplayEndDateTime()
            && Event.isWillDisplayDescription()
            && Event.isWillDisplayAddress()
            && Event.isWillDisplayZoomLink()
            && Event.isWillDisplayTags());
    }

    @Test
    public void parse_validArguments_success() {

        // only display start time
        assertParseSuccess(parser, EMPTY_PREFIX_START_DATE_TIME, E_LIST_COMMAND);
        assertTrue(Event.isWillDisplayStartDateTime()
            && !Event.isWillDisplayAddress()
            && !Event.isWillDisplayEndDateTime()
            && !Event.isWillDisplayDescription()
            && !Event.isWillDisplayZoomLink()
            && !Event.isWillDisplayTags());

        // only display end time
        assertParseSuccess(parser, EMPTY_PREFIX_END_DATE_TIME, E_LIST_COMMAND);
        assertTrue(!Event.isWillDisplayStartDateTime()
            && Event.isWillDisplayEndDateTime()
            && !Event.isWillDisplayDescription()
            && !Event.isWillDisplayAddress()
            && !Event.isWillDisplayZoomLink()
            && !Event.isWillDisplayTags());

        // only display address
        assertParseSuccess(parser, EMPTY_PREFIX_ADDRESS, E_LIST_COMMAND);
        assertTrue(!Event.isWillDisplayEndDateTime()
            && !Event.isWillDisplayStartDateTime()
            && !Event.isWillDisplayDescription()
            && Event.isWillDisplayAddress()
            && !Event.isWillDisplayZoomLink()
            && !Event.isWillDisplayTags());

        //only display description
        assertParseSuccess(parser, EMPTY_PREFIX_DESCRIPTION, E_LIST_COMMAND);
        assertTrue(!Event.isWillDisplayStartDateTime()
            && !Event.isWillDisplayEndDateTime()
            && Event.isWillDisplayDescription()
            && !Event.isWillDisplayAddress()
            && !Event.isWillDisplayZoomLink()
            && !Event.isWillDisplayTags());

        //only display zoom link
        assertParseSuccess(parser, EMPTY_PREFIX_ZOOM, E_LIST_COMMAND);
        assertTrue(!Event.isWillDisplayStartDateTime()
            && !Event.isWillDisplayEndDateTime()
            && !Event.isWillDisplayDescription()
            && !Event.isWillDisplayAddress()
            && Event.isWillDisplayZoomLink()
            && !Event.isWillDisplayTags());

        //only display tags
        assertParseSuccess(parser, EMPTY_PREFIX_TAG, E_LIST_COMMAND);
        assertTrue(!Event.isWillDisplayAddress()
            && !Event.isWillDisplayEndDateTime()
            && !Event.isWillDisplayStartDateTime()
            && !Event.isWillDisplayDescription()
            && !Event.isWillDisplayZoomLink()
            && Event.isWillDisplayTags());

        // display multiple fields
        assertParseSuccess(parser, EMPTY_PREFIX_TAG + EMPTY_PREFIX_START_DATE_TIME, E_LIST_COMMAND);
        assertTrue(Event.isWillDisplayStartDateTime()
            && !Event.isWillDisplayEndDateTime()
            && !Event.isWillDisplayDescription()
            && !Event.isWillDisplayAddress()
            && !Event.isWillDisplayZoomLink()
            && Event.isWillDisplayTags());
    }
}
