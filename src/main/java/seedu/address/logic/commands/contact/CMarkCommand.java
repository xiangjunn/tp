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


public class CMarkCommand extends Command implements Undoable {
    public static final String COMMAND_WORD = "cmark";

    public static final String PARAMETERS = "INDEX [INDEX]...\n";
    public static final String SYNTAX = COMMAND_WORD + " " + PARAMETERS;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks all contact(s) indexed at "
            + "the specified index(es) and displays them at the top of the contact list.\n"
            + "Parameters: " + PARAMETERS
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Marked contact: %1$s";
    public static final String MESSAGE_ALREADY_MARKED = "Contact already marked: %1$s";

    private final List<Index> indexesToMark;

    /**
     * Class constructor, takes in a list of {@code index}
     */
    public CMarkCommand(List<Index> indexes) {
        requireNonNull(indexes);
        this.indexesToMark = indexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        String commandResult = "";
        List<Contact> lastShownList = model.getFilteredContactList();
        if (indexesToMark.stream().anyMatch(index -> index.getZeroBased() >= lastShownList.size())) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
        }
        Collections.reverse(indexesToMark);
        List<Contact> contactsMarked = new ArrayList<>();
        for (Index index : indexesToMark) {
            Contact contact = lastShownList.get(index.getZeroBased());
            commandResult += String.format("%s", generateCommandResultMessage(contact, contact.getIsMarked()));
            Contact newContact = contact.markContact();
            model.setContact(contact, newContact);
            contactsMarked.add(newContact);
        }
        model.rearrangeContactsInOrder(contactsMarked, true);
        return new CommandResult(commandResult);
    }

    /**
     * Creates and returns a marked {@code Contact} with the details of {@code contactToMark}
     */
    private static Contact createMarkedContact(Contact contactToMark) {
        return new Contact(contactToMark.getName(), contactToMark.getPhone(), contactToMark.getEmail(),
                contactToMark.getAddress(), contactToMark.getZoomLink(), contactToMark.getTelegramHandle(),
                contactToMark.getTags(), contactToMark.getUuid(), contactToMark.getLinkedEvents(), true);
    }

    private String generateCommandResultMessage(Contact contact,
                                              boolean isAlreadyMarked) {
        String message;
        if (isAlreadyMarked) {
            message = String.format(MESSAGE_ALREADY_MARKED, contact);
        } else {
            message = String.format(MESSAGE_SUCCESS, contact);
        }
        return message += "\n";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CMarkCommand // instanceof handles nulls
                && indexesToMark.equals(((CMarkCommand) other).indexesToMark)); // state check
    }
}
