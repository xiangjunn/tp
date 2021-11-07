package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.common.ZoomLink;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Phone;
import seedu.address.model.contact.TelegramHandle;
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
                null, new TelegramHandle("yeoh_alex"), getTagSet("friends")),
            new Contact(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                new ZoomLink("nus-sg.zoom.us/j/08382435376?pwd=Oow3u9N098nh8nsdLp0"),
                new TelegramHandle("bbernicee"), getTagSet("TA", "friends")),
            new Contact(new Name("Charlotte Oliveiro"), null, new Email("charlotte@example.com"),
                null, null, null, getTagSet()),
            new Contact(new Name("David Li"), null, new Email("lidavid@comp.nus.edu.sg"),
                new Address("COM1-B1-0931"),
                null, new TelegramHandle("lidavid"), getTagSet("professor", "CS2103T")),
            new Contact(new Name("Irfan Ibrahim"), null, new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                null, new TelegramHandle("irfanx"), getTagSet("classmates")),
            new Contact(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                null,
                new ZoomLink("nus-sg.zoom.us/j/89157903482"), null, getTagSet("TA"))
        };
    }

    public static Event[] getSampleEvents() {
        return new Event[] {
            new Event(new Name("CS2103T project meeting"),
                new StartDateTime("10-10-2021 21:00"), new EndDateTime("10-10-2021 22:00"), null,
                null, new ZoomLink("nus-sg.zoom.us/j/21342513543"),
                Set.of(new Tag("Recurring"), new Tag("CS2103T"))),
            new Event(new Name("Basketball training"), new StartDateTime("01-11-2021 20:00"),
                new EndDateTime("01-11-2021 21:00"), new Description("Meeting every week"),
                new Address("NUS Sport Centre"), null,
                Set.of(new Tag("Recurring"), new Tag("CCA"))),
            new Event(new Name("Google Interview"), new StartDateTime("10-11-2021 15:30"),
                new EndDateTime("10-11-2021 16:00"), new Description("Revise on Data Structures and Algorithms."
                + " Also read up on Computer Networks and Object-Oriented Programming. I can do this!"),
                null, new ZoomLink("careers.google.com/student"),
                Set.of(new Tag("Internship"))),
            new Event(new Name("Dance class"), new StartDateTime("13-11-2021 20:00"),
                new EndDateTime("13-11-2021 22:00"), new Description("Dancing is my passion. I like pole dancing."),
                new Address("NUS UTown"), null,
                Set.of(new Tag("Recurring"), new Tag("CCA")))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        Contact[] sampleContacts = getSampleContacts();
        Event[] sampleEvents = getSampleEvents();
        for (Contact sampleContact : sampleContacts) {
            sampleAb.addContact(sampleContact);
        }
        for (Event sampleEvent : sampleEvents) {
            sampleAb.addEvent(sampleEvent);
        }
        // Link some contacts and events
        sampleAb.linkEventAndContact(sampleAb.getEventList().get(1), sampleAb.getContactList().get(0));
        sampleAb.linkEventAndContact(sampleAb.getEventList().get(1), sampleAb.getContactList().get(4));
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
