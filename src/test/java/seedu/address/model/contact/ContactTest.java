package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.ALICE_MARKED;
import static seedu.address.testutil.TypicalContacts.BOB;
import static seedu.address.testutil.TypicalEvents.BIRTHDAY_PARTY;
import static seedu.address.testutil.TypicalEvents.CS2101_MEETING;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ContactBuilder;

public class ContactTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Contact contact = new ContactBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> contact.getTags().remove(0));
    }

    @Test
    public void isSameContact() {
        // same object -> returns true
        assertTrue(ALICE_MARKED.isSameContact(ALICE_MARKED));

        // null -> returns false
        assertFalse(ALICE_MARKED.isSameContact(null));

        // same name, all other attributes different -> returns true
        Contact editedAlice = new ContactBuilder(ALICE_MARKED).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
            .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE_MARKED.isSameContact(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new ContactBuilder(ALICE_MARKED).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE_MARKED.isSameContact(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Contact editedBob = new ContactBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSameContact(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new ContactBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSameContact(editedBob));
    }

    @Test
    public void testLinkAndUnlink() {
        Contact aliceCopy = new ContactBuilder(ALICE_MARKED).build();
        Contact expectedContact = aliceCopy.linkTo(CS2101_MEETING);

        //link cs2101 meeting to alice
        assertTrue(expectedContact.hasLinkTo(CS2101_MEETING));
        assertEquals(expectedContact, ALICE_MARKED.linkTo(CS2101_MEETING));
        assertFalse(expectedContact.hasLinkTo(BIRTHDAY_PARTY));

        //link birthday party to alice
        expectedContact = expectedContact.linkTo(BIRTHDAY_PARTY);
        assertTrue(expectedContact.hasLinkTo(BIRTHDAY_PARTY));
        assertEquals(expectedContact, ALICE_MARKED.linkTo(BIRTHDAY_PARTY).linkTo(CS2101_MEETING));

        //unlink cs2101 meeting from alice
        expectedContact = expectedContact.unlink(CS2101_MEETING);
        assertFalse(expectedContact.hasLinkTo(CS2101_MEETING));
        assertEquals(expectedContact, ALICE_MARKED.linkTo(BIRTHDAY_PARTY));
        assertTrue(expectedContact.hasLinkTo(BIRTHDAY_PARTY));

        //clear all links
        expectedContact = expectedContact.clearAllLinks();
        assertEquals(expectedContact, ALICE_MARKED);

    }

    @Test
    public void constructor_invalidInputs() {
        assertThrows(NullPointerException.class, () -> new ContactBuilder().withUuid(null).build());
        assertThrows(NullPointerException.class, () -> new ContactBuilder().withName(null).build());
        assertThrows(NullPointerException.class, () -> new ContactBuilder().withEmail(null).build());
        assertThrows(NullPointerException.class, () -> new ContactBuilder().withLinkedEvents((UUID[]) null).build());
        assertThrows(
            NullPointerException.class, () -> new ContactBuilder().withLinkedEvents(null, UUID.randomUUID()).build());
        assertThrows(NullPointerException.class, () -> new ContactBuilder().withTags((String[]) null).build());
        assertThrows(NullPointerException.class, () -> new ContactBuilder().withTags(null, "tag").build());
    }

    @Test
    public void equals() {
        // same values -> returns true
        Contact aliceCopy = new ContactBuilder(ALICE_MARKED).build();
        assertTrue(ALICE_MARKED.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE_MARKED.equals(ALICE_MARKED));

        // null -> returns false
        assertFalse(ALICE_MARKED.equals(null));

        // different type -> returns false
        assertFalse(ALICE_MARKED.equals(5));

        // different contact -> returns false
        assertFalse(ALICE_MARKED.equals(BOB));

        // different name -> returns false
        Contact editedAlice = new ContactBuilder(ALICE_MARKED).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE_MARKED.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new ContactBuilder(ALICE_MARKED).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE_MARKED.equals(editedAlice));

        // different email -> returns false
        editedAlice = new ContactBuilder(ALICE_MARKED).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE_MARKED.equals(editedAlice));

        // different address -> returns false
        editedAlice = new ContactBuilder(ALICE_MARKED).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE_MARKED.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ContactBuilder(ALICE_MARKED).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE_MARKED.equals(editedAlice));
    }
}
