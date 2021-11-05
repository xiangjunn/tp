package seedu.address.logic.commands.general;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents the command to undo command changes.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    public static final String SYNTAX = COMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Restore the previous state of addressBook";

    public static final String MESSAGE_SUCCESS = "Command undone";

    public static final String MESSAGE_FAIL_TO_UNDO = "No command to undo.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.isUndoable()) {
            throw new CommandException(MESSAGE_FAIL_TO_UNDO);
        }
        model.undoHistory();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
