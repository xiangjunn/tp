package seedu.address.model.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;

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
    public void containsString() {
        ZoomLink zoomLink =
                new ZoomLink("https://nus-sg.zoom.us/j/83851237332?pwd=c1B0V2FtTXh1MWwyYVJ6TFAzczE4Zz09#success");

        // keywords contained in zoomLink
        List<String> listOfKeywordsContained = Arrays.asList("nus", "ZOOM");
        assertTrue(zoomLink.containsString(listOfKeywordsContained));

        //keywords not contained in zoomLink
        List<String> noKeywordsContained = Arrays.asList("BoB", "Pan", "Peter");
        assertFalse(zoomLink.containsString(noKeywordsContained));
    }

    @Test
    public void isValidZoomLink() {
        // null zoom link
        assertThrows(NullPointerException.class, () -> ZoomLink.isValidZoomLink(null));

        // blank zoom link
        assertFalse(ZoomLink.isValidZoomLink("")); // empty string
        assertFalse(ZoomLink.isValidZoomLink(" ")); // spaces only

        // valid zoom link
        assertTrue(ZoomLink.isValidZoomLink("googlecom"));
        assertTrue(ZoomLink.isValidZoomLink("http:peterjack"));
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
