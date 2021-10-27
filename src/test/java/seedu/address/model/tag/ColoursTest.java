package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class ColoursTest {

    @Test
    void getTagColour() {
        String colour1 = Colours.getTagColour();
        String colour2 = Colours.getTagColour();
        assertNotEquals(colour1, colour2);
    }

    @Test
    void getSameColour() {
        String colour1 = Colours.getTagColour();
        String colour2 = "";
        for (int i = 0; i < Colours.NUMBER_OF_COLOURS; i++) {
            colour2 = Colours.getTagColour();
        }
        assertEquals(colour1, colour2);
    }
}
