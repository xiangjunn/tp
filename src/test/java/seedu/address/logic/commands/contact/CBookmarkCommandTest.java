package seedu.address.logic.commands.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_CONTACTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.ContactNameContainsKeywordsPredicate;

class CBookmarkCommandTest {
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CBookmarkCommand(null));
    }


    @Test
    public void equals() {
        Index first = Index.fromOneBased(1);
        Index second = Index.fromOneBased(2);

        List<Index> firstIndexes = new ArrayList<>();
        firstIndexes.add(first);
        List<Index> secondIndexes = new ArrayList<>();
        secondIndexes.add(second);
        CBookmarkCommand bookmarkFirstCommand = new CBookmarkCommand(firstIndexes);
        CBookmarkCommand bookmarkSecondCommand = new CBookmarkCommand(secondIndexes);

        // same object -> returns true
        assertTrue(bookmarkFirstCommand.equals(bookmarkFirstCommand));

        // same values -> returns true
        CBookmarkCommand bookmarkFirstCommandCopy = new CBookmarkCommand(firstIndexes);
        ;
        assertTrue(bookmarkFirstCommand.equals(bookmarkFirstCommandCopy));

        // different types -> returns false
        assertFalse(bookmarkFirstCommand.equals(1));

        // null -> returns false
        assertFalse(bookmarkFirstCommand.equals(null));

        // different Index -> returns false
        assertFalse(bookmarkFirstCommand.equals(bookmarkSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 0);
        ContactNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        CFindCommand command = new CFindCommand(predicate);
        expectedModel.updateFilteredContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredContactList());
    }

    @Test
    public void execute_multipleCompleteKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 3);
        ContactNameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        CFindCommand command = new CFindCommand(predicate);
        expectedModel.updateFilteredContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredContactList());
    }

    @Test
    public void execute_multipleIncompleteKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 3);
        ContactNameContainsKeywordsPredicate predicate = preparePredicate("Ku Ell");
        CFindCommand command = new CFindCommand(predicate);
        expectedModel.updateFilteredContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredContactList());
    }

    /**
     * Parses {@code userInput} into a {@code ContactNameContainsKeywordsPredicate}.
     */
    private ContactNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new ContactNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

}
