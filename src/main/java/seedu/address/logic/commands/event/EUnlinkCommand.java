package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.event.Event;

public class EUnlinkCommand extends Command {
    public static final String COMMAND_WORD = "eunlink";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unlinks an event to a contact. Include the argument"
        + " \"c/*\" to clear all links.\n"
        + "Parameters: "
        + "EVENT_INDEX "
        + PREFIX_CONTACT + "CONTACT_INDEX [" + PREFIX_CONTACT + "CONTACT_INDEX]...\n"
        + "Examples:\n"
        + "eunlink 1 c/1\n"
        + "eunlink 3 c/1 c/2 c/3 c/4 c/5\n"
        + "eunlink 2 c/*";
    public static final String MESSAGE_SUCCESS = "Successfully unlinked the event %s from the contact%s %s";
    public static final String MESSAGE_SUCCESS_CLEAR_ALL = "Successfully unlinked the event %s from all contacts.";

    private final Index eventIndex;
    private final Set<Index> contactIndexes;
    private final boolean isClearAllLinks;

    /**
     * Creates an EUnlinkCommand to remove the link from the specified {@code Event} to a {@code Contact}
     */
    public EUnlinkCommand(Index eventIndex, Set<Index> contactIndexes, boolean isClearAllLinks) {
        requireAllNonNull(eventIndex, contactIndexes);
        this.eventIndex = eventIndex;
        this.contactIndexes = contactIndexes;
        this.isClearAllLinks = isClearAllLinks;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // check validity of command
        List<Event> lastShownEventList = model.getFilteredEventList();
        List<Contact> lastShownContactList = model.getFilteredContactList();
        checkCommandValidity(lastShownEventList, lastShownContactList);

        // execution of command
        CommandResult commandResult;
        Event eventToUnlink = lastShownEventList.get(eventIndex.getZeroBased());
        if (!isClearAllLinks) {
            commandResult = unlinkEventAndContacts(model, eventToUnlink, lastShownContactList);
        } else {
            commandResult = unlinkEventAndAllContacts(eventToUnlink, model);
        }

        // rerender UI to show the links between event and each of the contacts
        model.rerenderAllCards();

        return commandResult;
    }

    private void checkCommandValidity(List<Event> eventList, List<Contact> contactList) throws CommandException {
        if (eventIndex.getZeroBased() >= eventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        for (Index contactIndex : contactIndexes) {
            if (contactIndex.getZeroBased() >= contactList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
            }
        }
    }

    private CommandResult unlinkEventAndContacts(Model model, Event eventToUnlink, List<Contact> lastShownContactList) {
        String commandResult = "";
        int count = 0;
        for (Index contactIndex : contactIndexes) {
            Contact contactToUnlink = lastShownContactList.get(contactIndex.getZeroBased());
            model.unlinkEventAndContact(eventToUnlink, contactToUnlink);
            if (count == 0) {
                commandResult += String.format(MESSAGE_SUCCESS, eventToUnlink.getName(),
                    contactIndexes.size() > 1 ? "s" : "", contactToUnlink.getName());
            } else {
                commandResult += contactToUnlink.getName();
            }
            if (count != contactIndexes.size() - 1) {
                commandResult += ", ";
            } else {
                commandResult += ".";
            }
            count++;
        }
        return new CommandResult(commandResult);
    }

    private CommandResult unlinkEventAndAllContacts(Event eventToUnlink, Model model) {
        model.unlinkAllContactsFromEvent(eventToUnlink);
        return new CommandResult(String.format(MESSAGE_SUCCESS_CLEAR_ALL, eventToUnlink.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof EUnlinkCommand // instanceof handles nulls
            && eventIndex.equals(((EUnlinkCommand) other).eventIndex)
            && contactIndexes.equals(((EUnlinkCommand) other).contactIndexes)
            && isClearAllLinks == ((EUnlinkCommand) other).isClearAllLinks); // state check
    }
}
