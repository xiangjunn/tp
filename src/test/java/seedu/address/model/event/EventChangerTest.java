package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TypicalEvents;

class EventChangerTest {
    private final EventChanger clearEventChanger = EventChanger.clearEventChanger();
    private final EventChanger deleteEventChanger = EventChanger.deleteEventChanger(TypicalEvents.CS2101_MEETING);
    private final EventChanger addEventChanger = EventChanger.addEventChanger(TypicalEvents.CS2101_MEETING);
    private final EventChanger editEventChanger = EventChanger.editEventChanger(
        TypicalEvents.CS2101_MEETING,
        TypicalEvents.TEAM_MEETING);

    @Test
    public void clearEventChanger() {
        assertEquals(clearEventChanger, EventChanger.clearEventChanger());
        assertNotEquals(clearEventChanger, deleteEventChanger);

        assertNull(clearEventChanger.getNewEvent());
        assertNull(clearEventChanger.getOldEvent());

        assertTrue(clearEventChanger.isClearing());
        assertFalse(clearEventChanger.isAdding());
        assertFalse(clearEventChanger.isEditing());
        assertFalse(clearEventChanger.isDeleting());
    }

    @Test
    public void deleteEventChanger() {
        assertEquals(deleteEventChanger, EventChanger.deleteEventChanger(TypicalEvents.CS2101_MEETING));
        assertNotEquals(deleteEventChanger, editEventChanger);

        assertNull(deleteEventChanger.getNewEvent());
        assertEquals(deleteEventChanger.getOldEvent(), TypicalEvents.CS2101_MEETING);

        assertFalse(deleteEventChanger.isClearing());
        assertFalse(deleteEventChanger.isAdding());
        assertFalse(deleteEventChanger.isEditing());
        assertTrue(deleteEventChanger.isDeleting());
    }

    @Test
    public void editEventChanger() {
        assertEquals(editEventChanger, EventChanger.editEventChanger(
            TypicalEvents.CS2101_MEETING,
            TypicalEvents.TEAM_MEETING));
        assertNotEquals(editEventChanger, addEventChanger);

        assertEquals(editEventChanger.getNewEvent(), TypicalEvents.TEAM_MEETING);
        assertEquals(editEventChanger.getOldEvent(), TypicalEvents.CS2101_MEETING);

        assertFalse(editEventChanger.isClearing());
        assertFalse(editEventChanger.isAdding());
        assertTrue(editEventChanger.isEditing());
        assertFalse(editEventChanger.isDeleting());
    }

    @Test
    public void addEventChanger() {
        assertEquals(addEventChanger, EventChanger.addEventChanger(TypicalEvents.CS2101_MEETING));
        assertNotEquals(addEventChanger, clearEventChanger);

        assertNull(addEventChanger.getOldEvent());
        assertEquals(addEventChanger.getNewEvent(), TypicalEvents.CS2101_MEETING);

        assertFalse(addEventChanger.isClearing());
        assertTrue(addEventChanger.isAdding());
        assertFalse(addEventChanger.isEditing());
        assertFalse(addEventChanger.isDeleting());
    }

    @Test
    public void testHashCode() {
        assertEquals(Objects.hash(true, null, null), clearEventChanger.hashCode());
        assertEquals(Objects.hash(false, TypicalEvents.CS2101_MEETING, null), deleteEventChanger.hashCode());
        assertEquals(Objects.hash(false, null, TypicalEvents.CS2101_MEETING), addEventChanger.hashCode());
        assertEquals(
            Objects.hash(false, TypicalEvents.CS2101_MEETING, TypicalEvents.TEAM_MEETING),
            editEventChanger.hashCode());
    }
}
