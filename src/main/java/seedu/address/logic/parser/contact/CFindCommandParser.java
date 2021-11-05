package seedu.address.logic.parser.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ZOOM;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.contact.CFindCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.ContactContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new CFindCommand object
 */
public class CFindCommandParser implements Parser<CFindCommand> {

    public static final String MESSAGE_MISSING_KEYWORD = "There must be at least one keyword present for prefix '%s'.";
    private ContactContainsKeywordsPredicate predicate;

    /**
     * Parses the given {@code String} of arguments in the context of the CFindCommand
     * and returns a CFindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CFindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_TELEGRAM, PREFIX_ADDRESS, PREFIX_ZOOM, PREFIX_TAG);

        if (noPrefixesPresent(argMultimap)
                && argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CFindCommand.MESSAGE_USAGE));
        }

        predicate = new ContactContainsKeywordsPredicate();
        if (!argMultimap.getPreamble().isEmpty()) {
            String[] nameKeywords = argMultimap.getPreamble().trim().split("\\s+");
            predicate = new ContactContainsKeywordsPredicate(Arrays.asList(nameKeywords));
        }
        if (isPrefixValuePresent(argMultimap, PREFIX_PHONE)) {
            predicate.setPhoneKeywords(getPrefixValueAndSplit(argMultimap, PREFIX_PHONE));
        }
        if (isPrefixValuePresent(argMultimap, PREFIX_EMAIL)) {
            predicate.setEmailKeywords(getPrefixValueAndSplit(argMultimap, PREFIX_EMAIL));
        }
        if (isPrefixValuePresent(argMultimap, PREFIX_ADDRESS)) {
            predicate.setAddressKeywords(getPrefixValueAndSplit(argMultimap, PREFIX_ADDRESS));
        }
        if (isPrefixValuePresent(argMultimap, PREFIX_TELEGRAM)) {
            predicate.setTelegramHandleKeyword(getPrefixValueAndSplit(argMultimap, PREFIX_TELEGRAM));
        }
        if (isPrefixValuePresent(argMultimap, PREFIX_ZOOM)) {
            predicate.setZoomLinkKeywords(getPrefixValueAndSplit(argMultimap, PREFIX_ZOOM));
        }
        if (isPrefixValuePresent(argMultimap, PREFIX_TAG)) {
            predicate.setTagKeywords(getPrefixValueAndSplit(argMultimap, PREFIX_TAG));
        }
        return new CFindCommand(predicate);
    }

    /**
     * Returns true if none of the prefixes contains present {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean noPrefixesPresent(ArgumentMultimap argumentMultimap) {
        return Stream.of(PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_TELEGRAM, PREFIX_ADDRESS, PREFIX_ZOOM, PREFIX_TAG)
                .allMatch(prefix -> argumentMultimap.getValue(prefix).isEmpty());
    }

    /**
     * Returns true if the given prefix value is present
     *
     * @param prefix the given prefix
     * @return true if the value of the prefix is not empty, false otherwise
     */
    private static boolean isPrefixValuePresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return argumentMultimap.getValue(prefix).isPresent();
    }

    private static List<String> getPrefixValueAndSplit(ArgumentMultimap argumentMultimap, Prefix prefix)
        throws ParseException {
        requireNonNull(argumentMultimap.getValue(prefix)); //the prefix must not contain an empty value
        String value = argumentMultimap.getValue(prefix).get();
        if (value.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_MISSING_KEYWORD, prefix));
        }
        return Stream.of(value.split("\\s+")).collect(Collectors.toList());
    }

}
