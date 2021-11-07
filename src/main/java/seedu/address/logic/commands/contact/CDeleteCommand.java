package seedu.address.logic.commands.contact;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.core.range.Range;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.Undoable;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;

/**
 * Deletes a contact identified using it's displayed index from the address book.
 */
public class CDeleteCommand extends Command implements Undoable {

    public static final String COMMAND_WORD = "cdelete";
    public static final String PARAMETERS = "INDEX1[-INDEX2]";
    public static final String SYNTAX = COMMAND_WORD + " " + PARAMETERS;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the contact identified by the index number used in the displayed contact list.\n"
            + "Parameters: " + PARAMETERS + " (both indexes must be a positive integer)\n"
            + "Note: index must be a positive integer and INDEX1 must be smaller than or equal to INDEX2"
            + " if the optional INDEX2 is included)\n"
            + "Example 1: " + COMMAND_WORD + " 1\n"
            + "Example 2: " + COMMAND_WORD + " 2-5";

    public static final String MESSAGE_DELETE_CONTACT_SUCCESS = "Deleted Contact: %1$s";

    private final Range targetRange;

    public CDeleteCommand(Range targetRange) {
        this.targetRange = targetRange;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Contact> lastShownList = model.getFilteredContactList();

        Index startIndex = targetRange.getStart();
        Index endIndex = targetRange.getEnd();
        int end = endIndex.getZeroBased();
        if (end >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
        }

        String commandResult = "";
        int indexToDelete = startIndex.getZeroBased();
        // delete the same index starting from the start index, since after deleting a contact,
        // the remaining contacts with larger indexes will have their index decreased by 1. Hence,
        // the next bigger index will have the same index as the deleted contact.
        for (int i = indexToDelete; i <= end; i++) {
            Contact contactToDelete = lastShownList.get(indexToDelete);
            model.deleteContact(contactToDelete);
            commandResult += String.format(MESSAGE_DELETE_CONTACT_SUCCESS, contactToDelete);
            if (i != end) {
                commandResult += "\n";
            }
        }
        // rerender UI to update the links for events with links to deleted contact
        model.rerenderEventCards(true);
        return new CommandResult(commandResult);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CDeleteCommand // instanceof handles nulls
                && targetRange.equals(((CDeleteCommand) other).targetRange)); // state check
    }
}
