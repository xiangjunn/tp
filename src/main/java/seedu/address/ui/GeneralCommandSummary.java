package seedu.address.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.general.CalendarCommand;
import seedu.address.logic.commands.general.ExitCommand;
import seedu.address.logic.commands.general.HelpCommand;
import seedu.address.logic.commands.general.RedoCommand;
import seedu.address.logic.commands.general.UndoCommand;


public class GeneralCommandSummary {

    public static final String CALENDAR = "Calendar";
    public static final String EXIT = "Exit";
    public static final String HELP = "Help";
    public static final String UNDO = "Undo";
    public static final String REDO = "Redo";

    private StringProperty action;

    /** Format of a valid command. */
    private StringProperty format;


    /**
     * Constructs a GeneralCommandSummary object.
     *
     * @param action Description of what the command does.
     * @param format Format of a valid command.
     */
    public GeneralCommandSummary(String action, String format) {
        this.action = new SimpleStringProperty(action);
        this.format = new SimpleStringProperty(format);
    }

    /**
     * Returns the description of what the command does.
     *
     * @return Description of what the command does.
     */
    public String getAction() {
        return action.get();
    }

    /**
     * Returns the format of a valid command.
     *
     * @return Format of a valid command.
     */
    public String getFormat() {
        return format.get();
    }


    /**
     * Returns the StringProperty object of action.
     * @return StringProperty for action.
     */
    public StringProperty actionProperty() {
        return action;
    }

    /**
     * Returns the StringProperty object of format.
     * @return StringProperty for format.
     */
    public StringProperty formatProperty() {
        return format;
    }


    /**
     * Creates and returns an observable list of command actions and formats for contact management.
     *
     * @return An observable list of command actions and formats for contact management.
     */
    public static ObservableList<GeneralCommandSummary> getGeneralCommandSummary() {
        return FXCollections.observableArrayList(
                new GeneralCommandSummary(CALENDAR, CalendarCommand.SYNTAX),
                new GeneralCommandSummary(EXIT, ExitCommand.SYNTAX),
                new GeneralCommandSummary(HELP, HelpCommand.SYNTAX),
                new GeneralCommandSummary(REDO, RedoCommand.SYNTAX),
                new GeneralCommandSummary(UNDO, UndoCommand.SYNTAX));
    }
}
