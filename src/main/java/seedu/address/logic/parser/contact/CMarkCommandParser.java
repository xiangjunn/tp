package seedu.address.logic.parser.contact;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.contact.CMarkCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

public class CMarkCommandParser implements Parser<CMarkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the CMarkCommand
     * and returns a CMarkCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public CMarkCommand parse(String args) throws ParseException {
        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CMarkCommand.MESSAGE_USAGE));
        }
        try {
            List<Index> indexes = ParserUtil.parseMarkIndexes(args);
            return new CMarkCommand(indexes);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(pe.getMessage(), CMarkCommand.MESSAGE_USAGE), pe);
        }
    }
}
