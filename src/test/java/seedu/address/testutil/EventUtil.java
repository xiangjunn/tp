package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ZOOM;

import java.util.Set;

import seedu.address.logic.commands.event.EAddCommand;
import seedu.address.logic.commands.event.EEditCommand.EditEventDescriptor;
import seedu.address.model.event.Event;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Events
 */
public class EventUtil {

    /**
     * Returns an add command string for adding the {@code event}.
     */
    public static String getEAddCommand(Event event) {
        return EAddCommand.COMMAND_WORD + " " + getEventDetails(event);
    }

    /**
     * Returns the part of command string for the given {@code event}'s details.
     */
    public static String getEventDetails(Event event) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + event.getName().fullName + " ");
        sb.append(PREFIX_START_TIME + event.getStartDateAndTime().toString() + " ");
        sb.append(PREFIX_END_TIME + event.getEndDateAndTime().toString() + " ");
        sb.append(PREFIX_DESCRIPTION + event.getDescription().value + " ");
        sb.append(PREFIX_ADDRESS + event.getAddress().value + " ");
        sb.append(PREFIX_ZOOM + event.getZoomLink().link + " ");
        event.getTags().stream().forEach(s -> sb.append(PREFIX_TAG + s.tagName + " "));
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditEventDescriptor}'s details.
     */
    public static String getEditEventDescriptorDetails(EditEventDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getStartDateTime().ifPresent(time -> sb.append(PREFIX_START_TIME)
                .append(time.toString()).append(" "));
        descriptor.getEndDateTime().ifPresent(time -> sb.append(PREFIX_END_TIME).append(time.toString()).append(" "));
        descriptor.getDescription().ifPresent(des -> sb.append(PREFIX_DESCRIPTION).append(des.value).append(" "));
        descriptor.getAddress().ifPresent(add -> sb.append(PREFIX_ADDRESS).append(add.value).append(" "));
        descriptor.getZoomLink().ifPresent(link -> sb.append(PREFIX_ZOOM).append(link.link).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (!tags.isEmpty()) {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        if (descriptor.getTagsToDelete().isPresent()) {
            Set<Tag> tagsToDelete = descriptor.getTagsToDelete().get();
            if (!tagsToDelete.isEmpty()) {
                tagsToDelete.forEach(s -> sb.append(PREFIX_DELETE_TAG).append(s.tagName).append(" "));
            }
        }
        if (descriptor.getShouldDeleteAllTags()) {
            sb.append(PREFIX_DELETE_TAG).append("* ");
        }
        return sb.toString();
    }
}
