package seedu.address.logic.commands.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_HIDE_ALL_EVENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Clears all entries of contacts from SoConnect.
 */
public class CClearCommand extends Command {

    public static final String COMMAND_WORD = "cclear";
    public static final String MESSAGE_SUCCESS = "All entries of contacts have been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.resetContacts();
        model.updateFilteredEventList(PREDICATE_HIDE_ALL_EVENTS); // Hide first to update the event cards.
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
