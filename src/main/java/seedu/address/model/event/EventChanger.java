package seedu.address.model.event;

import java.util.Objects;

/**
 * Contains the changes to an event so that it will be updated in the calendar UI.
 */
public class EventChanger {
    private final boolean isClear;
    private final Event oldEvent;
    private final Event newEvent;

    /**
     * Creates an event changer with the following parameters.
     * @param isClear  true if a clear command is used.
     * @param oldEvent the initial event.
     * @param newEvent the edited or new event.
     */
    private EventChanger(boolean isClear, Event oldEvent, Event newEvent) {
        this.isClear = isClear;
        this.oldEvent = oldEvent;
        this.newEvent = newEvent;
    }

    public static EventChanger clearEventChanger() {
        return new EventChanger(true, null, null);
    }

    public static EventChanger deleteEventChanger(Event event) {
        return new EventChanger(false, event, null);
    }

    public static EventChanger editEventChanger(Event oldEvent, Event newEvent) {
        return new EventChanger(false, oldEvent, newEvent);
    }

    public static EventChanger addEventChanger(Event event) {
        return new EventChanger(false, null, event);
    }

    public boolean isClear() {
        return isClear;
    }

    public Event getOldEvent() {
        return oldEvent;
    }

    public Event getNewEvent() {
        return newEvent;
    }

    public boolean isDeleting() {
        return oldEvent != null && newEvent == null;
    }

    public boolean isEditing() {
        return oldEvent != null && newEvent != null;
    }

    public boolean isAdding() {
        return oldEvent == null && newEvent != null;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        EventChanger eventChanger = (EventChanger) other;
        return isClear() == eventChanger.isClear() && Objects.equals(getOldEvent(), eventChanger.getOldEvent())
            && Objects.equals(getNewEvent(), eventChanger.getNewEvent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(isClear(), getOldEvent(), getNewEvent());
    }
}
