package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class DateAndTimeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DateAndTime(null));
    }

    @Test
    public void constructor_invalidZoomLink_throwsIllegalArgumentException() {
        String emptyDateTime = "";
        assertThrows(IllegalArgumentException.class, () -> new DateAndTime(emptyDateTime));
    }

    @Test
    public void isValidDateAndTime() {
        // null DateAndTime
        assertThrows(NullPointerException.class, () -> DateAndTime.isValidDateTime(null));

        // blank DateAndTime
        assertFalse(DateAndTime.isValidDateTime("")); // empty string
        assertFalse(DateAndTime.isValidDateTime(" ")); // spaces only

        // missing parts
        assertFalse(DateAndTime.isValidDateTime("2012-10-11")); // missing time
        assertFalse(DateAndTime.isValidDateTime("11-10 12:10")); // missing year
        assertFalse(DateAndTime.isValidDateTime("11-10")); // missing year and time

        // invalid parts
        assertFalse(DateAndTime.isValidDateTime("2010/12/21 12:10")); // invalid connector
        assertFalse(DateAndTime.isValidDateTime("21-12-2020 12:10")); // wrong order
        assertFalse(DateAndTime.isValidDateTime("2012-13-01")); // wrong order of date and month
        assertFalse(DateAndTime.isValidDateTime(" 2012-10-01 12:05")); // leading space
        assertFalse(DateAndTime.isValidDateTime("2021-10-01 12:05 ")); // trailing space
        assertFalse(DateAndTime.isValidDateTime("2021 Oct 2 12:10")); // wrong date format
        assertFalse(DateAndTime.isValidDateTime("%2021-13-01 10:15")); // include invalid symbol '%'
        assertFalse(DateAndTime.isValidDateTime("2021-10-01 11:15pm")); // invalid time format

        // valid DateAndTime
        assertTrue(DateAndTime.isValidDateTime("2021-10-01 11:00")); // valid year, month, date and time
        assertTrue(DateAndTime.isValidDateTime("2021-10-3 10:05")); // valid day without leading 0
        assertTrue(DateAndTime.isValidDateTime("2021-10-11 1:2")); // valid hour and minute without leading 0
    }

    @Test
    public void isBefore() {
        DateAndTime firstDateTime = new DateAndTime("2021-09-30 22:50");
        DateAndTime secondDateTime = new DateAndTime("2021-09-30 22:55");
        DateAndTime thirdDateTime = new DateAndTime("2021-10-30 02:30");

        assertTrue(firstDateTime.isBefore(secondDateTime));
        assertTrue(firstDateTime.isBefore(thirdDateTime));
        assertFalse(thirdDateTime.isBefore(secondDateTime));
    }
}

