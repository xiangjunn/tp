package seedu.address.logic.parser.event;


import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ZOOM;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.event.EAddCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.common.Address;
import seedu.address.model.common.ZoomLink;
import seedu.address.model.event.Description;
import seedu.address.model.event.EndDateTime;
import seedu.address.model.event.Event;
import seedu.address.model.event.Name;
import seedu.address.model.event.StartDateTime;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new CAddCommand object
 */
public class EAddCommandParser implements Parser<EAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_DESCRIPTION,
                        PREFIX_ADDRESS, PREFIX_ZOOM, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_DESCRIPTION,
                PREFIX_ADDRESS, PREFIX_ZOOM, PREFIX_TAG) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EAddCommand.MESSAGE_USAGE));
        }

        Name name = EventParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        StartDateTime start = (StartDateTime) EventParserUtil
                .parseDateAndTime(argMultimap.getValue(PREFIX_START_TIME).get());
        EndDateTime end = (EndDateTime) EventParserUtil
                .parseDateAndTime(argMultimap.getValue(PREFIX_END_TIME).get());
        Description description = EventParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Address address = EventParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        ZoomLink zoom = EventParserUtil.parseZoomLink(argMultimap.getValue(PREFIX_ZOOM).get());
        Set<Tag> tagList = EventParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Event event = new Event(name, start, end, description, address, zoom, tagList);

        return new EAddCommand(event);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
