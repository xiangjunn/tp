package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.core.range.Range;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.Undoable;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventChanger;

/**
 * Deletes an event identified using its displayed index from the SoConnect.
 */
public class EDeleteCommand extends Command implements Undoable {

    public static final String COMMAND_WORD = "edelete";
    public static final String PARAMETERS = "INDEX1[-INDEX2]";
    public static final String SYNTAX = COMMAND_WORD + " " + PARAMETERS;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the event identified by the index number used in the displayed event list.\n"
            + "Parameters: " + PARAMETERS + " (both indexes must be a positive integer)\n"
            + "Note: index must be a positive integer and INDEX1 must be smaller than or equal to INDEX2"
            + " if the optional INDEX2 is included)\n"
            + "Example 1: " + COMMAND_WORD + " 1\n"
            + "Example 2: " + COMMAND_WORD + " 2-5";

    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted Event: %1$s";

    private final Range targetRange;

    public EDeleteCommand(Range targetRange) {
        this.targetRange = targetRange;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();

        Index startIndex = targetRange.getStart();
        Index endIndex = targetRange.getEnd();
        int end = endIndex.getZeroBased();
        if (end >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        String commandResult = "";
        int indexToDelete = startIndex.getZeroBased();
        List<EventChanger> eventChangerList = new ArrayList<>();
        for (int i = indexToDelete; i <= end; i++) {
            Event eventToDelete = lastShownList.get(indexToDelete);
            model.deleteEvent(eventToDelete);
            eventChangerList.add(EventChanger.deleteEventChanger(eventToDelete));
            commandResult += String.format(MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete);
            if (i != end) {
                commandResult += "\n";
            }
        }
        // rerender UI to update the links for contacts with links to deleted event
        model.rerenderContactCards(true);
        return new CommandResult(commandResult, eventChangerList);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EDeleteCommand // instanceof handles nulls
                && targetRange.equals(((EDeleteCommand) other).targetRange)); // state check
    }
}

