package seedu.address.logic.commands.contact;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandFailure;
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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.event.Event;
import seedu.address.testutil.PersonBuilder;

class CUnmarkCommandTest {

    private static final Contact ELLE_BOOKMARKED = new PersonBuilder(ELLE).withBookmarked().build();

    private Model expectedModel;
    private Model model;

    private List<Contact> getListWithBookmarkContact() {
        return new ArrayList<>(Arrays.asList(ELLE_BOOKMARKED, ALICE, BENSON, CARL, DANIEL, FIONA, GEORGE));
    }

    private List<Contact> getListAfterUnmark() {
        return new ArrayList<>(Arrays.asList(ELLE, ALICE, BENSON, CARL, DANIEL, FIONA, GEORGE));
    }

    public AddressBook getAddressBookWith(List<Contact> contactList) {
        AddressBook ab = new AddressBook();
        for (Contact contact : contactList) {
            ab.addContact(contact);
        }
        for (Event event : getTypicalAddressBook().getEventList()) {
            ab.addEvent(event);
        }
        return ab;
    }

    @BeforeEach
    private void setUp() {
        expectedModel = new ModelManager(getAddressBookWith(getListAfterUnmark()), new UserPrefs());
        model = new ModelManager(getAddressBookWith(getListWithBookmarkContact()), new UserPrefs());
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CUnmarkCommand(null));
    }

    //@Test
    //public void execute_validIndexUnfilteredList_success() {
    //        String expectedMessage = String.format(CUnmarkCommand.MESSAGE_SUCCESS, ELLE_BOOKMARKED) + "\n";
    //        List<Index> indexes = List.of(Index.fromOneBased(1));
    //        CUnmarkCommand cunmarkCommand = new CUnmarkCommand(indexes);
    //        assertCommandSuccess(cunmarkCommand, model, new CommandResult(expectedMessage), expectedModel);
    //
    //        //TODO test with at least one marked contact remaining
    //}

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        List<Index> outOfBoundIndex = List.of(Index.fromOneBased(model.getFilteredContactList().size() + 1));
        CUnmarkCommand cunmarkCommand = new CUnmarkCommand(outOfBoundIndex);

        assertCommandFailure(cunmarkCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    //@Test
    //public void execute_contactNotMarked() {
    //        List<Index> indexes = List.of(Index.fromOneBased(1));
    //
    //        CUnmarkCommand cunmarkCommand = new CUnmarkCommand(indexes);
    //        String expectedMessage = String.format(CUnmarkCommand.MESSAGE_NOT_MARKED, ELLE) + "\n";
    //        assertCommandSuccess(cunmarkCommand, model, expectedMessage, expectedModel);
    //}

    @Test
    public void equals() {
        Index first = Index.fromOneBased(1);
        Index second = Index.fromOneBased(2);

        List<Index> firstIndexes = new ArrayList<>();
        firstIndexes.add(first);
        List<Index> secondIndexes = new ArrayList<>();
        secondIndexes.add(second);
        CUnmarkCommand unmarkFirstCommand = new CUnmarkCommand(firstIndexes);
        CUnmarkCommand unmarkSecondCommand = new CUnmarkCommand(secondIndexes);

        // same object -> returns true
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommand));

        // same values -> returns true
        CUnmarkCommand unmarkFirstCommandCopy = new CUnmarkCommand(firstIndexes);
        ;
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommandCopy));

        // different types -> returns false
        assertFalse(unmarkFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unmarkFirstCommand.equals(null));

        // different Index -> returns false
        assertFalse(unmarkFirstCommand.equals(unmarkSecondCommand));
    }

}
