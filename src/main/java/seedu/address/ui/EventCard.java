package seedu.address.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.Messages;
import seedu.address.model.event.Event;

/**
 * An UI component that displays information of an {@code Event}.
 */
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";

    private static Logger logger = Logger.getLogger("Event Card");

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private final Event event;

    private MainWindow mainWindow;

    @FXML
    private Label id;

    @FXML
    private Label name;

    @FXML
    private Label from;

    @FXML
    private Label to;

    @FXML
    private Label address;

    @FXML
    private Label zoomLink;

    @FXML
    private Label description;

    @FXML
    private FlowPane tags;

    /**
     * Creates an {@code EventCard} with the given {@code Event} and index to display.
     */
    public EventCard(Event event, int displayedIndex, MainWindow mainWindow) {
        super(FXML);
        this.event = event;
        this.mainWindow = mainWindow;
        id.setText(displayedIndex + ". ");
        // compulsory fields
        name.setText(event.getName().fullName);

        // Compulsory fields
        if (Event.isWillDisplayStartDateTime()) {
            from.setText("from: " + event.getStartDateAndTime());
            from.setManaged(true);
        }
        // Optional fields
        if (event.getEndDateAndTime() != null && Event.isWillDisplayEndDateTime()) {
            to.setText("to: " + event.getEndDateAndTime());
            to.setManaged(true);
        }
        if (event.getAddress() != null && Event.isWillDisplayAddress()) {
            address.setText("location: " + event.getAddress().value);
            address.setManaged(true);
        }
        if (event.getZoomLink() != null && Event.isWillDisplayZoomLink()) {
            zoomLink.setText("link: " + event.getZoomLink().link);
            zoomLink.setManaged(true);
        }
        if (event.getDescription() != null && Event.isWillDisplayDescription()) {
            description.setText("description: " + event.getDescription().value);
            description.setManaged(true);
        }

        if (Event.isWillDisplayTags()) {
            event.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
            tags.setManaged(true);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventCard)) {
            return false;
        }

        // state check
        EventCard card = (EventCard) other;
        return id.getText().equals(card.id.getText())
            && event.equals(card.event);
    }

    /**
     * Open zoom link in browser.
     */
    @FXML
    private void openZoomLink() {
        try {
            Desktop.getDesktop().browse(new URI(event.getZoomLink().link));
            mainWindow.handleClick(Messages.MESSAGE_LINK_OPENED);
        } catch (URISyntaxException | IOException e) {
            logger.log(Level.WARNING, Messages.MESSAGE_LINK_NOT_FOUND);
            mainWindow.handleClick(Messages.MESSAGE_LINK_NOT_FOUND);
        }
    }
}
