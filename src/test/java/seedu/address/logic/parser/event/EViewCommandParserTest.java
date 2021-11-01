package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.event.EViewCommand;

public class EViewCommandParserTest {
    private EViewCommandParser parser = new EViewCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsViewCommand() {
        // no leading and trailing whitespaces and only one valid index
        EViewCommand expectedEViewCommand =
                new EViewCommand(INDEX_FIRST);
        assertParseSuccess(parser, "1", expectedEViewCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // multiple indexes
        assertParseFailure(parser, "1 2 3",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EViewCommand.MESSAGE_USAGE));

        //no index specified
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EViewCommand.MESSAGE_USAGE));

        //zero index
        assertParseFailure(parser, "0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EViewCommand.MESSAGE_USAGE));

        //negative index
        assertParseFailure(parser, "-10",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EViewCommand.MESSAGE_USAGE));

    }
}
