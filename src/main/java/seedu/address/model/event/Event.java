package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.common.ZoomLink;
import seedu.address.model.contact.Contact;
import seedu.address.model.tag.Tag;

/**
 * Represents an Event in SoConnect.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event {

    private static HashMap<UUID, Event> map = new HashMap<>();
    private static boolean willDisplayStartDateTime = true;
    private static boolean willDisplayEndDateTime = true;
    private static boolean willDisplayDescription = true;
    private static boolean willDisplayAddress = true;
    private static boolean willDisplayZoomLink = true;
    private static boolean willDisplayTags = true;


    //Compulsory fields
    private final Name name;
    private final StartDateTime startDateAndTime;

    private final EndDateTime endDateAndTime;
    private final Description description;
    private final Address address;
    private final ZoomLink zoomLink;
    private final UUID uuid;

    private final Set<Tag> tags = new HashSet<>();
    private final Set<UUID> linkedContacts = new HashSet<>();

    /**
     * All fields must be present and not null (currently).
     */
    public Event(Name name, StartDateTime startDateAndTime, EndDateTime endDateAndTime,
                 Description description, Address address, ZoomLink zoomLink, Set<Tag> tags) {
        requireAllNonNull(name, startDateAndTime, tags);
        this.name = name;
        this.startDateAndTime = startDateAndTime;
        this.endDateAndTime = endDateAndTime;
        this.description = description;
        this.address = address;
        this.zoomLink = zoomLink;
        this.tags.addAll(tags);
        this.uuid = UUID.randomUUID();
    }

    /**
     * This constructor is for creating event stored in storage. The event stored in storage contains information
     * of uuid and contacts linked to it, in addition to information about other fields.
     * This constructor ensures that everytime the application loads the data from storage, the uuid of the event
     * stays the same and contains uuid of contacts that are linked to it.
     */
    public Event(
            Name name, StartDateTime startDateAndTime, EndDateTime endDateAndTime, Description description,
            Address address, ZoomLink zoomLink, Set<Tag> tags, UUID uuid, Set<UUID> linkedContacts) {
        requireAllNonNull(name, startDateAndTime, tags);
        this.name = name;
        this.startDateAndTime = startDateAndTime;
        this.endDateAndTime = endDateAndTime;
        this.description = description;
        this.address = address;
        this.zoomLink = zoomLink;
        this.tags.addAll(tags);
        this.uuid = uuid;
        this.linkedContacts.addAll(linkedContacts);
    }

    public Name getName() {
        return name;
    }

    public StartDateTime getStartDateAndTime() {
        return startDateAndTime;
    }

    public EndDateTime getEndDateAndTime() {
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

    public UUID getUuid() {
        return uuid;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Set<UUID> getLinkedContacts() {
        return Collections.unmodifiableSet(linkedContacts);
    }

    public static boolean isWillDisplayStartDateTime() {
        return willDisplayStartDateTime;
    }

    public static void setWillDisplayStartDateTime(boolean willDisplayStartDateTime) {
        Event.willDisplayStartDateTime = willDisplayStartDateTime;
    }

    public static boolean isWillDisplayEndDateTime() {
        return willDisplayEndDateTime;
    }

    public static void setWillDisplayEndDateTime(boolean willDisplayEndDateTime) {
        Event.willDisplayEndDateTime = willDisplayEndDateTime;
    }

    public static boolean isWillDisplayDescription() {
        return willDisplayDescription;
    }

    public static void setWillDisplayDescription(boolean willDisplayDescription) {
        Event.willDisplayDescription = willDisplayDescription;
    }

    public static boolean isWillDisplayZoomLink() {
        return willDisplayZoomLink;
    }

    public static void setWillDisplayZoomLink(boolean willDisplayZoomLink) {
        Event.willDisplayZoomLink = willDisplayZoomLink;
    }

    public static boolean isWillDisplayAddress() {
        return willDisplayAddress;
    }

    public static void setWillDisplayAddress(boolean willDisplayAddress) {
        Event.willDisplayAddress = willDisplayAddress;
    }

    public static boolean isWillDisplayTags() {
        return willDisplayTags;
    }

    public static void setWillDisplayTags(boolean willDisplayTags) {
        Event.willDisplayTags = willDisplayTags;
    }

    public static void setAllDisplayToTrue() {
        willDisplayStartDateTime = true;
        willDisplayEndDateTime = true;
        willDisplayDescription = true;
        willDisplayZoomLink = true;
        willDisplayAddress = true;
        willDisplayTags = true;
    }

    public static void setAllDisplayToFalse() {
        willDisplayStartDateTime = false;
        willDisplayEndDateTime = false;
        willDisplayDescription = false;
        willDisplayZoomLink = false;
        willDisplayAddress = false;
        willDisplayTags = false;
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

    public void linkTo(Contact contact) { // haven't perform checks to determine if contact has already been linked
        this.linkedContacts.add(contact.getUuid());
    }

    public static void addToMap(Event event) {
        map.put(event.getUuid(), event);
    }

    public static Event findByUuid(UUID uuid) {
        return map.get(uuid);
    }

    public void unlink(Contact contact) {
        this.linkedContacts.remove(contact.getUuid());
    }

    public void clearAllLinks() {
        this.linkedContacts.clear();
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

    /**
     * Returns true if both events have the same details.
     * This defines a stronger notion of equality between two events.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Event event = (Event) other;
        return Objects.equals(getName(), event.getName())
                && Objects.equals(getStartDateAndTime(), event.getStartDateAndTime())
                && Objects.equals(getEndDateAndTime(), event.getEndDateAndTime())
                && Objects.equals(getDescription(), event.getDescription())
                && Objects.equals(getAddress(), event.getAddress())
                && Objects.equals(getZoomLink(), event.getZoomLink())
                && Objects.equals(getTags(), event.getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, startDateAndTime, endDateAndTime, description,
                address, zoomLink, tags);
    }
}

