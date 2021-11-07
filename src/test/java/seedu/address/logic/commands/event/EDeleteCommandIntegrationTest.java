package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalContacts.AMY;
import static seedu.address.testutil.TypicalContacts.BOB;
import static seedu.address.testutil.TypicalRanges.RANGE_FIRST_TO_FIRST;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventChanger;

/**
 * Contains integration tests (interaction with the Model and checks if unlinking is done properly)
 * for {@code EDeleteCommand}.
 */
public class EDeleteCommandIntegrationTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_deleteEvent_success() {
        Event eventToDelete = model.getFilteredEventList().get(0);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);
        EventChanger eventChanger = EventChanger.deleteEventChanger(eventToDelete);
        CommandResult expectedMessage = new CommandResult(
            String.format(EDeleteCommand.MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete), List.of(eventChanger));
        assertCommandSuccess(new EDeleteCommand(RANGE_FIRST_TO_FIRST), model,
            expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteEventWithLinks_success() throws CommandException {
        // link event to two contacts before deleting
        Contact contact1ToLink = AMY;
        Contact contact2ToLink = BOB;
        model.addContact(contact1ToLink);
        model.addContact(contact2ToLink);
        // due to event immutability, have to get new event from the event list
        model.linkEventAndContact(model.getFilteredEventList().get(0), contact1ToLink);
        model.linkEventAndContact(model.getFilteredEventList().get(0), contact2ToLink);
        Event eventToDelete = model.getFilteredEventList().get(0);
        assertTrue(eventToDelete.hasLinkTo(contact1ToLink));
        assertTrue(eventToDelete.hasLinkTo(contact2ToLink));
        EDeleteCommand cDeleteCommand = new EDeleteCommand(RANGE_FIRST_TO_FIRST);
        cDeleteCommand.execute(model);
        assertFalse(contact1ToLink.hasLinkTo(eventToDelete));
        assertFalse(contact2ToLink.hasLinkTo(eventToDelete));
    }
}
