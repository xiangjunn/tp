package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.model.contact.Contact;
import seedu.address.model.event.Event;

/**
 * An UI component that displays information of an {@code Event}.
 */
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private final Event event;

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

    @FXML
    private Label linksLabel;

    @FXML
    private FlowPane links;

    /**
     * Creates an {@code EventCard} with the given {@code Event} and index to display.
     */
    public EventCard(
        Event event, int displayedIndex) {
        super(FXML);
        this.event = event;

        boolean isViewMode = Event.isViewingMode();

        id.setText(displayedIndex + ". ");
        // compulsory fields
        name.setText(event.getName().fullName);
        name.setWrapText(isViewMode);

        // Compulsory fields
        if (Event.isWillDisplayStartDateTime()) {
            from.setText("from: " + event.getStartDateAndTime());
            from.setManaged(true);
            from.setWrapText(isViewMode);
        }
        // Optional fields
        if (event.getEndDateAndTime() != null && Event.isWillDisplayEndDateTime()) {
            to.setText("to: " + event.getEndDateAndTime());
            to.setManaged(true);
            to.setWrapText(isViewMode);
        }
        if (event.getAddress() != null && Event.isWillDisplayAddress()) {
            address.setText("location: " + event.getAddress().value);
            address.setManaged(true);
            address.setWrapText(isViewMode);
        }
        if (event.getZoomLink() != null && Event.isWillDisplayZoomLink()) {
            zoomLink.setText("link: " + event.getZoomLink().link);
            zoomLink.setManaged(true);
            zoomLink.setWrapText(isViewMode);
        }
        if (event.getDescription() != null && Event.isWillDisplayDescription()) {
            description.setText("description: " + event.getDescription().value);
            description.setManaged(true);
            description.setWrapText(isViewMode);
        }

        if (Event.isWillDisplayTags()) {
            event.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> {
                        Label label = new Label(tag.tagName);
                        label.setStyle("-fx-background-color: " + tag.tagColour + ";");
                        tags.getChildren().add(label);
                    });
            tags.setManaged(true);
        }

        if (!event.getLinkedContacts().isEmpty()) {
            event.getLinkedContacts().stream()
                .sorted(Comparator.comparing(contactUuid -> contactUuid.toString()))
                .forEach(contactUuid -> links.getChildren()
                    .add(new Label(Contact.findByUuid(contactUuid).getName().toString())));
            linksLabel.setManaged(true);
            links.setManaged(true);
        }
        if (event.getIsBookMarked()) {
            name.setStyle("-fx-background-color: gold");
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
}
