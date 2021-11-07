package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.event.EventChanger;
import seedu.address.testutil.TypicalEvents;

public class CommandResultTest {
    private CommandResult commandResult = new CommandResult("feedback");

    @Test
    public void equals() {

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false, false)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true, false)));

        // different showCalendar value -> return false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, false, true)));
    }

    @Test
    public void hashcode() {
        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true, false, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true, false).hashCode());

        // different showCalendar value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true, false).hashCode());
    }

    @Test
    public void getFeedbackToUser() {
        assertEquals("feedback", commandResult.getFeedbackToUser());
        assertNotEquals("feedbacks", commandResult.getFeedbackToUser());
    }

    @Test
    public void isShowHelp() {
        assertFalse(commandResult.isShowHelp());

        CommandResult commandResult1 = new CommandResult("feedback", true, false, false);
        assertTrue(commandResult1.isShowHelp());

        CommandResult commandResult2 = new CommandResult("feedback", false, true, false);
        assertFalse(commandResult2.isShowHelp());

        CommandResult commandResult3 = new CommandResult("feedback", List.of());
        assertFalse(commandResult3.isShowHelp());
    }

    @Test
    public void isExit() {
        assertFalse(commandResult.isExit());

        CommandResult commandResult1 = new CommandResult("feedback", true, false, false);
        assertFalse(commandResult1.isExit());

        CommandResult commandResult2 = new CommandResult("feedback", false, true, false);
        assertTrue(commandResult2.isExit());

        CommandResult commandResult3 = new CommandResult("feedback", List.of());
        assertFalse(commandResult3.isExit());
    }

    @Test
    public void isShowCalendar() {
        assertFalse(commandResult.isShowCalendar());

        CommandResult commandResult1 = new CommandResult("feedback", true, false, false);
        assertFalse(commandResult1.isShowCalendar());

        CommandResult commandResult2 = new CommandResult("feedback", false, false, true);
        assertTrue(commandResult2.isShowCalendar());

        CommandResult commandResult3 = new CommandResult("feedback", List.of());
        assertFalse(commandResult3.isShowCalendar());
    }

    @Test
    public void getEventChangerList() {
        assertEquals(0, commandResult.getEventChangerList().size());
        List<EventChanger> eventChangerList = List.of(
            EventChanger.editEventChanger(TypicalEvents.CS2101_MEETING, TypicalEvents.TEAM_MEETING),
            EventChanger.editEventChanger(TypicalEvents.CS2103_MIDTERM_MARKED, TypicalEvents.CS2100_CONSULTATION));
        CommandResult commandResult1 = new CommandResult("feedback", eventChangerList);
        assertEquals(eventChangerList, commandResult1.getEventChangerList());
    }
}
