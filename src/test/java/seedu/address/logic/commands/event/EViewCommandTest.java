package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.general.CommandTestUtil.showEventAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDisplaySetting;


public class EViewCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Event eventToView = model.getFilteredEventList().get(INDEX_FIRST.getZeroBased());
        EViewCommand eViewCommand = new EViewCommand(INDEX_FIRST);

        String expectedMessage = String.format(EViewCommand.MESSAGE_SUCCESS, eventToView);

        expectedModel.updateEventListByIndex(INDEX_FIRST);
        expectedModel.setEventDisplaySetting(new EventDisplaySetting(true));

        assertCommandSuccess(eViewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        EViewCommand eViewCommand = new EViewCommand(outOfBoundIndex);

        assertCommandFailure(eViewCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showEventAtIndex(model, INDEX_FIRST);

        Event eventToView = model.getFilteredEventList().get(INDEX_FIRST.getZeroBased());
        EViewCommand eViewCommand = new EViewCommand(INDEX_FIRST);

        String expectedMessage = String.format(EViewCommand.MESSAGE_SUCCESS, eventToView);

        expectedModel.updateEventListByIndex(INDEX_FIRST);
        expectedModel.setEventDisplaySetting(new EventDisplaySetting(true));

        assertCommandSuccess(eViewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showEventAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getEventList().size());

        EViewCommand eViewCommand = new EViewCommand(outOfBoundIndex);

        assertCommandFailure(eViewCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        EViewCommand viewFirstCommand = new EViewCommand(INDEX_FIRST);
        EViewCommand viewSecondCommand = new EViewCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        EViewCommand viewFirstCommandCopy = new EViewCommand(INDEX_FIRST);
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different event -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }
}
