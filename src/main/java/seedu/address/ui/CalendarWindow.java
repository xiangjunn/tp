package seedu.address.ui;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.event.DateAndTime;
import seedu.address.model.event.EndDateTime;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventChanger;

/**
 * Panel showing the list of events as a calendar.
 */
public class CalendarWindow extends UiPart<Stage> {
    private static final String FXML = "CalendarWindow.fxml";
    private static final Logger logger = LogsCenter.getLogger(CalendarWindow.class);

    private CalendarView calendarView = new CalendarView();
    private final Calendar calendarOfEvents = new Calendar("Events");

    private final Map<Event, Entry<Event>> mapOfCalendarEntries = new HashMap<>();

    @FXML
    private StackPane calendarUi;

    /**
     * Creates a {@code CalendarWindow} with the given {@code ObservableList}.
     */
    public CalendarWindow(ObservableList<Event> events) {
        super(FXML, new Stage());
        createTimeThread();
        calendarView.setMinWidth(800);
        createCalendar(events);
        updateCalendarView();
    }

    private void updateCalendarView() {
        // Adding calendar to the UI
        CalendarSource calendarSource = new CalendarSource();
        calendarSource.getCalendars().add(calendarOfEvents);
        calendarView.getCalendarSources().add(calendarSource);

        // Setting calendar view options
        calendarView.setRequestedTime(LocalTime.now());
        calendarView.showWeekPage();
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowPageToolBarControls(false);
        calendarView.setShowPrintButton(false);
        calendarView.setShowSearchField(false);
        calendarView.setShowSourceTrayButton(false);
        calendarView.setEntryContextMenuCallback(param -> null);
        calendarView.setContextMenuCallback(param -> null);
        calendarUi.getChildren().add(calendarView);
    }

    /** Creates a calendar with all entries from {@code events}. */
    public void createCalendar(List<Event> events) {
        calendarOfEvents.setReadOnly(true);
        events.forEach(this::createEntry);
    }

    /**
     * Creates a new calendar entry and adds them to the calendar based on {@code event}.
     */
    private void createEntry(Event event) {
        if (event == null) {
            return;
        }
        DateAndTime start = event.getStartDateAndTime();
        DateAndTime end = event.getEndDateAndTime();
        if (end == null) {
            String newEndString = start.getDateTime().plusHours(1)
                .format(DateAndTime.DATE_TIME_FORMATTER);
            end = new EndDateTime(newEndString);
        }
        Entry<Event> entry = new Entry<>();
        entry.setTitle(event.getName().fullName);
        entry.setMinimumDuration(Duration.ZERO);
        entry.setInterval(start.time, end.time);
        entry.setCalendar(calendarOfEvents);
        mapOfCalendarEntries.put(event, entry);
    }

    /**
     * Deletes a calendar entry from the calendar the calendar based on {@code event}.
     */
    private void deleteEntry(Event event) {
        Entry<Event> entryToDelete = mapOfCalendarEntries.remove(event);
        assert entryToDelete != null : "Entry must exist before it can be deleted";
        calendarOfEvents.removeEntry(entryToDelete);
    }

    /** Deletes all entries in the calendar. */
    public void clearCalendar() {
        calendarOfEvents.clear();
    }

    /** Updates the calendar according the events being changed. */
    public void updateCalendar(List<EventChanger> eventChangerList) {
        for (EventChanger eventChanger : eventChangerList) {
            if (eventChanger.isClearing()) {
                clearCalendar();
                return;
            }
            if (eventChanger.isAdding()) {
                createEntry(eventChanger.getNewEvent());
                continue;
            }
            if (eventChanger.isEditing()) {
                deleteEntry(eventChanger.getOldEvent());
                createEntry(eventChanger.getNewEvent());
                continue;
            }
            if (eventChanger.isDeleting()) {
                deleteEntry(eventChanger.getOldEvent());
            }
        }
    }

    /**
     * Shows the calendar window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing calendar.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the calendar window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the calendar window.
     */
    public void close() {
        getRoot().close();
    }

    /**
     * Adapted from CalendarFX developer manual.
     * http://dlsc.com/wp-content/html/calendarfx/manual.html#_quick_start
     */
    private void createTimeThread() {
        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        calendarView.setToday(LocalDate.now());
                        calendarView.setTime(LocalTime.now());
                    });
                    try {
                        // update every 10 seconds (equivalent to 10000 milliseconds)
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
    }
}
