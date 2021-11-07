package seedu.address.logic.commands.contact;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.general.CommandTestUtil.showContactAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactDisplaySetting;


public class CViewCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Contact contactToView = model.getFilteredContactList().get(INDEX_FIRST.getZeroBased());
        CViewCommand cViewCommand = new CViewCommand(INDEX_FIRST);

        String expectedMessage = String.format(CViewCommand.MESSAGE_SUCCESS, contactToView);

        expectedModel.updateContactListByIndex(INDEX_FIRST);
        expectedModel.setContactDisplaySetting(new ContactDisplaySetting(true));

        assertCommandSuccess(cViewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredContactList().size() + 1);
        CViewCommand cViewCommand = new CViewCommand(outOfBoundIndex);

        assertCommandFailure(cViewCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showContactAtIndex(model, INDEX_FIRST);

        Contact contactToView = model.getFilteredContactList().get(INDEX_FIRST.getZeroBased());
        CViewCommand cViewCommand = new CViewCommand(INDEX_FIRST);

        String expectedMessage = String.format(CViewCommand.MESSAGE_SUCCESS, contactToView);

        expectedModel.updateContactListByIndex(INDEX_FIRST);
        expectedModel.setContactDisplaySetting(new ContactDisplaySetting(true));

        assertCommandSuccess(cViewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showContactAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getContactList().size());

        CViewCommand cViewCommand = new CViewCommand(outOfBoundIndex);

        assertCommandFailure(cViewCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        CViewCommand viewFirstCommand = new CViewCommand(INDEX_FIRST);
        CViewCommand viewSecondCommand = new CViewCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        CViewCommand viewFirstCommandCopy = new CViewCommand(INDEX_FIRST);
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different contact -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }
}
