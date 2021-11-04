package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.EBookmarkCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

public class EBookmarkCommandParser implements Parser<EBookmarkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EBookmarkCommand
     * and returns a EBookmarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EBookmarkCommand parse(String args) throws ParseException {
        try {
            List<Index> indexes = ParserUtil.parseBookmarkIndexes(args);
            return new EBookmarkCommand(indexes);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EBookmarkCommand.MESSAGE_USAGE), pe);
        }
    }
}
