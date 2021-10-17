package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.event.EFindCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new EFindCommand object
 */
public class EFindCommandParser implements Parser<EFindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EFindCommand
     * and returns a EFindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EFindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EFindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new EFindCommand(new EventNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
