package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ZOOM;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.Undoable;
import seedu.address.model.Model;
import seedu.address.model.event.EventContainsKeywordsPredicate;
import seedu.address.model.event.EventDisplaySetting;

/**
 * Finds and lists all events in SoConnect which have names containing any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class EFindCommand extends Command implements Undoable {

    public static final String COMMAND_WORD = "efind";
    public static final String PARAMETERS = "[KEYWORD]… "
            + "[" + PREFIX_START_TIME + "KEYWORD…] "
            + "[" + PREFIX_END_TIME + "KEYWORD…] "
            + "[" + PREFIX_ADDRESS + "KEYWORD…] "
            + "[" + PREFIX_DESCRIPTION + "KEYWORD…] "
            + "[" + PREFIX_ZOOM + "KEYWORD…] "
            + "[" + PREFIX_TAG + "KEYWORD…]\n";
    public static final String SYNTAX = COMMAND_WORD + " " + PARAMETERS;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all events whose fields contains any of the "
            + "given keywords.\n"
            + "At least one keyword must be present. "
            + "For name search, keywords must follow directly after the command word\n"
            + "Parameters: " + PARAMETERS
            + "Example: " + COMMAND_WORD + " cs 2103t "
            + PREFIX_START_TIME + "2020-12-01 "
            + PREFIX_EMAIL + "johndoe@example.com";

    private final EventContainsKeywordsPredicate predicate;

    public EFindCommand(EventContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setEventDisplaySetting(EventDisplaySetting.DEFAULT_SETTING);
        model.updateFilteredEventList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW, model.getFilteredEventList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EFindCommand // instanceof handles nulls
                && predicate.equals(((EFindCommand) other).predicate)); // state check
    }
}
