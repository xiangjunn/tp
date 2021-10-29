package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

public class EBookmarkCommand extends Command {

    public static final String COMMAND_WORD = "emark";

    public static final String PARAMETERS = "INDEX [INDEX]...\n";
    public static final String SYNTAX = COMMAND_WORD + " " + PARAMETERS;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks all event(s) indexed at "
            + "the specified index(es) and displays them at the top of the event list.\n"
            + "Parameters: " + PARAMETERS
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Bookmarked Event: %1$s";
    public static final String MESSAGE_ALREADY_MARKED = "Event %1$s is already bookmarked!";

    private final List<Index> indexesToMark;

    /**
     * Class constryctor, takes in a list of {@code index}
     */
    public EBookmarkCommand(List<Index> indexes) {
        requireNonNull(indexes);
        this.indexesToMark = indexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        String commandResult = "";
        List<Event> lastShownList = model.getFilteredEventList();
        for (Index index : indexesToMark) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
            }
            Event eventToMark = lastShownList.get(index.getZeroBased());
            if (eventToMark.getIsBookMarked()) {
                commandResult += String.format(MESSAGE_ALREADY_MARKED, eventToMark);
                commandResult += "\n";
                continue;
            }
            commandResult += String.format(MESSAGE_SUCCESS, eventToMark);
            commandResult += "\n";
            model.bookmarkEventIndexedAt(index);
        }
        model.reshuffleEventsInOrder();
        return new CommandResult(commandResult);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EBookmarkCommand // instanceof handles nulls
                && indexesToMark.equals(((EBookmarkCommand) other).indexesToMark)); // state check
    }
}
