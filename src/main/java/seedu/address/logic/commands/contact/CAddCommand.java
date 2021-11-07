package seedu.address.logic.commands.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ZOOM;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.Undoable;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;

/**
 * Adds a contact to the address book.
 */
public class CAddCommand extends Command implements Undoable {

    public static final String COMMAND_WORD = "cadd";
    public static final String PARAMETERS = PREFIX_NAME + "NAME "
            + PREFIX_EMAIL + "EMAIL "
            + "[" + PREFIX_PHONE + "PHONE_NUMBER] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TELEGRAM + "TELEGRAM_HANDLE] "
            + "[" + PREFIX_ZOOM + "ZOOM] "
            + "[" + PREFIX_TAG + "TAG]...\n";
    public static final String SYNTAX = COMMAND_WORD + " " + PARAMETERS;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a contact to SoConnect. \n"
            + "Parameters: "
            + PARAMETERS
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_EMAIL + "e7654321@u.nus.edu "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_ADDRESS + "COM1-0201 "
            + PREFIX_TELEGRAM + "jonnyjonny "
            + PREFIX_ZOOM + "https://nus-sg.zoom.us/j/0123456789?pwd=ABCDEFGHIJKLMNOPDJFHISDFSDH "
            + PREFIX_TAG + "TA "
            + PREFIX_TAG + "Senior";

    public static final String MESSAGE_SUCCESS = "New contact added: %1$s";
    public static final String MESSAGE_DUPLICATE_CONTACT = "This contact already exists in the address book";

    private final Contact toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Contact}
     */
    public CAddCommand(Contact contact) {
        requireNonNull(contact);
        toAdd = contact;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasContact(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CONTACT);
        }
        model.addContact(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CAddCommand // instanceof handles nulls
                && toAdd.equals(((CAddCommand) other).toAdd));
    }
}
