package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ZOOM;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.Undoable;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.common.ZoomLink;
import seedu.address.model.event.Description;
import seedu.address.model.event.EndDateTime;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventChanger;
import seedu.address.model.event.StartDateTime;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing event in the address book.
 */
public class EEditCommand extends Command implements Undoable {

    public static final String COMMAND_WORD = "eedit";
    public static final String PARAMETERS = "INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_START_TIME + "START] "
            + "[" + PREFIX_END_TIME + "END] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_ZOOM + "ZOOM] "
            + "[" + PREFIX_TAG + "TAG]... "
            + "[" + PREFIX_DELETE_TAG + "DELETE TAG]...\n";
    public static final String SYNTAX = COMMAND_WORD + " " + PARAMETERS;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the event identified "
            + "by the index number used in the displayed event list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: " + PARAMETERS
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_START_TIME + "01-10-2021 15:00 "
            + PREFIX_ADDRESS + "12th Street";

    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited Event: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the address book.";
    public static final String MESSAGE_INVALID_DATE_TIME_RANGE = "Event start time cannot be later than end time.";
    public static final String MESSAGE_TAG_TO_ADD_ALREADY_IN_ORIGINAL = "Event already has %s tag.\n";
    public static final String MESSAGE_TAG_TO_DELETE_NOT_IN_ORIGINAL =
        "Event does not contain %s tag to delete.\n";

    private final Index index;
    private final EditEventDescriptor editEventDescriptor;
    // to be displayed to user if user tries to delete a tag that does not exist
    // or add a tag that already exists
    private String infoMessage = "";

    /**
     * @param index of the event in the filtered event list to edit
     * @param editEventDescriptor details to edit the event with
     */
    public EEditCommand(Index index, EditEventDescriptor editEventDescriptor) {
        requireNonNull(index);
        requireNonNull(editEventDescriptor);

        this.index = index;
        this.editEventDescriptor = new EditEventDescriptor(editEventDescriptor);
    }

    /**
     * Creates and returns a {@code Event} with the details of {@code eventToEdit}
     * edited with {@code editEventDescriptor}.
     */
    private Event createEditedEvent(Event eventToEdit, EditEventDescriptor editEventDescriptor) {
        assert eventToEdit != null;

        Name updatedName = editEventDescriptor.getName().orElse(eventToEdit.getName());
        StartDateTime updatedStartDateTime = editEventDescriptor.getStartDateTime()
                .orElse(eventToEdit.getStartDateAndTime());
        EndDateTime updatedEndDateTime = editEventDescriptor.getEndDateTime()
                .orElse(eventToEdit.getEndDateAndTime());
        Description updatedDescription = editEventDescriptor.getDescription().orElse(eventToEdit.getDescription());
        Address updatedAddress = editEventDescriptor.getAddress().orElse(eventToEdit.getAddress());
        ZoomLink updatedZoomLink = editEventDescriptor.getZoomLink().orElse(eventToEdit.getZoomLink());
        Set<Tag> updatedNewTags = editEventDescriptor.getTags().orElse(new HashSet<>());
        Set<Tag> updatedDeletedTags = editEventDescriptor.getTagsToDelete().orElse(new HashSet<>());
        Set<Tag> updatedTags = editEventDescriptor.getShouldDeleteAllTags()
                ? updatedNewTags : addAndRemoveTags(updatedNewTags, updatedDeletedTags, eventToEdit.getTags());
        Event updatedEvent = new Event(updatedName, updatedStartDateTime, updatedEndDateTime, updatedDescription,
            updatedAddress, updatedZoomLink, updatedTags, eventToEdit.getUuid(), eventToEdit.getLinkedContacts(),
                eventToEdit.getIsMarked());
        // update the edited event to the hashmap that stores references to all events
        Event.addToMap(updatedEvent);
        return updatedEvent;
    }

    /**
     * Creates and returns a {@code Set<Tag>} with tags from {@code original} and {@code toAdd}, but
     * tags in {@code toRemove} will be excluded.
     */
    private Set<Tag> addAndRemoveTags(Set<Tag> toAdd, Set<Tag> toRemove, Set<Tag> original) {
        Set<Tag> updatedTags = new HashSet<>(original);
        String result = "\nNote:\n";
        for (Tag tag : toAdd) {
            if (!updatedTags.add(tag)) { // if the tag to delete is not in the original tags
                result += String.format(MESSAGE_TAG_TO_ADD_ALREADY_IN_ORIGINAL, tag);
            }
        }
        for (Tag tag : toRemove) {
            if (!updatedTags.remove(tag)) { // if the tag to delete is not in the original tags
                result += String.format(MESSAGE_TAG_TO_DELETE_NOT_IN_ORIGINAL, tag);
            }
        }
        infoMessage = !result.equals("\nNote:\n") ? result : "";
        updatedTags.addAll(toAdd);
        return updatedTags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToEdit = lastShownList.get(index.getZeroBased());
        Event editedEvent = createEditedEvent(eventToEdit, editEventDescriptor);

        if (!eventToEdit.isSameEvent(editedEvent) && model.hasEvent(editedEvent)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        if (editedEvent.getEndDateAndTime() != null
                && editedEvent.getEndDateAndTime().isBefore(editedEvent.getStartDateAndTime())) {
            throw new CommandException(MESSAGE_INVALID_DATE_TIME_RANGE);
        }

        model.setEvent(eventToEdit, editedEvent);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        // rerender UI to show latest change for contacts with links to edited event
        model.rerenderContactCards(true);
        String result = String.format(MESSAGE_EDIT_EVENT_SUCCESS, editedEvent) + infoMessage;
        return new CommandResult(result, List.of(EventChanger.editEventChanger(eventToEdit, editedEvent)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EEditCommand)) {
            return false;
        }

        // state check
        EEditCommand e = (EEditCommand) other;
        return index.equals(e.index)
                && editEventDescriptor.equals(e.editEventDescriptor);
    }

    /**
     * Stores the details to edit the event with. Each non-empty field value will replace the
     * corresponding field value of the event.
     */
    public static class EditEventDescriptor {
        private Name name;
        private StartDateTime start;
        private EndDateTime end;
        private Description description;
        private Address address;
        private ZoomLink zoom;
        private Set<Tag> tags;
        private Set<Tag> tagsToDelete;
        private boolean shouldDeleteAllTags = false;

        public EditEventDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditEventDescriptor(EditEventDescriptor toCopy) {
            setName(toCopy.name);
            setStartDateTime(toCopy.start);
            setEndDateTime(toCopy.end);
            setDescription(toCopy.description);
            setAddress(toCopy.address);
            setZoomLink(toCopy.zoom);
            setTags(toCopy.tags);
            setTagsToDelete(toCopy.tagsToDelete);
            setShouldDeleteAllTags(toCopy.shouldDeleteAllTags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, start, end, description, address, zoom, tags, tagsToDelete)
                    || shouldDeleteAllTags;
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setStartDateTime(StartDateTime start) {
            this.start = start;
        }

        public Optional<StartDateTime> getStartDateTime() {
            return Optional.ofNullable(start);
        }

        public void setEndDateTime(EndDateTime end) {
            this.end = end;
        }

        public Optional<EndDateTime> getEndDateTime() {
            return Optional.ofNullable(end);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setZoomLink(ZoomLink zoom) {
            this.zoom = zoom;
        }

        public Optional<ZoomLink> getZoomLink() {
            return Optional.ofNullable(zoom);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null && !tags.isEmpty()) ? new HashSet<>(tags) : null;
        }

        public Optional<Set<Tag>> getTags() {
            return Optional.ofNullable(tags);
        }
        /**
         * Returns an unmodifiable tag set to be removed, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tagsToDelete} is null.
         */
        public Optional<Set<Tag>> getTagsToDelete() {
            return tagsToDelete != null ? Optional.of(Collections.unmodifiableSet((tagsToDelete))) : Optional.empty();
        }

        /**
         * Sets {@code tagsToDelete} to this object's {@code tagsToDelete}.
         * A defensive copy of {@code tagsToDelete} is used internally.
         */
        public void setTagsToDelete(Set<Tag> tagsToDelete) {
            this.tagsToDelete = (tagsToDelete != null && !tagsToDelete.isEmpty()) ? new HashSet<>(tagsToDelete) : null;
        }

        public boolean getShouldDeleteAllTags() {
            return shouldDeleteAllTags;
        }

        /**
         * Sets the boolean condition of whether all tags should be cleared first.
         */
        public void setShouldDeleteAllTags(boolean shouldDeleteAllTags) {
            this.shouldDeleteAllTags = shouldDeleteAllTags;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditEventDescriptor)) {
                return false;
            }

            // state check
            EditEventDescriptor e = (EditEventDescriptor) other;

            return getName().equals(e.getName())
                    && getStartDateTime().equals(e.getStartDateTime())
                    && getEndDateTime().equals(e.getEndDateTime())
                    && getDescription().equals(e.getDescription())
                    && getAddress().equals(e.getAddress())
                    && getZoomLink().equals(e.getZoomLink())
                    && getTags().equals(e.getTags())
                    && getTagsToDelete().equals(e.getTagsToDelete());
        }
    }
}
