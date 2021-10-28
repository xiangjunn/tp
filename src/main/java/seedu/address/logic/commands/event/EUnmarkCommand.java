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

public class EUnmarkCommand extends Command {

    public static final String COMMAND_WORD = "eumark";

    public static final String PARAMETERS = "INDEX [INDEX]...\n";
    public static final String SYNTAX = COMMAND_WORD + " " + PARAMETERS;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unmarks all event(s) indexed at "
            + "the specified index(es) and displays them after the marked events.\n"
            + "Parameters: " + PARAMETERS
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Unmarked Event: %1$s";
    public static final String MESSAGE_NOT_MARKED = "Event %1$s is not bookmarked!";

    private final List<Index> indexesToUnmark;

    /**
     * Class constryctor, takes in a list of {@code index}
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
        for (Index index : indexesToUnmark) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
            }
            Event eventToMark = lastShownList.get(index.getZeroBased());
            if (!eventToMark.getIsBookMarked()) {
                commandResult += String.format(MESSAGE_NOT_MARKED, eventToMark);
                commandResult += "\n";
                continue;
            }
            commandResult += String.format(MESSAGE_SUCCESS, eventToMark);
            commandResult += "\n";
            model.unmarkEventIndexedAt(index);
        }
        model.reshuffleEventsInOrder();
        return new CommandResult(commandResult);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EUnmarkCommand // instanceof handles nulls
                && indexesToUnmark.equals(((EUnmarkCommand) other).indexesToUnmark)); // state check
    }
}
