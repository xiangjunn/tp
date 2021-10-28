package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ZOOM;
import static seedu.address.model.Model.PREDICATE_HIDE_ALL_EVENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all events in the address book to the user.
 */
public class EListCommand extends Command {

    public static final String COMMAND_WORD = "elist";

    public static final String MESSAGE_SUCCESS = "Listed all events";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all the events on the screen with all"
            + " details by default.\n"
            + "Include optional parameters to filter details.\n"
            + "Parameters should not be followed by any values.\n" //added this to warn users not to add any values.
            + "Parameters: "
            + "[" + PREFIX_START_TIME + "] "
            + "[" + PREFIX_END_TIME + "] "
            + "[" + PREFIX_DESCRIPTION + "] "
            + "[" + PREFIX_ADDRESS + "] "
            + "[" + PREFIX_ZOOM + "] "
            + "[" + PREFIX_TAG + "] \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_END_TIME + " " + PREFIX_ZOOM;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEventList(PREDICATE_HIDE_ALL_EVENTS);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        return other instanceof EListCommand;
    }
}
