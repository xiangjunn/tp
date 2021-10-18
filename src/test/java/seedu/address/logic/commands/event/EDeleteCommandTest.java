package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.general.CommandTestUtil.showEventAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalRanges.RANGE_FIRST_TO_FIRST;
import static seedu.address.testutil.TypicalRanges.RANGE_SECOND_TO_THIRD;
import static seedu.address.testutil.TypicalEvents.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code EDeleteCommand}.
 */
public class EDeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    //Todo : change INDEX_FIRST_PERSON to a general index for both contacts and events
    @Test
    public void execute_validIndexUnfilteredList_success() {
        Event eventToDelete = model.getFilteredEventList().get(INDEX_FIRST_PERSON.getZeroBased());
        EDeleteCommand eDeleteCommand = new EDeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(eDeleteCommand.MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);

        assertCommandSuccess(eDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        EDeleteCommand eDeleteCommand = new EDeleteCommand(outOfBoundIndex);

        assertCommandFailure(eDeleteCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showEventAtIndex(model, INDEX_FIRST_PERSON);

        Event eventToDelete = model.getFilteredEventList().get(INDEX_FIRST_PERSON.getZeroBased());
        EDeleteCommand eDeleteCommand = new EDeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(eDeleteCommand.MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);
        showNoEvent(expectedModel);

        assertCommandSuccess(eDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showEventAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getEventList().size());

        EDeleteCommand eDeleteCommand = new EDeleteCommand(outOfBoundIndex);

        assertCommandFailure(eDeleteCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        EDeleteCommand deleteFirstCommand = new EDeleteCommand(RANGE_FIRST_TO_FIRST);
        EDeleteCommand deleteSecondToThirdCommand = new EDeleteCommand(RANGE_SECOND_TO_THIRD);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        EDeleteCommand deleteFirstCommandCopy = new EDeleteCommand(RANGE_FIRST_TO_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different event -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondToThirdCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoEvent(Model model) {
        model.updateFilteredEventList(p -> false);

        assertTrue(model.getFilteredEventList().isEmpty());
    }
}

