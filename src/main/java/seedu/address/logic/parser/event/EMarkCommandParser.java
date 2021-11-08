package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.EMarkCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

public class EMarkCommandParser implements Parser<EMarkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EMarkCommand
     * and returns a EMarkCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EMarkCommand parse(String args) throws ParseException {
        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EMarkCommand.MESSAGE_USAGE));
        }
        List<Index> indexes = ParserUtil.parseMarkIndexes(args);
        return new EMarkCommand(indexes);
    }
}
