package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_HIDE_ALL_CONTACTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONTACTS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.core.range.Range;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Deletes an event identified using its displayed index from the SoConnect.
 */
public class EDeleteCommand extends Command {

    public static final String COMMAND_WORD = "edelete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the event identified by the index number used in the displayed SoConnect.\n"
            + "Parameters: INDEX1[-INDEX2] (both indexes must be a positive integer)\n"
            + "Example 1: " + COMMAND_WORD + " 1"
            + "Example 2: " + COMMAND_WORD + " 2-5";

    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted Event: %1$s";

    private final Range targetRange;

    public EDeleteCommand(Index targetIndex) {
        this.targetRange = new Range(targetIndex, targetIndex);
    }

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
        if (startIndex.isMoreThan(endIndex)) {
            throw new CommandException(Messages.MESSAGE_START_MORE_THAN_END_INDEX);
        }

        String commandResult = "";
        int indexToDelete = startIndex.getZeroBased();
        for (int i = indexToDelete; i <= end; i++) {
            Event eventToDelete = lastShownList.get(indexToDelete);
            model.deleteEvent(eventToDelete);
            commandResult += String.format(MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete);
            if (i != end) {
                commandResult += "\n";
            }
        }
        model.updateFilteredContactList(PREDICATE_HIDE_ALL_CONTACTS); // Hide first to update the contact cards.
        model.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
        return new CommandResult(commandResult);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EDeleteCommand // instanceof handles nulls
                && targetRange.equals(((EDeleteCommand) other).targetRange)); // state check
    }
}

