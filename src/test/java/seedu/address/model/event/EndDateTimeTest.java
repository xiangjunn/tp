package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class EndDateTimeTest {

    // TODO: 10/5/2021 implement test for EndDateTime
    @Test
    public void dummyTest() {
        assertEquals(2, 2);
    }

    // TODO: 10/5/2021 update DateAndTime format
    @Test
    void isValidTime() {
        assertThrows(NullPointerException.class, () -> EndDateTime.isValidTime(null));
    }
}
