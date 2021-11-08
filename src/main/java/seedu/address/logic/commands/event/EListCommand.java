package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ZOOM;

import java.util.Objects;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.Undoable;
import seedu.address.model.Model;
import seedu.address.model.event.EventDisplaySetting;

/**
 * Lists all events in the address book to the user.
 */
public class EListCommand extends Command implements Undoable {

    public static final String COMMAND_WORD = "elist";
    public static final String PARAMETERS = "[" + PREFIX_START_TIME + "] "
            + "[" + PREFIX_END_TIME + "] "
            + "[" + PREFIX_DESCRIPTION + "] "
            + "[" + PREFIX_ADDRESS + "] "
            + "[" + PREFIX_ZOOM + "] "
            + "[" + PREFIX_TAG + "] \n";
    public static final String SYNTAX = COMMAND_WORD + " " + PARAMETERS;

    public static final String MESSAGE_SUCCESS = "Listed all events";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all the events on the screen with all"
            + " details by default.\n"
            + "Include optional parameters to filter details.\n"
            + "Parameters should not be followed by any values.\n" //added this to warn users not to add any values.
            + "Parameters: "
            + PARAMETERS
            + "Example: " + COMMAND_WORD + " " + PREFIX_END_TIME + " " + PREFIX_ZOOM;

    public final EventDisplaySetting displaySetting;

    public EListCommand(EventDisplaySetting displaySetting) {
        this.displaySetting = displaySetting;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setEventDisplaySetting(displaySetting);
        model.rerenderEventCards(false);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public int hashCode() {
        return Objects.hash(displaySetting);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        return other instanceof EListCommand
            && ((EListCommand) other).displaySetting.equals(displaySetting);
    }
}
