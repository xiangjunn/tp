package seedu.address.model.contact;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.common.ZoomLink;
import seedu.address.model.tag.Tag;

/**
 * Represents a Contact in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Contact {

    private static boolean willDisplayPhone = true;
    private static boolean willDisplayEmail = true;
    private static boolean willDisplayTelegramHandle = true;
    private static boolean willDisplayAddress = true;
    private static boolean willDisplayZoomLink = true;
    private static boolean willDisplayTags = true;

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final TelegramHandle telegramHandle;

    // Data fields
    private final Address address;
    private final ZoomLink zoomLink;
    private final Set<Tag> tags = new HashSet<>();

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

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
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

    /**
     * Checks if this {@code name} contains any keywords in {code strings}
     */
    public boolean nameAnyMatch(List<String> strings) {
        return name.containsString(strings);
    }

    /**
     * Checks if this {@code phone} contains any keywords in {code strings}
     */
    public boolean phoneAnyMatch(List<String> strings) {
        return phone.containsString(strings);
    }

    /**
     * Checks if this {@code email} contains any keywords in {code strings}
     */
    public boolean emailAnyMatch(List<String> strings) {
        return (email != null) && email.containsString(strings);
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
            && Objects.equals(getTags(), contact.getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            getName(), getPhone(), getEmail(), getTelegramHandle(), getZoomLink(), getAddress(), getTags());
    }
}
