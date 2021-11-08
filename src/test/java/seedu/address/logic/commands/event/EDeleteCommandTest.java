package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.general.CommandTestUtil.showEventAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;
import static seedu.address.testutil.TypicalRanges.RANGE_FIRST_TO_FIRST;
import static seedu.address.testutil.TypicalRanges.RANGE_FIRST_TO_THIRD;
import static seedu.address.testutil.TypicalRanges.RANGE_SECOND_TO_THIRD;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.core.range.Range;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventChanger;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code EDeleteCommand}.
 */
public class EDeleteCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Event eventToDelete = model.getFilteredEventList().get(INDEX_FIRST.getZeroBased());
        EDeleteCommand eDeleteCommand = new EDeleteCommand(RANGE_FIRST_TO_FIRST);

        String expectedMessage = String.format(EDeleteCommand.MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete);
        EventChanger eventChanger = EventChanger.deleteEventChanger(eventToDelete);
        expectedModel.deleteEvent(eventToDelete);

        assertCommandSuccess(eDeleteCommand, model, new CommandResult(expectedMessage, List.of(eventChanger)),
            expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        Range rangeOfIndexes = Range.convertFromIndex(outOfBoundIndex);
        EDeleteCommand eDeleteCommand = new EDeleteCommand(rangeOfIndexes);

        assertCommandFailure(eDeleteCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showEventAtIndex(model, INDEX_FIRST);

        Event eventToDelete = model.getFilteredEventList().get(INDEX_FIRST.getZeroBased());
        EDeleteCommand eDeleteCommand = new EDeleteCommand(RANGE_FIRST_TO_FIRST);

        String expectedMessage = String.format(EDeleteCommand.MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete);
        EventChanger eventChanger = EventChanger.deleteEventChanger(eventToDelete);
        expectedModel.deleteEvent(eventToDelete);
        showNoEvent(expectedModel);

        assertCommandSuccess(eDeleteCommand, model, new CommandResult(expectedMessage, List.of(eventChanger)),
            expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showEventAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getEventList().size());
        Range rangeOfIndexes = Range.convertFromIndex(outOfBoundIndex);

        EDeleteCommand eDeleteCommand = new EDeleteCommand(rangeOfIndexes);

        assertCommandFailure(eDeleteCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validRangeUnfilteredList_success() {
        Event firstEventToDelete = model.getFilteredEventList().get(INDEX_FIRST.getZeroBased());
        Event secondEventToDelete = model.getFilteredEventList().get(INDEX_SECOND.getZeroBased());
        Event thirdEventToDelete = model.getFilteredEventList().get(INDEX_THIRD.getZeroBased());
        EDeleteCommand eDeleteCommand = new EDeleteCommand(RANGE_FIRST_TO_THIRD);

        String expectedMessage = String.format(EDeleteCommand.MESSAGE_DELETE_EVENT_SUCCESS, firstEventToDelete)
            + "\n"
            + String.format(EDeleteCommand.MESSAGE_DELETE_EVENT_SUCCESS, secondEventToDelete)
            + "\n"
            + String.format(EDeleteCommand.MESSAGE_DELETE_EVENT_SUCCESS, thirdEventToDelete);

        EventChanger eventChangerOne = EventChanger.deleteEventChanger(firstEventToDelete);
        EventChanger eventChangerTwo = EventChanger.deleteEventChanger(secondEventToDelete);
        EventChanger eventChangerThree = EventChanger.deleteEventChanger(thirdEventToDelete);
        expectedModel.deleteEvent(firstEventToDelete);
        expectedModel.deleteEvent(secondEventToDelete);
        expectedModel.deleteEvent(thirdEventToDelete);

        assertCommandSuccess(eDeleteCommand, model,
            new CommandResult(expectedMessage, List.of(eventChangerOne, eventChangerTwo, eventChangerThree)),
                expectedModel);
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

