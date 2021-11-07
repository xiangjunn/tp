package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalEvents.BIRTHDAY_PARTY;
import static seedu.address.testutil.TypicalEvents.CS2100_CONSULTATION;
import static seedu.address.testutil.TypicalEvents.CS2101_MEETING;
import static seedu.address.testutil.TypicalEvents.CS2103_MIDTERM_MARKED;
import static seedu.address.testutil.TypicalEvents.FOOTBALL_PRACTICE;
import static seedu.address.testutil.TypicalEvents.TEAM_MEETING;

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
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.TypicalContacts;

class EMarkCommandTest {

    private static final Event TEAM_MEETING_MARKED = new EventBuilder(TEAM_MEETING).withMarked(true).build();

    private Model expectedModel = new ModelManager(getAddressBookWith(getListWithMarkEvent()), new UserPrefs());
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private List<Event> getListWithMarkEvent() {
        return new ArrayList<>(Arrays.asList(TEAM_MEETING_MARKED, CS2103_MIDTERM_MARKED, CS2100_CONSULTATION,
            CS2101_MEETING, FOOTBALL_PRACTICE, BIRTHDAY_PARTY));
    }

    public AddressBook getAddressBookWith(List<Event> eventList) {
        AddressBook ab = new AddressBook();
        for (Contact contact : TypicalContacts.getTypicalContacts()) {
            ab.addContact(contact);
        }
        for (Event event : eventList) {
            ab.addEvent(event);
        }
        return ab;
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EMarkCommand(null));
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        String expectedMessage = String.format(EMarkCommand.MESSAGE_SUCCESS, TEAM_MEETING) + "\n";
        List<Index> indexes = List.of(Index.fromOneBased(5));
        EMarkCommand eMarkCommand = new EMarkCommand(indexes);
        assertCommandSuccess(eMarkCommand, model, new CommandResult(expectedMessage), expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        List<Index> outOfBoundIndex = List.of(Index.fromOneBased(model.getFilteredEventList().size() + 1));
        EMarkCommand eMarkCommand = new EMarkCommand(outOfBoundIndex);
        assertCommandFailure(eMarkCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);

        List<Index> veryLargeNumberWithDuplicateDigit = List.of(Index.fromOneBased(1111111111));
        EMarkCommand secondMarkCommand = new EMarkCommand(veryLargeNumberWithDuplicateDigit);
        assertCommandFailure(secondMarkCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_eventAlreadyMarked() {
        List<Index> indexes = List.of(Index.fromOneBased(1));
        model = expectedModel;
        EMarkCommand eMarkCommand = new EMarkCommand(indexes);
        String expectedMessage = String.format(EMarkCommand.MESSAGE_ALREADY_MARKED, TEAM_MEETING_MARKED) + "\n";
        assertCommandSuccess(eMarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Index first = Index.fromOneBased(1);
        Index second = Index.fromOneBased(2);

        List<Index> firstIndexes = new ArrayList<>();
        firstIndexes.add(first);
        List<Index> secondIndexes = new ArrayList<>();
        secondIndexes.add(second);
        EMarkCommand markFirstCommand = new EMarkCommand(firstIndexes);
        EMarkCommand markSecondCommand = new EMarkCommand(secondIndexes);

        // same object -> returns true
        assertTrue(markFirstCommand.equals(markFirstCommand));

        // same values -> returns true
        EMarkCommand markFirstCommandCopy = new EMarkCommand(firstIndexes);
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
