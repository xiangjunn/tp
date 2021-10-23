package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TagColorsTest {

    @Test
    void getTagColour() {
        String colour1 = TagColors.getTagColour();
        String colour2 = TagColors.getTagColour();
        assertNotEquals(colour1, colour2);
    }
}