package seedu.address.logic.commands.general;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalAddressBook;

class UndoCommandTest {

    private final AddressBook initialAddressBook = TypicalAddressBook.getTypicalAddressBook();
    private final Model model = new ModelManager(initialAddressBook, new UserPrefs());

    @Test
    public void execute() {
        // TODO
    }
}
