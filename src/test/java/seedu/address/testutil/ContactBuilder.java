package seedu.address.testutil;

import static seedu.address.testutil.TypicalEvents.CS2100_CONSULTATION_UUID;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.common.ZoomLink;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Phone;
import seedu.address.model.contact.TelegramHandle;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Contact objects.
 */
public class ContactBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_TELEGRAM_HANDLE = "amyBeeBee";
    public static final String DEFAULT_ZOOM_LINK = "https://nus-sg.zoom.us/j/0123456789?pwd=ABCDEFG";
    public static final boolean DEFAULT_IS_MARKED = true;
    public static final String DEFAULT_TAG = "friends";
    public static final UUID DEFAULT_UUID = TypicalContacts.ALICE_UUID;
    public static final UUID DEFAULT_LINKED_EVENT = CS2100_CONSULTATION_UUID;

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private TelegramHandle telegramHandle;
    private ZoomLink zoomLink;
    private Set<Tag> tags;
    private UUID uuid;
    private Set<UUID> linkedEvents;
    private boolean isMarked;

    /**
     * Creates a {@code ContactBuilder} with the default details.
     */
    public ContactBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        // adding of telegram handle and zoom link not implemented yet
        telegramHandle = new TelegramHandle(DEFAULT_TELEGRAM_HANDLE);
        zoomLink = new ZoomLink(DEFAULT_ZOOM_LINK);
        tags = new HashSet<>();
        tags.add(new Tag(DEFAULT_TAG));
        uuid = DEFAULT_UUID;
        linkedEvents = new HashSet<>();
        linkedEvents.add(DEFAULT_LINKED_EVENT);
        isMarked = DEFAULT_IS_MARKED;
    }

    /**
     * Initializes the ContactBuilder with the data of {@code contactToCopy}.
     */
    public ContactBuilder(Contact contactToCopy) {
        name = contactToCopy.getName();
        phone = contactToCopy.getPhone();
        email = contactToCopy.getEmail();
        address = contactToCopy.getAddress();
        telegramHandle = contactToCopy.getTelegramHandle();
        zoomLink = contactToCopy.getZoomLink();
        tags = new HashSet<>(contactToCopy.getTags());
        uuid = contactToCopy.getUuid();
        linkedEvents = new HashSet<>(contactToCopy.getLinkedEvents());
        isMarked = contactToCopy.getIsMarked();
    }

    /**
     * Sets the {@code Name} of the {@code Contact} that we are building.
     */
    public ContactBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Contact} that we are building.
     */
    public ContactBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Contact} that we are building.
     */
    public ContactBuilder withAddress(String address) {
        this.address = address != null ? new Address(address) : null;
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Contact} that we are building.
     */
    public ContactBuilder withPhone(String phone) {
        this.phone = phone != null ? new Phone(phone) : null;
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Contact} that we are building.
     */
    public ContactBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code TelegramHandle} of the {@code Contact} that we are building.
     */
    public ContactBuilder withTelegramHandle(String telegramHandle) {
        this.telegramHandle = telegramHandle != null ? new TelegramHandle(telegramHandle) : null;
        return this;
    }

    /**
     * Sets the {@code ZoomLink} of the {@code Contact} that we are building.
     */
    public ContactBuilder withZoomLink(String zoomLink) {
        this.zoomLink = zoomLink != null ? new ZoomLink(zoomLink) : null;
        return this;
    }

    /**
     * Sets the {@code UUID} of the {@code Contact} that we are building.
     */
    public ContactBuilder withUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    /**
     * Sets the {@code UUID} of the {@code contact} that we are building to a random value.
     */
    public ContactBuilder withRandomUuid() {
        this.uuid = UUID.randomUUID();
        return this;
    }

    /**
     * Parses the {@code events} into a {@code Set<UUID>} and set it to the {@code Contact} that we are building.
     */
    public ContactBuilder withLinkedEvents(UUID ...events) {
        this.linkedEvents = Set.of(events);
        return this;
    }

    /**
     * Sets the {@code isMarked} of the {@code Contact} that we are building to {@code isMarked}.
     */
    public ContactBuilder withMarked(boolean isMarked) {
        this.isMarked = isMarked;
        return this;
    }

    /**
     * Creates an {@code Contact} from this {@code Contactbuilder}.
     */
    public Contact build() {
        return new Contact(name, phone, email, address, zoomLink, telegramHandle, tags, uuid, linkedEvents, isMarked);
    }
}
