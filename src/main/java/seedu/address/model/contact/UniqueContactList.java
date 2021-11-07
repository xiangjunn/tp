package seedu.address.model.contact;


import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.contact.exceptions.ContactNotFoundException;
import seedu.address.model.contact.exceptions.DuplicateContactException;

/**
 * A list of contacts that enforces uniqueness between its elements and does not allow nulls.
 * A contact is considered unique by comparing using {@code Contact#isSameContact(Contact)}. As such, adding and
 * updating of contacts uses Contact#isSameContact(Contact) for equality so as to ensure that the contact being added
 * or updated is unique in terms of identity in the UniqueContactList. However, the removal of a contact uses
 * Contact#equals(Object) so as to ensure that the contact with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Contact#isSameContact(Contact)
 */
public class UniqueContactList implements Iterable<Contact> {

    private final ObservableList<Contact> internalList = FXCollections.observableArrayList();
    private final ObservableList<Contact> internalUnmodifiableList =
        FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent contact as the given argument.
     */
    public boolean contains(Contact toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameContact);
    }

    /**
     * Adds a contact to the list.
     * The contact must not already exist in the list.
     */
    public void add(Contact toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateContactException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the contact {@code target} in the list with {@code editedContact}.
     * {@code target} must exist in the list.
     * The contact identity of {@code editedContact} must not be the same as another existing contact in the list.
     */
    public void setContact(Contact target, Contact editedContact) {
        requireAllNonNull(target, editedContact);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ContactNotFoundException();
        }

        if (!target.isSameContact(editedContact) && contains(editedContact)) {
            throw new DuplicateContactException();
        }

        internalList.set(index, editedContact);
    }

    /**
     * Removes the equivalent contact from the list.
     * The contact must exist in the list.
     */
    public void remove(Contact toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ContactNotFoundException();
        }
    }

    public void setContacts(UniqueContactList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code contacts}.
     * {@code contacts} must not contain duplicate contacts.
     */
    public void setContacts(List<Contact> contacts) {
        requireAllNonNull(contacts);
        if (!contactsAreUnique(contacts)) {
            throw new DuplicateContactException();
        }

        internalList.setAll(contacts);
    }

    /**
     * Remove all the contents of this list.
     */
    public void resetContacts() {
        internalList.clear();
    }

    /**
     * Moves marked contacts to the top of the list.
     * Places the newly marked contacts or replaces newly unmarked contacts
     * in the order specified in {@code contacts} and
     * based on {@code isMarked} which signals whether this method is called by
     * CMarkCommand or otherwise.
     */
    public void rearrangeContactsInOrder(List<Contact> contacts, boolean isMarked) {
        ObservableList<Contact> tempList = FXCollections.observableArrayList();
        if (isMarked) {
            tempList.addAll(contacts);
            tempList.addAll(internalList.filtered(contact -> !contacts.contains(contact)));
        } else {
            tempList.addAll(internalList.filtered(Contact::getIsMarked));
            tempList.addAll(internalList.filtered(c -> !c.getIsMarked()));
        }
        internalList.clear();
        internalList.addAll(tempList);
    }

    /**
     * Update the UUID map in contacts.
     */
    public void updateContactMap() {
        for (Contact contact : internalList) {
            Contact.addToMap(contact);
        }
    }

    /**
     * Get a copy of uniqueContactList
     *
     * @return a copy of a UniqueContactList
     */
    public ObservableList<Contact> copy() {
        List<Contact> contactList = new ArrayList<>(internalList);
        return FXCollections.observableArrayList(contactList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Contact> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Contact> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueContactList // instanceof handles nulls
                && internalList.equals(((UniqueContactList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code contacts} contains only unique contacts.
     */
    private boolean contactsAreUnique(List<Contact> contacts) {
        for (int i = 0; i < contacts.size() - 1; i++) {
            for (int j = i + 1; j < contacts.size(); j++) {
                if (contacts.get(i).isSameContact(contacts.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
