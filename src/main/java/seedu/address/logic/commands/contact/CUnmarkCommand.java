package seedu.address.logic.commands.contact;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.Undoable;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;

public class CUnmarkCommand extends Command implements Undoable {

    public static final String COMMAND_WORD = "cunmark";

    public static final String PARAMETERS = "INDEX [INDEX]...\n";
    public static final String SYNTAX = COMMAND_WORD + " " + PARAMETERS;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unmarks all contact(s) indexed at "
            + "the specified index(es) and displays them after the marked contacts.\n"
            + "Parameters: " + PARAMETERS
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Unmarked contact: %1$s";
    public static final String MESSAGE_NOT_MARKED = "Contact not marked: %1$s";

    private final List<Index> indexesToUnmark;

    /**
     * Class constructor, takes in a list of {@code index}
     */
    public CUnmarkCommand(List<Index> indexes) {
        requireNonNull(indexes);
        this.indexesToUnmark = indexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        String commandResult = "";
        List<Contact> lastShownList = model.getFilteredContactList();
        if (indexesToUnmark.stream().anyMatch(index -> index.getZeroBased() >= lastShownList.size())) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
        }
        Collections.reverse(indexesToUnmark);
        List<Contact> contactsUnmarked = new ArrayList<>();
        for (Index index : indexesToUnmark) {
            Contact contact = lastShownList.get(index.getZeroBased());
            commandResult += String.format("%s", generateCommandResultMessage(contact, contact.getIsMarked()));
            if (contact.getIsMarked()) {
                Contact newContact = contact.unmarkContact();
                model.setContact(contact, newContact);
                contactsUnmarked.add(newContact);
            }
        }
        model.rearrangeContactsInOrder(contactsUnmarked, false);
        return new CommandResult(commandResult);
    }

    private String generateCommandResultMessage(Contact contact, boolean isAlreadyMarked) {
        String message;
        if (!isAlreadyMarked) {
            message = String.format(MESSAGE_NOT_MARKED, contact);
        } else {
            message = String.format(MESSAGE_SUCCESS, contact);
        }
        return message += "\n";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CUnmarkCommand // instanceof handles nulls
                && indexesToUnmark.equals(((CUnmarkCommand) other).indexesToUnmark)); // state check
    }
}
