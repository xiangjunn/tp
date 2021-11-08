package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONTACTS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.ALICE_MARKED;
import static seedu.address.testutil.TypicalContacts.AMY;
import static seedu.address.testutil.TypicalContacts.BENSON;
import static seedu.address.testutil.TypicalEvents.CS2100_CONSULTATION;
import static seedu.address.testutil.TypicalEvents.INTERVIEW;
import static seedu.address.testutil.TypicalEvents.TUTORIAL;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.contact.ContactContainsKeywordsPredicate;
import seedu.address.model.contact.ContactDisplaySetting;
import seedu.address.model.event.EventDisplaySetting;
import seedu.address.model.history.ModelHistoryException;
import seedu.address.testutil.AddressBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
        assertEquals(ContactDisplaySetting.DEFAULT_SETTING, modelManager.getContactDisplaySetting());
        assertEquals(EventDisplaySetting.DEFAULT_SETTING, modelManager.getEventDisplaySetting());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setEventDisplaySetting_nullSetting_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setEventDisplaySetting(null));
    }

    @Test
    public void setEventDisplaySetting_validSetting_setSuccess() {
        EventDisplaySetting setting = new EventDisplaySetting(true);
        modelManager.setEventDisplaySetting(setting);
        assertEquals(setting, modelManager.getEventDisplaySetting());
    }

    @Test
    public void setContactDisplaySetting_nullSetting_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setContactDisplaySetting(null));
    }

    @Test
    public void setContactDisplaySetting_validSetting_setSuccess() {
        ContactDisplaySetting setting = new ContactDisplaySetting(true);
        modelManager.setContactDisplaySetting(setting);
        assertEquals(setting, modelManager.getContactDisplaySetting());
    }

    @Test
    public void commitHistory_success() {
        modelManager.addContact(AMY);
        modelManager.setContactDisplaySetting(new ContactDisplaySetting(true));
        modelManager.setEventDisplaySetting(new EventDisplaySetting(true));
        modelManager.commitHistory();
        assertTrue(modelManager.hasContact(AMY));
        assertEquals(new ContactDisplaySetting(true), modelManager.getContactDisplaySetting());
        assertEquals(new EventDisplaySetting(true), modelManager.getEventDisplaySetting());
    }

    @Test
    public void isUndoable() {
        assertTrue(modelManager.isUndoable());
        modelManager.addEvent(INTERVIEW);
        assertTrue(modelManager.isUndoable());
        modelManager.undoHistory();
        assertFalse(modelManager.isUndoable());
    }

    @Test
    public void undo_noHistory_failure() {
        modelManager.clearHistory();
        assertThrows(ModelHistoryException.class, () -> modelManager.undoHistory());
    }

    @Test
    public void undoHistory_addEvent_success() {
        modelManager.setEventDisplaySetting(new EventDisplaySetting(true));
        modelManager.addEvent(TUTORIAL);
        modelManager.commitHistory();
        modelManager.undoHistory();
        assertFalse(modelManager.hasEvent(TUTORIAL));
        assertNotEquals(modelManager.getEventDisplaySetting(), new EventDisplaySetting(true));
    }

    @Test
    public void isRedoable() {
        assertFalse(modelManager.isRedoable());
        modelManager.addEvent(CS2100_CONSULTATION);
        modelManager.undoHistory();
        assertTrue(modelManager.isRedoable());
        modelManager.redoHistory();
        assertFalse(modelManager.isRedoable());
    }
    @Test
    public void redo_noUndo_failure() {
        assertThrows(ModelHistoryException.class, () -> modelManager.redoHistory());
    }

    @Test
    public void redo_clearHistory_failure() {
        modelManager.addEvent(CS2100_CONSULTATION);
        modelManager.commitHistory();
        modelManager.clearHistory();
        assertThrows(ModelHistoryException.class, () -> modelManager.redoHistory());
    }

    @Test
    public void redo_singleUndo_success() {
        modelManager.addEvent(INTERVIEW);
        modelManager.commitHistory();
        modelManager.undoHistory();
        modelManager.redoHistory();
        assertTrue(modelManager.hasEvent(INTERVIEW));
        assertEquals(ContactDisplaySetting.DEFAULT_SETTING, modelManager.getContactDisplaySetting());
        assertEquals(EventDisplaySetting.DEFAULT_SETTING, modelManager.getEventDisplaySetting());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasContact_nullContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasContact(null));
    }

    @Test
    public void hasContact_contactNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasContact(ALICE_MARKED));
    }

    @Test
    public void hasContact_contactInAddressBook_returnsTrue() {
        modelManager.addContact(ALICE_MARKED);
        assertTrue(modelManager.hasContact(ALICE_MARKED));
    }

    @Test
    public void getFilteredContactList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredContactList().remove(0));
    }

    @Test
    public void hasEvent_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasEvent(null));
    }

    @Test
    public void hasEvent_eventNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasEvent(TUTORIAL));
    }

    @Test
    public void hasEvent_contactInAddressBook_returnsTrue() {
        modelManager.addEvent(TUTORIAL);
        assertTrue(modelManager.hasEvent(TUTORIAL));
    }

    @Test
    public void getFilteredEventList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredEventList().remove(1));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withContact(ALICE_MARKED).withContact(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredContactList -> returns false
        String[] keywords = ALICE_MARKED.getName().fullName.split("\\s+");
        modelManager.updateFilteredContactList(new ContactContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));

        // different ContactDisplaySetting
        modelManager.setContactDisplaySetting(new ContactDisplaySetting(true));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // different EventDisplaySetting
        modelManager.setEventDisplaySetting(new EventDisplaySetting(true));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));
    }
}
