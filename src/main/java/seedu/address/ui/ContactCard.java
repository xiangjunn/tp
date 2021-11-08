package seedu.address.ui;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

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
import seedu.address.model.contact.ContactDisplaySetting;
import seedu.address.model.event.Event;


/**
 * An UI component that displays information of a {@code Contact}.
 */
public class ContactCard extends UiPart<Region> {

    private static final String FXML = "ContactListCard.fxml";

    private static Logger logger = LogsCenter.getLogger(ContactCard.class);

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Contact contact;

    private MainWindow mainWindow;

    private boolean isShowLinks = false;

    @FXML
    private HBox cardPane;

    @FXML
    private Label name;

    @FXML
    private Label favourite;

    @FXML
    private Label id;

    @FXML
    private Label phone;

    @FXML
    private Label address;

    @FXML
    private Label email;

    @FXML
    private Label telegramHandleTitle;

    @FXML
    private Label telegramHandle;

    @FXML
    private Label zoom;

    @FXML
    private Label tagIcon;

    @FXML
    private FlowPane tags;

    @FXML
    private Label linkToEvent;

    @FXML
    private FlowPane links;

    @FXML
    private HBox linksHBox;
    /**
     * Creates a {@code ContactCard} with the given {@code Contact} and index to display.
     */
    public ContactCard(
        Contact contact, int displayedIndex, MainWindow mainWindow,
        ContactDisplaySetting displaySetting) {
        super(FXML);
        requireAllNonNull(contact, displayedIndex, mainWindow);
        this.mainWindow = mainWindow;
        this.contact = contact;
        boolean isViewMode = displaySetting.isViewingFull();

        id.setText(displayedIndex + ". ");
        name.setText(contact.getName().fullName);
        name.setWrapText(isViewMode);

        // Compulsory fields
        if (displaySetting.willDisplayEmail()) {
            email.setText(contact.getEmail().value);
            email.setManaged(true);
            email.setVisible(true);
            email.setWrapText(isViewMode);
        }
        // Optional fields
        if (contact.getPhone() != null && displaySetting.willDisplayPhone()) {
            phone.setText(contact.getPhone().value);
            phone.setManaged(true);
            phone.setVisible(true);
            phone.setWrapText(isViewMode);
        }
        if (contact.getAddress() != null && displaySetting.willDisplayAddress()) {
            address.setText(contact.getAddress().value);
            address.setManaged(true);
            address.setVisible(true);
            address.setWrapText(isViewMode);
        }
        if (contact.getTelegramHandle() != null && displaySetting.willDisplayTelegramHandle()) {
            telegramHandle.setText(contact.getTelegramHandle().handle);
            telegramHandle.setManaged(true);
            telegramHandle.setVisible(true);
            telegramHandle.setWrapText(isViewMode);
        }
        if (contact.getZoomLink() != null && displaySetting.willDisplayZoomLink()) {
            zoom.setText(contact.getZoomLink().link);
            zoom.setManaged(true);
            zoom.setVisible(true);
            zoom.setWrapText(isViewMode);
        }
        if (displaySetting.willDisplayTags() && !contact.getTags().isEmpty()) {
            contact.getTags().stream()
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

        if (!contact.getLinkedEvents().isEmpty()) {
            contact.getLinkedEvents().stream()
                    .sorted(Comparator.comparing(UUID::toString))
                    .forEach(eventUuid -> {
                        String eventName = Event.findByUuid(eventUuid).getName().toString();
                        links.getChildren().add(new Label(eventName));
                    });
            linkToEvent.setManaged(true);
            linkToEvent.setVisible(true);
            links.setManaged(true);

        }

        linkToEvent.addEventHandler(MouseEvent.MOUSE_CLICKED, this::toggleShowLinks);
        links.addEventHandler(MouseEvent.MOUSE_CLICKED, this::toggleShowLinks);

        if (contact.getIsMarked()) {
            favourite.setManaged(true);
            favourite.setVisible(true);
        }
    }

    private void toggleShowLinks(MouseEvent e) {
        if (isShowLinks) {
            mainWindow.showAllEvents();
        } else {
            mainWindow.showLinksOfContact(contact);
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
        if (!(other instanceof ContactCard)) {
            return false;
        }

        // state check
        ContactCard card = (ContactCard) other;
        return id.getText().equals(card.id.getText())
                && contact.equals(card.contact);
    }

    /**
     * Copies contact fields to the clipboard.
     */
    private void copy(String fieldContent, String fieldName) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(fieldContent);
        clipboard.setContent(content);
        mainWindow.handleClick(String.format(Messages.MESSAGE_CONTACT_FIELD_COPIED, fieldName));
        logger.info(String.format(Messages.MESSAGE_CONTACT_FIELD_COPIED, fieldName));
    }

    /**
     * Copies contact name to the clipboard.
     */
    @FXML
    private void copyName() {
        copy(contact.getName().fullName, "name");
    }

    /**
     * Copies contact email to the clipboard.
     */
    @FXML
    private void copyEmail() {
        copy(contact.getEmail().value, "email");
    }

    /**
     * Copies contact phone number to the clipboard.
     */
    @FXML
    private void copyPhone() {
        copy(contact.getPhone().value, "phone");
    }

    /**
     * Copies contact address to the clipboard.
     */
    @FXML
    private void copyAddress() {
        copy(contact.getAddress().value, "address");
    }

    /**
     * Open contact links in browser
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
            logger.warning(String.format(Messages.MESSAGE_CONTACT_LINK_NOT_FOUND, fieldName));
            mainWindow.handleClick(String.format(Messages.MESSAGE_CONTACT_LINK_NOT_FOUND, fieldName));
        }
    }

    /**
     * Open contact zoom link in browser.
     */
    @FXML
    private void openZoomLink() {
        openLink(contact.getZoomLink().link, "zoom");
    }

    /**
     * Open telegram link in browser.
     */
    @FXML
    private void openTelegramHandle() {
        openLink(contact.getTelegramHandle().link, "telegram");
    }
}
