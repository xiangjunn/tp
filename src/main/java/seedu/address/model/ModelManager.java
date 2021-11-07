package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactDisplaySetting;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDisplaySetting;
import seedu.address.model.history.ModelHistory;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ModelHistory modelHistory = new ModelHistory();
    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Contact> filteredContacts;
    private final FilteredList<Event> filteredEvents;

    private ModelDisplaySetting modelDisplaySetting = new ModelDisplaySetting();

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
        modelHistory.commit(addressBook, modelDisplaySetting);
    }

    /**
     * Initializes a ModelManager with the default addressBook and userPrefs.
     */
    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
        modelHistory.commit(addressBook, modelDisplaySetting);
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

    //=========== AddressBook Display Setting =======================================================================

    /**
     * Clear history of all display setting of model
     */
    public void clearHistory() {
        modelHistory.clearHistory();
    }

    public EventDisplaySetting getEventDisplaySetting() {
        return modelDisplaySetting.getEventDisplaySetting();
    }

    public void setEventDisplaySetting(EventDisplaySetting eventDisplaySetting) {
        requireNonNull(eventDisplaySetting);
        modelDisplaySetting = modelDisplaySetting.differentEventDisplaySetting(eventDisplaySetting);
    }

    @Override
    public ContactDisplaySetting getContactDisplaySetting() {
        return modelDisplaySetting.getContactDisplaySetting();
    }

    @Override
    public void setContactDisplaySetting(ContactDisplaySetting displaySetting) {
        requireNonNull(displaySetting);
        modelDisplaySetting = modelDisplaySetting.differentContactDisplaySetting(displaySetting);
    }

    //=========== AddressBook Storage ================================================================================

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
        return addressBook;
    }

    //=========== Versioned AddressBook ================================================================================

    @Override
    public void commitHistory() {
        modelHistory.commit(addressBook.copy(), modelDisplaySetting);
    }

    @Override
    public void undoHistory() {
        ModelHistory.HistoryInstance instance = modelHistory.undo();
        addressBook.resetData(instance.getAddressBook());
        addressBook.updateDataMaps();
        modelDisplaySetting = instance.getDisplaySetting();
        filteredContacts.setPredicate(modelDisplaySetting.getContactDisplayPredicate());
        filteredEvents.setPredicate(modelDisplaySetting.getEventDisplayPredicate());
        rerenderAllCards();
    }

    @Override
    public void redoHistory() {
        ModelHistory.HistoryInstance instance = modelHistory.redo();
        addressBook.resetData(instance.getAddressBook());
        addressBook.updateDataMaps();
        modelDisplaySetting = instance.getDisplaySetting();
        filteredContacts.setPredicate(modelDisplaySetting.getContactDisplayPredicate());
        filteredEvents.setPredicate(modelDisplaySetting.getEventDisplayPredicate());
        rerenderAllCards();
    }

    @Override
    public boolean isUndoable() {
        return modelHistory.isUndoable();
    }

    @Override
    public boolean isRedoable() {
        return modelHistory.isRedoable();
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
        // not necessary to remove links from contacts since they will be deleted, but just to be strict
        // about the bidirectional relationship
        removeAllLinks();

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
        // not necessary to remove links from events since they will be deleted, but just to be strict
        // about the bidirectional relationship
        removeAllLinks();

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
    public void updateFilteredContactList(Predicate<? super Contact> predicate) {
        requireNonNull(predicate);
        filteredContacts.setPredicate(predicate);
        modelDisplaySetting = modelDisplaySetting.differentContactDisplayPredicate(predicate);
    }

    @Override
    public void updateContactListByIndex(Index index) {
        requireNonNull(index);
        Contact targetContact = filteredContacts.get(index.getZeroBased());
        Predicate<? super Contact> predicate = curr -> curr.isSameContact(targetContact);
        modelDisplaySetting = modelDisplaySetting.differentContactDisplayPredicate(predicate);
        filteredContacts.setPredicate(predicate);
    }
    @Override
    public void rearrangeContactsInOrder(List<Contact> contacts, boolean isMarked) {
        addressBook.rearrangeContactsInOrder(contacts, isMarked);
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
        modelDisplaySetting = modelDisplaySetting.differentEventDisplayPredicate(predicate);
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
    public void updateEventListByIndex(Index index) {
        requireNonNull(index);
        Event targetEvent = filteredEvents.get(index.getZeroBased());
        Predicate<? super Event> predicate = curr -> curr.isSameEvent(targetEvent);
        filteredEvents.setPredicate(predicate);
        modelDisplaySetting = modelDisplaySetting.differentEventDisplayPredicate(predicate);
    }

    @Override
    public void rearrangeEventsInOrder(List<Event> events, boolean isMark) {
        addressBook.rearrangeEventsInOrder(events, isMark);
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
                && filteredEvents.equals(other.filteredEvents)
                && modelDisplaySetting.equals(other.modelDisplaySetting);
    }

    @Override
    public void linkEventAndContact(Event event, Contact contact) {
        requireAllNonNull(event, contact);

        addressBook.linkEventAndContact(event, contact);
    }

    @Override
    public void unlinkEventAndContact(Event event, Contact contact) {
        requireAllNonNull(event, contact);

        addressBook.unlinkEventAndContact(event, contact);
    }

    @Override
    public void unlinkAllContactsFromEvent(Event event) {
        addressBook.unlinkContactsFromEvent(event);
    }

    @Override
    public void rerenderContactCards(boolean useBackSamePredicate) {
        ModelHistory.HistoryInstance historyInstance = modelHistory.getCurrentHistoryInstance();
        Predicate<? super Contact> oldPred = historyInstance.getDisplaySetting().getContactDisplayPredicate();
        updateFilteredContactList(PREDICATE_HIDE_ALL_CONTACTS); // Hide first to update the contact cards.
        updateFilteredContactList(useBackSamePredicate ? oldPred : PREDICATE_SHOW_ALL_CONTACTS);
    }

    @Override
    public void rerenderEventCards(boolean useBackSamePredicate) {
        ModelHistory.HistoryInstance historyInstance = modelHistory.getCurrentHistoryInstance();
        Predicate<? super Event> oldPred = historyInstance.getDisplaySetting().getEventDisplayPredicate();
        updateFilteredEventList(PREDICATE_HIDE_ALL_EVENTS); // Hide first to update the event cards.
        updateFilteredEventList(useBackSamePredicate ? oldPred : PREDICATE_SHOW_ALL_EVENTS);
    }

    @Override
    public void rerenderAllCards() {
        rerenderContactCards(true);
        rerenderEventCards(true);
    }

    @Override
    public void removeAllLinks() {
        filteredEvents.forEach(event -> setEvent(event, event.clearAllLinks()));
        filteredContacts.forEach(contact -> setContact(contact, contact.clearAllLinks()));
    }
}
