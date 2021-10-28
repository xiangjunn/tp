package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.BIRTHDAY_PARTY;
import static seedu.address.testutil.TypicalEvents.CS2100_CONSULTATION;
import static seedu.address.testutil.TypicalEvents.CS2101_MEETING;
import static seedu.address.testutil.TypicalEvents.CS2103_MIDTERM;
import static seedu.address.testutil.TypicalEvents.FOOTBALL_PRACTICE;
import static seedu.address.testutil.TypicalEvents.TEAM_MEETING;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.testutil.EventBuilder;

class EUnmarkCommandTest {

    private static final Event TEAM_MEETING_BOOKMARKED = new EventBuilder(TEAM_MEETING).withBookmarked().build();

    private Model expectedModel = new ModelManager(getAddressBookWith(getListAfterUnmark()), new UserPrefs());
    private Model model = new ModelManager(getAddressBookWith(getListWithBookmarkEvent()), new UserPrefs());

    private List<Event> getListWithBookmarkEvent() {
        return new ArrayList<>(Arrays.asList(TEAM_MEETING_BOOKMARKED, CS2103_MIDTERM, CS2100_CONSULTATION,
                CS2101_MEETING, FOOTBALL_PRACTICE, BIRTHDAY_PARTY));
    }

    private List<Event> getListAfterUnmark() {
        return new ArrayList<>(Arrays.asList(TEAM_MEETING, CS2103_MIDTERM, CS2100_CONSULTATION,
                CS2101_MEETING, FOOTBALL_PRACTICE, BIRTHDAY_PARTY));
    }

    public static AddressBook getAddressBookWith(List<Event> eventList) {
        AddressBook ab = new AddressBook();
        for (Event event : eventList) {
            ab.addEvent(event);
        }
        return ab;
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EUnmarkCommand(null));
    }

    //@Test
    //public void execute_validIndexUnfilteredList_success() {
    //        String expectedMessage = String.format(EUnmarkCommand.MESSAGE_SUCCESS, TEAM_MEETING_BOOKMARKED) + "\n";
    //        List<Index> indexes = List.of(Index.fromOneBased(1));
    //        EUnmarkCommand eUnmarkCommand = new EUnmarkCommand(indexes);
    //        assertCommandSuccess(eUnmarkCommand, model, new CommandResult(expectedMessage), expectedModel);
    //
    //        //TODO test with at least one marked event remaining
    //}

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        List<Index> outOfBoundIndex = List.of(Index.fromOneBased(model.getFilteredEventList().size() + 1));
        EUnmarkCommand eunmarkCommand = new EUnmarkCommand(outOfBoundIndex);
        assertCommandFailure(eunmarkCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    //@Test
    //public void execute_eventNotMarked() {
    //        List<Index> indexes = List.of(Index.fromOneBased(1));
    //        model = expectedModel;
    //        EUnmarkCommand eunmarkCommand = new EUnmarkCommand(indexes);
    //        String expectedMessage = String.format(EUnmarkCommand.MESSAGE_NOT_MARKED, TEAM_MEETING) + "\n";
    //        assertCommandSuccess(eunmarkCommand, model, expectedMessage, expectedModel);
    //}

    @Test
    public void equals() {
        Index first = Index.fromOneBased(1);
        Index second = Index.fromOneBased(2);

        List<Index> firstIndexes = new ArrayList<>();
        firstIndexes.add(first);
        List<Index> secondIndexes = new ArrayList<>();
        secondIndexes.add(second);
        EUnmarkCommand unmarkFirstCommand = new EUnmarkCommand(firstIndexes);
        EUnmarkCommand unmarkSecondCommand = new EUnmarkCommand(secondIndexes);

        // same object -> returns true
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommand));

        // same values -> returns true
        EUnmarkCommand unmarkFirstCommandCopy = new EUnmarkCommand(firstIndexes);
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
