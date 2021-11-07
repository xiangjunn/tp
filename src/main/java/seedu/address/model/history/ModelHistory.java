package seedu.address.model.history;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.address.model.ModelDisplaySetting;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Stores the past history of the model manager. Meant for the undo and redo commands.
 */
public class ModelHistory {

    private final List<HistoryInstance> allHistory = new ArrayList<>();
    private int currentSize = 0; // Size of the history/Number of undo commands allowed
    private int maxSize = 0; // The last point of redo
    // maxSize - currentSize = Number of redo commands allowed.

    public ModelHistory() {}

    /** Resets the history. */
    public void clearHistory() {
        currentSize = 0;
        maxSize = 0;
    }

    /** Adds a commit to the history, with the given {@code AddressBook} and {@code ModelDisplaySetting}. */
    public void commit(ReadOnlyAddressBook addressBook, ModelDisplaySetting displaySetting) {
        allHistory.add(currentSize, new HistoryInstance(addressBook, displaySetting));
        currentSize++;
        maxSize = currentSize;
    }

    /** Performs an undo operation to move the current pointer back one position. */
    public HistoryInstance undo() {
        if (!isUndoable()) {
            throw new ModelHistoryException("Trying to undo even though there is no history.");
        }
        // Moves pointer from the latest commit to the previous commit of the new state.
        currentSize--;
        return getCurrentHistoryInstance();
    }

    /** Performs a redo operation to move the current pointer forward by one position. */
    public HistoryInstance redo() {
        if (!isRedoable()) {
            throw new ModelHistoryException("Trying to redo even though it is impossible.");
        }
        currentSize++;
        return getCurrentHistoryInstance();
    }

    /** Returns true if it is possible to perform an undo operation here. */
    public boolean isUndoable() {
        return currentSize > 1;
    }

    /** Returns true if it is possible to perform a redo operation here. */
    public boolean isRedoable() {
        return maxSize > currentSize;
    }

    public HistoryInstance getCurrentHistoryInstance() {
        return allHistory.get(currentSize - 1);
    }

    /** Encapsulates a point in history, with the address book and model display setting. */
    public static class HistoryInstance {
        private final ModelDisplaySetting displaySetting;
        private final ReadOnlyAddressBook addressBook;

        /** Creates a new instance of history. */
        public HistoryInstance(ReadOnlyAddressBook addressBook, ModelDisplaySetting displaySetting) {
            requireAllNonNull(displaySetting, addressBook);
            this.displaySetting = displaySetting;
            this.addressBook = addressBook;
        }

        public ModelDisplaySetting getDisplaySetting() {
            return displaySetting;
        }

        public ReadOnlyAddressBook getAddressBook() {
            return addressBook;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof HistoryInstance)) {
                return false;
            }
            HistoryInstance that = (HistoryInstance) o;
            return getDisplaySetting().equals(that.getDisplaySetting()) && getAddressBook().equals(
                that.getAddressBook());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getDisplaySetting(), getAddressBook());
        }
    }

    //// FOR TESTING (PACKAGE-PRIVATE ACCESS MODIFIER)
    List<HistoryInstance> getAllHistory() {
        return allHistory;
    }

    int getCurrentSize() {
        return currentSize;
    }

    int getMaxSize() {
        return maxSize;
    }
}
