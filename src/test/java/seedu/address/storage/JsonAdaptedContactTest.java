package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedContact.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.CARL;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.common.ZoomLink;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Phone;
import seedu.address.model.contact.TelegramHandle;
import seedu.address.testutil.ContactBuilder;

public class JsonAdaptedContactTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TELEGRAM_HANDLE = "abc";
    private static final String INVALID_ZOOM_LINK = "";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = CARL.getName().toString();
    private static final String VALID_PHONE = CARL.getPhone().toString();
    private static final String VALID_EMAIL = CARL.getEmail().toString();
    private static final String VALID_ADDRESS = CARL.getAddress().toString();
    private static final String VALID_TELEGRAM_HANDLE = CARL.getTelegramHandle().toString();
    private static final String VALID_ZOOM_LINK = CARL.getZoomLink().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = CARL.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final String VALID_UUID = UUID.randomUUID().toString();
    private static final List<String> VALID_LINKED_EVENTS = new ArrayList<>();

    @Test
    public void toModelType_validContactDetails_returnsContact() throws IllegalValueException {
        JsonAdaptedContact contact = new JsonAdaptedContact(CARL);
        assertEquals(CARL, contact.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedContact contact =
                new JsonAdaptedContact(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                    VALID_TELEGRAM_HANDLE, VALID_ZOOM_LINK, VALID_TAGS, VALID_UUID, VALID_LINKED_EVENTS,
                        false);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedContact contact = new JsonAdaptedContact(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
            VALID_TELEGRAM_HANDLE, VALID_ZOOM_LINK, VALID_TAGS, VALID_UUID, VALID_LINKED_EVENTS, false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedContact contact =
                new JsonAdaptedContact(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TELEGRAM_HANDLE,
                    VALID_ZOOM_LINK, VALID_TAGS, VALID_UUID, VALID_LINKED_EVENTS, false);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_nullPhone_returnsContact() throws IllegalValueException {
        Contact contactWithNullPhone = new ContactBuilder().withPhone(null).build();
        JsonAdaptedContact contact = new JsonAdaptedContact(contactWithNullPhone);
        assertEquals(contactWithNullPhone, contact.toModelType());
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedContact contact =
                new JsonAdaptedContact(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_TELEGRAM_HANDLE,
                    VALID_ZOOM_LINK, VALID_TAGS, VALID_UUID, VALID_LINKED_EVENTS, false);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedContact contact = new JsonAdaptedContact(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
            VALID_TELEGRAM_HANDLE, VALID_ZOOM_LINK, VALID_TAGS, VALID_UUID, VALID_LINKED_EVENTS, false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedContact contact =
                new JsonAdaptedContact(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_TELEGRAM_HANDLE,
                    VALID_ZOOM_LINK, VALID_TAGS, VALID_UUID, VALID_LINKED_EVENTS, false);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_nullAddress_returnsContact() throws IllegalValueException {
        Contact contactWithNullAddress = new ContactBuilder().withAddress(null).build();
        JsonAdaptedContact contact = new JsonAdaptedContact(contactWithNullAddress);
        assertEquals(contactWithNullAddress, contact.toModelType());
    }

    @Test
    public void toModelType_invalidTelegramHandle_throwsIllegalValueException() {
        JsonAdaptedContact contact =
            new JsonAdaptedContact(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, INVALID_TELEGRAM_HANDLE,
                VALID_ZOOM_LINK, VALID_TAGS, VALID_UUID, VALID_LINKED_EVENTS, false);
        String expectedMessage = TelegramHandle.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_nullTelegramHandle_returnsContact() throws IllegalValueException {
        Contact contactWithNullTelegramHandle = new ContactBuilder().withTelegramHandle(null).build();
        JsonAdaptedContact contact = new JsonAdaptedContact(contactWithNullTelegramHandle);
        assertEquals(contactWithNullTelegramHandle, contact.toModelType());
    }

    @Test
    public void toModelType_invalidZoomLink_throwsIllegalValueException() {
        JsonAdaptedContact contact =
            new JsonAdaptedContact(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TELEGRAM_HANDLE,
                INVALID_ZOOM_LINK, VALID_TAGS, VALID_UUID, VALID_LINKED_EVENTS, false);
        String expectedMessage = ZoomLink.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_nullZoomLink_returnsContact() throws IllegalValueException {
        Contact contactWithNullZoomLink = new ContactBuilder().withZoomLink(null).build();
        JsonAdaptedContact contact = new JsonAdaptedContact(contactWithNullZoomLink);
        assertEquals(contactWithNullZoomLink, contact.toModelType());
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedContact contact =
                new JsonAdaptedContact(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TELEGRAM_HANDLE,
                    VALID_ZOOM_LINK, invalidTags, VALID_UUID, VALID_LINKED_EVENTS, false);
        assertThrows(IllegalValueException.class, contact::toModelType);
    }
}
