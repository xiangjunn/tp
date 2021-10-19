package seedu.address.logic.commands.contact;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.general.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalRanges.RANGE_FIRST_TO_FIRST;
import static seedu.address.testutil.TypicalRanges.RANGE_FIRST_TO_THIRD;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.core.range.Range;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class CDeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Contact contactToDelete = model.getFilteredContactList().get(INDEX_FIRST_PERSON.getZeroBased());
        CDeleteCommand cDeleteCommand = new CDeleteCommand(RANGE_FIRST_TO_FIRST);

        String expectedMessage = String.format(cDeleteCommand.MESSAGE_DELETE_CONTACT_SUCCESS, contactToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteContact(contactToDelete);

        assertCommandSuccess(cDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredContactList().size() + 1);
        Range rangeOfIndexes = Range.convertFromIndex(outOfBoundIndex);
        CDeleteCommand cDeleteCommand = new CDeleteCommand(rangeOfIndexes);

        assertCommandFailure(cDeleteCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Contact contactToDelete = model.getFilteredContactList().get(INDEX_FIRST_PERSON.getZeroBased());
        CDeleteCommand cDeleteCommand = new CDeleteCommand(RANGE_FIRST_TO_FIRST);

        String expectedMessage = String.format(cDeleteCommand.MESSAGE_DELETE_CONTACT_SUCCESS, contactToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteContact(contactToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(cDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getContactList().size());
        Range rangeOfIndexes = Range.convertFromIndex(outOfBoundIndex);

        CDeleteCommand cDeleteCommand = new CDeleteCommand(rangeOfIndexes);

        assertCommandFailure(cDeleteCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validRangeUnfilteredList_success() {
        Contact firstContactToDelete = model.getFilteredContactList().get(INDEX_FIRST_PERSON.getZeroBased());
        Contact secondContactToDelete = model.getFilteredContactList().get(INDEX_SECOND_PERSON.getZeroBased());
        Contact thirdContactToDelete = model.getFilteredContactList().get(INDEX_THIRD_PERSON.getZeroBased());
        CDeleteCommand cDeleteCommand = new CDeleteCommand(RANGE_FIRST_TO_THIRD);

        String expectedMessage = String.format(cDeleteCommand.MESSAGE_DELETE_CONTACT_SUCCESS, firstContactToDelete)
                + "\n"
                + String.format(cDeleteCommand.MESSAGE_DELETE_CONTACT_SUCCESS, secondContactToDelete)
                + "\n"
                + String.format(cDeleteCommand.MESSAGE_DELETE_CONTACT_SUCCESS, thirdContactToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteContact(firstContactToDelete);
        expectedModel.deleteContact(secondContactToDelete);
        expectedModel.deleteContact(thirdContactToDelete);

        assertCommandSuccess(cDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Range fromIndexOneToTwo = new Range(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        Range fromIndexOneToThree = new Range(INDEX_FIRST_PERSON, INDEX_THIRD_PERSON);
        CDeleteCommand deleteFirstCommand = new CDeleteCommand(fromIndexOneToTwo);
        CDeleteCommand deleteSecondCommand = new CDeleteCommand(fromIndexOneToThree);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        CDeleteCommand deleteFirstCommandCopy = new CDeleteCommand(fromIndexOneToTwo);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different contact -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredContactList(p -> false);

        assertTrue(model.getFilteredContactList().isEmpty());
    }
}
