package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import java.util.HashSet;
import java.util.Set;

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
import seedu.address.testutil.TypicalAddressBook;
import seedu.address.testutil.TypicalIndexes;

class EUnlinkCommandTest {
    private final AddressBook typicalAddressBook = TypicalAddressBook.getTypicalAddressBook();
    private final Model typicalModel = new ModelManager(typicalAddressBook, new UserPrefs());

    /**
     * Links event 0 to contact 0 and 1. Links event 1 to contact 2. Links event 3 to contact 1.
     */
    @BeforeEach
    public void setUp() {
        setUp(typicalModel);
    }

    /**
     * Links event 0 to contact 0, 1, 3. Links event 1 to contact 2. Links event 3 to contact 1.
     */
    private void setUp(Model model) {
        model.linkEventAndContact(
            model.getFilteredEventList().get(0),
            model.getFilteredContactList().get(0));
        model.linkEventAndContact(
            model.getFilteredEventList().get(0),
            model.getFilteredContactList().get(1));
        model.linkEventAndContact(
            model.getFilteredEventList().get(0),
            model.getFilteredContactList().get(3));
        model.linkEventAndContact(
            model.getFilteredEventList().get(1),
            model.getFilteredContactList().get(2));
        model.linkEventAndContact(
            model.getFilteredEventList().get(2),
            model.getFilteredContactList().get(1));
    }

    private String generateCommandResult(Event eventToUnlink, Set<Contact> set) {
        assert !set.isEmpty();
        StringBuilder result = new StringBuilder();
        for (Contact contact : set) {
            String resultForEachUnlink = String.format(EUnlinkCommand.MESSAGE_SUCCESS,
                eventToUnlink.getName(), contact.getName());
            result.append(resultForEachUnlink);
        }
        return result.toString();
    }

    @Test
    public void execute_singleUnlink_success() {
        EUnlinkCommand eUnlinkCommand = new EUnlinkCommand(
            INDEX_FIRST,
            Set.of(TypicalIndexes.INDEX_FIRST), false);
        Event eventToUnlink = typicalModel.getFilteredEventList().get(0);
        Contact contactToUnlink = typicalModel.getFilteredContactList().get(0);
        Model newModel = new ModelManager(typicalModel.getAddressBook(), new UserPrefs());
        setUp(newModel);
        newModel.unlinkEventAndContact(
            newModel.getFilteredEventList().get(0), newModel.getFilteredContactList().get(0));
        String commandSuccessMessage = String.format(EUnlinkCommand.MESSAGE_SUCCESS,
            eventToUnlink.getName(), contactToUnlink.getName());
        assertCommandSuccess(eUnlinkCommand, typicalModel, commandSuccessMessage, newModel);
    }

    @Test
    public void execute_multipleUnlink_success() {
        EUnlinkCommand eUnlinkCommand = new EUnlinkCommand(
            INDEX_FIRST,
            Set.of(TypicalIndexes.INDEX_FIRST, TypicalIndexes.INDEX_SECOND), false);
        Event eventToUnlink = typicalModel.getFilteredEventList().get(0);
        Contact contact1ToUnlink = typicalModel.getFilteredContactList().get(0);
        Contact contact2ToUnlink = typicalModel.getFilteredContactList().get(1);
        Set<Contact> setOfContacts = Set.of(contact1ToUnlink, contact2ToUnlink);
        Model newModel = new ModelManager(typicalModel.getAddressBook(), new UserPrefs());
        setUp(newModel);
        newModel.unlinkEventAndContact(
            newModel.getFilteredEventList().get(0), newModel.getFilteredContactList().get(0));
        newModel.unlinkEventAndContact(
            newModel.getFilteredEventList().get(0), newModel.getFilteredContactList().get(1));
        String commandSuccessMessage = generateCommandResult(eventToUnlink, setOfContacts);
        assertCommandSuccess(eUnlinkCommand, typicalModel, commandSuccessMessage, newModel);
    }

    @Test
    public void execute_allUnlink_success() {
        EUnlinkCommand eUnlinkCommand = new EUnlinkCommand(
            INDEX_FIRST,
            Set.of(), true);
        Event eventToUnlink = typicalModel.getFilteredEventList().get(0);
        Model newModel = new ModelManager(typicalModel.getAddressBook(), new UserPrefs());
        setUp(newModel);
        newModel.unlinkEventAndContact(
            newModel.getFilteredEventList().get(0), newModel.getFilteredContactList().get(0));
        newModel.unlinkEventAndContact(
            newModel.getFilteredEventList().get(0), newModel.getFilteredContactList().get(1));
        newModel.unlinkEventAndContact(
            newModel.getFilteredEventList().get(0), newModel.getFilteredContactList().get(3));
        String commandSuccessMessage = String.format(
            EUnlinkCommand.MESSAGE_SUCCESS_CLEAR_ALL,
            eventToUnlink.getName());
        assertCommandSuccess(eUnlinkCommand, typicalModel, commandSuccessMessage, newModel);
    }

    @Test
    public void execute_alreadyUnlinked_successWithErrorMessage() {
        EUnlinkCommand eUnlinkCommand = new EUnlinkCommand(
            INDEX_SECOND,
            Set.of(INDEX_SECOND), false);
        Event eventToUnlink = typicalModel.getFilteredEventList().get(1);
        Contact contactToUnlink = typicalModel.getFilteredContactList().get(1);
        Model newModel = new ModelManager(typicalModel.getAddressBook(), new UserPrefs());
        setUp(newModel);
        newModel.unlinkEventAndContact(
            newModel.getFilteredEventList().get(0), newModel.getFilteredContactList().get(0));
        String commandSuccessMessage = String.format(EUnlinkCommand.MESSAGE_NOT_LINKED,
            eventToUnlink.getName(), contactToUnlink.getName());
        assertCommandSuccess(eUnlinkCommand, typicalModel, commandSuccessMessage, newModel);
    }

    @Test
    public void execute_invalidIndex_failure() {
        EUnlinkCommand eUnlinkCommand = new EUnlinkCommand(
            Index.fromZeroBased(100),
            Set.of(), true);
        assertCommandFailure(eUnlinkCommand, typicalModel, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        EUnlinkCommand eUnlinkCommand2 = new EUnlinkCommand(
            INDEX_FIRST,
            Set.of(INDEX_SECOND, Index.fromZeroBased(101)), false);
        assertCommandFailure(eUnlinkCommand2, typicalModel, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void testEquals() {
        EUnlinkCommand eUnlinkCommand1 = new EUnlinkCommand(INDEX_FIRST, Set.of(), true);
        assertEquals(eUnlinkCommand1, eUnlinkCommand1);

        EUnlinkCommand eUnlinkCommand2 = new EUnlinkCommand(INDEX_FIRST, new HashSet<>(), true);
        assertEquals(eUnlinkCommand1, eUnlinkCommand2);

        EUnlinkCommand eUnlinkCommand3 = new EUnlinkCommand(INDEX_FIRST,
            Set.of(INDEX_FIRST, INDEX_SECOND), false);
        EUnlinkCommand eUnlinkCommand4 = new EUnlinkCommand(INDEX_SECOND,
            Set.of(INDEX_FIRST, INDEX_SECOND), false);
        Set<Index> indexSet = new HashSet<>();
        indexSet.add(INDEX_SECOND);
        indexSet.add(INDEX_FIRST);
        EUnlinkCommand eUnlinkCommand5 = new EUnlinkCommand(INDEX_SECOND, indexSet, false);
        assertEquals(eUnlinkCommand5, eUnlinkCommand4);
        assertNotEquals(eUnlinkCommand3, eUnlinkCommand5);
        assertNotEquals(eUnlinkCommand4, eUnlinkCommand3);
        assertNotEquals(eUnlinkCommand1, eUnlinkCommand5);
    }

    @Test
    public void constructor_invalid_failure() {
        assertThrows(AssertionError.class, () -> new EUnlinkCommand(INDEX_FIRST, Set.of(), false));
        assertThrows(
            AssertionError.class, () -> new EUnlinkCommand(INDEX_FIRST, Set.of(INDEX_FIRST), true));
        assertThrows(NullPointerException.class, () -> new EUnlinkCommand(INDEX_FIRST, null, false));
        assertThrows(NullPointerException.class, () -> new EUnlinkCommand(null, Set.of(), false));
    }
}
