package seedu.address.model.contact;

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
import seedu.address.model.event.Event;
import seedu.address.model.tag.Tag;

/**
 * Represents a Contact in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Contact {
    // stores references to all contacts, accessible by their unique UUIDs
    private static HashMap<UUID, Contact> map = new HashMap<>();

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final TelegramHandle telegramHandle;
    private final UUID uuid;

    // Data fields
    private final Address address;
    private final ZoomLink zoomLink;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<UUID> linkedEvents = new HashSet<>();
    private final boolean isMarked;

    /**
     * Name, email and tags must be present and not null.
     */
    public Contact(
        Name name, Phone phone, Email email, Address address, ZoomLink zoomLink,
        TelegramHandle telegramHandle, Set<Tag> tags) {
        requireAllNonNull(name, email, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.telegramHandle = telegramHandle;
        this.zoomLink = zoomLink;
        this.uuid = UUID.randomUUID(); // to generate a uuid to uniquely identify contact
        this.isMarked = false;
    }

    /**
     * This constructor is for creating contact stored in storage. The contact stored in storage contains information
     * of uuid and events linked to it, in addition to information about other fields.
     * This constructor ensures that everytime the application loads the data from storage, the uuid of the contact
     * stays the same and contains uuid of events that are linked.
     */
    public Contact(
        Name name, Phone phone, Email email, Address address, ZoomLink zoomLink,
        TelegramHandle telegramHandle, Set<Tag> tags, UUID uuid, Set<UUID> linkedEvents, boolean isMarked) {
        requireAllNonNull(name, email, tags, uuid, linkedEvents);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.telegramHandle = telegramHandle;
        this.zoomLink = zoomLink;
        this.uuid = uuid;
        this.linkedEvents.addAll(linkedEvents);
        this.isMarked = isMarked;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public TelegramHandle getTelegramHandle() {
        return telegramHandle;
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
     * Returns a unmodifiable set of UUIDs, each uniquely represents an event, that are linked to the contact object
     * that calls this method.
     */
    public Set<UUID> getLinkedEvents() {
        return Collections.unmodifiableSet(linkedEvents);
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
     * Checks if this {@code email} contains any keywords in {code strings}
     */
    public boolean emailAnyMatch(List<String> strings) {
        return email.containsString(strings);
    }

    /**
     * Checks if this {@code phone} contains any keywords in {code strings}
     */
    public boolean phoneAnyMatch(List<String> strings) {
        return (phone != null) && phone.containsString(strings);
    }
    /**
     * Checks if this {@code address} contains any keywords in {code strings}
     */
    public boolean addressAnyMatch(List<String> strings) {
        return (address != null) && address.containsString(strings);
    }

    /**
     * Checks if this {@code telegramHandle} contains any keywords in {code strings}
     */
    public boolean telegramHandleAnyMatch(List<String> strings) {
        return (telegramHandle != null) && telegramHandle.containsString(strings);
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
     * Returns true if both contacts have the same name.
     * This defines a weaker notion of equality between two contacts.
     */
    public boolean isSameContact(Contact otherContact) {
        if (otherContact == this) {
            return true;
        }

        return otherContact != null
                && otherContact.getName().equals(getName());
    }

    /**
     * Checks if the contact is linked to a particular event.
     */
    public boolean hasLinkTo(Event event) {
        UUID eventUuid = event.getUuid();
        return linkedEvents.contains(eventUuid);
    }

    /**
     * Links the event to the contact object that calls this method.
     * @param event The event to be linked with.
     * @return The contact that has link to the event passed in as parameter.
     */
    public Contact linkTo(Event event) {
        Set<UUID> updatedLinkedEvents = new HashSet<>(linkedEvents);
        updatedLinkedEvents.add(event.getUuid());
        Contact updatedContact = new Contact(name, phone, email, address, zoomLink, telegramHandle, tags,
            uuid, updatedLinkedEvents, isMarked);
        addToMap(updatedContact); // must update the map to represent the latest changes
        return updatedContact;
    }

    /**
     * Adds the contact to the hashmap that stores references to all contacts.
     * If the hashmap already contains the UUID of the contact as key, the value associated to the
     * key will be replaced to the contact passed as parameter to this method.
     * @param contact The contact to be added.
     */
    public static void addToMap(Contact contact) {
        map.put(contact.getUuid(), contact);
    }

    /**
     * Returns a contact with the unique UUID that is passed in to the method.
     * The UUID passed as parameter MUST be a valid UUID that is stored in the hashmap as a key.
     * @param uuid The unique identifier for a contact in the hashmap.
     */
    public static Contact findByUuid(UUID uuid) {
        assert map.containsKey(uuid) : "The uuid must be valid and already in the hashmap as a key.";
        return map.get(uuid);
    }

    /**
     * Removes the link between the event and the contact object that calls this method.
     * @param event The event to be unlinked.
     * @return The contact that has no link to the event passed in as parameter.
     */
    public Contact unlink(Event event) {
        Set<UUID> updatedLinkedEvents = new HashSet<>(linkedEvents);
        updatedLinkedEvents.remove(event.getUuid());
        Contact updatedContact = new Contact(name, phone, email, address, zoomLink, telegramHandle, tags,
            uuid, updatedLinkedEvents, isMarked);
        addToMap(updatedContact);
        return updatedContact;
    }

    /**
     * Removes all links to the contact object that calls this method.
     * @return The contact that has no link to any contacts.
     */
    public Contact clearAllLinks() {
        Contact updatedContact = new Contact(name, phone, email, address, zoomLink, telegramHandle, tags,
            uuid, new HashSet<>(), isMarked);
        addToMap(updatedContact);
        return updatedContact;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Email: ")
                .append(getEmail())
                .append(getPhone() != null ? "; Phone: " + getPhone() : "") // optional
                .append(getAddress() != null ? "; Address: " + getAddress() : "") // optional
                .append(getZoomLink() != null ? "; Zoom Link: " + getZoomLink() : "") // optional
                .append(getTelegramHandle() != null ? "; Telegram: " + getTelegramHandle() : ""); // optional

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }

    /**
     * Returns true if both contacts have the same details.
     * This defines a stronger notion of equality between two contacts.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Contact contact = (Contact) other;
        return Objects.equals(getName(), contact.getName())
            && Objects.equals(getPhone(), contact.getPhone())
            && Objects.equals(getEmail(), contact.getEmail())
            && Objects.equals(getTelegramHandle(), contact.getTelegramHandle())
            && Objects.equals(getZoomLink(), contact.getZoomLink())
            && Objects.equals(getAddress(), contact.getAddress())
            && Objects.equals(getTags(), contact.getTags())
            && Objects.equals(getIsMarked(), contact.getIsMarked());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            getName(), getPhone(), getEmail(), getTelegramHandle(), getZoomLink(), getAddress(), getTags());
    }
}
