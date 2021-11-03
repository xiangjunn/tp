package seedu.address.model;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONTACTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import java.util.function.Predicate;

import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactDisplaySetting;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDisplaySetting;

/**
 * Keep track of display setting of each model
 */
public class ModelDisplaySetting {
    private final ContactDisplaySetting contactDisplaySetting;
    private final EventDisplaySetting eventDisplaySetting;
    private final Predicate<? super Contact> contactDisplayPredicate;
    private final Predicate<? super Event> eventDisplayPredicate;

    /**
     * Constructor of a default model display setting that shows all fields and all contacts and events
     */
    public ModelDisplaySetting() {
        contactDisplaySetting = ContactDisplaySetting.DEFAULT_SETTING;
        eventDisplaySetting = EventDisplaySetting.DEFAULT_SETTING;
        contactDisplayPredicate = PREDICATE_SHOW_ALL_CONTACTS;
        eventDisplayPredicate = PREDICATE_SHOW_ALL_EVENTS;
    }

    /**
     * Constructor of a customised display setting
     * @param contactDisplaySetting contact display setting to be set
     * @param eventDisplaySetting event display setting to be set
     * @param contactDisplayPredicate contact display predicate to be set
     * @param eventDisplayPredicate event display predicate to be set
     */
    public ModelDisplaySetting(ContactDisplaySetting contactDisplaySetting, EventDisplaySetting eventDisplaySetting,
                                 Predicate<? super Contact> contactDisplayPredicate,
                                 Predicate<? super Event> eventDisplayPredicate) {
        this.contactDisplaySetting = contactDisplaySetting;
        this.eventDisplaySetting = eventDisplaySetting;
        this.contactDisplayPredicate = contactDisplayPredicate;
        this.eventDisplayPredicate = eventDisplayPredicate;
    }

    public ContactDisplaySetting getContactDisplaySetting() {
        return contactDisplaySetting;
    }

    public EventDisplaySetting getEventDisplaySetting() {
        return eventDisplaySetting;
    }

    public Predicate<? super Contact> getContactDisplayPredicate() {
        return contactDisplayPredicate;
    }

    public Predicate<? super Event> getEventDisplayPredicate() {
        return eventDisplayPredicate;
    }

//    public void setContactDisplaySetting(ContactDisplaySetting contactDisplaySetting) {
//        this.contactDisplaySetting = contactDisplaySetting;
//    }
//
//    public void setEventDisplaySetting(EventDisplaySetting eventDisplaySetting) {
//        this.eventDisplaySetting = eventDisplaySetting;
//    }
//
//    public void setContactDisplayPredicate(Predicate<? super Contact> contactDisplayPredicate) {
//        this.contactDisplayPredicate = contactDisplayPredicate;
//    }
//
//    public void setEventDisplayPredicate(Predicate<? super Event> eventDisplayPredicate) {
//        this.eventDisplayPredicate = eventDisplayPredicate;
//    }

    /**
     * Create a copy of the current model display setting
     * @return a copy of model display setting
     */
    public ModelDisplaySetting copy() {
        return new ModelDisplaySetting(contactDisplaySetting, eventDisplaySetting, contactDisplayPredicate,
                eventDisplayPredicate);
    }

//    /**
//     * Change the current setting to a new setting
//     * @param toReset model display setting to be updated to
//     */
//    public void resetSetting(ModelDisplaySetting toReset) {
//        setContactDisplaySetting(toReset.getContactDisplaySetting());
//        setEventDisplaySetting(toReset.getEventDisplaySetting());
//        setContactDisplayPredicate(toReset.getContactDisplayPredicate());
//        setEventDisplayPredicate(toReset.getEventDisplayPredicate());
//    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof ModelDisplaySetting)) {
            return false;
        }

        ModelDisplaySetting other = (ModelDisplaySetting) obj;
        return eventDisplaySetting.equals(other.eventDisplaySetting)
                && contactDisplaySetting.equals(other.contactDisplaySetting)
                && eventDisplayPredicate.equals(other.eventDisplayPredicate)
                && contactDisplayPredicate.equals(other.contactDisplayPredicate);
    }

    public ModelDisplaySetting differentContactDisplaySetting(ContactDisplaySetting displaySetting) {
        return new ModelDisplaySetting(displaySetting, eventDisplaySetting,
            contactDisplayPredicate, eventDisplayPredicate);
    }

    public ModelDisplaySetting differentEventDisplaySetting(EventDisplaySetting displaySetting) {
        return new ModelDisplaySetting(contactDisplaySetting, displaySetting,
            contactDisplayPredicate, eventDisplayPredicate);
    }

    public ModelDisplaySetting differentContactDisplayPredicate(Predicate<? super Contact> predicate) {
        return new ModelDisplaySetting(contactDisplaySetting, eventDisplaySetting,
            predicate, eventDisplayPredicate);
    }

    public ModelDisplaySetting differentEventDisplayPredicate(Predicate<? super Event> predicate) {
        return new ModelDisplaySetting(contactDisplaySetting, eventDisplaySetting,
            contactDisplayPredicate, predicate);
    }
}
