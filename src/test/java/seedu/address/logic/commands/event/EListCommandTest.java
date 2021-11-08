package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.general.CommandTestUtil.showEventAtIndex;
import static seedu.address.model.event.EventDisplaySetting.DEFAULT_SETTING;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.EventDisplaySetting;

class EListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        expectedModel.setEventDisplaySetting(DEFAULT_SETTING);
        assertCommandSuccess(new EListCommand(DEFAULT_SETTING), model, EListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showEventAtIndex(model, INDEX_FIRST);
        expectedModel.setEventDisplaySetting(DEFAULT_SETTING);
        assertCommandSuccess(new EListCommand(DEFAULT_SETTING), model, EListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsNotFiltered_showsSomeFields() {
        EventDisplaySetting eventDisplaySetting = new EventDisplaySetting(true, false, false, false, true, false);
        expectedModel.setEventDisplaySetting(eventDisplaySetting);
        assertCommandSuccess(new EListCommand(eventDisplaySetting), model, EListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsSomeFields() {
        showEventAtIndex(model, INDEX_FIRST);
        EventDisplaySetting eventDisplaySetting = new EventDisplaySetting(true, false, false, false, true, false);
        expectedModel.setEventDisplaySetting(eventDisplaySetting);
        assertCommandSuccess(new EListCommand(eventDisplaySetting), model, EListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equal() {
        EListCommand standardCommand = new EListCommand(DEFAULT_SETTING);
        assertTrue(standardCommand.equals(standardCommand));
        assertTrue(standardCommand.equals(new EListCommand(DEFAULT_SETTING)));
        assertFalse(standardCommand.equals(new EListCommand(new EventDisplaySetting(false, false,
            false, true, true, true))));
        assertFalse(standardCommand.equals(new EClearCommand()));

    }

    @Test
    public void hashCode_equal() {
        EListCommand standardCommand = new EListCommand(DEFAULT_SETTING);
        assertEquals(standardCommand.hashCode(), Objects.hash(DEFAULT_SETTING));
        EListCommand anotherCommand = new EListCommand(
            new EventDisplaySetting(true, false, false, true, false, true)
        );
        assertEquals(anotherCommand.hashCode(), Objects.hash(
            new EventDisplaySetting(true, false, false, true, false, true)
        ));

    }
}

