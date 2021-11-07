package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ContactDisplaySettingTest {

    @Test
    public void testEquals() {
        ContactDisplaySetting contactDisplaySetting1 = new ContactDisplaySetting(false, true,
            false, true, false, true);
        ContactDisplaySetting contactDisplaySetting1Copy = new ContactDisplaySetting(false, true,
            false, true, false, true);

        ContactDisplaySetting contactDisplaySetting2 = new ContactDisplaySetting(true);
        ContactDisplaySetting contactDisplaySetting3 = new ContactDisplaySetting(true, true, false, true, false, true);

        //same setting -> equals
        assertEquals(contactDisplaySetting1, contactDisplaySetting1Copy);

        assertNotEquals(contactDisplaySetting1, contactDisplaySetting2);
        assertNotEquals(contactDisplaySetting1, contactDisplaySetting3);
        assertNotEquals(ContactDisplaySetting.DEFAULT_SETTING, contactDisplaySetting3);

        //different object type -> returns false
        assertFalse(contactDisplaySetting1.equals(1));

        //same object type -> returns true
        assertTrue(contactDisplaySetting1.equals(contactDisplaySetting1));
    }

    @Test
    public void testHashCode() {
        ContactDisplaySetting contactDisplaySetting1 = new ContactDisplaySetting(false, true,
            false, true, false, true);
        ContactDisplaySetting contactDisplaySetting2 = new ContactDisplaySetting(false, true,
            false, true, false, true);
        ContactDisplaySetting contactDisplaySetting3 = new ContactDisplaySetting(false);
        assertEqualHash(contactDisplaySetting1, contactDisplaySetting2);
        assertEqualHash(contactDisplaySetting3, ContactDisplaySetting.DEFAULT_SETTING);
    }

    @Test
    public void testToString() {
        String expectedValue = "ContactDisplaySetting{"
                + "willDisplayPhone=true"
                + ", willDisplayEmail=true"
                + ", willDisplayTelegramHandle=true"
                + ", willDisplayAddress=true"
                + ", willDisplayZoomLink=true"
                + ", willDisplayTags=true"
                + ", isViewingFull=false"
                + '}';

        String expectedValueView = "ContactDisplaySetting{"
                + "willDisplayPhone=true"
                + ", willDisplayEmail=true"
                + ", willDisplayTelegramHandle=true"
                + ", willDisplayAddress=true"
                + ", willDisplayZoomLink=true"
                + ", willDisplayTags=true"
                + ", isViewingFull=true"
                + '}';

        assertEquals(expectedValue, ContactDisplaySetting.DEFAULT_SETTING.toString());
        assertEquals(expectedValueView, new ContactDisplaySetting(true).toString());
    }

    private void assertEqualHash(ContactDisplaySetting c1, ContactDisplaySetting c2) {
        assertEquals(c1.hashCode(), c2.hashCode());
    }
}
