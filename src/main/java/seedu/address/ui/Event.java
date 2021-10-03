package seedu.address.ui;

import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Just a temporary class for UI testing
 */
public class Event {
    String eventName;
    String from;
    String to;
    String address;
    String meetingLink;
    String description;
    Set<Tag> tags;

    Event(
        String eventName, String from, String to, String address, String meetingLink, String description,
        Set<Tag> tags) {
        this.eventName = eventName;
        this.from = from;
        this.to = to;
        this.address = address;
        this.meetingLink = meetingLink;
        this.description = description;
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Event event = (Event) o;
        return Objects.equals(eventName, event.eventName) && Objects.equals(from, event.from) &&
            Objects.equals(to, event.to) && Objects.equals(address, event.address) &&
            Objects.equals(meetingLink, event.meetingLink) &&
            Objects.equals(description, event.description) && Objects.equals(tags, event.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventName, from, to, address, meetingLink, description, tags);
    }
}
