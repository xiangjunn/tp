package seedu.address.logic.commands.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_CONTACTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.ContactNameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code CFindCommand}.
 */
public class CFindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        ContactNameContainsKeywordsPredicate firstPredicate =
                new ContactNameContainsKeywordsPredicate(Collections.singletonList("first"));
        ContactNameContainsKeywordsPredicate secondPredicate =
                new ContactNameContainsKeywordsPredicate(Collections.singletonList("second"));

        CFindCommand findFirstCommand = new CFindCommand(firstPredicate);
        CFindCommand findSecondCommand = new CFindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        CFindCommand findFirstCommandCopy = new CFindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different contact -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
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
