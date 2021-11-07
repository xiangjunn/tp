package seedu.address.storage;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.CS2103_MIDTERM_MARKED;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.common.ZoomLink;
import seedu.address.model.event.Description;
import seedu.address.model.event.EndDateTime;
import seedu.address.model.event.Event;
import seedu.address.model.event.StartDateTime;
import seedu.address.testutil.EventBuilder;

class JsonAdaptedEventTest {
    private static final String INVALID_NAME = "d@nce";
    private static final String INVALID_START_DATE_AND_TIME = "12/12/2021";
    private static final String INVALID_END_DATE_AND_TIME = "2021/12/12";
    private static final String INVALID_DESCRIPTION = " ";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_ZOOM_LINK = "";
    private static final String INVALID_TAG = "#summer";

    private static final String VALID_NAME = CS2103_MIDTERM_MARKED.getName().toString();
    private static final String VALID_START_DATE_AND_TIME = CS2103_MIDTERM_MARKED.getStartDateAndTime().toString();
    private static final String VALID_END_DATE_AND_TIME = CS2103_MIDTERM_MARKED.getEndDateAndTime().toString();
    private static final String VALID_DESCRIPTION = CS2103_MIDTERM_MARKED.getDescription().toString();
    private static final String VALID_ADDRESS = CS2103_MIDTERM_MARKED.getAddress().toString();
    private static final String VALID_ZOOM_LINK = CS2103_MIDTERM_MARKED.getZoomLink().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = CS2103_MIDTERM_MARKED.getTags().stream()
        .map(JsonAdaptedTag::new)
        .collect(Collectors.toList());
    private static final String VALID_UUID = UUID.randomUUID().toString();
    private static final List<String> VALID_LINKED_CONTACTS = new ArrayList<>();

    @Test
    public void toModelType_validEventDetails_returnsEvent() throws IllegalValueException {
        JsonAdaptedEvent event = new JsonAdaptedEvent(CS2103_MIDTERM_MARKED);
        assertEquals(CS2103_MIDTERM_MARKED, event.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedEvent event =
            new JsonAdaptedEvent(INVALID_NAME, VALID_START_DATE_AND_TIME, VALID_END_DATE_AND_TIME, VALID_DESCRIPTION,
                VALID_ADDRESS, VALID_ZOOM_LINK, VALID_TAGS, VALID_UUID, VALID_LINKED_CONTACTS,
                false);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedEvent event =
            new JsonAdaptedEvent(null, VALID_START_DATE_AND_TIME, VALID_END_DATE_AND_TIME, VALID_DESCRIPTION,
                VALID_ADDRESS, VALID_ZOOM_LINK, VALID_TAGS, VALID_UUID, VALID_LINKED_CONTACTS, false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidStartDateTime_throwsIllegalValueException() {
        JsonAdaptedEvent event =
            new JsonAdaptedEvent(VALID_NAME, INVALID_START_DATE_AND_TIME, VALID_END_DATE_AND_TIME, VALID_DESCRIPTION,
                VALID_ADDRESS, VALID_ZOOM_LINK, VALID_TAGS, VALID_UUID, VALID_LINKED_CONTACTS, false);
        String expectedMessage = StartDateTime.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullStartDateTime_throwsIllegalValueException() {
        JsonAdaptedEvent event =
            new JsonAdaptedEvent(VALID_NAME, null, VALID_END_DATE_AND_TIME, VALID_DESCRIPTION,
                VALID_ADDRESS, VALID_ZOOM_LINK, VALID_TAGS, VALID_UUID, VALID_LINKED_CONTACTS, false);
        String expectedMessage = String.format(
            JsonAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT,
            StartDateTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidEndDateTime_throwsIllegalValueException() {
        JsonAdaptedEvent event =
            new JsonAdaptedEvent(VALID_NAME, VALID_START_DATE_AND_TIME, INVALID_END_DATE_AND_TIME, VALID_DESCRIPTION,
                VALID_ADDRESS, VALID_ZOOM_LINK, VALID_TAGS, VALID_UUID, VALID_LINKED_CONTACTS, false);
        String expectedMessage = EndDateTime.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullEndDateTime_returnsEvent() throws IllegalValueException {
        Event eventWithNullEndDateTime = new EventBuilder().withEndDateAndTime(null).build();
        JsonAdaptedEvent event = new JsonAdaptedEvent(eventWithNullEndDateTime);
        assertEquals(eventWithNullEndDateTime, event.toModelType());
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedEvent event =
            new JsonAdaptedEvent(VALID_NAME, VALID_START_DATE_AND_TIME, VALID_END_DATE_AND_TIME, INVALID_DESCRIPTION,
                VALID_ADDRESS, VALID_ZOOM_LINK, VALID_TAGS, VALID_UUID, VALID_LINKED_CONTACTS, false);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullDescription_returnsEvent() throws IllegalValueException {
        Event eventWithNullDescription = new EventBuilder().withDescription(null).build();
        JsonAdaptedEvent event = new JsonAdaptedEvent(eventWithNullDescription);
        assertEquals(eventWithNullDescription, event.toModelType());
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedEvent event =
            new JsonAdaptedEvent(VALID_NAME, VALID_START_DATE_AND_TIME, VALID_END_DATE_AND_TIME, VALID_DESCRIPTION,
                INVALID_ADDRESS, VALID_ZOOM_LINK, VALID_TAGS, VALID_UUID, VALID_LINKED_CONTACTS, false);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullAddress_returnsEvent() throws IllegalValueException {
        Event eventWithNullAddress = new EventBuilder().withAddress(null).build();
        JsonAdaptedEvent event = new JsonAdaptedEvent(eventWithNullAddress);
        assertEquals(eventWithNullAddress, event.toModelType());
    }

    @Test
    public void toModelType_invalidZoomLink_throwsIllegalValueException() {
        JsonAdaptedEvent event =
            new JsonAdaptedEvent(VALID_NAME, VALID_START_DATE_AND_TIME, VALID_END_DATE_AND_TIME, VALID_DESCRIPTION,
                VALID_ADDRESS, INVALID_ZOOM_LINK, VALID_TAGS, VALID_UUID, VALID_LINKED_CONTACTS, false);
        String expectedMessage = ZoomLink.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullZoomLink_returnsEvent() throws IllegalValueException {
        Event eventWithNullZoomLink = new EventBuilder().withZoomLink(null).build();
        JsonAdaptedEvent event = new JsonAdaptedEvent(eventWithNullZoomLink);
        assertEquals(eventWithNullZoomLink, event.toModelType());
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedEvent event =
            new JsonAdaptedEvent(VALID_NAME, VALID_START_DATE_AND_TIME, VALID_END_DATE_AND_TIME, VALID_DESCRIPTION,
                VALID_ADDRESS, VALID_ZOOM_LINK, invalidTags, VALID_UUID, VALID_LINKED_CONTACTS, false);
        assertThrows(IllegalValueException.class, event::toModelType);
    }

}

