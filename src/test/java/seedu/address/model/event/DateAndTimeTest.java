package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;

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
    public void containsString() {
        DateAndTime dateAndTime = new DateAndTime("01-12-2012 11:22");

        // keywords contained in dateAndTime
        List<String> listOfKeywordsContained = Arrays.asList("01-10-20", "11:");
        assertTrue(dateAndTime.containsString(listOfKeywordsContained));

        //keywords not contained in dateAndTime
        List<String> noKeywordsContained = Arrays.asList("morning", "11:3", "11:21");
        assertFalse(dateAndTime.containsString(noKeywordsContained));
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

        // invalid date
        assertFalse(DateAndTime.isValidDateTime("2010/12/21 12:10")); // invalid connector
        assertFalse(DateAndTime.isValidDateTime(" 01-12-2012 12:05")); // leading space
        assertFalse(DateAndTime.isValidDateTime("01-12-2012 12:05 ")); // trailing space
        assertFalse(DateAndTime.isValidDateTime("2021 Oct 2 12:10")); // wrong date format
        assertFalse(DateAndTime.isValidDateTime("%01-13-2012 10:15")); // include invalid symbol '%'
        assertFalse(DateAndTime.isValidDateTime("1-13-2012 11:15")); // missing leading 0
        assertFalse(DateAndTime.isValidDateTime("01-12-20001 11:00")); // year can only take in 4-digit numbers
        assertFalse(DateAndTime.isValidDateTime("00-12-2021 11:00")); // date must start from 0
        assertFalse(DateAndTime.isValidDateTime("31-02-2000 11:00")); // 31 Feb is not a valid date
        assertFalse(DateAndTime.isValidDateTime("29-02-2021 11:11")); // invalid date 29th feb for non-leap year

        // invalid time
        assertFalse(DateAndTime.isValidDateTime("01-12-2012 11:15pm")); // invalid time format
        assertFalse(DateAndTime.isValidDateTime("01-12-2021 24:00")); // time must be between 00:00 and 23:59
        assertFalse(DateAndTime.isValidDateTime("01-12-2021 15:60")); // time must be between 00:00 and 23:59
        assertFalse(DateAndTime.isValidDateTime("01-12-2021 3:16")); // missing leading 0 for hour input



        // valid DateAndTime
        assertTrue(DateAndTime.isValidDateTime("01-01-2021 11:00"));
        assertTrue(DateAndTime.isValidDateTime("31-12-1090 10:05"));
        assertTrue(DateAndTime.isValidDateTime("31-03-2012 11:22"));
        assertTrue(DateAndTime.isValidDateTime("01-12-9000 11:00")); // year can take in any 4 digits number
        assertTrue(DateAndTime.isValidDateTime("29-02-2024 11:11")); // leap year allows 29th feb
        assertTrue(DateAndTime.isValidDateTime("20-10-2021 00:00"));
        assertTrue(DateAndTime.isValidDateTime("20-10-2021 23:59"));

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



    @Test
    public void testEquals() {
        DateAndTime dateTime = new DateAndTime("30-09-2021 22:50");
        DateAndTime dateTimeCopy = new DateAndTime("30-09-2021 22:50");
        DateAndTime differentDate = new DateAndTime("30-12-2021 22:55");
        DateAndTime differentTime = new DateAndTime("30-09-2021 00:30");
        DateAndTime differentDateTime = new DateAndTime("30-12-2021 00:30");

        // same object -> returns true
        assertTrue(dateTime.equals(dateTime));

        // null -> returns false
        assertFalse(dateTime.equals(null));

        // same values -> returns true
        assertTrue(dateTime.equals(dateTimeCopy));

        // different type -> returns false
        assertFalse(dateTime.equals(1));

        // different date -> returns false
        assertFalse(dateTime.equals(differentDate));

        // different time -> returns false
        assertFalse(dateTime.equals(differentTime));

        // different date and time -> returns false
        assertFalse(dateTime.equals(differentDateTime));

    }
}

