package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.range.Range;
import seedu.address.logic.commands.event.EDeleteCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EDeleteCommand object
 */
public class EDeleteCommandParser implements Parser<EDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EDeleteCommand
     * and returns a EDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EDeleteCommand parse(String args) throws ParseException {
        try {
            Range range = ParserUtil.parseDeleteArgument(args);
            return new EDeleteCommand(range);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EDeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
