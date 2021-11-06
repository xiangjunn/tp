package seedu.address.logic.parser.contact;

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
import static seedu.address.model.contact.ContactDisplaySetting.DEFAULT_SETTING;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.contact.CListCommand;
import seedu.address.model.contact.ContactDisplaySetting;

public class CListCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT = String.format(
        MESSAGE_INVALID_COMMAND_FORMAT,
        CListCommand.MESSAGE_USAGE);

    private final CListCommandParser parser = new CListCommandParser();

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
        assertParseSuccess(parser, "", new CListCommand(DEFAULT_SETTING));
    }

    @Test
    public void parse_validArguments_success() {
        // Only display phone
        ContactDisplaySetting c1 = new ContactDisplaySetting(true, false, false, false, false, false);
        assertParseSuccess(parser, EMPTY_PREFIX_PHONE, new CListCommand(c1));
        // Only display email
        ContactDisplaySetting c2 = new ContactDisplaySetting(false, true, false, false, false, false);
        assertParseSuccess(parser, EMPTY_PREFIX_EMAIL, new CListCommand(c2));
        // Only display address
        ContactDisplaySetting c3 = new ContactDisplaySetting(false, false, false, true, false, false);
        assertParseSuccess(parser, EMPTY_PREFIX_ADDRESS, new CListCommand(c3));
        // Only display telegram
        ContactDisplaySetting c4 = new ContactDisplaySetting(false, false, true, false, false, false);
        assertParseSuccess(parser, EMPTY_PREFIX_TELEGRAM, new CListCommand(c4));
        // Only display zoom
        ContactDisplaySetting c5 = new ContactDisplaySetting(false, false, false, false, true, false);
        assertParseSuccess(parser, EMPTY_PREFIX_ZOOM, new CListCommand(c5));
        // Only display tags
        ContactDisplaySetting c6 = new ContactDisplaySetting(false, false, false, false, false, true);
        assertParseSuccess(parser, EMPTY_PREFIX_TAG, new CListCommand(c6));

        // Multiple filters
        ContactDisplaySetting c7 = new ContactDisplaySetting(true, false, false, false, false, true);
        assertParseSuccess(parser, EMPTY_PREFIX_TAG + EMPTY_PREFIX_PHONE, new CListCommand(c7));
    }
}
