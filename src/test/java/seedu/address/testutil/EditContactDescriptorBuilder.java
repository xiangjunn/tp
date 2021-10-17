package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.contact.CEditCommand.EditContactDescriptor;
import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.common.ZoomLink;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Phone;
import seedu.address.model.contact.TelegramHandle;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditContactDescriptor objects.
 */
public class EditContactDescriptorBuilder {

    private EditContactDescriptor descriptor;

    public EditContactDescriptorBuilder() {
        descriptor = new EditContactDescriptor();
    }

    public EditContactDescriptorBuilder(EditContactDescriptor descriptor) {
        this.descriptor = new EditContactDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditContactDescriptor} with fields containing {@code contact}'s details
     * and the set of tags to delete ({@code toDelete}). Also takes in a {@code shouldDeleteAllTags}
     * to indicate if all tags should be deleted first.
     */
    public EditContactDescriptorBuilder(Contact contact, Set<Tag> toDelete, boolean shouldDeleteAllTags) {
        descriptor = new EditContactDescriptor();
        descriptor.setName(contact.getName());
        descriptor.setPhone(contact.getPhone());
        descriptor.setEmail(contact.getEmail());
        descriptor.setAddress(contact.getAddress());
        descriptor.setZoomLink(contact.getZoomLink());
        descriptor.setTelegramHandle(contact.getTelegramHandle());
        descriptor.setTags(contact.getTags());
        descriptor.setTagsToDelete(toDelete);
        descriptor.setShouldDeleteAllTags(shouldDeleteAllTags);
    }

    /**
     * Sets the {@code Name} of the {@code EditContactDescriptor} that we are building.
     */
    public EditContactDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditContactDescriptor} that we are building.
     */
    public EditContactDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditContactDescriptor} that we are building.
     */
    public EditContactDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditContactDescriptor} that we are building.
     */
    public EditContactDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code Telegram} of the {@code EditContactDescriptor} that we are building.
     */
    public EditContactDescriptorBuilder withTelegram(String telegram) {
        descriptor.setTelegramHandle(new TelegramHandle(telegram));
        return this;
    }

    /**
     * Sets the {@code ZoomLink} of the {@code EditContactDescriptor} that we are building.
     */
    public EditContactDescriptorBuilder withZoomLink(String zoomLink) {
        descriptor.setZoomLink(new ZoomLink(zoomLink));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditContactDescriptor}
     * that we are building.
     */
    public EditContactDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditContactDescriptor}
     * that we are building for deletion.
     */
    public EditContactDescriptorBuilder withTagsToDelete(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTagsToDelete(tagSet);
        return this;
    }

    /**
     * Set the boolean {@code shouldDeleteAllTags} to the {@code EditContactDescriptor} that we are building.
     */
    public EditContactDescriptorBuilder withDeleteAllTags(boolean shouldDeleteAllTags) {
        descriptor.setShouldDeleteAllTags(shouldDeleteAllTags);
        return this;
    }

    public EditContactDescriptor build() {
        return descriptor;
    }
}
