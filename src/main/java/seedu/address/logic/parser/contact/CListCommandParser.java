package seedu.address.logic.parser.contact;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ZOOM;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.logic.commands.contact.CListCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
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
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CListCommand.MESSAGE_USAGE));
        }
        if (noPrefixesPresent(argMultimap)) {
            Contact.setAllDisplayToTrue();
        } else {
            Contact.setAllDisplayToFalse();
        }
        if (prefixPresent(PREFIX_PHONE, argMultimap)) {
            Contact.setWillDisplayPhone(true);
        }
        if (prefixPresent(PREFIX_EMAIL, argMultimap)) {
            Contact.setWillDisplayEmail(true);
        }
        if (prefixPresent(PREFIX_TELEGRAM, argMultimap)) {
            Contact.setWillDisplayTelegramHandle(true);
        }
        if (prefixPresent(PREFIX_ADDRESS, argMultimap)) {
            Contact.setWillDisplayAddress(true);
        }
        if (prefixPresent(PREFIX_ZOOM, argMultimap)) {
            Contact.setWillDisplayZoomLink(true);
        }
        if (prefixPresent(PREFIX_TAG, argMultimap)) {
            Contact.setWillDisplayTags(true);
        }
        Contact.setViewingMode(false);
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

    /**
     * Returns true if the {@code prefix} is present in the {@code multimap}.
     * Throws a {@code ParseException} if there is an argument after the prefix.
     */
    private boolean prefixPresent(Prefix prefix, ArgumentMultimap multimap) throws ParseException {
        Optional<String> value = multimap.getValue(prefix);
        if (value.isPresent()) {
            if (!value.get().isBlank()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CListCommand.MESSAGE_USAGE));
            }
            return true;
        }
        return false;
    }
}
