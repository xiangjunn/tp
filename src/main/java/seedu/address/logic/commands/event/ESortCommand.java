package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class ESortCommand extends Command {

    public static final String COMMAND_WORD = "esort";

    public static final String MESSAGE_SUCCESS = "Showing upcoming events in sorted order.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortUpcomingFilteredEventList();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
