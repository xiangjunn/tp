package seedu.address.logic.commands.contact;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;


/**
 *  Views one person in full detail in the address book to the user.
 */
public class CViewCommand extends Command {
    public static final String COMMAND_WORD = "cview";

    public static final String MESSAGE_SUCCESS = "Viewing Contact: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View one contact on the screen with all"
            + " details.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index index;

    public CViewCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Contact> lastShownList = model.getFilteredContactList();

        Index viewIndex = index;
        if (viewIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
        }
        model.updateContactListByIndex(viewIndex);
        return new CommandResult(String.format(MESSAGE_SUCCESS, lastShownList.get(viewIndex.getZeroBased())));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        return other instanceof CViewCommand;
    }
}
