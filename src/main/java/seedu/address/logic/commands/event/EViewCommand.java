package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.Undoable;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDisplaySetting;


/**
 *  Views one event in full detail in SoConnect to the user.
 */
public class EViewCommand extends Command implements Undoable {
    public static final String COMMAND_WORD = "eview";

    public static final String SYNTAX = COMMAND_WORD + " INDEX";

    public static final String MESSAGE_SUCCESS = "Viewing Event: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View one event of the index provided "
            + "on the screen with all details.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index index;

    public EViewCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();

        Index viewIndex = index;
        int intIndex = viewIndex.getZeroBased();
        if (intIndex < 0 || intIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }
        model.setEventDisplaySetting(new EventDisplaySetting(true));
        model.updateEventListByIndex(viewIndex);
        return new CommandResult(String.format(MESSAGE_SUCCESS, lastShownList.get(0)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        return other instanceof EViewCommand
                && ((EViewCommand) other).index.equals(index);
    }
}

