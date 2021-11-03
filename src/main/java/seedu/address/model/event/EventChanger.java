package seedu.address.model.event;

import java.util.Objects;

/**
 * Contains the changes to an event so that it will be updated in the calendar UI.
 */
public abstract class EventChanger {
    private final Event oldEvent;
    private final Event newEvent;

    /**
     * Creates an event changer with the following parameters.
     * @param oldEvent the initial event.
     * @param newEvent the edited or new event.
     */
    private EventChanger(Event oldEvent, Event newEvent) {
        this.oldEvent = oldEvent;
        this.newEvent = newEvent;
    }

    public static EventChanger clearEventChanger() {
        return ClearEventChanger.CLEAR_EVENT_CHANGER;
    }

    public static EventChanger deleteEventChanger(Event event) {
        return new DeleteEventChanger(event);
    }

    public static EventChanger editEventChanger(Event oldEvent, Event newEvent) {
        return new EditEventChanger(oldEvent, newEvent);
    }

    public static EventChanger addEventChanger(Event event) {
        return new AddEventChanger(event);
    }

    public Event getOldEvent() {
        return oldEvent;
    }

    public Event getNewEvent() {
        return newEvent;
    }

    public abstract boolean isClearing();

    public abstract boolean isDeleting();

    public abstract boolean isEditing();

    public abstract boolean isAdding();

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        EventChanger eventChanger = (EventChanger) other;
        return isClearing() == eventChanger.isClearing() && Objects.equals(getOldEvent(), eventChanger.getOldEvent())
            && Objects.equals(getNewEvent(), eventChanger.getNewEvent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(isClearing(), getOldEvent(), getNewEvent());
    }

    private static class ClearEventChanger extends EventChanger {
        private static final ClearEventChanger CLEAR_EVENT_CHANGER = new ClearEventChanger();

        private ClearEventChanger() {
            super(null, null);
        }

        @Override
        public boolean isClearing() {
            return true;
        }

        @Override
        public boolean isDeleting() {
            return false;
        }

        @Override
        public boolean isEditing() {
            return false;
        }

        @Override
        public boolean isAdding() {
            return false;
        }
    }

    private static class DeleteEventChanger extends EventChanger {

        private DeleteEventChanger(Event oldEvent) {
            super(oldEvent, null);
        }

        @Override
        public boolean isClearing() {
            return false;
        }

        @Override
        public boolean isDeleting() {
            return true;
        }

        @Override
        public boolean isEditing() {
            return false;
        }

        @Override
        public boolean isAdding() {
            return false;
        }
    }

    private static class EditEventChanger extends EventChanger {

        private EditEventChanger(Event oldEvent, Event newEvent) {
            super(oldEvent, newEvent);
        }

        @Override
        public boolean isClearing() {
            return false;
        }

        @Override
        public boolean isDeleting() {
            return false;
        }

        @Override
        public boolean isEditing() {
            return true;
        }

        @Override
        public boolean isAdding() {
            return false;
        }
    }

    private static class AddEventChanger extends EventChanger {

        private AddEventChanger(Event event) {
            super(null, event);
        }

        @Override
        public boolean isClearing() {
            return false;
        }

        @Override
        public boolean isDeleting() {
            return false;
        }

        @Override
        public boolean isEditing() {
            return false;
        }

        @Override
        public boolean isAdding() {
            return true;
        }
    }
}
