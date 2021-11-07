package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.EUnmarkCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

public class EUnmarkCommandParser implements Parser<EUnmarkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EUnmarkCommand
     * and returns a EUnmarkCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EUnmarkCommand parse(String args) throws ParseException {
        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EUnmarkCommand.MESSAGE_USAGE));
        }
        try {
            List<Index> indexes = ParserUtil.parseMarkIndexes(args);
            return new EUnmarkCommand(indexes);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(pe.getMessage(), EUnmarkCommand.MESSAGE_USAGE), pe);
        }
    }
}
