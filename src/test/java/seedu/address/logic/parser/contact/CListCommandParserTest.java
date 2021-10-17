package seedu.address.logic.parser.contact;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.general.CommandTestUtil.EMPTY_PREFIX_ADDRESS;
import static seedu.address.logic.commands.general.CommandTestUtil.EMPTY_PREFIX_EMAIL;
import static seedu.address.logic.commands.general.CommandTestUtil.EMPTY_PREFIX_PHONE;
import static seedu.address.logic.commands.general.CommandTestUtil.EMPTY_PREFIX_TAG;
import static seedu.address.logic.commands.general.CommandTestUtil.EMPTY_PREFIX_TELEGRAM;
import static seedu.address.logic.commands.general.CommandTestUtil.EMPTY_PREFIX_ZOOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ZOOM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.contact.CListCommand;
import seedu.address.model.contact.Contact;

public class CListCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT = String.format(
        MESSAGE_INVALID_COMMAND_FORMAT,
        CListCommand.MESSAGE_USAGE);
    private static final CListCommand C_LIST_COMMAND = new CListCommand(); // same for all

    private final CListCommandParser parser = new CListCommandParser();

    static {
        Contact.setAllDisplayToTrue();
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
        assertParseFailure(parser, EMPTY_PREFIX_PHONE + "123", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, EMPTY_PREFIX_EMAIL + "123", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, EMPTY_PREFIX_ADDRESS + "123", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, EMPTY_PREFIX_TELEGRAM + "123", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, EMPTY_PREFIX_ZOOM + "123", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, EMPTY_PREFIX_TAG + "123", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, EMPTY_PREFIX_ZOOM + " " + PREFIX_ADDRESS + "123", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_noArguments_success() {
        assertParseSuccess(parser, "", C_LIST_COMMAND);
        assertTrue(Contact.isWillDisplayAddress()
            && Contact.isWillDisplayEmail()
            && Contact.isWillDisplayPhone()
            && Contact.isWillDisplayTelegramHandle()
            && Contact.isWillDisplayZoomLink()
            && Contact.isWillDisplayTags());
    }

    @Test
    public void parse_validArguments_success() {
        assertParseSuccess(parser, EMPTY_PREFIX_PHONE, C_LIST_COMMAND);
        assertTrue(!Contact.isWillDisplayAddress()
            && !Contact.isWillDisplayEmail()
            && Contact.isWillDisplayPhone()
            && !Contact.isWillDisplayTelegramHandle()
            && !Contact.isWillDisplayZoomLink()
            && !Contact.isWillDisplayTags());
        assertParseSuccess(parser, EMPTY_PREFIX_EMAIL, C_LIST_COMMAND);
        assertTrue(!Contact.isWillDisplayAddress()
            && Contact.isWillDisplayEmail()
            && !Contact.isWillDisplayPhone()
            && !Contact.isWillDisplayTelegramHandle()
            && !Contact.isWillDisplayZoomLink()
            && !Contact.isWillDisplayTags());
        assertParseSuccess(parser, EMPTY_PREFIX_ADDRESS, C_LIST_COMMAND);
        assertTrue(Contact.isWillDisplayAddress()
            && !Contact.isWillDisplayEmail()
            && !Contact.isWillDisplayPhone()
            && !Contact.isWillDisplayTelegramHandle()
            && !Contact.isWillDisplayZoomLink()
            && !Contact.isWillDisplayTags());
        assertParseSuccess(parser, EMPTY_PREFIX_TELEGRAM, C_LIST_COMMAND);
        assertTrue(!Contact.isWillDisplayAddress()
            && !Contact.isWillDisplayEmail()
            && !Contact.isWillDisplayPhone()
            && Contact.isWillDisplayTelegramHandle()
            && !Contact.isWillDisplayZoomLink()
            && !Contact.isWillDisplayTags());
        assertParseSuccess(parser, EMPTY_PREFIX_ZOOM, C_LIST_COMMAND);
        assertTrue(!Contact.isWillDisplayAddress()
            && !Contact.isWillDisplayEmail()
            && !Contact.isWillDisplayPhone()
            && !Contact.isWillDisplayTelegramHandle()
            && Contact.isWillDisplayZoomLink()
            && !Contact.isWillDisplayTags());
        assertParseSuccess(parser, EMPTY_PREFIX_TAG, C_LIST_COMMAND);
        assertTrue(!Contact.isWillDisplayAddress()
            && !Contact.isWillDisplayEmail()
            && !Contact.isWillDisplayPhone()
            && !Contact.isWillDisplayTelegramHandle()
            && !Contact.isWillDisplayZoomLink()
            && Contact.isWillDisplayTags());

        // Multiple filters
        assertParseSuccess(parser, EMPTY_PREFIX_TAG + EMPTY_PREFIX_PHONE, C_LIST_COMMAND);
        assertTrue(!Contact.isWillDisplayAddress()
            && !Contact.isWillDisplayEmail()
            && Contact.isWillDisplayPhone()
            && !Contact.isWillDisplayTelegramHandle()
            && !Contact.isWillDisplayZoomLink()
            && Contact.isWillDisplayTags());
    }
}
