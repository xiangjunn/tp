package seedu.address.logic.commands;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactDisplaySetting;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDisplaySetting;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    public static final String ERROR_MESSAGE = "This method should not be called.";

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public EventDisplaySetting getEventDisplaySetting() {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public void setEventDisplaySetting(EventDisplaySetting eventDisplaySetting) {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public ContactDisplaySetting getContactDisplaySetting() {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public void setContactDisplaySetting(ContactDisplaySetting displaySetting) {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public Path getAddressBookFilePath() {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook newData) {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        throw new AssertionError(ERROR_MESSAGE);
    }

    // manage versioned addressBook
    @Override
    public void commitHistory() {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public void undoHistory() {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public void redoHistory() {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public boolean isUndoable() {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public boolean isRedoable() {
        throw new AssertionError(ERROR_MESSAGE);
    }

    // manage contacts
    @Override
    public void addContact(Contact contact) {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public boolean hasContact(Contact contact) {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public void deleteContact(Contact target) {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public void setContact(Contact target, Contact editedContact) {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public void resetContacts() {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public ObservableList<Contact> getFilteredContactList() {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public void updateFilteredContactList(Predicate<? super Contact> predicate) {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public void updateContactListByIndex(Index index) {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public void rearrangeContactsInOrder(List<Contact> contacts, boolean isMark) {
        throw new AssertionError(ERROR_MESSAGE);
    }

    // manage events

    @Override
    public void addEvent(Event event) {
        throw new AssertionError(ERROR_MESSAGE);
    }


    @Override
    public boolean hasEvent(Event event) {
        throw new AssertionError(ERROR_MESSAGE);
    }


    @Override
    public void deleteEvent(Event target) {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public void setEvent(Event target, Event editedEvent) {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public ObservableList<Event> getFilteredEventList() {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public void resetEvents() {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public void updateFilteredEventList(Predicate<? super Event> predicate) {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public void sortUpcomingFilteredEventList() {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public void updateEventListByIndex(Index index) {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public void rearrangeEventsInOrder(List<Event> events, boolean isMark) {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public void linkEventAndContact(Event event, Contact contact) {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public void unlinkEventAndContact(Event event, Contact contact) {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public void unlinkAllContactsFromEvent(Event event) {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public void rerenderContactCards(boolean useBackSamePredicate) {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public void rerenderEventCards(boolean useBackSamePredicate) {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public void rerenderAllCards() {
        throw new AssertionError(ERROR_MESSAGE);
    }

    @Override
    public void removeAllLinks() {
        throw new AssertionError(ERROR_MESSAGE);
    }
}
