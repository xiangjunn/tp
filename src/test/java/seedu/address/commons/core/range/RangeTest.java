package seedu.address.commons.core.range;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;

public class RangeTest {

    @Test
    public void createRange() {
        // valid range
        final Range rangeOne = new Range(INDEX_FIRST, INDEX_SECOND);
        assertTrue(rangeOne.getStart().equals(INDEX_FIRST));
        assertTrue(rangeOne.getEnd().equals(INDEX_SECOND));

        // start and end index same
        final Range rangeTwo = Range.convertFromIndex(INDEX_THIRD);
        assertTrue(rangeTwo.getStart().equals(INDEX_THIRD));
        assertTrue(rangeTwo.getEnd().equals(INDEX_THIRD));

        // start index more than end index
        assertThrows(IndexOutOfBoundsException.class, () -> new Range(INDEX_THIRD, INDEX_FIRST));
    }

    @Test
    public void testEquals() {
        final Range range = new Range(INDEX_FIRST, INDEX_THIRD);

        // same values -> returns true
        final Range rangeFromConstructor = new Range(INDEX_THIRD, INDEX_THIRD);
        final Range rangeFromIndex = Range.convertFromIndex(INDEX_THIRD);
        assertTrue(rangeFromConstructor.equals(rangeFromIndex));

        // same object -> returns true
        assertTrue(range.equals(range));

        // null -> returns false
        assertFalse(range.equals(null));

        // different types -> returns false
        assertFalse(range.equals(5.0f));

        // different start index but same end index -> returns false
        final Range rangeStartIndexDifferent = new Range(INDEX_SECOND, INDEX_THIRD);
        assertFalse(range.equals(rangeStartIndexDifferent));

        // same start index but different end index -> returns false
        final Range rangeEndIndexDifferent = new Range(INDEX_FIRST, INDEX_SECOND);
        assertFalse(range.equals(rangeStartIndexDifferent));
    }

    @Test
    void getStart() {
        final Range range = new Range(INDEX_FIRST, INDEX_SECOND);
        Index start = range.getStart();
        assertEquals(start, INDEX_FIRST);
    }

    @Test
    void getEnd() {
        final Range range = new Range(INDEX_FIRST, INDEX_SECOND);
        Index end = range.getEnd();
        assertEquals(end, INDEX_SECOND);
    }
}
