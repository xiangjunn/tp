package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.Undoable;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

public class EUnmarkCommand extends Command implements Undoable {

    public static final String COMMAND_WORD = "eunmark";

    public static final String PARAMETERS = "INDEX [INDEX]...\n";
    public static final String SYNTAX = COMMAND_WORD + " " + PARAMETERS;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unmarks all event(s) indexed at "
            + "the specified index(es) and displays them after the marked events.\n"
            + "Parameters: " + PARAMETERS
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Unmarked event: %1$s";
    public static final String MESSAGE_NOT_MARKED = "Event not marked: %1$s";

    private final List<Index> indexesToUnmark;

    /**
     * Class constructor, takes in a list of {@code index}
     */
    public EUnmarkCommand(List<Index> indexes) {
        requireNonNull(indexes);
        this.indexesToUnmark = indexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        String commandResult = "";
        List<Event> lastShownList = model.getFilteredEventList();
        if (indexesToUnmark.stream().anyMatch(index -> index.getZeroBased() >= lastShownList.size())) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }
        Collections.reverse(indexesToUnmark);
        for (Index index : indexesToUnmark) {
            Event event = lastShownList.get(index.getZeroBased());
            commandResult += String.format("%s", generateCommandResultMessage(event, event.getIsMarked()));
            if (event.getIsMarked()) {
                model.setEvent(event, createUnmarkedEvent(event));
            }
        }
        model.rearrangeEventsInOrder(indexesToUnmark, false);
        return new CommandResult(commandResult);
    }

    /**
     * Creates and returns an unmarked {@code Event} with the details of {@code eventToMark}
     */
    private static Event createUnmarkedEvent(Event eventToUnmark) {
        return new Event(eventToUnmark.getName(), eventToUnmark.getStartDateAndTime(),
                eventToUnmark.getEndDateAndTime(), eventToUnmark.getDescription(), eventToUnmark.getAddress(),
                eventToUnmark.getZoomLink(), eventToUnmark.getTags(), eventToUnmark.getUuid(),
                eventToUnmark.getLinkedContacts(), false);
    }

    private String generateCommandResultMessage(Event event, boolean isAlreadyMarked) {
        String message;
        if (!isAlreadyMarked) {
            message = String.format(MESSAGE_NOT_MARKED, event);
        } else {
            message = String.format(MESSAGE_SUCCESS, event);
        }
        return message += "\n";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EUnmarkCommand // instanceof handles nulls
                && indexesToUnmark.equals(((EUnmarkCommand) other).indexesToUnmark)); // state check
    }
}
