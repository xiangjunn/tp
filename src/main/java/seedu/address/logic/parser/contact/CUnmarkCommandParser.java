package seedu.address.logic.parser.contact;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.contact.CUnmarkCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

public class CUnmarkCommandParser implements Parser<CUnmarkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the CUnmarkCommand
     * and returns a CUnmarkCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public CUnmarkCommand parse(String args) throws ParseException {
        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CUnmarkCommand.MESSAGE_USAGE));
        }
        try {
            List<Index> indexes = ParserUtil.parseMarkIndexes(args);
            return new CUnmarkCommand(indexes);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(pe.getMessage(), CUnmarkCommand.MESSAGE_USAGE), pe);
        }
    }
}
