package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.address.model.event.EventChanger;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /**
     * Help information should be shown to the user.
     */
    private final boolean showHelp;

    /**
     * The application should exit.
     */
    private final boolean exit;

    /**
     * The calendar should be shown to the user.
     */
    private final boolean showCalendar;

    /**
     * The list of all events to be changed. By default an empty list
     */
    private final List<EventChanger> eventChangerList;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean showCalendar) {
        assert numberOfTrueAtMost1(showHelp, exit, showCalendar) : "Invalid combination of boolean values.";
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.showCalendar = showCalendar;
        this.eventChangerList = new ArrayList<>();
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, List<EventChanger> eventChangerList) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = false;
        this.exit = false;
        this.showCalendar = false;
        this.eventChangerList = requireNonNull(eventChangerList);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isShowCalendar() {
        return showCalendar;
    }

    public List<EventChanger> getEventChangerList() {
        return eventChangerList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
            && showHelp == otherCommandResult.showHelp
            && exit == otherCommandResult.exit
            && showCalendar == otherCommandResult.showCalendar
            && eventChangerList.equals(otherCommandResult.eventChangerList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }

    /**
     * For assertion checking.
     */
    private boolean numberOfTrueAtMost1(boolean first, boolean second, boolean third) {
        // Calculated using k-map to obtain disjunctive normal form.
        return (!first && !second) || (!first && !third) || (!second && !third);
    }

    /**
     * For testing purposes.
     */
    @Override
    public String toString() {
        return "CommandResult{"
            + "feedbackToUser='" + feedbackToUser + '\''
            + ", showHelp=" + showHelp
            + ", exit=" + exit
            + ", showCalendar=" + showCalendar
            + ", eventChangerList=" + eventChangerList
            + '}';
    }
}
