package seedu.address.logic.parser.contact;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ZOOM;

import seedu.address.logic.commands.contact.CListCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.ContactDisplaySetting;

public class CListCommandParser implements Parser<CListCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the CListCommand
     * and returns a CListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_TELEGRAM, PREFIX_ADDRESS, PREFIX_ZOOM, PREFIX_TAG);
        if (argMultimap.anyPrefixValueNotEmpty() || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CListCommand.MESSAGE_USAGE));
        }
        ContactDisplaySetting displaySetting;
        if (argMultimap.noPrefixesPresent()) {
            displaySetting = ContactDisplaySetting.DEFAULT_SETTING;
        } else {
            displaySetting = new ContactDisplaySetting(
                argMultimap.getValue(PREFIX_PHONE).isPresent(),
                argMultimap.getValue(PREFIX_EMAIL).isPresent(),
                argMultimap.getValue(PREFIX_TELEGRAM).isPresent(),
                argMultimap.getValue(PREFIX_ADDRESS).isPresent(),
                argMultimap.getValue(PREFIX_ZOOM).isPresent(),
                argMultimap.getValue(PREFIX_TAG).isPresent()
            );
        }
        return new CListCommand(displaySetting);
    }
}
