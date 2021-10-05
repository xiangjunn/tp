package seedu.address.ui;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Just a temporary class for UI testing
 */
public class Event {
    private final String eventName;
    private final String from;
    private final String to;
    private final String address;
    private final String zoomLink;
    private final String description;
    private final Set<Tag> tags;

    Event(
        String eventName, String from, String to, String address, String zoomLink, String description,
        Set<Tag> tags) {
        requireAllNonNull(eventName, from, to, address, zoomLink, description, tags);
        this.eventName = eventName;
        this.from = from;
        this.to = to;
        this.address = address;
        this.zoomLink = zoomLink;
        this.description = description;
        this.tags = tags;
    }

    public String getEventName() {
        return eventName;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getAddress() {
        return address;
    }

    public String getZoomLink() {
        return zoomLink;
    }

    public String getDescription() {
        return description;
    }

    public Set<Tag> getTags() {
        return tags;
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
        return Objects.equals(eventName, event.eventName) && Objects.equals(from, event.from)
            && Objects.equals(to, event.to) && Objects.equals(address, event.address)
            && Objects.equals(zoomLink, event.zoomLink)
            && Objects.equals(description, event.description) && Objects.equals(tags, event.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventName, from, to, address, zoomLink, description, tags);
    }
}
