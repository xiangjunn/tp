package seedu.address.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.event.EAddCommand;
import seedu.address.logic.commands.event.EClearCommand;
import seedu.address.logic.commands.event.EDeleteCommand;
import seedu.address.logic.commands.event.EEditCommand;
import seedu.address.logic.commands.event.EFindCommand;
import seedu.address.logic.commands.event.ELinkCommand;
import seedu.address.logic.commands.event.EListCommand;
import seedu.address.logic.commands.event.EMarkCommand;
import seedu.address.logic.commands.event.ESortCommand;
import seedu.address.logic.commands.event.EUnlinkCommand;
import seedu.address.logic.commands.event.EUnmarkCommand;
import seedu.address.logic.commands.event.EViewCommand;



public class EventCommandSummary {

    public static final String ADD = "Add";
    public static final String MARK = "Mark";
    public static final String CLEAR = "Clear";
    public static final String DELETE = "Delete";
    public static final String EDIT = "Edit";
    public static final String FIND = "Find\n(at least one keyword must be present)";
    public static final String LINK = "Link";
    public static final String LIST = "List";
    public static final String REMOVE_MARK = "Remove mark";
    public static final String SORT = "Sort";
    public static final String UNLINK = "Unlink";
    public static final String VIEW = "View";

    /** Description of what the command does. */
    private StringProperty action;

    /** Format of a valid command. */
    private StringProperty format;


    /**
     * Constructs a EventCommandSummary object.
     *
     * @param action Description of what the command does.
     * @param format Format of a valid command.
     */
    public EventCommandSummary(String action, String format) {
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
     * Creates and returns an observable list of command actions and formats for event management.
     *
     * @return An observable list of command actions and formats for event management.
     */
    public static ObservableList<EventCommandSummary> getEventCommandSummary() {
        return FXCollections.observableArrayList(
                new EventCommandSummary(ADD, EAddCommand.SYNTAX),
                new EventCommandSummary(CLEAR, EClearCommand.SYNTAX),
                new EventCommandSummary(DELETE, EDeleteCommand.SYNTAX),
                new EventCommandSummary(EDIT, EEditCommand.SYNTAX),
                new EventCommandSummary(FIND, EFindCommand.SYNTAX),
                new EventCommandSummary(LINK, ELinkCommand.SYNTAX),
                new EventCommandSummary(LIST, EListCommand.SYNTAX),
                new EventCommandSummary(MARK, EMarkCommand.SYNTAX),
                new EventCommandSummary(REMOVE_MARK, EUnmarkCommand.SYNTAX),
                new EventCommandSummary(SORT, ESortCommand.SYNTAX),
                new EventCommandSummary(UNLINK, EUnlinkCommand.SYNTAX),
                new EventCommandSummary(VIEW, EViewCommand.SYNTAX));
    }
}

