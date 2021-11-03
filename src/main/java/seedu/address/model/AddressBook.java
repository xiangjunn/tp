package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javafx.collections.ObservableList;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.UniqueContactList;
import seedu.address.model.event.Event;
import seedu.address.model.event.UniqueEventList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameContact comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {
    private static final ArrayList<AddressBook> addressBookStateList = new ArrayList<>();
    private static int currentPointer = 0;

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

    //// manage addressBookStates

    /**
     * Gets the current version of AddressBook
     * @return current version of AddressBook
     */
    public static AddressBook getCurrentAddressBook() {
        if (addressBookStateList.isEmpty()) {
            currentPointer = 0;
            addressBookStateList.add(new AddressBook());
        }
        assert !addressBookStateList.isEmpty() : "addressBookStateList should have been initialised here";
        assert currentPointer >= 0 && currentPointer <= addressBookStateList.size() - 1;
        return addressBookStateList.get(currentPointer);
    }

    /**
     * Clear history of addressBook when exit the app
     */
    public static void clearHistory() {
        addressBookStateList.clear();
        currentPointer = 0;
    }

    /**
     * Save the current state of address book to the addressBookStateList
     */
    public void commit() {
        if (addressBookStateList.isEmpty()) {
            getCurrentAddressBook();
        }
        assert !addressBookStateList.isEmpty();
        assert currentPointer >= 0 && currentPointer < addressBookStateList.size();

        AddressBook currentAddressBook = this.copy();
        if (currentPointer < addressBookStateList.size() - 1) {
            addressBookStateList.set(currentPointer + 1, currentAddressBook);
            for (int i = currentPointer + 2; i < addressBookStateList.size(); i++) {
                addressBookStateList.set(i, null);
            }
        } else {
            addressBookStateList.add(currentAddressBook);
        }
        currentPointer++;
    }

    /**
     * Check if the current version of addressBook is undoable
     * @return false if addressBook is already at its original state or if currentIndex is out of range
     */
    public boolean isUndoable() {
        return currentPointer > 0 && currentPointer < addressBookStateList.size();
    }

    /**
     * Restore the previous address book state from the addressBookStateList
     */
    public void undo() {
        assert isUndoable() : "AddressBook should be undoable when this method is called.";
        currentPointer--;
    }

    /**
     * Create a copy of the current addressBook
     * @return a copy of current addressBook
     */
    public AddressBook copy() {
        AddressBook toCopy = new AddressBook();
        toCopy.setContacts(contacts.copy());
        toCopy.setEvents(events.copy());
        return toCopy;
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
        this.events.iterator().forEachRemaining(Event::clearAllLinks);
        this.contacts.iterator().forEachRemaining(Contact::clearAllLinks);
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
     * Unlink all the contacts linked to the given event {@code e}, but does not remove the stored links in the event.
     */
    public void unlinkContactsFromEvent(Event e) {
        Set<UUID> contactsUuid = e.getLinkedContacts();
        contactsUuid.iterator()
            .forEachRemaining(contactUuid -> {
                Contact linkedContact = Contact.findByUuid(contactUuid);
                linkedContact.unlink(e);
            });
        e.clearAllLinks();
    }

    /**
     * Resets the existing data of events of this {@code AddressBook}.
     */
    public void resetEvents() {
        this.contacts.iterator().forEachRemaining(Contact::clearAllLinks);
        this.events.iterator().forEachRemaining(Event::clearAllLinks);
        this.events.resetEvents();
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
     * Unlink all the events linked to the given contact {@code c}, but does not remove the stored links in the contact.
     */
    public void unlinkEventsFromContact(Contact c) {
        Set<UUID> eventsUuid = c.getLinkedEvents();
        eventsUuid.iterator()
            .forEachRemaining(eventUuid -> {
                Event linkedEvent = Event.findByUuid(eventUuid);
                linkedEvent.unlink(c);
            });
        c.clearAllLinks();
    }

    //// util methods

    @Override
    public String toString() {
        return contacts.asUnmodifiableObservableList().size() + " contacts\n"
                + events.asUnmodifiableObservableList().size() + " events";
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
