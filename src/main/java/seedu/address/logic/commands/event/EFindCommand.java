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
import seedu.address.model.Model;
import seedu.address.model.event.EventNameContainsKeywordsPredicate;

/**
 * Finds and lists all events in SoConnect which have names containing any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class EFindCommand extends Command {

    public static final String COMMAND_WORD = "efind";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all events whose fields contains any of the "
            + "given keywords.\n"
            + "At least one field must be present, name keywords must follow directly after the command word\n"
            + "Parameters: [NAME_KEYWORD] "
            + "[" + PREFIX_START_TIME + "START_TIME_KEYWORDS] "
            + "[" + PREFIX_END_TIME + "END_TIME_KEYWORDS] "
            + "[" + PREFIX_ADDRESS + "ADDRESS_KEYWORDS] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION_KEYWORDS] "
            + "[" + PREFIX_ZOOM + "ZOOM_KEYWORDS] "
            + "[" + PREFIX_TAG + "TAG_KEYWORDS]\n"
            + "Example: " + COMMAND_WORD + " cs 2103t "
            + PREFIX_START_TIME + "2020-12-01 "
            + PREFIX_EMAIL + "johndoe@example.com";

    private final EventNameContainsKeywordsPredicate predicate;

    public EFindCommand(EventNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
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
