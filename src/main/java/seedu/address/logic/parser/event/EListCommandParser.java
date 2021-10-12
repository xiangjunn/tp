package seedu.address.logic.parser.event;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ZOOM;

import java.util.stream.Stream;

import seedu.address.logic.commands.event.EListCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;

/**
 * Parses input arguments and creates a new EListCommand object
 */
public class EListCommandParser implements Parser<EListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EListCommand
     * and returns an EListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_END_TIME, PREFIX_DESCRIPTION,
                        PREFIX_ADDRESS, PREFIX_ZOOM, PREFIX_TAG);
        if (noPrefixesPresent(argMultimap)) {
            Event.setAllFieldsToTrue();
        } else {
            Event.setAllFieldsToFalse();
        }

        if (argMultimap.getValue(PREFIX_END_TIME).isPresent()) {
            Event.setWillDisplayEndDateTime(true);
        }

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            Event.setWillDisplayDescription(true);
        }

        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            Event.setWillDisplayAddress(true);
        }

        if (argMultimap.getValue(PREFIX_ZOOM).isPresent()) {
            Event.setWillDisplayZoomLink(true);
        }

        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            Event.setWillDisplayTags(true);
        }

        return new EListCommand();

    }

    /**
     * Returns true if none of the prefixes contains present {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean noPrefixesPresent(ArgumentMultimap argumentMultimap) {
        return Stream.of(PREFIX_END_TIME, PREFIX_DESCRIPTION, PREFIX_ADDRESS, PREFIX_ZOOM, PREFIX_TAG)
                    .allMatch(prefix -> argumentMultimap.getValue(prefix).isEmpty());
    }

}
