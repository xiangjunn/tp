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

public class CBookmarkCommand extends Command {
    public static final String COMMAND_WORD = "cmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks all contact(s) indexed at "
            + "the specified index(es) and displays them at the top of the contact list.\n"
            + "Parameters: INDEX \n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Bookmarked Contact: %1$s";
    public static final String MESSAGE_ALREADY_MARKED = "Contact %1$s is already bookmarked!";

    private final List<Index> indexesToMark;

    /**
     * Class constryctor, takes in a list of {@code index}
     */
    public CBookmarkCommand(List<Index> indexes) {
        requireNonNull(indexes);
        this.indexesToMark = indexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        String commandResult = "";
        List<Contact> lastShownList = model.getFilteredContactList();
        for (Index index : indexesToMark) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
            }
            Contact contactToMark = lastShownList.get(index.getZeroBased());
            if (contactToMark.getIsBookMarked()) {
                commandResult += String.format(MESSAGE_ALREADY_MARKED, contactToMark);
                commandResult += "\n";
                continue;
            }
            commandResult += String.format(MESSAGE_SUCCESS, contactToMark);
            commandResult += "\n";
            model.bookmarkContactIndexedAt(index);
        }
        model.reshuffleContactsInOrder();
        return new CommandResult(commandResult);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CBookmarkCommand // instanceof handles nulls
                && indexesToMark.equals(((CBookmarkCommand) other).indexesToMark)); // state check
    }
}
