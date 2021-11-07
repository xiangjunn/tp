package seedu.address.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.contact.CAddCommand;
import seedu.address.logic.commands.contact.CClearCommand;
import seedu.address.logic.commands.contact.CDeleteCommand;
import seedu.address.logic.commands.contact.CEditCommand;
import seedu.address.logic.commands.contact.CFindCommand;
import seedu.address.logic.commands.contact.CListCommand;
import seedu.address.logic.commands.contact.CMarkCommand;
import seedu.address.logic.commands.contact.CUnmarkCommand;
import seedu.address.logic.commands.contact.CViewCommand;

public class ContactCommandSummary {

    public static final String ADD = "Add";
    public static final String MARK = "Mark";
    public static final String CLEAR = "Clear";
    public static final String DELETE = "Delete";
    public static final String EDIT = "Edit";
    public static final String FIND = "Find\n(at least one keyword must be present)";
    public static final String LIST = "List";
    public static final String REMOVE_MARK = "Remove mark";
    public static final String VIEW = "View";

    /** Description of what the command does. */
    private StringProperty action;

    /** Format of a valid command. */
    private StringProperty format;


    /**
     * Constructs a ContactCommandSummary object.
     *
     * @param action Description of what the command does.
     * @param format Format of a valid command.
     */
    public ContactCommandSummary(String action, String format) {
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
    public static ObservableList<ContactCommandSummary> getContactCommandSummary() {
        return FXCollections.observableArrayList(
                new ContactCommandSummary(ADD, CAddCommand.SYNTAX),
                new ContactCommandSummary(CLEAR, CClearCommand.SYNTAX),
                new ContactCommandSummary(DELETE, CDeleteCommand.SYNTAX),
                new ContactCommandSummary(EDIT, CEditCommand.SYNTAX),
                new ContactCommandSummary(FIND, CFindCommand.SYNTAX),
                new ContactCommandSummary(LIST, CListCommand.SYNTAX),
                new ContactCommandSummary(MARK, CMarkCommand.SYNTAX),
                new ContactCommandSummary(REMOVE_MARK, CUnmarkCommand.SYNTAX),
                new ContactCommandSummary(VIEW, CViewCommand.SYNTAX));
    }
}
