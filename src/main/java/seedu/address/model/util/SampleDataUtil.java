package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.common.Address;
import seedu.address.model.common.ZoomLink;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Phone;
import seedu.address.model.event.Description;
import seedu.address.model.event.EndDateTime;
import seedu.address.model.event.Event;
import seedu.address.model.event.StartDateTime;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Contact[] getSampleContacts() {
        return new Contact[] {
            new Contact(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                null, null, getTagSet("friends")),
            new Contact(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                null, null, getTagSet("colleagues", "friends")),
            new Contact(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                null, null, getTagSet("neighbours")),
            new Contact(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                null, null, getTagSet("family")),
            new Contact(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                null, null, getTagSet("classmates")),
            new Contact(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                null, null, getTagSet("colleagues"))
        };
    }

    public static Event[] getSampleEvents() {
        return new Event[] {
            new Event(new seedu.address.model.event.Name("CS2103T project meeting"),
                new StartDateTime("2021-10-02 21:00"), new EndDateTime("2021-10-02 22:00"), null,
                null, new ZoomLink("nus-sg.zoom.us/j/21342513543"),
                Set.of(new Tag("Recurring"), new Tag("CS2103T"))),
            new Event(new seedu.address.model.event.Name("Basketball training"), new StartDateTime("2021-10-02 20:00"),
                new EndDateTime("2021-10-02 21:00"), new Description("Meeting every week"),
                new Address("NUS Sport Centre"), null,
                Set.of(new Tag("Recurring"), new Tag("CCA"))),
            new Event(new seedu.address.model.event.Name("Google Interview"), new StartDateTime("2021-10-09 15:30"),
                new EndDateTime("2021-10-09 16:00"), null,
                null, new ZoomLink("careers.google.com/summer"),
                Set.of(new Tag("Internship"))),
            new Event(new seedu.address.model.event.Name("Dance class"), new StartDateTime("2021-10-13 20:00"),
                new EndDateTime("2021-10-13 22:00"), new Description("Lorem ipsum dolor sit amet, consectetur"
                + " adipiscing elit. Sed lorem urna, auctor vel elit vitae, hendrerit convallis lorem. Aliquam non "
                + "lobortis nisl, convallis placerat urna."),
                new Address("NUS UTown"), null,
                Set.of(new Tag("Recurring"), new Tag("CCA")))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Contact sampleContact : getSampleContacts()) {
            sampleAb.addContact(sampleContact);
        }
        for (Event sampleEvent : getSampleEvents()) {
            sampleAb.addEvent(sampleEvent);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
