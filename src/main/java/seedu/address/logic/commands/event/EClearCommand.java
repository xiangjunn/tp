package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.event.EventChanger;

/**
 * Clears all events in SoConnect.
 */
public class EClearCommand extends Command {

    public static final String COMMAND_WORD = "eclear";
    public static final String MESSAGE_SUCCESS = "All events have been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.resetEvents();
        return new CommandResult(MESSAGE_SUCCESS, List.of(EventChanger.clearEventChanger()));
    }
}
