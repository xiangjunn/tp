package seedu.address.testutil;

import static seedu.address.testutil.TypicalEvents.CS2103_MIDTERM_MARKED_UUID;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.common.ZoomLink;
import seedu.address.model.event.Description;
import seedu.address.model.event.EndDateTime;
import seedu.address.model.event.Event;
import seedu.address.model.event.StartDateTime;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {
    public static final String DEFAULT_NAME = "Midterms";
    public static final String DEFAULT_START_DATE_AND_TIME = "13-10-2021 21:00";
    public static final String DEFAULT_END_DATE_AND_TIME = "13-10-2021 23:00";
    public static final String DEFAULT_DESCRIPTION = "Important";
    public static final String DEFAULT_ADDRESS = "COM2, SR1, #02-11";
    public static final String DEFAULT_ZOOM_LINK = "https://nus-sg.zoom.us/j/0123456789?pwd=ABCDEFG";
    public static final String DEFAULT_TAG = "exams";
    public static final UUID DEFAULT_UUID = CS2103_MIDTERM_MARKED_UUID;
    public static final boolean DEFAULT_IS_MARKED = true;

    //Compulsory fields
    private Name name;
    private StartDateTime startDateAndTime;

    //Optional fields
    private EndDateTime endDateAndTime;
    private Description description;
    private Address address;
    private ZoomLink zoomLink;
    private Set<Tag> tags;
    private UUID uuid;
    private Set<UUID> linkedContacts;
    private boolean isMarked;

    /**
     * Creates a {@code EventBuilder} with the default details.
     */
    public EventBuilder() {
        name = new Name(DEFAULT_NAME);
        startDateAndTime = new StartDateTime(DEFAULT_START_DATE_AND_TIME);
        endDateAndTime = new EndDateTime(DEFAULT_END_DATE_AND_TIME);
        description = new Description(DEFAULT_DESCRIPTION);
        address = new Address(DEFAULT_ADDRESS);
        zoomLink = new ZoomLink(DEFAULT_ZOOM_LINK);
        tags = new HashSet<>();
        tags.add(new Tag(DEFAULT_TAG));
        uuid = DEFAULT_UUID;
        linkedContacts = new HashSet<>();
        isMarked = DEFAULT_IS_MARKED;
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        name = eventToCopy.getName();
        startDateAndTime = eventToCopy.getStartDateAndTime();
        endDateAndTime = eventToCopy.getEndDateAndTime();
        description = eventToCopy.getDescription();
        address = eventToCopy.getAddress();
        zoomLink = eventToCopy.getZoomLink();
        tags = new HashSet<>(eventToCopy.getTags());
        isMarked = eventToCopy.getIsMarked();
        uuid = eventToCopy.getUuid();
        linkedContacts = new HashSet<>(eventToCopy.getLinkedContacts());
    }

    /**
     * Sets the {@code Name} of the {@code Event} that we are building.
     */
    public EventBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Event} that we are building.
     */
    public EventBuilder withTags(String ...tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code description} of the {@code Event} that we are building.
     */
    public EventBuilder withStartDateAndTime(String startDateTime) {
        this.startDateAndTime = new StartDateTime(startDateTime);
        return this;
    }

    /**
     * Sets the {@code endDateTime} of the {@code Event} that we are building.
     */
    public EventBuilder withEndDateAndTime(String endDateTime) {
        this.endDateAndTime = endDateTime != null ? new EndDateTime(endDateTime) : null;
        return this;
    }

    /**
     * Sets the {@code description} of the {@code Event} that we are building.
     */
    public EventBuilder withDescription(String description) {
        this.description = description != null ? new Description(description) : null;
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Event} that we are building.
     */
    public EventBuilder withAddress(String address) {
        this.address = address != null ? new Address(address) : null;
        return this;
    }

    /**
     * Sets the {@code ZoomLink} of the {@code Event} that we are building.
     */
    public EventBuilder withZoomLink(String zoomLink) {
        this.zoomLink = zoomLink != null ? new ZoomLink(zoomLink) : null;
        return this;
    }

    /**
     * Sets the {@code UUID} of the {@code Event} that we are building.
     */
    public EventBuilder withUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    /**
     * Sets the {@code UUID} of the {@code Event} that we are building to a random one.
     */
    public EventBuilder withRandomUuid() {
        this.uuid = UUID.randomUUID();
        return this;
    }

    /**
     * Parses the {@code contacts} into a {@code Set<UUID>} and set it to the {@code Event} that we are building.
     */
    public EventBuilder withLinkedContacts(UUID ...contacts) {
        this.linkedContacts = Set.of(contacts);
        return this;
    }

    /**
     * Sets the {@code isMarked} of the {@code Event} that we are building to {@code isMarked}.
     */
    public EventBuilder withMarked(boolean isMarked) {
        this.isMarked = isMarked;
        return this;
    }

    /**
     * Creates an {@code Event} from this {@code Eventbuilder}.
     */
    public Event build() {
        return new Event(name, startDateAndTime, endDateAndTime, description, address, zoomLink,
            tags, uuid, linkedContacts, isMarked);

    }
}
