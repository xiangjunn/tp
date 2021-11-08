package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.common.ZoomLink;
import seedu.address.model.event.Description;
import seedu.address.model.event.EndDateTime;
import seedu.address.model.event.Event;
import seedu.address.model.event.StartDateTime;
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
    private final String uuid;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final List<String> linkedContacts = new ArrayList<>();
    private final boolean isMarked;

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("name") String name, @JsonProperty("start") String startDateTime,
                            @JsonProperty("end") String endDateTime, @JsonProperty("description") String description,
                            @JsonProperty("address") String address, @JsonProperty("zoom") String zoomLink,
                            @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                            @JsonProperty("uuid") String uuid,
                            @JsonProperty("linkedContacts") List<String> linkedContacts,
                            @JsonProperty("isMarked") boolean isMarked) {
        this.name = name;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.description = description;
        this.address = address;
        this.zoomLink = zoomLink;
        this.uuid = uuid;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        if (linkedContacts != null) {
            this.linkedContacts.addAll(linkedContacts);
        }
        this.isMarked = isMarked;
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        // compulsory fields
        name = source.getName().fullName;
        startDateTime = source.getStartDateAndTime().toString();
        // optional fields
        endDateTime = source.getEndDateAndTime() != null ? source.getEndDateAndTime().toString() : null;
        description = source.getDescription() != null ? source.getDescription().value : null;
        address = source.getAddress() != null ? source.getAddress().value : null;
        zoomLink = source.getZoomLink() != null ? source.getZoomLink().link : null;
        uuid = source.getUuid().toString();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        linkedContacts.addAll(source.getLinkedContacts().stream()
            .map(UUID::toString)
            .collect(Collectors.toList()));
        isMarked = source.getIsMarked();
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

        if (endDateTime != null && !EndDateTime.isValidDateTime(endDateTime)) {
            throw new IllegalValueException(EndDateTime.MESSAGE_CONSTRAINTS);
        }
        final EndDateTime modelEndDateTime = endDateTime == null ? null : new EndDateTime(endDateTime);

        if (description != null && !Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = description == null ? null : new Description(description);

        if (address != null && !Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = address == null ? null : new Address(address);

        if (zoomLink != null && !ZoomLink.isValidZoomLink(zoomLink)) {
            throw new IllegalValueException(ZoomLink.MESSAGE_CONSTRAINTS);
        }
        final ZoomLink modelZoomLink = zoomLink == null ? null : new ZoomLink(zoomLink);

        if (uuid == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        final UUID modelUuid = UUID.fromString(uuid);

        final Set<Tag> modelTags = new HashSet<>(eventTags);
        final Set<UUID> modelLinkedContacts = new HashSet<>(
            linkedContacts.stream().map(UUID::fromString).collect(Collectors.toList()));

        return new Event(modelName, modelStartDateTime, modelEndDateTime, modelDescription, modelAddress, modelZoomLink,
                modelTags, modelUuid, modelLinkedContacts, isMarked);
    }

}
