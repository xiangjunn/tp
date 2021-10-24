package seedu.address.logic.commands.general;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class CalendarCommand extends Command {

    public static final String COMMAND_WORD = "calendar";

    public static final String MESSAGE_SUCCESS = "Showing calendar.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SUCCESS, false, false, true);
    }
}
