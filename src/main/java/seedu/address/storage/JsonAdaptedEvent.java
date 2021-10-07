package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Address;
import seedu.address.model.event.Description;
import seedu.address.model.event.EndDateTime;
import seedu.address.model.event.Event;
import seedu.address.model.event.StartDateTime;
import seedu.address.model.event.Name;
import seedu.address.model.event.ZoomLink;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Event}.
 */
class JsonAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final String name;
    private final String startDateTime;
    private final String endDateTime;
    private final String description;
    private final String address;
    private final String zoomLink;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("name") String name, @JsonProperty("start") String startDateTime,
                            @JsonProperty("end") String endDateTime, @JsonProperty("description") String description,
                            @JsonProperty("address") String address, @JsonProperty("zoom link") String zoomLink,
                            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.description = description;
        this.address = address;
        this.zoomLink = zoomLink;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        name = source.getName().fullName;
        startDateTime = source.getStartDateAndTime().toString();
        endDateTime = source.getEndDateAndTime().toString();
        description = source.getDescription().value;
        address = source.getAddress().value;
        zoomLink = source.getZoomLink().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {
        final List<Tag> eventTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            eventTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (startDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StartDateTime.class.getSimpleName()));
        }
        if (!StartDateTime.isValidDateTime(startDateTime)) {
            throw new IllegalValueException(StartDateTime.MESSAGE_CONSTRAINTS);
        }
        final StartDateTime modelStartDateTime = new StartDateTime(startDateTime);

        if (endDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EndDateTime.class.getSimpleName()));
        }
        if (!EndDateTime.isValidDateTime(endDateTime)) {
            throw new IllegalValueException(EndDateTime.MESSAGE_CONSTRAINTS);
        }
        final EndDateTime modelEndDateTime = new EndDateTime(endDateTime);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }

        final Description modelDescription = new Description(description);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (zoomLink == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ZoomLink.class.getSimpleName()));
        }
        if (!ZoomLink.isValidZoomLink(zoomLink)) {
            throw new IllegalValueException(ZoomLink.MESSAGE_CONSTRAINTS);
        }
        final ZoomLink modelZoomLink = new ZoomLink(zoomLink);

        final Set<Tag> modelTags = new HashSet<>(eventTags);
        return new Event(modelName, modelStartDateTime, modelEndDateTime, modelDescription, modelAddress, modelZoomLink,
                modelTags);
    }

}

