package seedu.address.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.UUID;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.model.contact.Contact;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDisplaySetting;

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

    /** True only when the user is attempting to show all links of the {@code event}. */
    private boolean isShowLinks = false;

    @FXML
    private Label id;

    @FXML
    private Label eventName;

    @FXML
    private Label favourite;

    @FXML
    private Label from;

    @FXML
    private Label to;

    @FXML
    private Label address;

    @FXML
    private Label zoom;

    @FXML
    private Label description;

    @FXML
    private Label tagIcon;

    @FXML
    private FlowPane tags;

    @FXML
    private Label linkToContact;

    @FXML
    private FlowPane links;

    @FXML
    private HBox linksHBox;

    /**
     * Creates an {@code EventCard} with the given {@code Event} and index to display.
     */
    public EventCard(
        Event event, int displayedIndex, MainWindow mainWindow,
        EventDisplaySetting eventDisplaySetting) {
        super(FXML);
        this.event = event;
        this.mainWindow = mainWindow;

        boolean isViewMode = eventDisplaySetting.isViewingFull();

        id.setText(displayedIndex + ". ");
        // compulsory fields
        eventName.setText(event.getName().fullName);
        eventName.setWrapText(isViewMode);

        // Compulsory fields
        if (eventDisplaySetting.willDisplayStartDateTime()) {
            from.setText(event.getStartDateAndTime().toString());
            from.setManaged(true);
            from.setVisible(true);
            from.setWrapText(isViewMode);
        }
        // Optional fields
        if (event.getEndDateAndTime() != null && eventDisplaySetting.willDisplayEndDateTime()) {
            to.setText(event.getEndDateAndTime().toString());
            to.setManaged(true);
            to.setVisible(true);
            to.setWrapText(isViewMode);
        }
        if (event.getAddress() != null && eventDisplaySetting.willDisplayAddress()) {
            address.setText(event.getAddress().value);
            address.setManaged(true);
            address.setVisible(true);
            address.setWrapText(isViewMode);
        }
        if (event.getZoomLink() != null && eventDisplaySetting.willDisplayZoomLink()) {
            zoom.setText(event.getZoomLink().link);
            zoom.setManaged(true);
            zoom.setVisible(true);
            zoom.setWrapText(isViewMode);
        }
        if (event.getDescription() != null && eventDisplaySetting.willDisplayDescription()) {
            description.setText(event.getDescription().value);
            description.setManaged(true);
            description.setVisible(true);
            description.setWrapText(isViewMode);
        }

        if (eventDisplaySetting.willDisplayTags() && !event.getTags().isEmpty()) {
            event.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> {
                        Label label = new Label(tag.tagName);
                        label.setStyle("-fx-background-color: " + tag.tagColour + ";");
                        label.setWrapText(isViewMode);
                        tags.getChildren().add(label);
                    });
            tagIcon.setManaged(true);
            tagIcon.setVisible(true);
            tags.setManaged(true);
        }

        if (!event.getLinkedContacts().isEmpty()) {
            event.getLinkedContacts().stream()
                .sorted(Comparator.comparing(UUID::toString))
                .forEach(contactUuid -> links.getChildren()
                    .add(new Label(Contact.findByUuid(contactUuid).getName().toString())));
            linkToContact.setManaged(true);
            linkToContact.setVisible(true);
            links.setManaged(true);
            linkToContact.addEventHandler(MouseEvent.MOUSE_CLICKED, this::toggleShowLinks);
            links.addEventHandler(MouseEvent.MOUSE_CLICKED, this::toggleShowLinks);
        }
        if (event.getIsMarked()) {
            favourite.setManaged(true);
            favourite.setVisible(true);
        }
    }

    private void toggleShowLinks(MouseEvent e) {
        if (isShowLinks) {
            mainWindow.showAllContacts();
        } else {
            mainWindow.showLinksOfEvent(event);
        }
        isShowLinks = !isShowLinks;
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
            if (!link.matches("^http(s)?://.*$")) {
                link = "http://" + link;
            }
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(link));
                logger.info(String.format(Messages.MESSAGE_EVENT_LINK_OPENED, fieldName));
                mainWindow.handleClick(String.format(Messages.MESSAGE_EVENT_LINK_OPENED, fieldName));
            } else {
                copy(link, fieldName);
                logger.warning("Desktop does not support opening URL in browser. Copied link to clipboard");
            }
        } catch (URISyntaxException | IOException e) {
            logger.warning(String.format(Messages.MESSAGE_EVENT_LINK_NOT_FOUND, fieldName));
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
