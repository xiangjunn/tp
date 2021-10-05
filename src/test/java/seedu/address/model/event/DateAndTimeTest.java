package seedu.address.model.event;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class DateAndTimeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DateAndTime(null));
    }

    // TODO: 10/5/2021 update DateAndTime format
    @Test
    void isValidTime() {
        assertThrows(NullPointerException.class, () -> DateAndTime.isValidTime(null));
    }

}

