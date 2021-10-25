package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class TelegramHandleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TelegramHandle(null));
    }

    @Test
    public void constructor_invalidHandle_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new TelegramHandle(""));
    }

    @Test
    public void containsString() {
        TelegramHandle telegramHandle = new TelegramHandle("Beasty");

        // keywords contained in telegram handle
        List<String> listOfKeywordsContained = Arrays.asList("beast", "TY");
        assertTrue(telegramHandle.containsString(listOfKeywordsContained));

        //keywords not contained in telegram handle
        List<String> noKeywordsContained = Arrays.asList("retep", "@BEASTY", "yahoo");
        assertFalse(telegramHandle.containsString(noKeywordsContained));
    }

    @Test
    void isValidHandle() {
        // null
        assertThrows(NullPointerException.class, () -> TelegramHandle.isValidHandle(null));

        // invalid
        assertFalse(TelegramHandle.isValidHandle("")); // empty string
        assertFalse(TelegramHandle.isValidHandle(" ")); // spaces
        assertFalse(TelegramHandle.isValidHandle("91a5")); // less than 5 characters
        assertFalse(TelegramHandle.isValidHandle("tele man")); // spaces
        assertFalse(TelegramHandle.isValidHandle("(^///^)3")); // illegal characters

        // valid
        assertTrue(TelegramHandle.isValidHandle("beast")); // exactly 5 characters
        assertTrue(TelegramHandle.isValidHandle("Beast_69")); // use of capital letters, numbers and underscore
    }

    @Test
    void getTelegramLink() {
        TelegramHandle handle = new TelegramHandle("Beast");
        assertEquals(handle.getTelegramLink(), "https://t.me/Beast");
    }

    @Test
    void testToString() {
        TelegramHandle handle = new TelegramHandle("Beast");
        assertEquals(handle.toString(), "Beast");
    }

    @Test
    void testEquals() {
        TelegramHandle handle1 = new TelegramHandle("beast");
        TelegramHandle handle2 = new TelegramHandle("beast");
        TelegramHandle handle3 = new TelegramHandle("Beast");
        String hello = "Hello";
        assertEquals(handle1, handle2); // Same telegram handle
        assertNotEquals(handle1, handle3); // Different handle
        assertNotEquals(handle1, hello); // Different type
    }
}
