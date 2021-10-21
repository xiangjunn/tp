package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        assertFalse(DateAndTime.isValidDateTime("11-10-2021")); // missing time
        assertFalse(DateAndTime.isValidDateTime("11-10 12:10")); // missing year
        assertFalse(DateAndTime.isValidDateTime("11-10")); // missing year and time

        // invalid parts
        assertFalse(DateAndTime.isValidDateTime("2010/12/21 12:10")); // invalid connector
        assertFalse(DateAndTime.isValidDateTime("2012-12-20 12:10")); // wrong order
        assertFalse(DateAndTime.isValidDateTime("01-13-2012")); // wrong order of date and month
        assertFalse(DateAndTime.isValidDateTime(" 01-13-2012 12:05")); // leading space
        assertFalse(DateAndTime.isValidDateTime("01-13-2012 12:05 ")); // trailing space
        assertFalse(DateAndTime.isValidDateTime("2021 Oct 2 12:10")); // wrong date format
        assertFalse(DateAndTime.isValidDateTime("%01-13-2012 10:15")); // include invalid symbol '%'
        assertFalse(DateAndTime.isValidDateTime("01-13-2012 11:15pm")); // invalid time format
        assertFalse(DateAndTime.isValidDateTime("1-13-2012 11:15")); // missing leading 0

        // valid DateAndTime
        assertTrue(DateAndTime.isValidDateTime("01-12-2012 11:00"));
        assertTrue(DateAndTime.isValidDateTime("01-12-2012 10:05"));
        assertTrue(DateAndTime.isValidDateTime("01-12-2012 11:22"));
    }

    @Test
    public void isBefore() {
        DateAndTime firstDateTime = new DateAndTime("30-09-2021 22:50");
        DateAndTime secondDateTime = new DateAndTime("30-09-2021 22:55");
        DateAndTime thirdDateTime = new DateAndTime("30-10-2021 02:30");

        assertTrue(firstDateTime.isBefore(secondDateTime));
        assertTrue(firstDateTime.isBefore(thirdDateTime));
        assertFalse(thirdDateTime.isBefore(secondDateTime));
    }

    @Test
    public void compareTo() {
        DateAndTime firstDateTime = new DateAndTime("30-09-2021 22:50");
        DateAndTime secondDateTime = new DateAndTime("30-09-2021 22:55");
        DateAndTime secondDuplicate = new DateAndTime("30-09-2021 22:55");
        DateAndTime thirdDateTime = new DateAndTime("30-10-2021 02:30");

        assertTrue(firstDateTime.compareTo(secondDateTime) < 0);
        assertTrue(firstDateTime.compareTo(thirdDateTime) < 0);
        assertTrue(thirdDateTime.compareTo(firstDateTime) > 0);
        assertEquals(0, secondDateTime.compareTo(secondDuplicate));
    }
}

