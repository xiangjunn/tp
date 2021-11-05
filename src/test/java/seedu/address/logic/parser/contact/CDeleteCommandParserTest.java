package seedu.address.logic.parser.contact;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.range.Range;
import seedu.address.logic.commands.contact.CDeleteCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the CDeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the CDeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class CDeleteCommandParserTest {

    private CDeleteCommandParser parser = new CDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsCDeleteCommand() {
        Range rangeOfIndexes = Range.convertFromIndex(INDEX_FIRST);
        assertParseSuccess(parser, "1", new CDeleteCommand(rangeOfIndexes));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, CDeleteCommand.MESSAGE_USAGE));
    }
}
