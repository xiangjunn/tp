package seedu.address.logic.commands.contact;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.testutil.PersonBuilder;

class CBookmarkCommandTest {
    private static final Contact ELLE_BOOKMARKED = new PersonBuilder(ELLE).withBookmarked().build();
    private Model expectedModel = new ModelManager(getAddressBookWith(getListWithBookmarkContact()), new UserPrefs());
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private List<Contact> getListWithBookmarkContact() {
        return new ArrayList<>(Arrays.asList(ELLE_BOOKMARKED, ALICE, BENSON, CARL, DANIEL, FIONA, GEORGE));
    }

    public AddressBook getAddressBookWith(List<Contact> contactList) {
        AddressBook ab = new AddressBook();
        for (Contact contact : contactList) {
            ab.addContact(contact);
        }
        return ab;
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CBookmarkCommand(null));
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        String expectedMessage = String.format(CBookmarkCommand.MESSAGE_SUCCESS, ELLE) + "\n";
        List<Index> indexes = List.of(Index.fromOneBased(5));
        CBookmarkCommand cBookmarkCommand = new CBookmarkCommand(indexes);
        assertCommandSuccess(cBookmarkCommand, model, new CommandResult(expectedMessage), expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        List<Index> outOfBoundIndex = List.of(Index.fromOneBased(model.getFilteredContactList().size() + 1));
        CBookmarkCommand cbookmarkCommand = new CBookmarkCommand(outOfBoundIndex);

        assertCommandFailure(cbookmarkCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_contactAlreadyMarked() {
        List<Index> indexes = List.of(Index.fromOneBased(1));
        model = expectedModel;
        CBookmarkCommand cbookmarkCommand = new CBookmarkCommand(indexes);
        String expectedMessage = String.format(CBookmarkCommand.MESSAGE_ALREADY_MARKED, ELLE) + "\n";
        assertCommandSuccess(cbookmarkCommand, model, expectedMessage, expectedModel);
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

}
