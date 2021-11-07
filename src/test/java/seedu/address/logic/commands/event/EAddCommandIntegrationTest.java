package seedu.address.logic.commands.event;

import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventChanger;
import seedu.address.testutil.EventBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code EAddCommand}.
 */
public class EAddCommandIntegrationTest {

    private Model model;

    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newEvent_success() {
        Event validEvent = new EventBuilder().build();

        expectedModel.addEvent(validEvent);

        EventChanger eventChanger = EventChanger.addEventChanger(validEvent);

        assertCommandSuccess(new EAddCommand(validEvent), model,
                new CommandResult(String.format(EAddCommand.MESSAGE_SUCCESS, validEvent), List.of(eventChanger)),
            expectedModel);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() {
        Event eventInList = model.getAddressBook().getEventList().get(0);
        assertCommandFailure(new EAddCommand(eventInList), model, EAddCommand.MESSAGE_DUPLICATE_EVENT);
    }

}
