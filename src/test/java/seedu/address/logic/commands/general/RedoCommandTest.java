package seedu.address.logic.commands.general;

import static seedu.address.logic.commands.general.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.general.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.general.CommandTestUtil.NAME_DESC_TUTORIAL;
import static seedu.address.logic.commands.general.CommandTestUtil.START_DATE_TIME_DESC_TUTORIAL;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.general.RedoCommand.MESSAGE_FAIL_TO_REDO;
import static seedu.address.logic.commands.general.RedoCommand.MESSAGE_SUCCESS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Logic;
import seedu.address.logic.LogicStub;
import seedu.address.logic.commands.contact.CAddCommand;
import seedu.address.logic.commands.event.EAddCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalAddressBook;

class RedoCommandTest {

    private final RedoCommand redoCommand = new RedoCommand();
    private final AddressBook initialAddressBook = TypicalAddressBook.getTypicalAddressBook();
    private final Model model = new ModelManager(initialAddressBook, new UserPrefs());
    private final Logic logicManager = new LogicStub(model);

    @Test
    public void execute_initial_failure() {
        assertCommandFailure(redoCommand, model, MESSAGE_FAIL_TO_REDO);
    }

    @Test
    public void execute_withoutUndo_failure() throws CommandException, ParseException {
        logicManager.execute(CAddCommand.COMMAND_WORD + NAME_DESC_AMY + EMAIL_DESC_AMY);
        assertCommandFailure(redoCommand, model, MESSAGE_FAIL_TO_REDO);
    }

    @Test
    public void execute_withUndo_success() throws CommandException, ParseException {
        logicManager.execute(CAddCommand.COMMAND_WORD + NAME_DESC_AMY + EMAIL_DESC_AMY);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getUserPrefs());
        logicManager.execute(UndoCommand.COMMAND_WORD);
        assertCommandSuccess(redoCommand, model, MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_tooManyRedo_failure() throws CommandException, ParseException {
        logicManager.execute(CAddCommand.COMMAND_WORD + NAME_DESC_AMY + EMAIL_DESC_AMY);
        logicManager.execute(UndoCommand.COMMAND_WORD);
        logicManager.execute(RedoCommand.COMMAND_WORD);
        assertCommandFailure(redoCommand, model, MESSAGE_FAIL_TO_REDO);
    }

    @Test
    public void execute_undoAfterRedo_success() throws CommandException, ParseException {
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getUserPrefs());
        logicManager.execute(EAddCommand.COMMAND_WORD + NAME_DESC_TUTORIAL + START_DATE_TIME_DESC_TUTORIAL);
        logicManager.execute(UndoCommand.COMMAND_WORD);
        logicManager.execute(RedoCommand.COMMAND_WORD);
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
