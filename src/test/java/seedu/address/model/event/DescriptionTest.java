package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class DescriptionTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = "";
        assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescription));
    }

    @Test
    public void isValidDescription() {
        assertTrue(Description.isValidDescription("This is a description."));
        assertTrue(Description.isValidDescription("This description contains %,?,()"));
        assertTrue(Description.isValidDescription("A12%912thisIsADescription"));
    }

    @Test
    public void containsString() {
        Description description = new Description("description 123");

        // keywords contained in description
        List<String> listOfKeywordsContained = Arrays.asList("desc", "       23");
        assertTrue(description.containsString(listOfKeywordsContained));

        //keywords not contained in description
        List<String> noKeywordsContained = Arrays.asList("dt", "blah", "descpt");
        assertFalse(description.containsString(noKeywordsContained));
    }

    @Test
    public void testEquals() {
        Description description = new Description("description 1");
        Description sameDescription = new Description("description 1");
        Description differentDescription = new Description("description 2");

        assertEquals(description, sameDescription);
        assertEquals(description, description);
        assertNotEquals(description, differentDescription);
    }
}
