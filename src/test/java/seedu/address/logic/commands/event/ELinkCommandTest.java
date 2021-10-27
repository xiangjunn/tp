package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.HashSet;
import java.util.Set;

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

class ELinkCommandTest {
    private final AddressBook typicalAddressBook = TypicalAddressBook.getTypicalAddressBook();
    private final Model typicalModel = new ModelManager(typicalAddressBook, new UserPrefs());

    private String generateStringFromSet(Set<Contact> set) {
        assert !set.isEmpty();
        StringBuilder result = new StringBuilder();
        for (Contact contact : set) {
            result.append(contact.getName());
            result.append(", ");
        }
        result.replace(result.length() - 2, result.length(), "");
        return result.append(".").toString();
    }

    @Test
    public void execute_singleLink_success() {
        ELinkCommand eLinkCommand = new ELinkCommand(
            INDEX_FIRST_EVENT,
            Set.of(INDEX_FIRST_PERSON));
        Event eventToLink = typicalModel.getFilteredEventList().get(0);
        Contact contactToLink = typicalModel.getFilteredContactList().get(0);
        Model newModel = new ModelManager(typicalAddressBook, new UserPrefs());
        newModel.linkEventAndContact(
            newModel.getFilteredEventList().get(0),
            newModel.getFilteredContactList().get(0));
        assertCommandSuccess(
            eLinkCommand, typicalModel, String.format(ELinkCommand.MESSAGE_SUCCESS, eventToLink.getName(), "",
                generateStringFromSet(Set.of(contactToLink))), newModel);
    }

    @Test
    public void execute_multiLink_success() {
        ELinkCommand eLinkCommand = new ELinkCommand(
            INDEX_FIRST_EVENT,
            Set.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
        Event eventToLink = typicalModel.getFilteredEventList().get(0);
        Contact contact1ToLink = typicalModel.getFilteredContactList().get(0);
        Contact contact2ToLink = typicalModel.getFilteredContactList().get(1);
        Model newModel = new ModelManager(typicalAddressBook, new UserPrefs());
        newModel.linkEventAndContact(
            newModel.getFilteredEventList().get(0),
            newModel.getFilteredContactList().get(0));
        newModel.linkEventAndContact(
            newModel.getFilteredEventList().get(0),
            newModel.getFilteredContactList().get(1));
        assertCommandSuccess(
            eLinkCommand, typicalModel, String.format(ELinkCommand.MESSAGE_SUCCESS, eventToLink.getName(), "s",
                generateStringFromSet(Set.of(contact1ToLink, contact2ToLink))), newModel);
    }

    @Test
    public void execute_invalidIndex_failure() {
        ELinkCommand eLinkCommand = new ELinkCommand(
            Index.fromZeroBased(100),
            Set.of(INDEX_SECOND_PERSON));
        assertCommandFailure(eLinkCommand, typicalModel, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        ELinkCommand eLinkCommand2 = new ELinkCommand(
            INDEX_FIRST_EVENT,
            Set.of(INDEX_SECOND_PERSON, Index.fromZeroBased(101)));
        assertCommandFailure(eLinkCommand2, typicalModel, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void testEquals() {
        ELinkCommand eLinkCommand1 = new ELinkCommand(INDEX_FIRST_EVENT, Set.of(INDEX_FIRST_PERSON));
        Set<Index> set = new HashSet<>();
        set.add(INDEX_FIRST_PERSON);
        set.add(INDEX_SECOND_PERSON);
        ELinkCommand eLinkCommand2 = new ELinkCommand(INDEX_FIRST_EVENT, set);
        ELinkCommand eLinkCommand3 = new ELinkCommand(
            INDEX_FIRST_EVENT,
            Set.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
        ELinkCommand eLinkCommand4 = new ELinkCommand(INDEX_SECOND_EVENT, set);
        assertEquals(eLinkCommand1, eLinkCommand1);
        assertNotEquals(eLinkCommand1, eLinkCommand2);
        assertEquals(eLinkCommand2, eLinkCommand3);
        assertNotEquals(eLinkCommand3, eLinkCommand4);
    }
}
