package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

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

    private String generateSuccessfulLink(Event eventToLink, Set<Contact> set) {
        assert !set.isEmpty();
        StringBuilder result = new StringBuilder();
        for (Contact contact : set) {
            String resultForEachLink = String.format(ELinkCommand.MESSAGE_SUCCESS,
                    eventToLink.getName(), contact.getName());
            result.append(resultForEachLink);
        }
        return result.toString();
    }

    private String generateAlreadyLinked(Event eventToLink, Set<Contact> set) {
        assert !set.isEmpty();
        StringBuilder result = new StringBuilder();
        for (Contact contact : set) {
            String resultForEachLink = String.format(ELinkCommand.MESSAGE_ALREADY_LINKED,
                eventToLink.getName(), contact.getName());
            result.append(resultForEachLink);
        }
        return result.toString();
    }

    @Test
    public void execute_singleLink_success() {
        // first index of event list and first index of contact list
        ELinkCommand eLinkCommand = new ELinkCommand(
            INDEX_FIRST,
            Set.of(INDEX_FIRST));
        Event eventToLink = typicalModel.getFilteredEventList().get(0);
        Contact contactToLink = typicalModel.getFilteredContactList().get(0);
        Model newModel = new ModelManager(typicalAddressBook, new UserPrefs());
        newModel.linkEventAndContact(
            newModel.getFilteredEventList().get(0),
            newModel.getFilteredContactList().get(0));
        assertCommandSuccess(
            eLinkCommand, typicalModel, generateSuccessfulLink(eventToLink, Set.of(contactToLink)), newModel);
    }

    @Test
    public void execute_multiLink_success() {
        // first index of event list and set of first and second indexes of contact list
        ELinkCommand eLinkCommand = new ELinkCommand(
            INDEX_FIRST,
            Set.of(INDEX_FIRST, INDEX_SECOND));
        Event eventToLink = typicalModel.getFilteredEventList().get(0);
        Contact contact1ToLink = typicalModel.getFilteredContactList().get(0);
        Contact contact2ToLink = typicalModel.getFilteredContactList().get(1);
        Set<Contact> setOfContacts = Set.of(contact1ToLink, contact2ToLink);
        Model newModel = new ModelManager(typicalAddressBook, new UserPrefs());
        newModel.linkEventAndContact(
            newModel.getFilteredEventList().get(0),
            newModel.getFilteredContactList().get(0));
        newModel.linkEventAndContact(
            newModel.getFilteredEventList().get(0),
            newModel.getFilteredContactList().get(1));
        assertCommandSuccess(
            eLinkCommand, typicalModel, generateSuccessfulLink(eventToLink, setOfContacts),
            newModel);
    }

    @Test
    public void execute_alreadyLinked_successWithErrorMessage() {
        // first index of event list and first index of contact list
        ELinkCommand eLinkCommand = new ELinkCommand(
            INDEX_FIRST,
            Set.of(INDEX_FIRST));
        Event eventToLink = typicalModel.getFilteredEventList().get(0);
        Contact contactToLink = typicalModel.getFilteredContactList().get(0);
        typicalModel.linkEventAndContact(eventToLink, contactToLink);
        Model newModel = new ModelManager(typicalAddressBook, new UserPrefs());
        newModel.linkEventAndContact(
            newModel.getFilteredEventList().get(0),
            newModel.getFilteredContactList().get(0));
        assertCommandSuccess(
            eLinkCommand, typicalModel, generateAlreadyLinked(eventToLink, Set.of(contactToLink)), newModel);
    }

    @Test
    public void execute_invalidIndex_failure() {
        ELinkCommand eLinkCommand = new ELinkCommand(
            Index.fromZeroBased(100),
            Set.of(INDEX_SECOND));
        assertCommandFailure(eLinkCommand, typicalModel, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        ELinkCommand eLinkCommand2 = new ELinkCommand(
            INDEX_FIRST,
            Set.of(INDEX_SECOND, Index.fromZeroBased(101)));
        assertCommandFailure(eLinkCommand2, typicalModel, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void testEquals() {
        // first index of event list and first index of contact list
        ELinkCommand eLinkCommand1 = new ELinkCommand(INDEX_FIRST, Set.of(INDEX_FIRST));
        Set<Index> setOfContactIndexes = new HashSet<>();
        setOfContactIndexes.add(INDEX_FIRST);
        setOfContactIndexes.add(INDEX_SECOND);

        // first index of event list and the set of contact list indexes
        ELinkCommand eLinkCommand2 = new ELinkCommand(INDEX_FIRST, setOfContactIndexes);

        // first index of event list and set of first and second indexes of contact list
        ELinkCommand eLinkCommand3 = new ELinkCommand(
            INDEX_FIRST,
            Set.of(INDEX_FIRST, INDEX_SECOND));

        // second index of event list and the set of contact list indexes
        ELinkCommand eLinkCommand4 = new ELinkCommand(INDEX_SECOND, setOfContactIndexes);

        assertEquals(eLinkCommand1, eLinkCommand1);
        assertNotEquals(eLinkCommand1, eLinkCommand2);
        assertEquals(eLinkCommand2, eLinkCommand3);
        assertNotEquals(eLinkCommand3, eLinkCommand4);
    }
}
