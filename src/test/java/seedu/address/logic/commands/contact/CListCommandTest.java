package seedu.address.logic.commands.contact;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.general.CommandTestUtil.showContactAtIndex;
import static seedu.address.model.contact.ContactDisplaySetting.DEFAULT_SETTING;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.ContactDisplaySetting;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class CListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new CListCommand(ContactDisplaySetting.DEFAULT_SETTING), model,
            CListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showContactAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new CListCommand(ContactDisplaySetting.DEFAULT_SETTING),
            model, CListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equal() {
        CListCommand standardCommand = new CListCommand(DEFAULT_SETTING);
        assertTrue(standardCommand.equals(standardCommand));
        assertTrue(standardCommand.equals(new CListCommand(DEFAULT_SETTING)));
        assertFalse(standardCommand.equals(new CListCommand(new ContactDisplaySetting(false, false,
            false, true, true, true))));
        assertFalse(standardCommand.equals(new CClearCommand()));
    }
}
