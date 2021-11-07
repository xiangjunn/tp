package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.general.CommandTestUtil.DESC_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.DESC_TUTORIAL;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_NAME_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_NAME_TUTORIAL;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_START_DATE_TIME_TUTORIAL;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_TAG_COOL;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.general.CommandTestUtil.showEventAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.event.EEditCommand.EditEventDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventChanger;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditEventDescriptorBuilder;
import seedu.address.testutil.EventBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EEditCommand.
 */
class EEditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Event editedEvent = new EventBuilder().build();
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder(editedEvent,
            null, true).build();
        EEditCommand eEditCommand = new EEditCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EEditCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        EventChanger eventChanger = EventChanger.editEventChanger(model.getFilteredEventList().get(0), editedEvent);
        expectedModel.setEvent(model.getFilteredEventList().get(0), editedEvent);

        assertCommandSuccess(eEditCommand, model, new CommandResult(expectedMessage, List.of(eventChanger)),
            expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastEvent = Index.fromOneBased(model.getFilteredEventList().size());
        Event lastEvent = model.getFilteredEventList().get(indexLastEvent.getZeroBased());

        EventBuilder eventInList = new EventBuilder(lastEvent);
        Event editedEvent = eventInList.withName(VALID_NAME_TUTORIAL)
            .withStartDateAndTime(VALID_START_DATE_TIME_TUTORIAL).withTags(VALID_TAG_COOL).build();

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withName(VALID_NAME_TUTORIAL)
            .withStartDateTime(VALID_START_DATE_TIME_TUTORIAL).withDeleteAllTags(true)
            .withTags(VALID_TAG_COOL).build();
        EEditCommand eEditCommand = new EEditCommand(indexLastEvent, descriptor);

        String expectedMessage = String.format(EEditCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        expectedModel.setEvent(lastEvent, editedEvent);

        EventChanger eventChanger = EventChanger.editEventChanger(lastEvent, editedEvent);

        assertCommandSuccess(eEditCommand, model, new CommandResult(expectedMessage, List.of(eventChanger)),
            expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EEditCommand eEditCommand = new EEditCommand(INDEX_FIRST, new EditEventDescriptor());
        Event editedEvent = model.getFilteredEventList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EEditCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        EventChanger eventChanger = EventChanger.editEventChanger(editedEvent, editedEvent);

        assertCommandSuccess(eEditCommand, model, new CommandResult(expectedMessage, List.of(eventChanger)),
            expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showEventAtIndex(model, INDEX_FIRST);

        Event eventInFilteredList = model.getFilteredEventList().get(INDEX_FIRST.getZeroBased());
        Event editedEvent = new EventBuilder(eventInFilteredList).withName(VALID_NAME_EXAM).build();
        EEditCommand eEditCommand = new EEditCommand(
            INDEX_FIRST,
            new EditEventDescriptorBuilder().withName(VALID_NAME_EXAM).build());

        String expectedMessage = String.format(EEditCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        expectedModel.setEvent(model.getFilteredEventList().get(0), editedEvent);

        EventChanger eventChanger = EventChanger.editEventChanger(eventInFilteredList, editedEvent);

        assertCommandSuccess(eEditCommand, model, new CommandResult(expectedMessage, List.of(eventChanger)),
            expectedModel);
    }

    @Test
    public void execute_duplicateEventUnfilteredList_failure() {
        Event firstEvent = model.getFilteredEventList().get(INDEX_FIRST.getZeroBased());
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder(firstEvent, Set.of(),
            false).build();
        EEditCommand eEditCommand = new EEditCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(eEditCommand, model, EEditCommand.MESSAGE_DUPLICATE_EVENT);
    }

    @Test
    public void execute_duplicateEventFilteredList_failure() {
        showEventAtIndex(model, INDEX_FIRST);

        // edit event in filtered list into a duplicate in address book
        Event eventInList = model.getAddressBook().getEventList().get(INDEX_SECOND.getZeroBased());
        EEditCommand eEditCommand = new EEditCommand(
            INDEX_FIRST,
            new EditEventDescriptorBuilder(eventInList, Set.of(), false).build());

        assertCommandFailure(eEditCommand, model, EEditCommand.MESSAGE_DUPLICATE_EVENT);
    }

    @Test
    public void execute_invalidEventIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withName(VALID_NAME_EXAM)
            .build();
        EEditCommand eEditCommand = new EEditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(eEditCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidEventIndexFilteredList_failure() {
        showEventAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getEventList().size());

        EEditCommand eEditCommand = new EEditCommand(
            outOfBoundIndex,
            new EditEventDescriptorBuilder().withName(VALID_NAME_EXAM).build());

        assertCommandFailure(eEditCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidDateTimeRangeEvent_failure() {
        EEditCommand eEditCommand = new EEditCommand(INDEX_FIRST, new EditEventDescriptorBuilder()
            .withStartDateTime("20-10-2021 20:00").withEndDateTime("20-10-2021 18:00").build());
        assertCommandFailure(eEditCommand, model, EEditCommand.MESSAGE_INVALID_DATE_TIME_RANGE);
    }

    @Test
    public void execute_tagToDeleteNotInOriginalEvent_success() {
        Tag toDelete = new Tag("notInOriginal");
        Event editedEvent = new EventBuilder().withMarked(false).build();
        EEditCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder(editedEvent,
            Set.of(toDelete), false).build();
        // the index must not have any tags initially (check TypicalEvents)
        EEditCommand eEditCommand = new EEditCommand(INDEX_FOURTH, descriptor);
        String expectedMessage = String.format(EEditCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent)
            + "\nNote:\n" + String.format(EEditCommand.MESSAGE_TAG_TO_DELETE_NOT_IN_ORIGINAL, toDelete);
        EventChanger eventChanger = EventChanger.editEventChanger(model.getFilteredEventList().get(3), editedEvent);
        Model expectedModel = new ModelManager(new AddressBook(getTypicalAddressBook()), new UserPrefs());
        expectedModel.setEvent(model.getFilteredEventList().get(3), editedEvent);
        assertCommandSuccess(eEditCommand, model, new CommandResult(expectedMessage, List.of(eventChanger)),
            expectedModel);
    }

    @Test
    public void execute_tagToAddAlreadyInOriginalEvent_success() {
        Tag toAdd = new Tag("exams");
        Event editedEvent = new EventBuilder().withTags("exams").build();
        EEditCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder(editedEvent,
            null, false).build();
        // the index must not have any tags initially (check TypicalEvents)
        EEditCommand eEditCommand = new EEditCommand(INDEX_FIRST, descriptor);
        String expectedMessage = String.format(EEditCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent)
            + "\nNote:\n" + String.format(EEditCommand.MESSAGE_TAG_TO_ADD_ALREADY_IN_ORIGINAL, toAdd);
        EventChanger eventChanger = EventChanger.editEventChanger(model.getFilteredEventList().get(0), editedEvent);
        Model expectedModel = new ModelManager(new AddressBook(getTypicalAddressBook()), new UserPrefs());
        expectedModel.setEvent(model.getFilteredEventList().get(0), editedEvent);
        assertCommandSuccess(eEditCommand, model, new CommandResult(expectedMessage, List.of(eventChanger)),
            expectedModel);
    }

    @Test
    public void equals() {
        final EEditCommand standardCommand = new EEditCommand(INDEX_FIRST, DESC_TUTORIAL);

        // same values -> returns true
        EditEventDescriptor copyDescriptor = new EditEventDescriptor(DESC_TUTORIAL);
        EEditCommand commandWithSameValues = new EEditCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new EClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EEditCommand(INDEX_SECOND, DESC_TUTORIAL)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EEditCommand(INDEX_FIRST, DESC_EXAM)));
    }
}
