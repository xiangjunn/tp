package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.UniqueContactList;
import seedu.address.model.event.Event;
import seedu.address.model.event.UniqueEventList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameContact comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueContactList contacts;
    private final UniqueEventList events;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        contacts = new UniqueContactList();
        events = new UniqueEventList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Contacts and Events in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the contact list with {@code contacts}.
     * {@code contacts} must not contain duplicate contacts.
     */
    public void setContacts(List<Contact> contacts) {
        this.contacts.setContacts(contacts);
    }

    /**
     * Replaces the contents of the event list with {@code events}.
     * {@code events} must not contain duplicate events.
     */
    public void setEvents(List<Event> events) {
        this.events.setEvents(events);
    }

    /**
     * Resets the existing data of contacts of this {@code AddressBook}.
     */
    public void resetContacts() {
        this.events.iterator()
            .forEachRemaining(Event::clearAllLinks);
        this.contacts.resetContacts();
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setContacts(newData.getContactList());
        setEvents(newData.getEventList());
    }

    /**
     * Bookmarks the contact indexed at {@code index}
     */
    public void bookmarkContact(Index index) {
        contacts.bookmarkContact(index);
    }

    /**
     * Places bookmarked {@code contacts} at the top of the list.
     */
    public void reshuffleContactsInOrder() {
        contacts.reshuffleContactsInOrder();
    }

    //// event-level operations

    /**
     * Returns true if an event with the same name as {@code event} exists in the address book.
     */
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return events.contains(event);
    }

    /**
     * Adds an event to the address book.
     * The event must not already exist in the address book.
     */
    public void addEvent(Event e) {
        events.add(e);
        Event.addToMap(e);
    }

    /**
     * Replaces the given event {@code target} in the list with {@code editedEvent}.
     * {@code target} must exist in the address book.
     * The event name of {@code editedEvent} must not be the same as another existing event in the address book.
     */
    public void setEvent(Event target, Event editedEvent) {
        requireNonNull(editedEvent);

        events.setEvent(target, editedEvent);
    }

    /**
     * Sorts the filtered event list to show all upcoming events. This will change the order of the filtered list
     * and remove any events which have concluded.
     */
    public void sortEvents() {
        events.sortEvents();
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeEvent(Event key) {
        // unlink all the contacts linked to event before removing event
        unlinkContactsFromEvent(key);
        events.remove(key);
    }

    /**
     * Unlink all the contacts linked to the given event.
     *
     * @param e The event from which to unlink all linked contacts.
     */
    public void unlinkContactsFromEvent(Event e) {
        Set<UUID> contactsUuid = e.getLinkedContacts();
        contactsUuid.iterator()
            .forEachRemaining(contactUuid -> Contact.findByUuid(contactUuid).unlink(e));
    }

    /**
     * Resets the existing data of events of this {@code AddressBook}.
     */
    public void resetEvents() {
        this.contacts.iterator()
            .forEachRemaining(Contact::clearAllLinks);
        this.events.resetEvents();
    }

    /**
     * Bookmarks the event indexed at {@code index}
     */
    public void bookmarkEvent(Index index) {
        events.bookmarkEvent(index);
    }

    /**
     * Places bookmarked {@code events} at the top of the list.
     */
    public void reshuffleEventsInOrder() {
        events.reshuffleEventsInOrder();
    }

    //// contact-level operations

    /**
     * Returns true if a contact with the same identity as {@code contact} exists in the address book.
     */
    public boolean hasContact(Contact contact) {
        requireNonNull(contact);
        return contacts.contains(contact);
    }

    /**
     * Adds a contact to the address book.
     * The contact must not already exist in the address book.
     */
    public void addContact(Contact c) {
        contacts.add(c);
        Contact.addToMap(c);
    }

    /**
     * Replaces the given contact {@code target} in the list with {@code editedContact}.
     * {@code target} must exist in the address book.
     * The contact name of {@code editedContact} must not be the same as another existing contact in the address book.
     */
    public void setContact(Contact target, Contact editedContact) {
        requireNonNull(editedContact);

        contacts.setContact(target, editedContact);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeContact(Contact key) {
        // unlink all the events linked to contact before removing contact
        unlinkEventsFromContact(key);
        contacts.remove(key);
    }

    /**
     * Unlink all the events linked to the given contact.
     *
     * @param c The contact from which to unlink all linked events.
     */
    public void unlinkEventsFromContact(Contact c) {
        Set<UUID> eventsUuid = c.getLinkedEvents();
        eventsUuid.iterator()
                .forEachRemaining(eventUuid -> Event.findByUuid(eventUuid).unlink(c));
    }
    /**
     * Bookmarks contact indexed at {@code index} in this {@code AddressBook}.
     */
    public void markContactAt(Index index) {
        contacts.bookmarkContact(index);
    }

    //// util methods

    @Override
    public String toString() {
        return contacts.asUnmodifiableObservableList().size() + " contacts\n"
                + events.asUnmodifiableObservableList().size() + " events";
        // TODO: refine later
    }

    @Override
    public ObservableList<Contact> getContactList() {
        return contacts.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Event> getEventList() {
        return events.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && contacts.equals(((AddressBook) other).contacts)
                && events.equals(((AddressBook) other).events));
    }

    @Override
    public int hashCode() {
        return contacts.hashCode();
    }
}
