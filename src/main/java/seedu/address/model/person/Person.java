package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.common.Address;
import seedu.address.model.common.ZoomLink;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final TelegramHandle telegramHandle;

    // Data fields
    private final ZoomLink zoomLink;
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field, except telegram handle and zoom link must be present and not null.
     */
    public Person(
        Name name, Phone phone, Email email, Address address, ZoomLink zoomLink,
        TelegramHandle telegramHandle, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
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

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
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
        Person person = (Person) other;
        return Objects.equals(getName(), person.getName())
            && Objects.equals(getPhone(), person.getPhone())
            && Objects.equals(getEmail(), person.getEmail())
            && Objects.equals(getTelegramHandle(), person.getTelegramHandle())
            && Objects.equals(getZoomLink(), person.getZoomLink())
            && Objects.equals(getAddress(), person.getAddress())
            && Objects.equals(getTags(), person.getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            getName(), getPhone(), getEmail(), getTelegramHandle(), getZoomLink(), getAddress(), getTags());
    }
}
