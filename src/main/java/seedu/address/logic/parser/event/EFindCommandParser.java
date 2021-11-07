package seedu.address.logic.parser.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ZOOM;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.event.EFindCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new EFindCommand object
 */
public class EFindCommandParser implements Parser<EFindCommand> {

    public static final String MESSAGE_MISSING_KEYWORD = "There must be at least one keyword present for prefix '%s'.";
    private EventContainsKeywordsPredicate predicate;

    /**
     * Parses the given {@code String} of arguments in the context of the EFindCommand
     * and returns a EFindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EFindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_START_TIME, PREFIX_END_TIME,
                        PREFIX_DESCRIPTION, PREFIX_ADDRESS, PREFIX_ZOOM, PREFIX_TAG);

        if (noPrefixesPresent(argMultimap)
                && argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EFindCommand.MESSAGE_USAGE));
        }

        predicate = new EventContainsKeywordsPredicate();
        if (!argMultimap.getPreamble().isEmpty()) {
            String[] nameKeywords = argMultimap.getPreamble().trim().split("\\s+");
            predicate = new EventContainsKeywordsPredicate(Arrays.asList(nameKeywords));
        }
        if (isPrefixValuePresent(argMultimap, PREFIX_START_TIME)) {
            predicate.setStartDateTimeKeywords(getPrefixValueAndSplit(argMultimap, PREFIX_START_TIME));
        }
        if (isPrefixValuePresent(argMultimap, PREFIX_END_TIME)) {
            predicate.setEndDateTimeKeywords(getPrefixValueAndSplit(argMultimap, PREFIX_END_TIME));
        }
        if (isPrefixValuePresent(argMultimap, PREFIX_ADDRESS)) {
            predicate.setAddressKeywords(getPrefixValueAndSplit(argMultimap, PREFIX_ADDRESS));
        }
        if (isPrefixValuePresent(argMultimap, PREFIX_DESCRIPTION)) {
            predicate.setDescriptionKeywords(getPrefixValueAndSplit(argMultimap, PREFIX_DESCRIPTION));
        }
        if (isPrefixValuePresent(argMultimap, PREFIX_ZOOM)) {
            predicate.setZoomLinkKeywords(getPrefixValueAndSplit(argMultimap, PREFIX_ZOOM));
        }
        if (isPrefixValuePresent(argMultimap, PREFIX_TAG)) {
            predicate.setTagKeywords(getPrefixValueAndSplit(argMultimap, PREFIX_TAG));
        }
        return new EFindCommand(predicate);
    }
    /**
     * Returns true if none of the prefixes contains present {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean noPrefixesPresent(ArgumentMultimap argumentMultimap) {
        return Stream.of(PREFIX_START_TIME, PREFIX_END_TIME,
                PREFIX_ADDRESS, PREFIX_DESCRIPTION, PREFIX_ZOOM, PREFIX_TAG)
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
