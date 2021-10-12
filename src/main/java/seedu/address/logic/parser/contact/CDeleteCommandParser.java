package seedu.address.logic.parser.contact;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.range.Range;
import seedu.address.logic.commands.contact.CDeleteCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CDeleteCommand object
 */
public class CDeleteCommandParser implements Parser<CDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CDeleteCommand
     * and returns a CDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CDeleteCommand parse(String args) throws ParseException {
        try {
            Range range = ParserUtil.parseDeleteArgument(args);
            return new CDeleteCommand(range);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CDeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
