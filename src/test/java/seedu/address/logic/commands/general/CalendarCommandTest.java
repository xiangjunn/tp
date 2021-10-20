package seedu.address.logic.commands.general;

import static seedu.address.logic.commands.general.CalendarCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

class CalendarCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    void execute_calendar_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, false, false, true);
        assertCommandSuccess(new CalendarCommand(), model, expectedCommandResult, expectedModel);
    }
}
