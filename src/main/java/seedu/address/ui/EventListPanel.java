package seedu.address.ui;

import java.util.function.Supplier;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDisplaySetting;

/**
 * Panel containing the list of Event.
 */
public class EventListPanel extends UiPart<Region> {
    private static final String FXML = "EventListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(EventListPanel.class);

    @FXML
    private ListView<Event> eventListView;

    /**
     * Creates a {@code EventListPanel} with the given {@code ObservableList}.
     */
    public EventListPanel(
        ObservableList<Event> eventList, MainWindow mainWindow, Supplier<EventDisplaySetting> displaySettingSupplier) {
        super(FXML);
        eventListView.setItems(eventList);
        eventListView.setCellFactory(listView -> new EventListViewCell(mainWindow, displaySettingSupplier));
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Event} using a {@code EventCard}.
     */
    class EventListViewCell extends ListCell<Event> {
        private final MainWindow mainWindow;
        private final Supplier<EventDisplaySetting> displaySettingSupplier;

        public EventListViewCell(
            MainWindow mainWindow, Supplier<EventDisplaySetting> displaySettingSupplier) {
            this.mainWindow = mainWindow;
            this.displaySettingSupplier = displaySettingSupplier;
        }

        @Override
        protected void updateItem(Event event, boolean empty) {
            super.updateItem(event, empty);

            if (empty || event == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EventCard(event, getIndex() + 1, mainWindow,
                    displaySettingSupplier.get()).getRoot());
            }
        }
    }

}
