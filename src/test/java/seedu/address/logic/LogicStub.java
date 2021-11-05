package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.Undoable;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

/**
 * Stub for LogicManager, without the storage capability.
 */
public class LogicStub extends LogicManager {
    private final Model model;
    private final AddressBookParser addressBookParser;

    public LogicStub(Model model) {
        super(model, null);
        addressBookParser = new AddressBookParser();
        this.model = model;
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText);
        commandResult = command.execute(model);
        if (command instanceof Undoable) {
            model.commitHistory();
        }
        return commandResult;
    }
}
