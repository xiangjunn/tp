package seedu.address.logic.commands.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ZOOM;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONTACTS;

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
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Phone;
import seedu.address.model.contact.TelegramHandle;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing contact in the address book.
 */
public class CEditCommand extends Command implements Undoable {

    public static final String COMMAND_WORD = "cedit";
    public static final String PARAMETERS = "INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TELEGRAM + "TELEGRAM] "
            + "[" + PREFIX_ZOOM + "ZOOM] "
            + "[" + PREFIX_TAG + "TAG]... "
            + "[" + PREFIX_DELETE_TAG + "DELETE_TAG]...\n";
    public static final String SYNTAX = COMMAND_WORD + " " + PARAMETERS;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the contact identified "
        + "by the index number used in the displayed contact list. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: " + PARAMETERS
        + "Example: " + COMMAND_WORD + " 1 "
        + PREFIX_PHONE + "91234567 "
        + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_CONTACT_SUCCESS = "Edited Contact: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CONTACT = "This contact already exists in the address book.";
    public static final String MESSAGE_TAG_TO_ADD_ALREADY_IN_ORIGINAL = "Contact already has %s tag.\n";
    public static final String MESSAGE_TAG_TO_DELETE_NOT_IN_ORIGINAL =
            "Contact does not contain %s tag to delete.\n";

    private final Index index;
    private final EditContactDescriptor editContactDescriptor;
    // to be displayed to user if user tries to delete a tag that does not exist
    // or add a tag that already exists
    private String infoMessage = "";

    /**
     * @param index                 of the contact in the filtered contact list to edit
     * @param editContactDescriptor details to edit the contact with
     */
    public CEditCommand(Index index, EditContactDescriptor editContactDescriptor) {
        requireNonNull(index);
        requireNonNull(editContactDescriptor);

        this.index = index;
        this.editContactDescriptor = new EditContactDescriptor(editContactDescriptor);
    }

    /**
     * Creates and returns a {@code Contact} with the details of {@code contactToEdit}
     * edited with {@code editContactDescriptor}.
     */
    private Contact createEditedContact(Contact contactToEdit, EditContactDescriptor editContactDescriptor) {
        assert contactToEdit != null;

        Name updatedName = editContactDescriptor.getName().orElse(contactToEdit.getName());
        Phone updatedPhone = editContactDescriptor.getPhone().orElse(contactToEdit.getPhone());
        Email updatedEmail = editContactDescriptor.getEmail().orElse(contactToEdit.getEmail());
        Address updatedAddress = editContactDescriptor.getAddress().orElse(contactToEdit.getAddress());
        TelegramHandle updatedTelegram = editContactDescriptor.getTelegramHandle()
            .orElse(contactToEdit.getTelegramHandle());
        ZoomLink updatedZoomLink = editContactDescriptor.getZoomLink().orElse(contactToEdit.getZoomLink());
        Set<Tag> updatedNewTags = editContactDescriptor.getTags().orElse(new HashSet<>());
        Set<Tag> updatedDeletedTags = editContactDescriptor.getTagsToDelete().orElse(new HashSet<>());
        Set<Tag> updatedTags = editContactDescriptor.isShouldDeleteAllTags()
            ? updatedNewTags : addAndRemoveTags(updatedNewTags, updatedDeletedTags, contactToEdit.getTags());
        Contact updatedContact = new Contact(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedZoomLink,
            updatedTelegram, updatedTags, contactToEdit.getUuid(), contactToEdit.getLinkedEvents(),
                contactToEdit.getIsMarked());
        // update the edited contact to the hashmap that stores references to all contacts
        Contact.addToMap(updatedContact);
        return updatedContact;
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
        toRemove.forEach(updatedTags::remove);
        updatedTags.addAll(toAdd);
        return updatedTags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Contact> lastShownList = model.getFilteredContactList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
        }

        Contact contactToEdit = lastShownList.get(index.getZeroBased());
        Contact editedContact = createEditedContact(contactToEdit, editContactDescriptor);

        if (!contactToEdit.isSameContact(editedContact) && model.hasContact(editedContact)) {
            throw new CommandException(MESSAGE_DUPLICATE_CONTACT);
        }

        model.setContact(contactToEdit, editedContact);
        model.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
        // rerender UI to show latest change for events with links to edited contact
        model.rerenderEventCards(true);
        String result = String.format(MESSAGE_EDIT_CONTACT_SUCCESS, editedContact) + infoMessage;
        return new CommandResult(result);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CEditCommand)) {
            return false;
        }

        // state check
        CEditCommand e = (CEditCommand) other;
        return index.equals(e.index)
            && editContactDescriptor.equals(e.editContactDescriptor);
    }

    /**
     * Stores the details to edit the contact with. Each non-empty field value will replace the
     * corresponding field value of the contact.
     */
    public static class EditContactDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private TelegramHandle telegramHandle;
        private ZoomLink zoomLink;
        private Set<Tag> tags;
        private Set<Tag> tagsToDelete;
        private boolean shouldDeleteAllTags = false;

        public EditContactDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditContactDescriptor(EditContactDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTelegramHandle(toCopy.telegramHandle);
            setZoomLink(toCopy.zoomLink);
            setTags(toCopy.tags);
            setTagsToDelete(toCopy.tagsToDelete);
            setShouldDeleteAllTags(toCopy.shouldDeleteAllTags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, telegramHandle, zoomLink,
                tags, tagsToDelete) || shouldDeleteAllTags;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<TelegramHandle> getTelegramHandle() {
            return Optional.ofNullable(telegramHandle);
        }

        public void setTelegramHandle(TelegramHandle telegramHandle) {
            this.telegramHandle = telegramHandle;
        }

        public Optional<ZoomLink> getZoomLink() {
            return Optional.ofNullable(zoomLink);
        }

        public void setZoomLink(ZoomLink zoomLink) {
            this.zoomLink = zoomLink;
        }

        /**
         * Returns an unmodifiable tag set to be added, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null && !tags.isEmpty()) ? new HashSet<>(tags) : null;
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

        public boolean isShouldDeleteAllTags() {
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
            if (!(other instanceof EditContactDescriptor)) {
                return false;
            }

            // state check
            EditContactDescriptor e = (EditContactDescriptor) other;

            return getName().equals(e.getName())
                && getPhone().equals(e.getPhone())
                && getEmail().equals(e.getEmail())
                && getAddress().equals(e.getAddress())
                && getTelegramHandle().equals(e.getTelegramHandle())
                && getZoomLink().equals(e.getZoomLink())
                && getTags().equals(e.getTags())
                && getTagsToDelete().equals(e.getTagsToDelete());
        }
    }
}
