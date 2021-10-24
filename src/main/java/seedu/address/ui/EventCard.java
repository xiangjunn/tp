package seedu.address.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.model.event.Event;

/**
 * An UI component that displays information of an {@code Event}.
 */
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";

    private static Logger logger = LogsCenter.getLogger(EventCard.class);

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
    private Label zoomLinkTitle;

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
            zoomLinkTitle.setText("link: ");
            zoomLinkTitle.setManaged(true);
            zoomLink.setText(event.getZoomLink().link);
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
     * Copies event fields to the clipboard.
     */
    private void copy(String fieldContent, String fieldName) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(fieldContent);
        clipboard.setContent(content);
        mainWindow.handleClick(String.format(Messages.MESSAGE_EVENT_FIELD_COPIED, fieldName));
        logger.info(String.format(Messages.MESSAGE_EVENT_FIELD_COPIED, fieldName));
    }

    /**
     * Copies event name to the clipboard.
     */
    @FXML
    private void copyName() {
        copy(event.getName().fullName, "name");
    }

    /**
     * Copies event start date time to the clipboard.
     */
    @FXML
    private void copyStartDateTime() {
        copy(event.getStartDateAndTime().toString(), "start date time");
    }

    /**
     * Copies contact end date time to the clipboard.
     */
    @FXML
    private void copyEndDateTime() {
        copy(event.getEndDateAndTime().toString(), "end date time");
    }

    /**
     * Copies event description to the clipboard.
     */
    @FXML
    private void copyDescription() {
        copy(event.getDescription().value, "description");
    }

    /**
     * Copies event address to the clipboard.
     */
    @FXML
    private void copyAddress() {
        copy(event.getAddress().value, "address");
    }

    /**
     * Open event links in browser.
     */
    private void openLink(String link, String fieldName) {
        try {
            Desktop.getDesktop().browse(new URI(link));
            logger.info(String.format(Messages.MESSAGE_EVENT_LINK_OPENED, fieldName));
            mainWindow.handleClick(String.format(Messages.MESSAGE_EVENT_LINK_OPENED, fieldName));
        } catch (URISyntaxException | IOException e) {
            logger.info(String.format(Messages.MESSAGE_EVENT_LINK_NOT_FOUND, fieldName));
            mainWindow.handleClick(String.format(Messages.MESSAGE_EVENT_LINK_NOT_FOUND, fieldName));
        }
    }

    /**
     * Open event zoom link in browser.
     */
    @FXML
    private void openZoomLink() {
        openLink(event.getZoomLink().link, "zoom");
    }
}
