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
     *
     * @param contactDisplaySetting   contact display setting to be set
     * @param eventDisplaySetting     event display setting to be set
     * @param contactDisplayPredicate contact display predicate to be set
     * @param eventDisplayPredicate   event display predicate to be set
     */
    public ModelDisplaySetting(
        ContactDisplaySetting contactDisplaySetting, EventDisplaySetting eventDisplaySetting,
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

    /**
     * Create a copy of the current model display setting
     *
     * @return a copy of model display setting
     */
    public ModelDisplaySetting copy() {
        return new ModelDisplaySetting(contactDisplaySetting, eventDisplaySetting, contactDisplayPredicate,
            eventDisplayPredicate);
    }

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
            && contactDisplaySetting.equals(other.contactDisplaySetting);
    }

    /**
     * Returns a new {@code ModelDisplaySetting} object with the same properties,
     * except for the {@code ContactDisplaySetting}.
     */
    public ModelDisplaySetting differentContactDisplaySetting(ContactDisplaySetting displaySetting) {
        return new ModelDisplaySetting(displaySetting, eventDisplaySetting,
            contactDisplayPredicate, eventDisplayPredicate);
    }

    /**
     * Returns a new {@code ModelDisplaySetting} object with the same properties,
     * except for the {@code EventDisplaySetting}.
     */
    public ModelDisplaySetting differentEventDisplaySetting(EventDisplaySetting displaySetting) {
        return new ModelDisplaySetting(contactDisplaySetting, displaySetting,
            contactDisplayPredicate, eventDisplayPredicate);
    }

    /**
     * Returns a new {@code ModelDisplaySetting} object with the same properties,
     * except for the {@code predicate} for contacts.
     */
    public ModelDisplaySetting differentContactDisplayPredicate(Predicate<? super Contact> predicate) {
        return new ModelDisplaySetting(contactDisplaySetting, eventDisplaySetting,
            predicate, eventDisplayPredicate);
    }

    /**
     * Returns a new {@code ModelDisplaySetting} object with the same properties,
     * except for the {@code predicate} for events.
     */
    public ModelDisplaySetting differentEventDisplayPredicate(Predicate<? super Event> predicate) {
        return new ModelDisplaySetting(contactDisplaySetting, eventDisplaySetting,
            contactDisplayPredicate, predicate);
    }

    @Override
    public String toString() {
        return "ModelDisplaySetting{"
            + "contactDisplaySetting=" + contactDisplaySetting
            + ", eventDisplaySetting=" + eventDisplaySetting
            + '}';
    }
}
