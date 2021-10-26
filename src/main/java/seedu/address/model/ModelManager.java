package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.contact.Contact;
import seedu.address.model.event.Event;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Contact> filteredContacts;
    private final FilteredList<Event> filteredEvents;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredContacts = new FilteredList<>(this.addressBook.getContactList());
        filteredEvents = new FilteredList<>(this.addressBook.getEventList());
    }

    public ModelManager() {
        this(AddressBook.getCurrentAddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return AddressBook.getCurrentAddressBook();
    }

    //=========== AddressBook ================================================================================
    @Override
    public void commitAddressBook() {
        addressBook.commit();
    }

    @Override
    public void undoAddressBook() {
        addressBook.undo();
        addressBook.resetData(getAddressBook());
    }

    @Override
    public void redoAddressBook() {
        addressBook.redo();
        addressBook.resetData(getAddressBook());
    }

    @Override
    public void clearHistory() {
        addressBook.clearHistory();
    }

    @Override
    public boolean isUndoable() {
        return addressBook.isUndoable();
    }

    @Override
    public boolean isRedoable() {
        return addressBook.isRedoable();
    }

    @Override
    public void print() {
        addressBook.print();
    }

    //=========== Manage Contacts ======================

    @Override
    public boolean hasContact(Contact contact) {
        requireNonNull(contact);
        return addressBook.hasContact(contact);
    }

    @Override
    public void deleteContact(Contact target) {
        addressBook.removeContact(target);
    }

    @Override
    public void addContact(Contact contact) {
        addressBook.addContact(contact);
        updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
    }

    @Override
    public void setContact(Contact target, Contact editedContact) {
        requireAllNonNull(target, editedContact);

        addressBook.setContact(target, editedContact);
    }

    @Override
    public void resetContacts() {
        this.addressBook.resetContacts();
    }

    //=========== Manage Events ======================

    @Override
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return addressBook.hasEvent(event);
    }

    @Override
    public void deleteEvent(Event target) {
        addressBook.removeEvent(target);
    }

    @Override
    public void addEvent(Event event) {
        addressBook.addEvent(event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
    }

    @Override
    public void setEvent(Event target, Event editedEvent) {
        requireAllNonNull(target, editedEvent);
        addressBook.setEvent(target, editedEvent);
    }

    @Override
    public void resetEvents() {
        this.addressBook.resetEvents();
    }

    //=========== Filtered Contact List Accessors =====================

    /**
     * Returns an unmodifiable view of the list of {@code Contact} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Contact> getFilteredContactList() {
        return filteredContacts;
    }

    @Override
    public void updateFilteredContactList(Predicate<Contact> predicate) {
        requireNonNull(predicate);
        filteredContacts.setPredicate(predicate);
    }

    //=========== Filtered Event List Accessors =======================
    /**
     * Returns an unmodifiable view of the list of {@code Event} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Event> getFilteredEventList() {
        return filteredEvents;
    }

    @Override
    public void updateFilteredEventList(Predicate<? super Event> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }

    @Override
    public void sortUpcomingFilteredEventList() {
        Predicate<? super Event> currentPredicate = filteredEvents.getPredicate();
        // Remove events that have passed
        addressBook.sortEvents();
        updateFilteredEventList(getNewPredicate(currentPredicate));
    }

    /**
     * Returns the new predicate for displaying only upcoming and ongoing events
     */
    private static Predicate<? super Event> getNewPredicate(Predicate<? super Event> originalPredicate) {
        return event -> (originalPredicate == null || originalPredicate.test(event))
            && ((event.getEndDateAndTime() == null && event.getStartDateAndTime().isNotBeforeNow())
            || (event.getEndDateAndTime() != null && event.getEndDateAndTime().isNotBeforeNow()));
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredContacts.equals(other.filteredContacts)
                && filteredEvents.equals(other.filteredEvents);
    }

    @Override
    public void linkEventAndContact(Event event, Contact contact) {
        event.linkTo(contact);
        contact.linkTo(event);
    }

    @Override
    public void rerenderContactCards() {
        updateFilteredContactList(PREDICATE_HIDE_ALL_CONTACTS); // Hide first to update the contact cards.
        updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
    }

    @Override
    public void rerenderEventCards() {
        updateFilteredEventList(PREDICATE_HIDE_ALL_EVENTS); // Hide first to update the event cards.
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
    }

    @Override
    public void rerenderAllCards() {
        rerenderContactCards();
        rerenderEventCards();
    }
}
