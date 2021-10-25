package seedu.address.model.contact;

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
import seedu.address.model.event.Event;
import seedu.address.model.tag.Tag;

/**
 * Represents a Contact in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Contact {
    // stores references to all contacts, accessible by their unique UUIDs
    private static HashMap<UUID, Contact> map = new HashMap<>();
    private static boolean willDisplayPhone = true;
    private static boolean willDisplayEmail = true;
    private static boolean willDisplayTelegramHandle = true;
    private static boolean willDisplayZoomLink = true;
    private static boolean willDisplayAddress = true;
    private static boolean willDisplayTags = true;
    private static boolean viewingMode = false;

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final TelegramHandle telegramHandle;
    private final UUID uuid;

    // Data fields
    private final ZoomLink zoomLink;
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<UUID> linkedEvents = new HashSet<>();

    /**
     * Every field, except telegram handle and zoom link must be present and not null.
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
    }

    /**
     * This constructor is for creating contact stored in storage. The contact stored in storage contains information
     * of uuid and events linked to it, in addition to information about other fields.
     * This constructor ensures that everytime the application loads the data from storage, the uuid of the contact
     * stays the same and contains uuid of events that are linked.
     */
    public Contact(
        Name name, Phone phone, Email email, Address address, ZoomLink zoomLink,
        TelegramHandle telegramHandle, Set<Tag> tags, UUID uuid, Set<UUID> linkedEvents) {
        requireAllNonNull(name, email, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.telegramHandle = telegramHandle;
        this.zoomLink = zoomLink;
        this.uuid = uuid;
        this.linkedEvents.addAll(linkedEvents);
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

    public static boolean isWillDisplayPhone() {
        return willDisplayPhone;
    }

    public static void setWillDisplayPhone(boolean willDisplayPhone) {
        Contact.willDisplayPhone = willDisplayPhone;
    }

    public static boolean isWillDisplayEmail() {
        return willDisplayEmail;
    }

    public static void setWillDisplayEmail(boolean willDisplayEmail) {
        Contact.willDisplayEmail = willDisplayEmail;
    }

    public static boolean isWillDisplayTelegramHandle() {
        return willDisplayTelegramHandle;
    }

    public static void setWillDisplayTelegramHandle(boolean willDisplayTelegramHandle) {
        Contact.willDisplayTelegramHandle = willDisplayTelegramHandle;
    }

    public static boolean isWillDisplayZoomLink() {
        return willDisplayZoomLink;
    }

    public static void setWillDisplayZoomLink(boolean willDisplayZoomLink) {
        Contact.willDisplayZoomLink = willDisplayZoomLink;
    }

    public static boolean isWillDisplayAddress() {
        return willDisplayAddress;
    }

    public static void setWillDisplayAddress(boolean willDisplayAddress) {
        Contact.willDisplayAddress = willDisplayAddress;
    }

    public static boolean isWillDisplayTags() {
        return willDisplayTags;
    }

    public static void setWillDisplayTags(boolean willDisplayTags) {
        Contact.willDisplayTags = willDisplayTags;
    }

    public static void setAllDisplayToTrue() {
        willDisplayPhone = true;
        willDisplayEmail = true;
        willDisplayTelegramHandle = true;
        willDisplayZoomLink = true;
        willDisplayAddress = true;
        willDisplayTags = true;
    }

    public static void setAllDisplayToFalse() {
        willDisplayPhone = false;
        willDisplayEmail = false;
        willDisplayTelegramHandle = false;
        willDisplayZoomLink = false;
        willDisplayAddress = false;
        willDisplayTags = false;
    }

    public static boolean isViewingMode() {
        return viewingMode;
    }

    public static void setViewingMode(boolean viewingMode) {
        Contact.viewingMode = viewingMode;
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
     * Links the event to the contact object that calls this method.
     * @param event The event to be linked with.
     */
    public void linkTo(Event event) {
        this.linkedEvents.add(event.getUuid());
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
     */
    public void unlink(Event event) {
        this.linkedEvents.remove(event.getUuid());
    }

    /**
     * Removes all links to the contact object that calls this method.
     */
    public void clearAllLinks() {
        this.linkedEvents.clear();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Phone: ")
                .append(getPhone())
                .append("; Email: ")
                .append(getEmail())
                .append("; Address: ")
                .append(getAddress())
                .append("; Zoom Link: ")
                .append(getZoomLink())
                .append("; Telegram: ")
                .append(getTelegramHandle());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }

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
            && Objects.equals(getTags(), contact.getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            getName(), getPhone(), getEmail(), getTelegramHandle(), getZoomLink(), getAddress(), getTags());
    }
}
