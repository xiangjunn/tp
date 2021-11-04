package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class ContactDisplaySettingTest {

    @Test
    public void testEquals() {
        ContactDisplaySetting contactDisplaySetting1 = new ContactDisplaySetting(false, true,
            false, true, false, true);
        ContactDisplaySetting contactDisplaySetting2 = new ContactDisplaySetting(false, true,
            false, true, false, true);
        ContactDisplaySetting contactDisplaySetting3 = new ContactDisplaySetting(true);
        ContactDisplaySetting contactDisplaySetting4 = new ContactDisplaySetting(true, true, false, true, false, true);
        assertEquals(contactDisplaySetting1, contactDisplaySetting2);
        assertNotEquals(contactDisplaySetting1, contactDisplaySetting3);
        assertNotEquals(contactDisplaySetting1, contactDisplaySetting4);
        assertNotEquals(ContactDisplaySetting.DEFAULT_SETTING, contactDisplaySetting3);
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

    private void assertEqualHash(ContactDisplaySetting c1, ContactDisplaySetting c2) {
        assertEquals(c1.hashCode(), c2.hashCode());
    }
}
