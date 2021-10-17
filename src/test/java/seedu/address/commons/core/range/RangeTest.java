package seedu.address.commons.core.range;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

public class RangeTest {

    @Test
    public void createRange() {
        // valid range
        final Range rangeOne = new Range(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        assertTrue(rangeOne.getStart().equals(INDEX_FIRST_PERSON));
        assertTrue(rangeOne.getEnd().equals(INDEX_SECOND_PERSON));

        // start and end index same
        final Range rangeTwo = Range.convertFromIndex(INDEX_THIRD_PERSON);
        assertTrue(rangeTwo.getStart().equals(INDEX_THIRD_PERSON));
        assertTrue(rangeTwo.getEnd().equals(INDEX_THIRD_PERSON));

        // start index more than end index
        assertThrows(IndexOutOfBoundsException.class, () -> new Range(INDEX_THIRD_PERSON, INDEX_FIRST_PERSON));


    }

    @Test
    public void equals() {
        final Range range = new Range(INDEX_FIRST_PERSON, INDEX_THIRD_PERSON);

        // same values -> returns true
        final Range rangeFromConstructor = new Range(INDEX_THIRD_PERSON, INDEX_THIRD_PERSON);
        final Range rangeFromIndex = Range.convertFromIndex(INDEX_THIRD_PERSON);
        assertTrue(rangeFromConstructor.equals(rangeFromIndex));

        // same object -> returns true
        assertTrue(range.equals(range));

        // null -> returns false
        assertFalse(range.equals(null));

        // different types -> returns false
        assertFalse(range.equals(5.0f));

        // different start index but same end index -> returns false
        final Range rangeStartIndexDifferent = new Range(INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);
        assertFalse(range.equals(rangeStartIndexDifferent));

        // same start index but different end index -> returns false
        final Range rangeEndIndexDifferent = new Range(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        assertFalse(range.equals(rangeStartIndexDifferent));
    }
}
