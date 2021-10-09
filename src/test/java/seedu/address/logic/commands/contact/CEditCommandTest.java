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
import static seedu.address.logic.commands.general.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.contact.CEditCommand.EditContactDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.testutil.EditContactDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class CEditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Contact editedContact = new PersonBuilder().build();
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder(editedContact).build();
        CEditCommand cEditCommand = new CEditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(cEditCommand.MESSAGE_EDIT_CONTACT_SUCCESS, editedContact);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(model.getFilteredContactList().get(0), editedContact);

        assertCommandSuccess(cEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredContactList().size());
        Contact lastContact = model.getFilteredContactList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastContact);
        Contact editedContact = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        CEditCommand cEditCommand = new CEditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(cEditCommand.MESSAGE_EDIT_CONTACT_SUCCESS, editedContact);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(lastContact, editedContact);

        assertCommandSuccess(cEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        CEditCommand cEditCommand = new CEditCommand(INDEX_FIRST_PERSON, new CEditCommand.EditContactDescriptor());
        Contact editedContact = model.getFilteredContactList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(cEditCommand.MESSAGE_EDIT_CONTACT_SUCCESS, editedContact);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(cEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Contact contactInFilteredList = model.getFilteredContactList().get(INDEX_FIRST_PERSON.getZeroBased());
        Contact editedContact = new PersonBuilder(contactInFilteredList).withName(VALID_NAME_BOB).build();
        CEditCommand cEditCommand = new CEditCommand(INDEX_FIRST_PERSON,
                new EditContactDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(cEditCommand.MESSAGE_EDIT_CONTACT_SUCCESS, editedContact);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(model.getFilteredContactList().get(0), editedContact);

        assertCommandSuccess(cEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Contact firstContact = model.getFilteredContactList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder(firstContact).build();
        CEditCommand cEditCommand = new CEditCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(cEditCommand, model, cEditCommand.MESSAGE_DUPLICATE_CONTACT);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit contact in filtered list into a duplicate in address book
        Contact contactInList = model.getAddressBook().getContactList().get(INDEX_SECOND_PERSON.getZeroBased());
        CEditCommand cEditCommand = new CEditCommand(INDEX_FIRST_PERSON,
                new EditContactDescriptorBuilder(contactInList).build());

        assertCommandFailure(cEditCommand, model, cEditCommand.MESSAGE_DUPLICATE_CONTACT);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
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
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getContactList().size());

        CEditCommand cEditCommand = new CEditCommand(outOfBoundIndex,
                new EditContactDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(cEditCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final CEditCommand standardCommand = new CEditCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditContactDescriptor copyDescriptor = new EditContactDescriptor(DESC_AMY);
        CEditCommand commandWithSameValues = new CEditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new CClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new CEditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new CEditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

}
