package seedu.address.logic.commands.contact;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.general.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.general.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.general.CommandTestUtil.showContactAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.contact.CEditCommand.EditContactDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ContactBuilder;
import seedu.address.testutil.EditContactDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for CEditCommand.
 */
public class CEditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Contact editedContact = new ContactBuilder().build();
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder(editedContact,
            null, true).build();
        CEditCommand cEditCommand = new CEditCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(CEditCommand.MESSAGE_EDIT_CONTACT_SUCCESS, editedContact);

        Model expectedModel = new ModelManager(new AddressBook(getTypicalAddressBook()), new UserPrefs());
        expectedModel.setContact(model.getFilteredContactList().get(0), editedContact);

        assertCommandSuccess(cEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastContact = Index.fromOneBased(model.getFilteredContactList().size());
        Contact lastContact = model.getFilteredContactList().get(indexLastContact.getZeroBased());

        ContactBuilder contactInList = new ContactBuilder(lastContact);
        Contact editedContact = contactInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        CEditCommand cEditCommand = new CEditCommand(indexLastContact, descriptor);

        String expectedMessage = String.format(CEditCommand.MESSAGE_EDIT_CONTACT_SUCCESS, editedContact);

        Model expectedModel = new ModelManager(new AddressBook(getTypicalAddressBook()), new UserPrefs());
        expectedModel.setContact(lastContact, editedContact);

        assertCommandSuccess(cEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        CEditCommand cEditCommand = new CEditCommand(INDEX_FIRST, new CEditCommand.EditContactDescriptor());
        Contact editedContact = model.getFilteredContactList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(CEditCommand.MESSAGE_EDIT_CONTACT_SUCCESS, editedContact);

        Model expectedModel = new ModelManager(new AddressBook(getTypicalAddressBook()), new UserPrefs());

        assertCommandSuccess(cEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showContactAtIndex(model, INDEX_FIRST);

        Contact contactInFilteredList = model.getFilteredContactList().get(INDEX_FIRST.getZeroBased());
        Contact editedContact = new ContactBuilder(contactInFilteredList).withName(VALID_NAME_BOB).build();
        CEditCommand cEditCommand = new CEditCommand(INDEX_FIRST,
                new EditContactDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(CEditCommand.MESSAGE_EDIT_CONTACT_SUCCESS, editedContact);

        Model expectedModel = new ModelManager(new AddressBook(getTypicalAddressBook()), new UserPrefs());
        expectedModel.setContact(model.getFilteredContactList().get(0), editedContact);

        assertCommandSuccess(cEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateContactUnfilteredList_failure() {
        Contact firstContact = model.getFilteredContactList().get(INDEX_FIRST.getZeroBased());
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder(firstContact, Set.of(), false).build();
        CEditCommand cEditCommand = new CEditCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(cEditCommand, model, CEditCommand.MESSAGE_DUPLICATE_CONTACT);
    }

    @Test
    public void execute_duplicateContactFilteredList_failure() {
        showContactAtIndex(model, INDEX_FIRST);

        // edit contact in filtered list into a duplicate in address book
        Contact contactInList = getTypicalAddressBook().getContactList().get(INDEX_SECOND.getZeroBased());
        CEditCommand cEditCommand = new CEditCommand(INDEX_FIRST,
                new EditContactDescriptorBuilder(contactInList, Set.of(), false).build());

        assertCommandFailure(cEditCommand, model, CEditCommand.MESSAGE_DUPLICATE_CONTACT);
    }

    @Test
    public void execute_invalidContactIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredContactList().size() + 1);
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withName(VALID_NAME_BOB).build();
        CEditCommand cEditCommand = new CEditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(cEditCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidContactIndexFilteredList_failure() {
        showContactAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < getTypicalAddressBook().getContactList().size());

        CEditCommand cEditCommand = new CEditCommand(outOfBoundIndex,
                new EditContactDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(cEditCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_tagToDeleteNotInOriginalContact_success() {
        Tag toDelete = new Tag("notInOriginal");
        Contact defaultContact = new ContactBuilder().withMarked(false).build(); // with friends and unmarked
        Contact editedContact = new ContactBuilder().withTags().build();
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder(editedContact,
            Set.of(toDelete), false).build();
        // the index must not have any tags initially (check TypicalContacts)
        CEditCommand cEditCommand = new CEditCommand(INDEX_THIRD, descriptor);
        String expectedMessage = String.format(CEditCommand.MESSAGE_EDIT_CONTACT_SUCCESS, defaultContact)
                + "\nNote:\n" + String.format(CEditCommand.MESSAGE_TAG_TO_DELETE_NOT_IN_ORIGINAL, toDelete);
        Model expectedModel = new ModelManager(new AddressBook(getTypicalAddressBook()), new UserPrefs());
        expectedModel.setContact(model.getFilteredContactList().get(2), defaultContact);
        assertCommandSuccess(cEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_tagToAddAlreadyInOriginalContact_success() {
        Tag toAdd = new Tag("friends");
        Contact editedContact = new ContactBuilder().withTags("friends").build();
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder(editedContact,
            null, false).build();
        // the index must contain [friends] tag initially (check TypicalContacts)
        CEditCommand cEditCommand = new CEditCommand(INDEX_FIRST, descriptor);
        String expectedMessage = String.format(CEditCommand.MESSAGE_EDIT_CONTACT_SUCCESS, editedContact)
            + "\nNote:\n" + String.format(CEditCommand.MESSAGE_TAG_TO_ADD_ALREADY_IN_ORIGINAL, toAdd);
        Model expectedModel = new ModelManager(new AddressBook(getTypicalAddressBook()), new UserPrefs());
        expectedModel.setContact(model.getFilteredContactList().get(0), editedContact);
        assertCommandSuccess(cEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final CEditCommand standardCommand = new CEditCommand(INDEX_FIRST, DESC_AMY);

        // same values -> returns true
        EditContactDescriptor copyDescriptor = new EditContactDescriptor(DESC_AMY);
        CEditCommand commandWithSameValues = new CEditCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new CClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new CEditCommand(INDEX_SECOND, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new CEditCommand(INDEX_FIRST, DESC_BOB)));
    }
}
