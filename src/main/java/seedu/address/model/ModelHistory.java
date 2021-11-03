package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

public class ModelHistory {
    private final List<ModelDisplaySetting> modelDisplaySettingHistory = new ArrayList<>();
    private final List<AddressBook> addressBookHistory = new ArrayList<>();
    private int currentPointer = 0; // Size of the history
    private int maxPointer = 0; // The last point of redo
    private static final ModelHistory THE_ONLY_HISTORY = new ModelHistory();
    private ModelHistory() {}

    public static ModelHistory getHistory() {
        return THE_ONLY_HISTORY;
    }

    public void clearHistory() {
        currentPointer = 0;
        maxPointer = 0;
    }

    public void commit(AddressBook addressBook, ModelDisplaySetting displaySetting) {
        modelDisplaySettingHistory.add(currentPointer, displaySetting);
        addressBookHistory.add(currentPointer, addressBook);
        currentPointer++;
        maxPointer = currentPointer;
    }

    public AddressBook getLatestAddressBook() {
        if (!isUndoable()) {
            throw new ModelHistoryException("Trying to access address book when history is empty.");
        }
        return addressBookHistory.get(currentPointer - 1);
    }

    public ModelDisplaySetting getLatestModelDisplaySetting() {
        if (!isUndoable()) {
            throw new ModelHistoryException("Trying to access display setting when history is empty.");
        }
        return modelDisplaySettingHistory.get(currentPointer - 1);
    }

    public void undo() {
        if (!isUndoable()) {
            throw new ModelHistoryException("Trying to undo even though there is no history.");
        }
        currentPointer--;
    }

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

    public boolean isUndoable() {
        return currentPointer > 0;
    }

    public boolean isRedoable() {
        return maxPointer > currentPointer;
    }
}
