package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ModelStub;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Event;
import seedu.address.testutil.EventBuilder;

class EAddCommandTest {

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EAddCommand(null));
    }

    @Test
    public void execute_eventAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        Event validEvent = new EventBuilder().build();

        CommandResult commandResult = new EAddCommand(validEvent).execute(modelStub);

        assertEquals(String.format(EAddCommand.MESSAGE_SUCCESS, validEvent), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validEvent), modelStub.eventsAdded);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() {
        Event validEvent = new EventBuilder().build();
        EAddCommand addCommand = new EAddCommand(validEvent);
        ModelStub modelStub = new ModelStubWithEvent(validEvent);

        assertThrows(CommandException.class, EAddCommand.MESSAGE_DUPLICATE_EVENT, () ->
                addCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidDateTimeRange_throwsCommandException() {
        Event event = new EventBuilder().withName("Outing").withStartDateAndTime("20-10-2021 20:00")
                .withEndDateAndTime("20-10-2021 18:00").build();
        ModelStub modelStub = new ModelStubAcceptingEventAdded();

        EAddCommand command = new EAddCommand(event);

        assertThrows(CommandException.class, EAddCommand.MESSAGE_INVALID_DATE_TIME_RANGE, () ->
                command.execute(modelStub));
    }

    @Test
    public void equals() {
        Event lecture = new EventBuilder().withName("Lecture").build();
        Event exam = new EventBuilder().withName("Exam").build();
        EAddCommand addLectureCommand = new EAddCommand(lecture);
        EAddCommand addExamCommand = new EAddCommand(exam);

        // same object -> returns true
        assertTrue(addLectureCommand.equals(addLectureCommand));

        // same values -> returns true
        EAddCommand addLectureCommandCopy = new EAddCommand(lecture);
        assertTrue(addLectureCommand.equals(addLectureCommandCopy));

        // different types -> returns false
        assertFalse(addLectureCommand.equals(1));

        // null -> returns false
        assertFalse(addLectureCommand.equals(null));

        // different event -> returns false
        assertFalse(addLectureCommand.equals(addExamCommand));
    }

    /**
     * A Model stub that contains a single event.
     */
    private class ModelStubWithEvent extends ModelStub {
        private final Event event;

        ModelStubWithEvent(Event event) {
            requireNonNull(event);
            this.event = event;
        }

        @Override
        public boolean hasEvent(Event event) {
            requireNonNull(event);
            return this.event.isSameEvent(event);
        }
    }

    /**
     * A Model stub that always accept the event being added.
     */
    private class ModelStubAcceptingEventAdded extends ModelStub {
        final ArrayList<Event> eventsAdded = new ArrayList<>();

        @Override
        public boolean hasEvent(Event event) {
            requireNonNull(event);
            return eventsAdded.stream().anyMatch(event::isSameEvent);
        }

        @Override
        public void addEvent(Event event) {
            requireNonNull(event);
            eventsAdded.add(event);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public void commitHistory() {}
    }
}
