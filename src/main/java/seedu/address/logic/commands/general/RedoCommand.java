package seedu.address.logic.commands.general;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents the command to redo a change by {@code UndoCommand}.
 */
public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";

    public static final String SYNTAX = COMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Restore the previously undone state of addressBook";

    public static final String MESSAGE_SUCCESS = "Command redone";

    public static final String MESSAGE_FAIL_TO_REDO = "No command to redo.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.isRedoable()) {
            throw new CommandException(MESSAGE_FAIL_TO_REDO);
        }
        model.redoHistory();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
