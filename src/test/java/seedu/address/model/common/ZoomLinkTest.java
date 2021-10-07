package seedu.address.model.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class ZoomLinkTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ZoomLink(null));
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
