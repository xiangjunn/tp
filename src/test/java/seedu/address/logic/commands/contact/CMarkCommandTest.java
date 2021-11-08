package seedu.address.logic.commands.contact;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalContacts.ALICE_MARKED;
import static seedu.address.testutil.TypicalContacts.BENSON;
import static seedu.address.testutil.TypicalContacts.CARL;
import static seedu.address.testutil.TypicalContacts.DANIEL;
import static seedu.address.testutil.TypicalContacts.ELLE;
import static seedu.address.testutil.TypicalContacts.FIONA;
import static seedu.address.testutil.TypicalContacts.GEORGE;

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
import seedu.address.model.event.Event;
import seedu.address.testutil.ContactBuilder;
import seedu.address.testutil.TypicalEvents;


class CMarkCommandTest {

    private static final Contact ELLE_MARKED = new ContactBuilder(ELLE).withMarked(true).build();
    private Model expectedModel = new ModelManager(getAddressBookWith(getListWithMarkContact()), new UserPrefs());
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private List<Contact> getListWithMarkContact() {
        return new ArrayList<>(Arrays.asList(ELLE_MARKED, ALICE_MARKED, BENSON, CARL, DANIEL, FIONA, GEORGE));
    }

    public AddressBook getAddressBookWith(List<Contact> contactList) {
        AddressBook ab = new AddressBook();
        for (Contact contact : contactList) {
            ab.addContact(contact);
        }
        for (Event event : TypicalEvents.getTypicalEvents()) {
            ab.addEvent(event);
        }
        return ab;
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CMarkCommand(null));
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        String expectedMessage = String.format(CMarkCommand.MESSAGE_SUCCESS, ELLE) + "\n";
        List<Index> indexes = List.of(Index.fromOneBased(5));
        CMarkCommand cMarkCommand = new CMarkCommand(indexes);
        assertCommandSuccess(cMarkCommand, model, new CommandResult(expectedMessage), expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        List<Index> outOfBoundIndex = List.of(Index.fromOneBased(model.getFilteredContactList().size() + 1));
        CMarkCommand cMarkCommand = new CMarkCommand(outOfBoundIndex);
        assertCommandFailure(cMarkCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);

        List<Index> veryLargeNumberWithDuplicateDigit = List.of(Index.fromOneBased(1111111111));
        CMarkCommand secondMarkCommand = new CMarkCommand(veryLargeNumberWithDuplicateDigit);
        assertCommandFailure(secondMarkCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_contactAlreadyMarked() {
        List<Index> indexes = List.of(Index.fromOneBased(1));
        model = expectedModel;
        CMarkCommand cMarkCommand = new CMarkCommand(indexes);
        String expectedMessage = String.format(CMarkCommand.MESSAGE_ALREADY_MARKED, ELLE) + "\n";
        assertCommandSuccess(cMarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Index first = Index.fromOneBased(1);
        Index second = Index.fromOneBased(2);

        List<Index> firstIndexes = new ArrayList<>();
        firstIndexes.add(first);
        List<Index> secondIndexes = new ArrayList<>();
        secondIndexes.add(second);
        CMarkCommand markFirstCommand = new CMarkCommand(firstIndexes);
        CMarkCommand markSecondCommand = new CMarkCommand(secondIndexes);

        // same object -> returns true
        assertTrue(markFirstCommand.equals(markFirstCommand));

        // same values -> returns true
        CMarkCommand markFirstCommandCopy = new CMarkCommand(firstIndexes);
        ;
        assertTrue(markFirstCommand.equals(markFirstCommandCopy));

        // different types -> returns false
        assertFalse(markFirstCommand.equals(1));

        // null -> returns false
        assertFalse(markFirstCommand.equals(null));

        // different Index -> returns false
        assertFalse(markFirstCommand.equals(markSecondCommand));
    }

}
