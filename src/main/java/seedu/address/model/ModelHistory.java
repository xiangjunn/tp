package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the past history of the model manager. Meant for the undo and redo commands.
 */
public class ModelHistory {
    private static final ModelHistory THE_ONLY_HISTORY = new ModelHistory();

    private final List<ModelDisplaySetting> modelDisplaySettingHistory = new ArrayList<>();
    private final List<AddressBook> addressBookHistory = new ArrayList<>();
    private int currentPointer = 0; // Size of the history
    private int maxPointer = 0; // The last point of redo

    private ModelHistory() {}

    /** Returns the {@code ModelHistory} object. */
    public static ModelHistory getHistory() {
        return THE_ONLY_HISTORY;
    }

    /** Resets the history. */
    public void clearHistory() {
        currentPointer = 0;
        maxPointer = 0;
    }

    /** Adds a commit to the history, with the given {@code AddressBook} and {@code ModelDisplaySetting}. */
    public void commit(AddressBook addressBook, ModelDisplaySetting displaySetting) {
        modelDisplaySettingHistory.add(currentPointer, displaySetting);
        addressBookHistory.add(currentPointer, addressBook);
        currentPointer++;
        maxPointer = currentPointer;
    }

    /** Retrieves the most recent address book from history. */
    public AddressBook getLatestAddressBook() {
        if (!isUndoable()) {
            throw new ModelHistoryException("Trying to access address book when history is empty.");
        }
        return addressBookHistory.get(currentPointer - 1);
    }

    /** Retrieves the most recent model display settings from history. */
    public ModelDisplaySetting getLatestModelDisplaySetting() {
        if (!isUndoable()) {
            throw new ModelHistoryException("Trying to access display setting when history is empty.");
        }
        return modelDisplaySettingHistory.get(currentPointer - 1);
    }

    /** Performs an undo operation to move the current pointer back one position. */
    public void undo() {
        if (!isUndoable()) {
            throw new ModelHistoryException("Trying to undo even though there is no history.");
        }
        currentPointer--;
    }

    /** Performs a redo operation to move the current pointer forward by one position. */
    public void redo() {
        if (!isRedoable()) {
            throw new ModelHistoryException("Trying to redo even though it is impossible.");
        }
        currentPointer++;
    }

    /** Removes the commit, as if nothing happened. This is different from undo because there is no re-doing. */
    public void removeCommit() {
        undo();
        maxPointer--;
        assert maxPointer >= currentPointer;
    }

    /** Returns true if it is possible to perform an undo operation here. */
    public boolean isUndoable() {
        return currentPointer > 0;
    }

    /** Returns true if it is possible to perform a redo operation here. */
    public boolean isRedoable() {
        return maxPointer > currentPointer;
    }
}
