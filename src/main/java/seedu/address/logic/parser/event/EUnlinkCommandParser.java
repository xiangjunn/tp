package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.EUnlinkCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EUnlinkCommand object
 */
public class EUnlinkCommandParser implements Parser<EUnlinkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EUnlinkCommand
     * and returns an EUnlinkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EUnlinkCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CONTACT);
        if (!arePrefixesPresent(argMultimap, PREFIX_CONTACT)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EUnlinkCommand.MESSAGE_USAGE));
        }
        String index = argMultimap.getPreamble();
        try {
            Index eventIndex = ParserUtil.parseIndex(index);
            List<String> listOfValues = argMultimap.getAllValues(PREFIX_CONTACT);
            Set<Index> contactIndexes = new HashSet<>();
            for (String value : listOfValues) {
                if (value.equals("*")) { // delete all links
                    return new EUnlinkCommand(eventIndex, Set.of(), true);
                }
                contactIndexes.add(ParserUtil.parseIndex(value));
            }
            return new EUnlinkCommand(eventIndex, contactIndexes, false);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EUnlinkCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
