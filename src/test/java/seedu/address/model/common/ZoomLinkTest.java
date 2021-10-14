package seedu.address.model.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class ZoomLinkTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ZoomLink(null));
    }
    @Test
    public void constructor_invalidZoomLink_throwsIllegalArgumentException() {
        String invalidZoomLink = "";
        assertThrows(IllegalArgumentException.class, () -> new ZoomLink(invalidZoomLink));
    }

    @Test
    public void isValidZoomLink() {
        // null zoom link
        assertThrows(NullPointerException.class, () -> ZoomLink.isValidZoomLink(null));

        // blank zoom link
        assertFalse(ZoomLink.isValidZoomLink("")); // empty string
        assertFalse(ZoomLink.isValidZoomLink(" ")); // spaces only

        // missing parts
        assertFalse(ZoomLink.isValidZoomLink("https://.com")); // missing body part
        assertFalse(ZoomLink.isValidZoomLink("googlecom")); // missing '.' symbol
        assertFalse(ZoomLink.isValidZoomLink("http:peterjack")); // missing end part

        // invalid parts
        assertFalse(ZoomLink.isValidZoomLink("http://peterjack@-")); // invalid body part, missing '.' and end part
        assertFalse(ZoomLink.isValidZoomLink("exam_ple.com")); // underscore in body part
        assertFalse(ZoomLink.isValidZoomLink("peter example.com")); // spaces in body part
        assertFalse(ZoomLink.isValidZoomLink(" example.com")); // leading space
        assertFalse(ZoomLink.isValidZoomLink("example.com ")); // trailing space
        assertFalse(ZoomLink.isValidZoomLink("http:example.com ")); // invalid header
        assertFalse(ZoomLink.isValidZoomLink("%example.com")); // '%' symbol in body part
        assertFalse(ZoomLink.isValidZoomLink("-peterjack.example.com")); // body part starts with a hyphen
        assertFalse(ZoomLink.isValidZoomLink("/peterjack.example.com")); // body part starts with a hyphen
        assertFalse(ZoomLink.isValidZoomLink("peterjack-.example.com")); // body part ends with a hyphen
        assertFalse(ZoomLink.isValidZoomLink("peter.jack@example.com&")); // end part should end with alphanumeric

        // valid zoom link
        assertTrue(ZoomLink.isValidZoomLink("https://PeterJack1190.example.com")); // valid header, body and end part
        assertTrue(ZoomLink.isValidZoomLink("PeterJack1190.example.com%123")); // valid body and end part without header
        assertTrue(ZoomLink.isValidZoomLink("www.PeterJack-1190.example.com")); // hyphen in body part
        assertTrue(ZoomLink.isValidZoomLink("www.PeterJack/1190.example.com")); // '/' symbol in body part
        assertTrue(ZoomLink.isValidZoomLink("PeterJack-1190.example.com")); // hyphen in local part
        assertTrue(ZoomLink.isValidZoomLink("abc.co@m")); // end part can contain symbols
        assertTrue(ZoomLink.isValidZoomLink("123.145")); // numeric body and end part
        assertTrue(ZoomLink.isValidZoomLink("a1be.d@example1.com")); // end part contains mixture of alphanumeric
        // and special characters
        assertTrue(ZoomLink.isValidZoomLink("peter/jack.very-very-very-long-example.com")); // long end part
        assertTrue(ZoomLink.isValidZoomLink("if/you/dream/it-you/can-do/it.example.com")); // long body part
        assertTrue(ZoomLink.isValidZoomLink("e1234567.u.nus.edu")); // more than one period in domain
        assertTrue(ZoomLink.isValidZoomLink(
                "https://nus-sg.zoom.us/j/83851237332?pwd=c1B0V2FtTXh1MWwyYVJ6TFAzczE4Zz09#success"));
    }

    @Test
    public void testEquals() {
        ZoomLink link = new ZoomLink("http://zoomlink.com");
        ZoomLink sameLink = new ZoomLink("http://zoomlink.com");
        ZoomLink differentLink = new ZoomLink("http://123.com");

        assertEquals(link, sameLink);
        assertEquals(link, link);
        assertNotEquals(link, differentLink);

    }
}
