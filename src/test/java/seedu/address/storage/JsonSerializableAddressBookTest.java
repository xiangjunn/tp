package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.contact.Contact;
import seedu.address.testutil.TypicalContacts;


public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths
            .get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_CONTACTS_FILE = TEST_DATA_FOLDER
            .resolve("typicalContactsAndEventsAddressBook.json");
    private static final Path INVALID_CONTACT_FILE = TEST_DATA_FOLDER
            .resolve("invalidContactAndEventAddressBook.json");
    private static final Path DUPLICATE_CONTACT_FILE = TEST_DATA_FOLDER
            .resolve("duplicateContactAndEventAddressBook.json");

    @Test
    public void toModelType_typicalContactsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_CONTACTS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalContactsAndEventAddressBook = TypicalContacts.getTypicalAddressBook();
        AddressBook contactOnlyAddressBook = new AddressBook();
        for (Contact p : addressBookFromFile.getContactList()) {
            contactOnlyAddressBook.addContact(p);
        }
        assertEquals(contactOnlyAddressBook, typicalContactsAndEventAddressBook);
    }

    @Test
    public void toModelType_invalidContactFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_CONTACT_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateContacts_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_CONTACT_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_CONTACT,
                dataFromFile::toModelType);
    }

    //TODO implement these

    @Test
    public void toModelType_typicalEventsFile_success() {
    }

    @Test
    public void toModelType_invalidEventFile_throwsIllegalValueException() throws Exception {
    }

    @Test
    public void toModelType_duplicateEvents_throwsIllegalValueException() throws Exception {
    }
}
