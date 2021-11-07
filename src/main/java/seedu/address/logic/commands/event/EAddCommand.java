package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ZOOM;

import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.Undoable;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventChanger;

/**
 * Adds a event to the address book.
 */
public class EAddCommand extends Command implements Undoable {

    public static final String COMMAND_WORD = "eadd";
    public static final String PARAMETERS = "" + PREFIX_NAME + "NAME "
            + "" + PREFIX_START_TIME + "START "
            + "[" + PREFIX_END_TIME + "END] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_ZOOM + "ZOOM] "
            + "[" + PREFIX_TAG + "TAG]...\n";
    public static final String SYNTAX = COMMAND_WORD + " " + PARAMETERS;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to SoConnect. \n"
            + "Parameters: "
            + PARAMETERS
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Summer Party "
            + PREFIX_START_TIME + "12-12-2021 15:12 "
            + PREFIX_DESCRIPTION + "end of semester party "
            + PREFIX_ADDRESS + "123, Clementi Rd, S1234665 "
            + PREFIX_ZOOM + "http://zoomlink.com "
            + PREFIX_TAG + "fun";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the address book";
    public static final String MESSAGE_INVALID_DATE_TIME_RANGE = "Event start time cannot be later than end time.";

    private final Event toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Event}
     * @param event the specified Event
     */
    public EAddCommand(Event event) {
        requireNonNull(event);
        toAdd = event;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasEvent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        if (toAdd.getEndDateAndTime() != null && toAdd.getEndDateAndTime().isBefore(toAdd.getStartDateAndTime())) {
            throw new CommandException(MESSAGE_INVALID_DATE_TIME_RANGE);
        }

        model.addEvent(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), List.of(EventChanger.addEventChanger(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EAddCommand // instanceof handles nulls
                && toAdd.equals(((EAddCommand) other).toAdd));
    }
}
