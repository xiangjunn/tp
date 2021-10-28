package seedu.address.testutil;

import static seedu.address.testutil.TypicalEvents.getTypicalEvents;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import seedu.address.model.AddressBook;
import seedu.address.model.contact.Contact;
import seedu.address.model.event.Event;

public class TypicalAddressBook {
    private TypicalAddressBook() {}

    /**
     * Returns an {@code AddressBook} with all the typical events and contacts.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Event event : getTypicalEvents()) {
            ab.addEvent(event);
        }
        for (Contact contact : getTypicalPersons()) {
            ab.addContact(contact);
        }
        return ab;
    }
}
