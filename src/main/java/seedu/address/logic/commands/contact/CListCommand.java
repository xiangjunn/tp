package seedu.address.logic.commands.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ZOOM;
import static seedu.address.model.Model.PREDICATE_HIDE_ALL_CONTACTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONTACTS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class CListCommand extends Command {

    public static final String COMMAND_WORD = "clist";

    public static final String MESSAGE_SUCCESS = "Listed all contacts";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all the contacts on the screen with all"
        + " details by default.\n"
        + "Include optional parameters to filter details.\n"
        + "Parameters: "
        + "[" + PREFIX_PHONE + "] "
        + "[" + PREFIX_EMAIL + "] "
        + "[" + PREFIX_ADDRESS + "] "
        + "[" + PREFIX_TELEGRAM + "] "
        + "[" + PREFIX_ZOOM + "] "
        + "[" + PREFIX_TAG + "] \n"
        + "Example: " + COMMAND_WORD + " " + PREFIX_EMAIL + " " + PREFIX_ZOOM;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredContactList(PREDICATE_HIDE_ALL_CONTACTS); // Hide first to update the cards.
        model.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        return other instanceof CListCommand;
    }
}
