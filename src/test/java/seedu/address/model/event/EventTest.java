package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_ADDRESS_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_NAME_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_START_DATE_TIME_EXAM;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_TAG_EXAMS;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_ZOOM_EXAM;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.AMY;
import static seedu.address.testutil.TypicalContacts.BOB;
import static seedu.address.testutil.TypicalEvents.CS2103_MIDTERM_MARKED;
import static seedu.address.testutil.TypicalEvents.FOOTBALL_PRACTICE;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EventBuilder;

class EventTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Event event = new EventBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> event.getTags().remove(0));
    }

    @Test
    public void constructor_invalidInputs() {
        assertThrows(NullPointerException.class, () -> new EventBuilder().withName(null).build());
        assertThrows(NullPointerException.class, () -> new EventBuilder().withStartDateAndTime(null).build());
        assertThrows(NullPointerException.class, () -> new EventBuilder().withUuid(null).build());
        assertThrows(NullPointerException.class, () -> new EventBuilder().withLinkedContacts((UUID[]) null).build());
        assertThrows(
            NullPointerException.class, () -> new EventBuilder().withLinkedContacts(null, UUID.randomUUID()).build());
        assertThrows(NullPointerException.class, () -> new EventBuilder().withTags((String[]) null).build());
        assertThrows(NullPointerException.class, () -> new EventBuilder().withTags(null, "tag").build());
    }

    @Test
    public void testIsSameEvent() {
        // same object -> returns true
        assertTrue(FOOTBALL_PRACTICE.isSameEvent(FOOTBALL_PRACTICE));

        // null -> returns false
        assertFalse(FOOTBALL_PRACTICE.isSameEvent(null));

        // same name, all other attributes different -> returns true
        Event editedPractice = new EventBuilder(FOOTBALL_PRACTICE).withZoomLink(VALID_ZOOM_EXAM)
            .withStartDateAndTime(VALID_START_DATE_TIME_EXAM)
            .withAddress(VALID_ADDRESS_EXAM).withTags(VALID_TAG_EXAMS).build();
        assertTrue(FOOTBALL_PRACTICE.isSameEvent(editedPractice));

        // different name, all other attributes same -> returns false
        editedPractice = new EventBuilder(FOOTBALL_PRACTICE).withName(VALID_NAME_EXAM).build();
        assertFalse(FOOTBALL_PRACTICE.isSameEvent(editedPractice));

        // name differs in case, all other attributes same -> returns false
        Event editedMidterm = new EventBuilder(CS2103_MIDTERM_MARKED).withName(VALID_NAME_EXAM.toLowerCase()).build();
        assertFalse(CS2103_MIDTERM_MARKED.isSameEvent(editedMidterm));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_EXAM + " ";
        editedMidterm = new EventBuilder(CS2103_MIDTERM_MARKED).withName(nameWithTrailingSpaces).build();
        assertFalse(CS2103_MIDTERM_MARKED.isSameEvent(editedMidterm));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Event midtermCopy = new EventBuilder(CS2103_MIDTERM_MARKED).build();
        assertTrue(CS2103_MIDTERM_MARKED.equals(midtermCopy));

        // same object -> returns true
        assertTrue(CS2103_MIDTERM_MARKED.equals(CS2103_MIDTERM_MARKED));

        // null -> returns false
        assertFalse(CS2103_MIDTERM_MARKED.equals(null));

        // different type -> returns false
        assertFalse(CS2103_MIDTERM_MARKED.equals(5));

        // different event -> returns false
        assertFalse(CS2103_MIDTERM_MARKED.equals(FOOTBALL_PRACTICE));

        // different name -> returns false
        Event editedPractice = new EventBuilder(FOOTBALL_PRACTICE).withName(VALID_NAME_EXAM).build();
        assertFalse(FOOTBALL_PRACTICE.equals(editedPractice));

        // different address -> returns false
        editedPractice = new EventBuilder(FOOTBALL_PRACTICE).withAddress(VALID_ADDRESS_EXAM).build();
        assertFalse(FOOTBALL_PRACTICE.equals(editedPractice));

        // different email -> returns false
        editedPractice = new EventBuilder(FOOTBALL_PRACTICE).withZoomLink(VALID_ZOOM_EXAM).build();
        assertFalse(FOOTBALL_PRACTICE.equals(editedPractice));

        // different start time and date -> returns false
        editedPractice = new EventBuilder(FOOTBALL_PRACTICE).withStartDateAndTime(VALID_START_DATE_TIME_EXAM).build();
        assertFalse(FOOTBALL_PRACTICE.equals(editedPractice));

        // different tags -> returns false
        editedPractice = new EventBuilder(FOOTBALL_PRACTICE).withTags(VALID_TAG_EXAMS).build();
        assertFalse(FOOTBALL_PRACTICE.equals(editedPractice));
    }

    @Test
    void testLinkAndUnlink() {
        Event midtermCopy = new EventBuilder(CS2103_MIDTERM_MARKED).build();
        Event expectedEvent = midtermCopy.linkTo(AMY);

        //link Amy to CS2103 Midterm
        assertTrue(expectedEvent.hasLinkTo(AMY));
        assertEquals(expectedEvent, CS2103_MIDTERM_MARKED.linkTo(AMY));
        assertFalse(expectedEvent.hasLinkTo(BOB));

        //link Bob to CS2103 Midterm
        expectedEvent = expectedEvent.linkTo(BOB);
        assertEquals(expectedEvent, CS2103_MIDTERM_MARKED.linkTo(AMY).linkTo(BOB));
        assertTrue(expectedEvent.hasLinkTo(BOB));

        //unlink Amy from CS2103 Midterm
        expectedEvent = expectedEvent.unlink(AMY);
        assertTrue(expectedEvent.hasLinkTo(BOB));
        assertEquals(expectedEvent, CS2103_MIDTERM_MARKED.linkTo(BOB));
        assertFalse(expectedEvent.hasLinkTo(AMY));

        expectedEvent = expectedEvent.clearAllLinks();
        assertEquals(expectedEvent, CS2103_MIDTERM_MARKED);
    }

}
