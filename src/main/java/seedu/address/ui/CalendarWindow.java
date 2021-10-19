package seedu.address.ui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

/**
 * Panel showing the list of events as a calendar.
 */
public class CalendarWindow extends UiPart<Stage> {
    private static final String FXML = "CalendarWindow.fxml";
    private static final Logger logger = LogsCenter.getLogger(CalendarWindow.class);

    private CalendarView calendarView = new CalendarView();
    private final Calendar calendarOfEvents = new Calendar("Events");

    @FXML
    private StackPane calendarui;

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
        CalendarSource calendarSource = new CalendarSource();
        calendarSource.getCalendars().add(calendarOfEvents);
        calendarView.getCalendarSources().add(calendarSource);
        calendarView.setRequestedTime(LocalTime.now());
        calendarView.showWeekPage();
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowPageToolBarControls(false);
        calendarView.setShowPrintButton(false);
        calendarView.setShowSearchField(false);
        calendarView.setShowSourceTrayButton(false);
        calendarView.setEntryEditPolicy(param -> false);
        calendarView.setEntryContextMenuCallback(param -> null);
        calendarui.getChildren().add(calendarView);
    }

    private void createCalendar(ObservableList<Event> events) {
        events.forEach(event -> {
            DateAndTime start = event.getStartDateAndTime();
            DateAndTime end = event.getEndDateAndTime();
            if (end == null) {
                String newEndString = start.getDateTime().plusHours(1)
                    .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
                end = new EndDateTime(newEndString);
            }
            Entry<Event> entry = new Entry<>();
            entry.setTitle(event.getName().fullName);
            entry.changeStartDate(start.time.toLocalDate());
            entry.changeStartTime(start.time.toLocalTime());
            entry.changeEndDate(end.time.toLocalDate());
            entry.changeEndTime(end.time.toLocalTime());
            entry.setCalendar(calendarOfEvents);
        });
    }

    /**
     * Shows the help window.
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
                        // update every 10 seconds
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
