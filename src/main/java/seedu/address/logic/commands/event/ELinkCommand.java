package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class ELinkCommand extends Command {
    public static final String COMMAND_WORD = "elink";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Links an event to a contact. "
        + "Parameters: "
        + PREFIX_LINK + "EVENT_INDEX "
        + PREFIX_CONTACT + "CONTACT_INDEX ";
    public static final String MESSAGE_SUCCESS = "Successfully linked the event to a contact.";

    private final Index eventIndex;
    private final Index contactIndex;

    public ELinkCommand(Index eventIndex, Index contactIndex) {
        this.eventIndex = eventIndex;
        this.contactIndex = contactIndex;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
