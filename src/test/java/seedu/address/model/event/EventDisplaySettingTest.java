package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EventDisplaySettingTest {

    @Test
    public void testEquals() {
        EventDisplaySetting eventDisplaySetting1 = new EventDisplaySetting(false, true,
            false, true, false, true);
        EventDisplaySetting eventDisplaySetting1Copy = new EventDisplaySetting(false, true,
            false, true, false, true);
        EventDisplaySetting eventDisplaySetting2 = new EventDisplaySetting(true);
        EventDisplaySetting eventDisplaySetting3 = new EventDisplaySetting(true, true,
                false, true, false, true);

        //same settings -> equals
        assertEquals(eventDisplaySetting1, eventDisplaySetting1Copy);

        assertNotEquals(eventDisplaySetting1, eventDisplaySetting2);
        assertNotEquals(eventDisplaySetting1, eventDisplaySetting3);
        assertNotEquals(EventDisplaySetting.DEFAULT_SETTING, eventDisplaySetting2);

        //different object type -> returns false
        assertFalse(eventDisplaySetting1.equals(1));

        //same object type -> returns true
        assertTrue(eventDisplaySetting1.equals(eventDisplaySetting1));
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

    @Test
    public void testToString() {
        String expectedValue = "EventDisplaySetting{"
                + "willDisplayStartDateTime=true"
                + ", willDisplayEndDateTime=true"
                + ", willDisplayDescription=true"
                + ", willDisplayAddress=true"
                + ", willDisplayZoomLink=true"
                + ", willDisplayTags=true"
                + ", isViewingFull=false"
                + '}';

        String expectedValueView = "EventDisplaySetting{"
                + "willDisplayStartDateTime=true"
                + ", willDisplayEndDateTime=true"
                + ", willDisplayDescription=true"
                + ", willDisplayAddress=true"
                + ", willDisplayZoomLink=true"
                + ", willDisplayTags=true"
                + ", isViewingFull=true"
                + '}';

        EventDisplaySetting eventDisplaySettingViewEnable = new EventDisplaySetting(true);

        assertEquals(expectedValue, EventDisplaySetting.DEFAULT_SETTING.toString());
        assertEquals(expectedValueView, eventDisplaySettingViewEnable.toString());
    }

    private void assertEqualHash(EventDisplaySetting e1, EventDisplaySetting e2) {
        assertEquals(e1.hashCode(), e2.hashCode());
    }
}
