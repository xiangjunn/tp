package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class EndDateTimeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EndDateTime(null));
    }

    @Test
    public void constructor_invalidZoomLink_throwsIllegalArgumentException() {
        String emptyDateTime = "";
        assertThrows(IllegalArgumentException.class, () -> new EndDateTime(emptyDateTime));
    }

    @Test
    public void containsString() {
        EndDateTime dateAndTime = new EndDateTime("01-12-2012 11:22");

        // keywords contained in dateAndTime
        List<String> listOfKeywordsContained = Arrays.asList("01-10-20", "11:");
        assertTrue(dateAndTime.containsString(listOfKeywordsContained));

        //keywords not contained in dateAndTime
        List<String> noKeywordsContained = Arrays.asList("morning", "11:3", "11:21");
        assertFalse(dateAndTime.containsString(noKeywordsContained));
    }

    @Test
    public void isValidEndDateTime() {
        // null EndDateTime
        assertThrows(NullPointerException.class, () -> EndDateTime.isValidDateTime(null));

        // blank EndDateTime
        assertFalse(EndDateTime.isValidDateTime("")); // empty string
        assertFalse(EndDateTime.isValidDateTime(" ")); // spaces only

        // missing parts
        assertFalse(EndDateTime.isValidDateTime("11-10-2021")); // missing time
        assertFalse(EndDateTime.isValidDateTime("11-10 12:10")); // missing year
        assertFalse(EndDateTime.isValidDateTime("11-10")); // missing year and time

        // invalid parts
        assertFalse(EndDateTime.isValidDateTime("2010/12/21 12:10")); // invalid connector
        assertFalse(EndDateTime.isValidDateTime("2012-12-20 12:10")); // wrong order
        assertFalse(EndDateTime.isValidDateTime("01-13-2012")); // wrong order of date and month
        assertFalse(EndDateTime.isValidDateTime(" 01-13-2012 12:05")); // leading space
        assertFalse(EndDateTime.isValidDateTime("01-13-2012 12:05 ")); // trailing space
        assertFalse(EndDateTime.isValidDateTime("2021 Oct 2 12:10")); // wrong date format
        assertFalse(EndDateTime.isValidDateTime("%01-13-2012 10:15")); // include invalid symbol '%'
        assertFalse(EndDateTime.isValidDateTime("01-13-2012 11:15pm")); // invalid time format
        assertFalse(EndDateTime.isValidDateTime("1-13-2012 11:15")); // missing leading 0

        // valid EndDateTime
        assertTrue(EndDateTime.isValidDateTime("01-12-2012 11:00"));
        assertTrue(EndDateTime.isValidDateTime("01-12-2012 10:05"));
        assertTrue(EndDateTime.isValidDateTime("01-12-2012 11:22"));
    }

    @Test
    public void isBefore() {
        EndDateTime firstDateTime = new EndDateTime("30-09-2021 22:50");
        EndDateTime secondDateTime = new EndDateTime("30-09-2021 22:55");
        EndDateTime thirdDateTime = new EndDateTime("30-10-2021 02:30");

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
