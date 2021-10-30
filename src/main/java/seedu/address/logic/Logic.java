package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactDisplaySetting;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDisplaySetting;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see seedu.address.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Contact> getFilteredContactList();

    /** Returns an unmodifiable view of the filtered list of events */
    ObservableList<Event> getFilteredEventList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Returns the display settings of the events.
     */
    EventDisplaySetting getEventDisplaySetting();

    /**
     * Returns the display settings of the contacts.
     */
    ContactDisplaySetting getContactDisplaySetting();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /** Changes the filter to the model so that only contacts linked to {@code event} will be shown. */
    void filterContactsWithLinksToEvent(Event event);

    /** Changes the filter to the model so that only events linked to {@code contact} will be shown. */
    void filterEventsWithLinkToContact(Contact contact);

    /** Changes the filter of the contacts to show all contacts. */
    void resetFilterOfContacts();

    /** Changes the filter of the events to show all events. */
    void resetFilterOfEvents();
}
