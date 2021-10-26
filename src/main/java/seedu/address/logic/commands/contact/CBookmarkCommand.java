package seedu.address.logic.commands.contact;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

public class CBookmarkCommand extends Command {
    public static final String COMMAND_WORD = "cmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks all contact indexed at "
            + "the specified index and displays them at the top of the contact list.\n"
            + "Parameters: INDEX \n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Bookmarked Contact: %1$s";

    private final Index indexToMark;
    public CBookmarkCommand(Index index) {
        requireNonNull(index);
        this.indexToMark = index;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.bookMarkContact(indexToMark);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CBookmarkCommand // instanceof handles nulls
                && indexToMark.equals(((CBookmarkCommand) other).indexToMark)); // state check
    }
}
