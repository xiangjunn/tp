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
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.Messages;
import seedu.address.model.contact.Contact;

/**
 * An UI component that displays information of a {@code Contact}.
 */
public class ContactCard extends UiPart<Region> {

    private static final String FXML = "ContactListCard.fxml";

    private static Logger logger = Logger.getLogger("ContactCard");

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Contact contact;

    private MainWindow mainWindow;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label telegramHandle;
    @FXML
    private Label zoom;
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView emailIcon;

    /**
     * Creates a {@code ContactCard} with the given {@code Contact} and index to display.
     */
    public ContactCard(Contact contact, int displayedIndex, MainWindow mainWindow) {
        super(FXML);
        this.mainWindow = mainWindow;
        this.contact = contact;
        id.setText(displayedIndex + ". ");
        name.setText(contact.getName().fullName);
        // Compulsory fields
        if (Contact.isWillDisplayEmail()) {
            email.setText("email: " + contact.getEmail().value);
            email.setManaged(true);
        }
        // Optional fields
        if (contact.getPhone() != null && Contact.isWillDisplayPhone()) {
            phone.setText("phone: " + contact.getPhone().value);
            phone.setManaged(true);
        }
        if (contact.getAddress() != null && Contact.isWillDisplayAddress()) {
            address.setText("address: " + contact.getAddress().value);
            address.setManaged(true);
        }
        if (contact.getTelegramHandle() != null && Contact.isWillDisplayTelegramHandle()) {
            telegramHandle.setText("telegram handle: " + contact.getTelegramHandle().handle);
            telegramHandle.setManaged(true);
        }
        if (contact.getZoomLink() != null && Contact.isWillDisplayZoomLink()) {
            zoom.setText("zoom: " + contact.getZoomLink().link);
            zoom.setManaged(true);
        }
        if (Contact.isWillDisplayTags()) {
            contact.getTags().stream()
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
        if (!(other instanceof ContactCard)) {
            return false;
        }

        // state check
        ContactCard card = (ContactCard) other;
        return id.getText().equals(card.id.getText())
                && contact.equals(card.contact);
    }

    /**
     * Copies the email to the clipboard.
     */
    @FXML
    private void copyEmail() {
        logger.log(Level.INFO, Messages.MESSAGE_FIELD_COPIED);
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(contact.getEmail().value);
        clipboard.setContent(url);
        mainWindow.handleClick(Messages.MESSAGE_FIELD_COPIED);
    }

    /**
     * Open zoom link in browser.
     */
    @FXML
    private void openZoomLink() {
        try {
            Desktop.getDesktop().browse(new URI(contact.getZoomLink().link));
            mainWindow.handleClick(Messages.MESSAGE_LINK_OPENED);
        } catch (URISyntaxException | IOException e) {
            logger.log(Level.WARNING, Messages.MESSAGE_LINK_NOT_FOUND);
            mainWindow.handleClick(Messages.MESSAGE_LINK_NOT_FOUND);
        }
    }

    /**
     * Open telegram link in browser.
     */
    @FXML
    private void openTelegramHandle() {
        try {
            Desktop.getDesktop().browse(new URI(contact.getTelegramHandle().link));
            mainWindow.handleClick(Messages.MESSAGE_LINK_OPENED);
        } catch (URISyntaxException | IOException e) {
            logger.log(Level.WARNING, Messages.MESSAGE_LINK_NOT_FOUND);
            mainWindow.handleClick(Messages.MESSAGE_LINK_NOT_FOUND);
        }
    }
}
