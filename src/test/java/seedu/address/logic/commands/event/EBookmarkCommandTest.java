package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

class EBookmarkCommandTest {
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EBookmarkCommand(null));
    }


    @Test
    public void equals() {
        Index first = Index.fromOneBased(1);
        Index second = Index.fromOneBased(2);

        List<Index> firstIndexes = new ArrayList<>();
        firstIndexes.add(first);
        List<Index> secondIndexes = new ArrayList<>();
        secondIndexes.add(second);
        EBookmarkCommand bookmarkFirstCommand = new EBookmarkCommand(firstIndexes);
        EBookmarkCommand bookmarkSecondCommand = new EBookmarkCommand(secondIndexes);

        // same object -> returns true
        assertTrue(bookmarkFirstCommand.equals(bookmarkFirstCommand));

        // same values -> returns true
        EBookmarkCommand bookmarkFirstCommandCopy = new EBookmarkCommand(firstIndexes);
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
    }

}
