package seedu.address.logic.commands.contact;

import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class CAddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Contact validContact = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addContact(validContact);

        assertCommandSuccess(new CAddCommand(validContact), model,
                String.format(CAddCommand.MESSAGE_SUCCESS, validContact), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Contact contactInList = model.getAddressBook().getContactList().get(0);
        assertCommandFailure(new CAddCommand(contactInList), model, CAddCommand.MESSAGE_DUPLICATE_CONTACT);
    }

}
