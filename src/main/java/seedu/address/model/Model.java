package seedu.address.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactDisplaySetting;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDisplaySetting;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Contact> PREDICATE_SHOW_ALL_CONTACTS = unused -> true;
    Predicate<Contact> PREDICATE_HIDE_ALL_CONTACTS = unused -> false;
    Predicate<Event> PREDICATE_SHOW_ALL_EVENTS = unused -> true;
    Predicate<Event> PREDICATE_HIDE_ALL_EVENTS = unused -> false;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the display settings of the events.
     */
    EventDisplaySetting getEventDisplaySetting();

    /**
     * Sets the display settings of the events.
     */
    void setEventDisplaySetting(EventDisplaySetting eventDisplaySetting);

    /**
     * Returns the display settings of the contacts.
     */
    ContactDisplaySetting getContactDisplaySetting();

    /**
     * Sets the display settings of the contacts.
     */
    void setContactDisplaySetting(ContactDisplaySetting displaySetting);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the current AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Adds new state of AddressBook to its history list */
    void commitHistory();

    /** Restores the previous addressBook state from its history */
    void undoHistory();

    /** Restores the previously undone state from its history */
    void redoHistory();

    /** Checks if the current state of addressBook is undoable */
    boolean isUndoable();

    /** Check if the current state of addressBook is redoable */
    boolean isRedoable();

    //=========== Contact Management =============================================================

    /**
     * Returns true if a contact with the same identity as {@code contact} exists in the address book.
     */
    boolean hasContact(Contact contact);

    /**
     * Deletes the given contact.
     * The contact must exist in the address book.
     */
    void deleteContact(Contact target);

    /**
     * Adds the given contact.
     * {@code contact} must not already exist in the address book.
     */
    void addContact(Contact contact);

    /**
     * Replaces the given contact {@code target} with {@code editedContact}.
     * {@code target} must exist in the address book.
     * The contact name of {@code editedContact} must not be the same as another existing contact in the address book.
     */
    void setContact(Contact target, Contact editedContact);

    /**
     * Remove all contacts from SoConnect.
     */
    void resetContacts();

    /** Returns an unmodifiable view of the filtered contact list */
    ObservableList<Contact> getFilteredContactList();

    /**
     * Updates the filter of the filtered contact list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredContactList(Predicate<? super Contact> predicate);

    /**
     * This will change the order of the filtered list, marked contacts will be placed at the top of the list.
     * Places the newly marked contacts or replaces unmarked contacts
     * in the order specified in {@code indexes} and depending on {@code isMark}.
     */
    void rearrangeContactsInOrder(List<Contact> contacts, boolean isMark);

    /**
     * Updates the filter of the filtered contact list to show the contact at {@code index}.
     * @throws NullPointerException if {@code index} is null.
     */
    void updateContactListByIndex(Index index);

    //=========== Event Management =============================================================

    /**
     * Returns true if an event with the same name as {@code event} exists in the address book.
     */
    boolean hasEvent(Event event);

    /**
     * Deletes the given event.
     * The event must exist in the address book.
     */
    void deleteEvent(Event target);

    /**
     * Adds the given event.
     * {@code event} must not already exist in the address book.
     */
    void addEvent(Event event);

    /**
     * Replaces the given event {@code target} with {@code editedEvent}.
     * {@code target} must exist in the address book.
     * The event name of {@code editedEvent} must not be the same as another existing event in the address book.
     */
    void setEvent(Event target, Event editedEvent);

    /** Returns an unmodifiable view of the filtered event list */
    ObservableList<Event> getFilteredEventList();

    /**
     * Removes all events from SoConnect.
     */
    void resetEvents();

    /**
     * Updates the filter of the filtered event list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<? super Event> predicate);

    /**
     * Links an event to a contact. Both the event and contact will have reference to each other.
     * @param event The event to link to contact.
     * @param contact The contact to link to event.
     */
    void linkEventAndContact(Event event, Contact contact);

    /**
     * Unlinks an event to a contact. Both references between the contact and the event will be removed.
     * @param event The event to unlink from contact.
     * @param contact The contact to unlink from event.
     */
    void unlinkEventAndContact(Event event, Contact contact);

    /** Unlinks an {@code event} from all its linked contacts. Both references between the contact and the event
     *  will be removed.
     */
    void unlinkAllContactsFromEvent(Event event);

    /**
     * Sorts the filtered event list to show all upcoming events. This will change the order of the filtered list
     * and remove any events which have concluded.
     */
    void sortUpcomingFilteredEventList();

    /**
     * Updates the filter of the filtered event list to show the event at {@code index}.
     * @throws NullPointerException if {@code index} is null.
     */
    void updateEventListByIndex(Index index);

    /**
     * Re-render contact cards in UI to show the most updated version.
     * @param useBackSamePredicate whether the same predicate should be refreshed.
     *                             Otherwise, the filter will be set to all contacts.
     */
    void rerenderContactCards(boolean useBackSamePredicate);

    /**
     * Re-render event cards in UI to show the most updated version.
     * @param useBackSamePredicate whether the same predicate should be refreshed.
     *                             Otherwise, the filter will be set to all contacts.
     */
    void rerenderEventCards(boolean useBackSamePredicate);

    /**
     * Re-render both contact and event cards in UI to show the most updated version.
     */
    void rerenderAllCards();

    /**
     * This will change the order of the filtered list, marked events will be placed at the top of the list.
     * Places the newly marked events or replaces unmarked events
     * in the order specified in {@code indexes} and depending on {@code isMark}.
     */
    void rearrangeEventsInOrder(List<Event> events, boolean isMark);

    /**
     * Removes all links between contacts and events.
     */
    void removeAllLinks();
}
