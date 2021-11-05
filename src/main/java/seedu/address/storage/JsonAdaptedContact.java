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
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Phone;
import seedu.address.model.contact.TelegramHandle;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Contact}.
 */
class JsonAdaptedContact {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Contact's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String telegramHandle;
    private final String zoomLink;
    private final String uuid;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final List<String> linkedEvents = new ArrayList<>();
    private final boolean isMarked;

    /**
     * Constructs a {@code JsonAdaptedContact} with the given contact details.
     */
    @JsonCreator
    public JsonAdaptedContact(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("telegramHandle") String telegramHandle, @JsonProperty("zoomLink") String zoomLink,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged, @JsonProperty("uuid") String uuid,
            @JsonProperty("linkedEvents") List<String> linkedEvents,
            @JsonProperty("isMarked") boolean isMarked) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.telegramHandle = telegramHandle;
        this.zoomLink = zoomLink;
        this.uuid = uuid;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        if (linkedEvents != null) {
            this.linkedEvents.addAll(linkedEvents);
        }
        this.isMarked = isMarked;
    }

    /**
     * Converts a given {@code Contact} into this class for Jackson use.
     */
    public JsonAdaptedContact(Contact source) {
        // compulsory fields
        name = source.getName().fullName;
        email = source.getEmail().value;
        // optional fields
        phone = source.getPhone() != null ? source.getPhone().value : null;
        address = source.getAddress() != null ? source.getAddress().value : null;
        telegramHandle = source.getTelegramHandle() != null ? source.getTelegramHandle().handle : null;
        zoomLink = source.getZoomLink() != null ? source.getZoomLink().link : null;
        uuid = source.getUuid().toString();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        linkedEvents.addAll(source.getLinkedEvents().stream()
            .map(UUID::toString)
            .collect(Collectors.toList()));
        isMarked = source.getIsMarked();
    }

    /**
     * Converts this Jackson-friendly adapted contact object into the model's {@code Contact} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted contact.
     */
    public Contact toModelType() throws IllegalValueException {
        final List<Tag> contactTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            contactTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (phone != null && !Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = phone != null ? new Phone(phone) : null;


        if (address != null && !Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = address != null ? new Address(address) : null;

        if (telegramHandle != null && !TelegramHandle.isValidHandle(telegramHandle)) {
            throw new IllegalValueException(TelegramHandle.MESSAGE_CONSTRAINTS);
        }
        final TelegramHandle modelTelegramHandle = telegramHandle != null ? new TelegramHandle(telegramHandle) : null;

        if (zoomLink != null && !ZoomLink.isValidZoomLink(zoomLink)) {
            throw new IllegalValueException(ZoomLink.MESSAGE_CONSTRAINTS);
        }
        final ZoomLink modelZoomLink = zoomLink != null ? new ZoomLink(zoomLink) : null;

        if (uuid == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, UUID.class.getSimpleName()));
        }
        final UUID modelUuid = UUID.fromString(uuid);

        final Set<Tag> modelTags = new HashSet<>(contactTags);
        final Set<UUID> modelLinkedEvents = new HashSet<>(
                linkedEvents.stream().map(UUID::fromString).collect(Collectors.toList()));

        return new Contact(modelName, modelPhone, modelEmail, modelAddress, modelZoomLink,
                modelTelegramHandle, modelTags, modelUuid, modelLinkedEvents, isMarked);
    }

}
