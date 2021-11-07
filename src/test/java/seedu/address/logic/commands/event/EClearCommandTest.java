package seedu.address.logic.commands.event;

import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.EventChanger;
import seedu.address.testutil.TypicalContacts;

public class EClearCommandTest {
    private final CommandResult commandResult = new CommandResult(
        EClearCommand.MESSAGE_SUCCESS,
        List.of(EventChanger.clearEventChanger()));

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new EClearCommand(), model, commandResult, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(TypicalContacts.getTypicalAddressBook());

        assertCommandSuccess(new EClearCommand(), model, commandResult, expectedModel);
    }

}
