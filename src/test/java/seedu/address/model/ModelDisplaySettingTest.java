package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.model.Model.PREDICATE_HIDE_ALL_CONTACTS;
import static seedu.address.model.Model.PREDICATE_HIDE_ALL_EVENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONTACTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import org.junit.jupiter.api.Test;

import seedu.address.model.contact.ContactDisplaySetting;
import seedu.address.model.event.EventDisplaySetting;

class ModelDisplaySettingTest {

    private ModelDisplaySetting displaySetting = new ModelDisplaySetting();

    @Test
    public void constructor() {
        assertEquals(ContactDisplaySetting.DEFAULT_SETTING, displaySetting.getContactDisplaySetting());
        assertEquals(EventDisplaySetting.DEFAULT_SETTING, displaySetting.getEventDisplaySetting());
        assertEquals(PREDICATE_SHOW_ALL_CONTACTS, displaySetting.getContactDisplayPredicate());
        assertEquals(PREDICATE_SHOW_ALL_EVENTS, displaySetting.getEventDisplayPredicate());
    }

    @Test
    void copy() {
        ModelDisplaySetting copiedSetting = displaySetting.copy();
        assertEquals(displaySetting.getContactDisplaySetting(), copiedSetting.getContactDisplaySetting());
        assertEquals(displaySetting.getEventDisplaySetting(), copiedSetting.getEventDisplaySetting());
        assertEquals(displaySetting.getContactDisplayPredicate(), copiedSetting.getContactDisplayPredicate());
        assertEquals(displaySetting.getEventDisplayPredicate(), copiedSetting.getEventDisplayPredicate());
    }

    @Test
    void testEquals() {
        ModelDisplaySetting anotherSetting = new ModelDisplaySetting();
        assertEquals(anotherSetting, displaySetting);
    }

    @Test
    void differentContactDisplaySetting() {
        ModelDisplaySetting differentSetting = displaySetting
                .differentContactDisplaySetting(new ContactDisplaySetting(true));
        assertNotEquals(displaySetting, differentSetting);
    }

    @Test
    void differentEventDisplaySetting() {
        ModelDisplaySetting differentSetting = displaySetting
                .differentEventDisplaySetting(new EventDisplaySetting(true));
        assertNotEquals(displaySetting, differentSetting);
    }

    @Test
    void differentContactDisplayPredicate() {
        ModelDisplaySetting differentSetting = displaySetting
                .differentContactDisplayPredicate(PREDICATE_HIDE_ALL_CONTACTS);
        assertNotEquals(displaySetting.getContactDisplayPredicate(), differentSetting.getContactDisplayPredicate());
    }

    @Test
    void differentEventDisplayPredicate() {
        ModelDisplaySetting differentSetting = displaySetting
                .differentEventDisplayPredicate(PREDICATE_HIDE_ALL_EVENTS);
        assertNotEquals(displaySetting.getEventDisplayPredicate(), differentSetting.getEventDisplayPredicate());
    }
}
