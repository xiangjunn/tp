package seedu.address.logic.parser.contact;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.contact.CFindCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new CFindCommand object
 */
public class CFindCommandParser implements Parser<CFindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CFindCommand
     * and returns a CFindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CFindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CFindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        Contact.setViewingMode(false);
        return new CFindCommand(new ContactNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
