package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
    // stores references to all events, accessible by their unique UUIDs
    private static final HashMap<UUID, Event> map = new HashMap<>();

    // Identity fields
    private final Name name;
    private final StartDateTime startDateAndTime;
    private final EndDateTime endDateAndTime;
    private final Description description;

    // Data fields
    private final Address address;
    private final ZoomLink zoomLink;
    private final UUID uuid;

    private final Set<Tag> tags = new HashSet<>();
    private final Set<UUID> linkedContacts = new HashSet<>();
    private final boolean isMarked;

    /**
     * Name, startDateTime and tags must be present and not null.
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
        this.isMarked = false;
    }


    /**
     * This constructor is for creating event stored in storage. The event stored in storage contains information
     * of uuid and contacts linked to it, in addition to information about other fields.
     * This constructor ensures that everytime the application loads the data from storage, the uuid of the event
     * stays the same and contains uuid of contacts that are linked to it.
     */
    public Event(
            Name name, StartDateTime startDateAndTime, EndDateTime endDateAndTime, Description description,
            Address address, ZoomLink zoomLink, Set<Tag> tags, UUID uuid, Set<UUID> linkedContacts,
            boolean isMarked) {
        requireAllNonNull(name, startDateAndTime, tags, uuid, linkedContacts);
        this.name = name;
        this.startDateAndTime = startDateAndTime;
        this.endDateAndTime = endDateAndTime;
        this.description = description;
        this.address = address;
        this.zoomLink = zoomLink;
        this.tags.addAll(tags);
        this.uuid = uuid;
        this.linkedContacts.addAll(linkedContacts);
        this.isMarked = isMarked;
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

    /**
     * Returns a unmodifiable set of UUIDs, each uniquely represents an contact, that are linked to the event object
     * that calls this method.
     */
    public Set<UUID> getLinkedContacts() {
        return Collections.unmodifiableSet(linkedContacts);
    }

    public boolean getIsMarked() {
        return isMarked;
    }

    /**
     * Checks if this {@code name} contains any keywords in {code strings}
     */
    public boolean nameAnyMatch(List<String> strings) {
        return name.containsString(strings);
    }

    /**
     * Checks if this {@code startDateTime} contains any keywords in {code strings}
     */
    public boolean startTimeAnyMatch(List<String> strings) {
        return startDateAndTime.containsString(strings);
    }

    /**
     * Checks if this {@code endDateTime} contains any keywords in {code strings}
     */
    public boolean endTimeAnyMatch(List<String> strings) {
        return (endDateAndTime != null) && endDateAndTime.containsString(strings);
    }

    /**
     * Checks if this {@code address} contains any keywords in {code strings}
     */
    public boolean addressAnyMatch(List<String> strings) {
        return (address != null) && address.containsString(strings);
    }

    /**
     * Checks if this {@code description} contains any keywords in {code strings}
     */
    public boolean descriptionAnyMatch(List<String> strings) {
        return (description != null) && description.containsString(strings);
    }

    /**
     * Checks if this {@code zoomLink} contains any keywords in {code strings}
     */
    public boolean zoomLinkAnyMatch(List<String> strings) {
        return (zoomLink != null) && zoomLink.containsString(strings);
    }

    /**
     * Checks if at least one of the {@code tags} contains any keywords in {code strings}
     */
    public boolean anyTagsContain(List<String> strings) {
        return tags.stream().anyMatch(tag -> tag.containsString(strings));
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
     * Checks if the event is linked to a particular contact.
     */
    public boolean hasLinkTo(Contact contact) {
        UUID contactUuid = contact.getUuid();
        return linkedContacts.contains(contactUuid);
    }

    /**
     * Links the contact to the event object that calls this method.
     * @param contact The contact to be linked with.
     * @return The event that has link to the contact passed in as parameter.
     */
    public Event linkTo(Contact contact) {
        Set<UUID> updatedLinkedContacts = new HashSet<>(linkedContacts);
        updatedLinkedContacts.add(contact.getUuid());
        Event updatedEvent = new Event(name, startDateAndTime, endDateAndTime, description, address, zoomLink, tags,
            uuid, updatedLinkedContacts, isMarked);
        addToMap(updatedEvent); // must update the map to represent the latest changes
        return updatedEvent;
    }

    /**
     * Adds the event to the hashmap that stores references to all events.
     * If the hashmap already contains the UUID of the event as key, the value associated to the
     * key will be replaced to the event passed as parameter to this method.
     * @param event The event to be added.
     */
    public static void addToMap(Event event) {
        map.put(event.getUuid(), event);
    }

    /**
     * Returns an event with the unique UUID that is passed in to the method.
     * The UUID passed as parameter MUST be a valid UUID that is stored in the hashmap as a key.
     * @param uuid The unique identifier for an event in the hashmap.
     */
    public static Event findByUuid(UUID uuid) {
        assert map.containsKey(uuid) : "The uuid must be valid and already in the hashmap as a key.";
        return map.get(uuid);
    }

    /**
     * Removes the link between the contact and the event object that calls this method.
     * @param contact The contact to be unlinked.
     * @return The event that has link to the contact passed in as parameter.
     */
    public Event unlink(Contact contact) {
        Set<UUID> updatedLinkedContacts = new HashSet<>(linkedContacts);
        updatedLinkedContacts.remove(contact.getUuid());
        Event updatedEvent = new Event(name, startDateAndTime, endDateAndTime, description, address, zoomLink, tags,
            uuid, updatedLinkedContacts, isMarked);
        addToMap(updatedEvent);
        return updatedEvent;
    }

    /**
     * Removes all links to the event object that calls this method.
     * @return The event that has no link to any contacts.
     */
    public Event clearAllLinks() {
        Event updatedEvent = new Event(name, startDateAndTime, endDateAndTime, description, address, zoomLink, tags,
            uuid, new HashSet<>(), isMarked);
        addToMap(updatedEvent);
        return updatedEvent;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Start: ")
                .append(getStartDateAndTime())
                .append(getEndDateAndTime() != null ? "; End: " + getEndDateAndTime() : "") // optional
                .append(getDescription() != null ? "; Description: " + getDescription() : "") // optional
                .append(getAddress() != null ? "; Address: " + getAddress() : "") // optional
                .append(getZoomLink() != null ? "; Zoom Link: " + getZoomLink() : ""); // optional

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
                && Objects.equals(getTags(), event.getTags())
                && Objects.equals(getIsMarked(), event.getIsMarked());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, startDateAndTime, endDateAndTime, description,
                address, zoomLink, tags);
    }
}
