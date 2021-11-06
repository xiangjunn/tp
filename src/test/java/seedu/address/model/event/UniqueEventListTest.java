package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_ADDRESS_TUTORIAL;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_START_DATE_TIME_TUTORIAL;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.BIRTHDAY_PARTY;
import static seedu.address.testutil.TypicalEvents.CS2100_CONSULTATION;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.event.exceptions.InvalidDateTimeRangeException;
import seedu.address.testutil.EventBuilder;

class UniqueEventListTest {

    private final UniqueEventList uniqueEventList = new UniqueEventList();

    @Test
    public void contains_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.contains(null));
    }

    @Test
    public void contains_eventNotInList_returnsFalse() {
        assertFalse(uniqueEventList.contains(BIRTHDAY_PARTY));
    }

    @Test
    public void contains_eventInList_returnsTrue() {
        uniqueEventList.add(BIRTHDAY_PARTY);
        assertTrue(uniqueEventList.contains(BIRTHDAY_PARTY));
    }

    @Test
    public void contains_eventWithSameIdentityFieldsInList_returnsTrue() {
        uniqueEventList.add(BIRTHDAY_PARTY);
        Event editedBirthdayParty = new EventBuilder(BIRTHDAY_PARTY).withAddress(VALID_ADDRESS_TUTORIAL)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(uniqueEventList.contains(editedBirthdayParty));
    }

    @Test
    public void add_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.add(null));
    }

    @Test
    public void add_duplicateEvent_throwsDuplicateEventException() {
        uniqueEventList.add(BIRTHDAY_PARTY);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.add(BIRTHDAY_PARTY));
    }

    @Test
    public void addEvent_invalidDateTimeRange_throwsInvalidDateTimeRangeException() {
        assertThrows(InvalidDateTimeRangeException.class, () -> uniqueEventList.add(
                        new EventBuilder(BIRTHDAY_PARTY).withEndDateAndTime(VALID_START_DATE_TIME_TUTORIAL).build()));
    }

    @Test
    public void setEvent_nullTargetEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvent(null, BIRTHDAY_PARTY));
    }

    @Test
    public void setEvent_nullEditedEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvent(BIRTHDAY_PARTY, null));
    }

    @Test
    public void setEvent_targetEventNotInList_throwsEventNotFoundException() {
        assertThrows(EventNotFoundException.class, () -> uniqueEventList.setEvent(BIRTHDAY_PARTY, BIRTHDAY_PARTY));
    }

    @Test
    public void setEvent_editedEventIsSameEvent_success() {
        uniqueEventList.add(BIRTHDAY_PARTY);
        uniqueEventList.setEvent(BIRTHDAY_PARTY, BIRTHDAY_PARTY);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(BIRTHDAY_PARTY);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvent_editedEventHasSameIdentity_success() {
        uniqueEventList.add(BIRTHDAY_PARTY);
        Event editedBirthdayParty = new EventBuilder(BIRTHDAY_PARTY).withAddress(VALID_ADDRESS_TUTORIAL)
            .withTags(VALID_TAG_HUSBAND).build();
        uniqueEventList.setEvent(BIRTHDAY_PARTY, editedBirthdayParty);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(editedBirthdayParty);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvent_editedEventHasDifferentIdentity_success() {
        uniqueEventList.add(BIRTHDAY_PARTY);
        uniqueEventList.setEvent(BIRTHDAY_PARTY, CS2100_CONSULTATION);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(CS2100_CONSULTATION);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvent_editedEventHasNonUniqueIdentity_throwsDuplicateEventException() {
        uniqueEventList.add(BIRTHDAY_PARTY);
        uniqueEventList.add(CS2100_CONSULTATION);
        assertThrows(
                DuplicateEventException.class, () -> uniqueEventList.setEvent(BIRTHDAY_PARTY, CS2100_CONSULTATION));
    }

    @Test
    public void setEvent_editedEventHasInvalidDateTimeRange_throwsInvalidDateTimeRangeException() {
        uniqueEventList.add(BIRTHDAY_PARTY);
        assertThrows(InvalidDateTimeRangeException.class, () -> uniqueEventList.setEvent(BIRTHDAY_PARTY,
                        new EventBuilder(BIRTHDAY_PARTY).withEndDateAndTime(VALID_START_DATE_TIME_TUTORIAL).build()));
    }


    @Test
    public void remove_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.remove(null));
    }

    @Test
    public void remove_eventDoesNotExist_throwsEventNotFoundException() {
        assertThrows(EventNotFoundException.class, () -> uniqueEventList.remove(BIRTHDAY_PARTY));
    }

    @Test
    public void remove_existingEvent_removesEvent() {
        uniqueEventList.add(BIRTHDAY_PARTY);
        uniqueEventList.remove(BIRTHDAY_PARTY);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvents_nullUniqueEventList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvents((UniqueEventList) null));
    }

    @Test
    public void setEvents_uniqueEventList_replacesOwnListWithProvidedUniqueEventList() {
        uniqueEventList.add(BIRTHDAY_PARTY);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(CS2100_CONSULTATION);
        uniqueEventList.setEvents(expectedUniqueEventList);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvents_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvents((List<Event>) null));
    }

    @Test
    public void setEvents_list_replacesOwnListWithProvidedList() {
        uniqueEventList.add(BIRTHDAY_PARTY);
        List<Event> eventList = Collections.singletonList(CS2100_CONSULTATION);
        uniqueEventList.setEvents(eventList);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(CS2100_CONSULTATION);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvents_listWithDuplicateEvents_throwsDuplicateEventException() {
        List<Event> listWithDuplicateEvents = Arrays.asList(BIRTHDAY_PARTY, BIRTHDAY_PARTY);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.setEvents(listWithDuplicateEvents));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueEventList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void test_updateEventMap() {
        uniqueEventList.add(BIRTHDAY_PARTY);
        uniqueEventList.updateEventMap();
        assertEquals(BIRTHDAY_PARTY, Event.findByUuid(BIRTHDAY_PARTY.getUuid()));
    }

    @Test
    public void test_hashCode() {
        UniqueEventList uniqueEventListCopy = new UniqueEventList();
        assertEquals(uniqueEventList.hashCode(), uniqueEventListCopy.hashCode());

        uniqueEventListCopy.add(BIRTHDAY_PARTY);
        uniqueEventList.add(BIRTHDAY_PARTY);
        assertEquals(uniqueEventList.hashCode(), uniqueEventListCopy.hashCode());

        uniqueEventList.add(CS2100_CONSULTATION);
        assertNotEquals(uniqueEventList.hashCode(), uniqueEventListCopy.hashCode());
    }

    @Test
    public void test_iterator() {
        //empty uniqueEventList
        assertFalse(uniqueEventList.iterator().hasNext());

        //non-empty uniqueEventList
        uniqueEventList.add(BIRTHDAY_PARTY);
        assertTrue(uniqueEventList.iterator().hasNext());
    }
}
