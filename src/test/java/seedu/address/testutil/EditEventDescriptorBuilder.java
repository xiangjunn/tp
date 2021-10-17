package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.event.EEditCommand.EditEventDescriptor;
import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.common.ZoomLink;
import seedu.address.model.event.Description;
import seedu.address.model.event.EndDateTime;
import seedu.address.model.event.Event;
import seedu.address.model.event.StartDateTime;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditEventDescriptor objects.
 */
public class EditEventDescriptorBuilder {

    private EditEventDescriptor descriptor;

    public EditEventDescriptorBuilder() {
        descriptor = new EditEventDescriptor();
    }

    public EditEventDescriptorBuilder(EditEventDescriptor descriptor) {
        this.descriptor = new EditEventDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditEventDescriptor} with fields containing {@code event}'s details
     * and the set of tags to delete ({@code toDelete}). Also takes in a {@code shouldDeleteAllTags}
     * to indicate if all tags should be deleted first.
     */
    public EditEventDescriptorBuilder(Event event, Set<Tag> toDelete, boolean shouldDeleteAllTags) {
        descriptor = new EditEventDescriptor();
        descriptor.setName(event.getName());
        descriptor.setStartDateTime(event.getStartDateAndTime());
        descriptor.setEndDateTime(event.getEndDateAndTime());
        descriptor.setDescription(event.getDescription());
        descriptor.setAddress(event.getAddress());
        descriptor.setZoomLink(event.getZoomLink());
        descriptor.setTags(event.getTags());
        descriptor.setTagsToDelete(toDelete);
        descriptor.setShouldDeleteAllTags(shouldDeleteAllTags);
    }

    /**
     * Sets the {@code Name} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code startDateTime} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withStartDateTime(String startDateTime) {
        descriptor.setStartDateTime(new StartDateTime(startDateTime));
        return this;
    }

    /**
     * Sets the {@code endDateTime} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withEndDateTime(String endDateTime) {
        descriptor.setEndDateTime(new EndDateTime(endDateTime));
        return this;
    }


    /**
     * Sets the {@code Description} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code ZoomLink} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withZoomLink(String zoomLink) {
        descriptor.setZoomLink(new ZoomLink(zoomLink));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditEventDescriptor}
     * that we are building.
     */
    public EditEventDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditEventDescriptor}
     * that we are building for deletion.
     */
    public EditEventDescriptorBuilder withTagsToDelete(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTagsToDelete(tagSet);
        return this;
    }

    /**
     * Set the boolean {@code shouldDeleteAllTags} to the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withDeleteAllTags(boolean shouldDeleteAllTags) {
        descriptor.setShouldDeleteAllTags(shouldDeleteAllTags);
        return this;
    }

    public EditEventDescriptor build() {
        return descriptor;
    }
}
