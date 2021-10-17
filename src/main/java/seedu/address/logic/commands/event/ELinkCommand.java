package seedu.address.logic.commands.event;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class ELinkCommand extends Command {
    public static final String COMMAND_WORD = "elink";
    public static final String MESSAGE_USAGE = COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}
