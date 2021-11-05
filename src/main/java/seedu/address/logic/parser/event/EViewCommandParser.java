package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.EViewCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

public class EViewCommandParser implements Parser<EViewCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EViewCommand
     * and returns an EViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public EViewCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new EViewCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EViewCommand.MESSAGE_USAGE), pe);
        }
    }
}
