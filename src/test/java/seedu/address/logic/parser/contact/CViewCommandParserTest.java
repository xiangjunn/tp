package seedu.address.logic.parser.contact;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.contact.CViewCommand;

public class CViewCommandParserTest {

    private CViewCommandParser parser = new CViewCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsViewCommand() {
        // no leading and trailing whitespaces and only one valid index
        CViewCommand expectedCViewCommand =
                new CViewCommand(INDEX_FIRST);
        assertParseSuccess(parser, "1", expectedCViewCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // multiple indexes
        assertParseFailure(parser, "1 2 3",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CViewCommand.MESSAGE_USAGE));

        //no index specified
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CViewCommand.MESSAGE_USAGE));

        //zero index
        assertParseFailure(parser, "0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CViewCommand.MESSAGE_USAGE));

        //negative index
        assertParseFailure(parser, "-10",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CViewCommand.MESSAGE_USAGE));

    }
}
