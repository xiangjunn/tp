package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalContacts.ALICE_MARKED;
import static seedu.address.testutil.TypicalEvents.INTERVIEW;
import static seedu.address.testutil.TypicalEvents.TEAM_MEETING;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.exceptions.DuplicateContactException;
import seedu.address.model.event.Event;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.testutil.ContactBuilder;
import seedu.address.testutil.EventBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getContactList());
        assertEquals(Collections.emptyList(), addressBook.getEventList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateContacts_throwsDuplicateContactException() {
        // Two contacts with the same identity fields
        Contact editedAlice = new ContactBuilder(ALICE_MARKED).withAddress(VALID_ADDRESS_BOB)
            .withTags(VALID_TAG_HUSBAND).build();
        List<Contact> newContacts = Arrays.asList(ALICE_MARKED, editedAlice);
        List<Event> newEvents = new ArrayList<>(); //empty event list
        AddressBookStub newData = new AddressBookStub(newContacts, newEvents);

        assertThrows(DuplicateContactException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void resetData_withDuplicateEvents_throwsDuplicateEventException() {
        // Two events with the same name fields
        Event editedEvent = new EventBuilder(TEAM_MEETING).withAddress("New Address").build();
        List<Event> newEvents = Arrays.asList(TEAM_MEETING, editedEvent);
        List<Contact> newContacts = new ArrayList<>();
        AddressBookStub newData = new AddressBookStub(newContacts, newEvents);

        assertThrows(DuplicateEventException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasContact_nullContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasContact(null));
    }

    @Test
    public void hasEvent_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasEvent(null));
    }

    @Test
    public void hasContact_contactNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasContact(ALICE_MARKED));
    }

    @Test
    public void hasEvent_eventNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasEvent(INTERVIEW));
    }

    @Test
    public void hasContact_contactInAddressBook_returnsTrue() {
        addressBook.addContact(ALICE_MARKED);
        assertTrue(addressBook.hasContact(ALICE_MARKED));
    }

    @Test
    public void hasEvent_eventInAddressBook_returnsTrue() {
        addressBook.addEvent(INTERVIEW);
        assertTrue(addressBook.hasEvent(INTERVIEW));
    }

    @Test
    public void hasContact_contactWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addContact(ALICE_MARKED);
        Contact editedAlice =
            new ContactBuilder(ALICE_MARKED).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasContact(editedAlice));
    }

    @Test
    public void hasEvent_eventWithSameNameFieldsInAddressBook_returnsTrue() {
        addressBook.addEvent(INTERVIEW);
        Event editedEvent = new EventBuilder(INTERVIEW).withAddress("Google Office")
            .withStartDateAndTime("28-10-2021 11:00").build();
        assertTrue(addressBook.hasEvent(editedEvent));
    }

    @Test
    public void getContactList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getContactList().remove(0));
    }

    @Test
    public void getEventList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getEventList().remove(0));
    }

    @Test
    public void copy_success() {
        AddressBook copy = addressBook.copy();
        assertEquals(addressBook, copy);
    }

    /**
     * A stub ReadOnlyAddressBook whose contacts list and event list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Contact> contacts = FXCollections.observableArrayList();
        private final ObservableList<Event> events = FXCollections.observableArrayList();

        AddressBookStub(Collection<Contact> contacts, Collection<Event> events) {
            this.contacts.setAll(contacts);
            this.events.setAll(events);
        }

        @Override
        public ObservableList<Contact> getContactList() {
            return contacts;
        }

        @Override
        public ObservableList<Event> getEventList() {
            return events;
        }
    }
}
