package seedu.address.logic.commands.general;

import static seedu.address.logic.commands.general.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.general.CommandTestUtil.EMPTY_PREFIX_EMAIL;
import static seedu.address.logic.commands.general.CommandTestUtil.EMPTY_PREFIX_START_DATE_TIME;
import static seedu.address.logic.commands.general.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.general.CommandTestUtil.NAME_DESC_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.START_DATE_TIME_DESC_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.general.UndoCommand.MESSAGE_FAIL_TO_UNDO;
import static seedu.address.logic.commands.general.UndoCommand.MESSAGE_SUCCESS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Logic;
import seedu.address.logic.LogicStub;
import seedu.address.logic.commands.contact.CAddCommand;
import seedu.address.logic.commands.contact.CListCommand;
import seedu.address.logic.commands.event.EAddCommand;
import seedu.address.logic.commands.event.EListCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalAddressBook;

class UndoCommandTest {

    private final UndoCommand undoCommand = new UndoCommand();
    private final AddressBook initialAddressBook = TypicalAddressBook.getTypicalAddressBook();
    private final Model model = new ModelManager(initialAddressBook, new UserPrefs());
    private final Logic logicManager = new LogicStub(model);

    @Test
    public void execute_initial_failure() {
        assertCommandFailure(undoCommand, model, MESSAGE_FAIL_TO_UNDO);
    }

    @Test
    public void execute_notInitial_success() throws CommandException, ParseException {
        Model expectedModel = new ModelManager(initialAddressBook, new UserPrefs());
        logicManager.execute(CAddCommand.COMMAND_WORD + NAME_DESC_AMY + EMAIL_DESC_AMY);
        assertCommandSuccess(undoCommand, model, MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_twice_success() throws CommandException, ParseException {
        Model expectedModel2 = new ModelManager(initialAddressBook, new UserPrefs());
        logicManager.execute(EAddCommand.COMMAND_WORD + NAME_DESC_EXAM + START_DATE_TIME_DESC_EXAM);
        Model expectedModel1 = new ModelManager(model.getAddressBook(), model.getUserPrefs());
        logicManager.execute(CAddCommand.COMMAND_WORD + NAME_DESC_AMY + EMAIL_DESC_AMY);
        assertCommandSuccess(undoCommand, model, MESSAGE_SUCCESS, expectedModel1);
        assertCommandSuccess(undoCommand, model, MESSAGE_SUCCESS, expectedModel2);
    }

    @Test
    public void execute_changeDisplay_success() throws CommandException, ParseException {
        Model expectedModel = new ModelManager(initialAddressBook, model.getUserPrefs());
        Logic expectedLogicManager = new LogicStub(expectedModel);
        expectedLogicManager.execute(CListCommand.COMMAND_WORD + EMPTY_PREFIX_EMAIL);
        logicManager.execute(CListCommand.COMMAND_WORD + EMPTY_PREFIX_EMAIL);
        logicManager.execute(EListCommand.COMMAND_WORD + EMPTY_PREFIX_START_DATE_TIME);
        assertCommandSuccess(undoCommand, model, MESSAGE_SUCCESS, expectedModel);
    }
}
