package seedu.address.logic.commands.contact;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalEvents.EXAM;
import static seedu.address.testutil.TypicalEvents.INTERVIEW;
import static seedu.address.testutil.TypicalRanges.RANGE_FIRST_TO_FIRST;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.event.Event;

/**
 * Contains integration tests (interaction with the Model and checks if unlinking is done properly)
 * for {@code CDeleteCommand}.
 */
public class CDeleteCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_deleteContact_success() {
        Contact contactToDelete = model.getFilteredContactList().get(0);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.deleteContact(contactToDelete);

        assertCommandSuccess(new CDeleteCommand(RANGE_FIRST_TO_FIRST), model,
            String.format(CDeleteCommand.MESSAGE_DELETE_CONTACT_SUCCESS, contactToDelete), expectedModel);
    }

    @Test
    public void execute_deleteContactWithLinks_success() throws CommandException {
        // link contact to two events before deleting
        Event event1ToLink = INTERVIEW;
        Event event2ToLink = EXAM;
        model.addEvent(event1ToLink);
        model.addEvent(event2ToLink);
        // due to contact immutability, have to get new contact from the contact list
        model.linkEventAndContact(event1ToLink, model.getFilteredContactList().get(0));
        model.linkEventAndContact(event2ToLink, model.getFilteredContactList().get(0));
        Contact contactToDelete = model.getFilteredContactList().get(0);
        assertTrue(contactToDelete.hasLinkTo(event1ToLink));
        assertTrue(contactToDelete.hasLinkTo(event2ToLink));
        CDeleteCommand cDeleteCommand = new CDeleteCommand(RANGE_FIRST_TO_FIRST);
        cDeleteCommand.execute(model);
        assertFalse(event1ToLink.hasLinkTo(contactToDelete));
        assertFalse(event2ToLink.hasLinkTo(contactToDelete));
    }
}
