package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.common.Address;
import seedu.address.model.common.ZoomLink;
import seedu.address.model.tag.Tag;

/**
 * Represents an Event in SoConnect.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event {
    //Compulsory fields
    private final Name name;
    private final DateAndTime startDateAndTime;

    //Optional fields
    private final DateAndTime endDateAndTime;
    private final Description description;
    private final Address address;
    private final ZoomLink zoomLink;

    private final Set<Tag> tags = new HashSet<>();

    /**
     * All fields must be present and not null (currently).
     */
    public Event(Name name, DateAndTime startDateAndTime, DateAndTime endDateAndTime,
                 Description description, Address address, ZoomLink zoomLink, Set<Tag> tags) {
        requireAllNonNull(name, startDateAndTime, endDateAndTime, description, address, zoomLink, tags);
        this.name = name;
        this.startDateAndTime = startDateAndTime;
        this.endDateAndTime = endDateAndTime;
        this.description = description;
        this.address = address;
        this.zoomLink = zoomLink;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public DateAndTime getStartDateAndTime() {
        return startDateAndTime;
    }

    public DateAndTime getEndDateAndTime() {
        return endDateAndTime;
    }

    public Description getDescription() {
        return description;
    }

    public Address getAddress() {
        return address;
    }

    public ZoomLink getZoomLink() {
        return zoomLink;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both events have the same name.
     * This defines a weaker notion of equality between two events.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getName().equals(getName());
    }

    /**
     * Returns true if both events have the same details.
     * This defines a stronger notion of equality between two events.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getName().equals(getName())
                && otherEvent.getStartDateAndTime().equals(getStartDateAndTime())
                && otherEvent.getEndDateAndTime().equals(getEndDateAndTime())
                && otherEvent.getDescription().equals(getDescription())
                && otherEvent.getAddress().equals(getAddress())
                && otherEvent.getZoomLink().equals(getZoomLink())
                && otherEvent.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, startDateAndTime, endDateAndTime, description,
                address, zoomLink, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Start: ")
                .append(getStartDateAndTime())
                .append("; End: ")
                .append(getEndDateAndTime())
                .append("; Description: ")
                .append(getDescription())
                .append("; Address: ")
                .append(getAddress())
                .append("; ZoomLink: ")
                .append(getZoomLink());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }
}

