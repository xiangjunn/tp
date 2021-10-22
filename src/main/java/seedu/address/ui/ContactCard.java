package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.contact.Contact;
import seedu.address.model.event.Event;

/**
 * An UI component that displays information of a {@code Contact}.
 */
public class ContactCard extends UiPart<Region> {

    private static final String FXML = "ContactListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Contact contact;

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
    private Label linksLabel;
    @FXML
    private FlowPane links;

    /**
     * Creates a {@code ContactCard} with the given {@code Contact} and index to display.
     */
    public ContactCard(Contact contact, int displayedIndex) {
        super(FXML);
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
        if (!contact.getLinkedEvents().isEmpty()) {
            contact.getLinkedEvents().stream()
                .sorted(Comparator.comparing(eventUuid -> eventUuid.toString()))
                .forEach(eventUuid -> {
                    String eventName = Event.findByUuid(eventUuid).getName().toString();
                    links.getChildren().add(new Label(eventName));
                });
            linksLabel.setManaged(true);
            links.setManaged(true);
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
}
