package seedu.address.logic.parser.contact;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.contact.CBookmarkCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

public class CBookmarkCommandParser implements Parser<CBookmarkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the CBookmarkCommand
     * and returns a CBookmarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CBookmarkCommand parse(String args) throws ParseException {
        try {
            List<Index> indexes = ParserUtil.parseBookmarkIndexes(args);
            return new CBookmarkCommand(indexes);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CBookmarkCommand.MESSAGE_USAGE), pe);
        }
    }
}
