package seedu.address.logic.commands.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ZOOM;

import java.util.Objects;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.Undoable;
import seedu.address.model.Model;
import seedu.address.model.contact.ContactDisplaySetting;

/**
 * Lists all persons in the address book to the user.
 */
public class CListCommand extends Command implements Undoable {

    public static final String COMMAND_WORD = "clist";
    public static final String PARAMETERS = "[" + PREFIX_PHONE + "] "
            + "[" + PREFIX_EMAIL + "] "
            + "[" + PREFIX_ADDRESS + "] "
            + "[" + PREFIX_TELEGRAM + "] "
            + "[" + PREFIX_ZOOM + "] "
            + "[" + PREFIX_TAG + "] \n";
    public static final String SYNTAX = COMMAND_WORD + " " + PARAMETERS;

    public static final String MESSAGE_SUCCESS = "Listed all contacts";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all the contacts on the screen with all"
        + " details by default.\n"
        + "Include optional parameters to filter details.\n"
        + "Parameters should not be followed by any values.\n" //added this to warn users not to add any values.
        + "Parameters: "
        + PARAMETERS
        + "Example: " + COMMAND_WORD + " " + PREFIX_EMAIL + " " + PREFIX_ZOOM;

    private final ContactDisplaySetting displaySetting;

    public CListCommand(ContactDisplaySetting displaySetting) {
        this.displaySetting = displaySetting;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setContactDisplaySetting(displaySetting);
        model.rerenderContactCards(false);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public int hashCode() {
        return Objects.hash(displaySetting);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        return other instanceof CListCommand
            && ((CListCommand) other).displaySetting.equals(displaySetting);
    }
}
