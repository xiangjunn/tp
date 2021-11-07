package seedu.address.model.history;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_HIDE_ALL_EVENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONTACTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.address.testutil.TypicalContacts.HOON;
import static seedu.address.testutil.TypicalEvents.TUTORIAL;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.ModelDisplaySetting;
import seedu.address.model.contact.ContactDisplaySetting;
import seedu.address.model.event.EventDisplaySetting;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.TypicalAddressBook;

class ModelHistoryTest {

    private final ModelHistory history = new ModelHistory();

    @Test
    public void clearHistory_nonEmpty_success() {
        ModelHistory anotherHistory = new ModelHistory();
        AddressBook ab = TypicalAddressBook.getTypicalAddressBook();
        anotherHistory.commit(ab, new ModelDisplaySetting());
        anotherHistory.commit(new AddressBookBuilder(ab).withContact(HOON).build(), new ModelDisplaySetting());
        anotherHistory.clearHistory();
        assertEquals(anotherHistory.getCurrentSize(), history.getCurrentSize());
        assertEquals(anotherHistory.getMaxSize(), history.getMaxSize());
        assertEquals(anotherHistory.getCurrentSize(), 0);
        assertEquals(anotherHistory.getMaxSize(), 0);
    }

    @Test
    public void clearHistory_empty_success() {
        history.clearHistory();
        assertEquals(history.getCurrentSize(), 0);
        assertEquals(history.getMaxSize(), 0);
    }

    @Test
    public void commit_success() {
        history.clearHistory();
        AddressBook ab = TypicalAddressBook.getTypicalAddressBook();
        history.commit(ab, new ModelDisplaySetting());
        history.commit(new AddressBookBuilder(ab).withContact(HOON).build(), new ModelDisplaySetting());
        assertEquals(history.getMaxSize(), 2);
        assertEquals(history.getCurrentSize(), 2);
        assertEquals(history.getAllHistory(), List.of(new ModelHistory.HistoryInstance(
            ab, new ModelDisplaySetting()
        ), new ModelHistory.HistoryInstance(
            new AddressBookBuilder(ab).withContact(HOON).build(), new ModelDisplaySetting()
        )));
    }

    @Test
    public void undo_success() {
        AddressBook ab = TypicalAddressBook.getTypicalAddressBook();
        history.commit(ab, new ModelDisplaySetting());
        history.commit(new AddressBookBuilder(ab).withContact(HOON).build(), new ModelDisplaySetting());
        assertEquals(history.undo(), new ModelHistory.HistoryInstance(
            ab, new ModelDisplaySetting()));
        assertEquals(history.getCurrentSize(), 1);
        assertEquals(history.getMaxSize(), 2);
    }

    @Test
    public void undo_noHistory_failure() {
        history.clearHistory();
        assertThrows(ModelHistoryException.class, () -> history.undo());
    }

    @Test
    public void redo_singleUndo_success() {
        AddressBook ab = TypicalAddressBook.getTypicalAddressBook();
        history.commit(ab, new ModelDisplaySetting());
        history.commit(new AddressBookBuilder(ab).withContact(HOON).build(), new ModelDisplaySetting());
        history.undo();
        assertEquals(history.redo(), new ModelHistory.HistoryInstance(
            new AddressBookBuilder(ab).withContact(HOON).build(), new ModelDisplaySetting()));
        assertEquals(history.getCurrentSize(), 2);
        assertEquals(history.getMaxSize(), 2);
    }

    @Test
    public void redo_doubleUndo_success() {
        AddressBook ab = TypicalAddressBook.getTypicalAddressBook();
        history.commit(ab, new ModelDisplaySetting());
        history.commit(new AddressBookBuilder(ab).withContact(HOON).build(), new ModelDisplaySetting());
        history.commit(
            new AddressBookBuilder(ab).withContact(HOON).withEvent(TUTORIAL).build(),
            new ModelDisplaySetting(ContactDisplaySetting.DEFAULT_SETTING, new EventDisplaySetting(true),
                PREDICATE_SHOW_ALL_CONTACTS, PREDICATE_HIDE_ALL_EVENTS));
        history.undo();
        history.undo();
        assertEquals(history.redo(), new ModelHistory.HistoryInstance(
            new AddressBookBuilder(ab).withContact(HOON).build(), new ModelDisplaySetting()));
        assertEquals(history.getCurrentSize(), 2);
        assertEquals(history.getMaxSize(), 3);
    }

    @Test
    public void redo_noUndo_failure() {
        AddressBook ab = TypicalAddressBook.getTypicalAddressBook();
        history.commit(ab, new ModelDisplaySetting());
        history.commit(new AddressBookBuilder(ab).withContact(HOON).build(), new ModelDisplaySetting());
        assertThrows(ModelHistoryException.class, () -> history.redo());
        history.clearHistory();
        assertThrows(ModelHistoryException.class, () -> history.redo());
    }

    @Test
    public void isUndoable() {
        AddressBook ab = TypicalAddressBook.getTypicalAddressBook();
        history.commit(ab, new ModelDisplaySetting());
        history.commit(new AddressBookBuilder(ab).withContact(HOON).build(), new ModelDisplaySetting());
        assertTrue(history.isUndoable());
        history.undo();
        assertFalse(history.isUndoable());
    }

    @Test
    public void isRedoable() {
        AddressBook ab = TypicalAddressBook.getTypicalAddressBook();
        history.commit(ab, new ModelDisplaySetting());
        history.commit(new AddressBookBuilder(ab).withContact(HOON).build(), new ModelDisplaySetting());
        assertFalse(history.isRedoable());
        history.undo();
        assertTrue(history.isRedoable());
    }

    static class HistoryInstanceTest {
        @Test
        public void equal_test() {
            ModelDisplaySetting displaySetting =
                new ModelDisplaySetting(ContactDisplaySetting.DEFAULT_SETTING, EventDisplaySetting.DEFAULT_SETTING,
                    PREDICATE_SHOW_ALL_CONTACTS, PREDICATE_SHOW_ALL_EVENTS);
            ModelHistory.HistoryInstance instance = new ModelHistory.HistoryInstance(
                    TypicalAddressBook.getTypicalAddressBook(),
                    displaySetting);
            assertNotEquals(instance, null);
            assertNotEquals(instance, 1);
            assertEquals(instance, instance);
            assertNotEquals(instance, new ModelHistory.HistoryInstance(new AddressBook(), displaySetting));
        }
    }
}
