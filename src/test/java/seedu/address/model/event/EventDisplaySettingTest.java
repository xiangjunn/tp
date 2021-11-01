package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class EventDisplaySettingTest {

    @Test
    public void testEquals() {
        EventDisplaySetting eventDisplaySetting1 = new EventDisplaySetting(false, true,
            false, true, false, true);
        EventDisplaySetting eventDisplaySetting2 = new EventDisplaySetting(false, true,
            false, true, false, true);
        EventDisplaySetting eventDisplaySetting3 = new EventDisplaySetting(true);
        EventDisplaySetting eventDisplaySetting4 = new EventDisplaySetting(true, true, false, true, false, true);
        assertEquals(eventDisplaySetting1, eventDisplaySetting2);
        assertNotEquals(eventDisplaySetting1, eventDisplaySetting3);
        assertNotEquals(eventDisplaySetting1, eventDisplaySetting4);
        assertNotEquals(EventDisplaySetting.DEFAULT_SETTING, eventDisplaySetting3);
    }

    @Test
    public void testHashCode() {
        EventDisplaySetting eventDisplaySetting1 = new EventDisplaySetting(false, true,
            false, true, false, true);
        EventDisplaySetting eventDisplaySetting2 = new EventDisplaySetting(false, true,
            false, true, false, true);
        EventDisplaySetting eventDisplaySetting3 = new EventDisplaySetting(false);
        assertEqualHash(eventDisplaySetting1, eventDisplaySetting2);
        assertEqualHash(eventDisplaySetting3, EventDisplaySetting.DEFAULT_SETTING);
    }

    private void assertEqualHash(EventDisplaySetting e1, EventDisplaySetting e2) {
        assertEquals(e1.hashCode(), e2.hashCode());
    }
}
