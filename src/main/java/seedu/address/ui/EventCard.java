package seedu.address.ui;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.model.tag.Tag;

/**
 * An UI component that displays information of an {@code Event}.
 */
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";

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
    private Label meetingLink;

    @FXML
    private Label description;

    @FXML
    private FlowPane tags;

    /**
     * Test code to be deleted.
     */
    EventCard() {
        super(FXML);
        this.event = new Event("CS2103T Lecture",
            "01/10/21 16:00",
            "01/10/21 18:00",
            null,
            "nus-sg.zoom.us/j/123",
            null,
            new HashSet<>(List.of(new Tag("Lectures"), new Tag("Recurring")))
            );
        id.setText("1. ");
        name.setText(event.getEventName());
        from.setText(event.getFrom());
        address.setText(event.getAddress());
        meetingLink.setText(event.getMeetingLink());
        description.setText(event.getDescription());
        event.getTags().stream()
            .sorted(Comparator.comparing(tag -> tag.tagName))
            .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Creates an {@code EventCard} with the given {@code Event} and index to display.
     */
    public EventCard(
        Event event, int displayedIndex) {
        super(FXML);
        this.event = event;
        id.setText(displayedIndex + ". ");
        name.setText(event.getEventName());
        from.setText(event.getFrom());
        to.setText(event.getTo());
        address.setText(event.getAddress());
        meetingLink.setText(event.getMeetingLink());
        description.setText(event.getDescription());
        event.getTags().stream()
            .sorted(Comparator.comparing(tag -> tag.tagName))
            .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventCard eventCard = (EventCard) o;
        return Objects.equals(event, eventCard.event) && Objects.equals(id, eventCard.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, id);
    }
}
