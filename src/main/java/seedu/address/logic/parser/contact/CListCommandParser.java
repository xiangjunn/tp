package seedu.address.logic.parser.contact;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ZOOM;

import java.util.stream.Stream;

import seedu.address.logic.commands.contact.CListCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Contact;

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
        if (noPrefixesPresent(argMultimap)) {
            Contact.setAllDisplayToTrue();
        } else {
            Contact.setAllDisplayToFalse();
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            Contact.setWillDisplayPhone(true);
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            Contact.setWillDisplayEmail(true);
        }
        if (argMultimap.getValue(PREFIX_TELEGRAM).isPresent()) {
            Contact.setWillDisplayTelegramHandle(true);
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            Contact.setWillDisplayAddress(true);
        }
        if (argMultimap.getValue(PREFIX_ZOOM).isPresent()) {
            Contact.setWillDisplayZoomLink(true);
        }
        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            Contact.setWillDisplayTags(true);
        }
        return new CListCommand();
    }

    /**
     * Returns true if no prefixes are present in the argument for the command.
     */
    private boolean noPrefixesPresent(ArgumentMultimap argumentMultimap) {
        return Stream.of(PREFIX_PHONE, PREFIX_EMAIL,
            PREFIX_TELEGRAM, PREFIX_ADDRESS, PREFIX_ZOOM, PREFIX_TAG)
            .allMatch(prefix -> argumentMultimap.getValue(prefix).isEmpty());
    }
}
