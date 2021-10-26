package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void containsString() {
        Tag tag = new Tag("recurring");

        List<String> listOfStringsKeywordsContained = Arrays.asList("RE", "curr");
        assertTrue(tag.containsString(listOfStringsKeywordsContained));

        List<String> noKeywordsContained = Arrays.asList("fri", "fam");
        assertFalse(tag.containsString(noKeywordsContained));

    }
    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }
}
