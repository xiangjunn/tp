package seedu.address.testutil;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import seedu.address.commons.core.range.Range;


/**
 * A utility class containing a list of {@code Range} objects to be used in tests.
 */
public class TypicalRanges {
    public static final Range RANGE_FIRST_TO_SECOND = new Range(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
    public static final Range RANGE_SECOND_TO_THIRD = new Range(INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);
    public static final Range RANGE_FIRST_TO_THIRD = new Range(INDEX_FIRST_PERSON, INDEX_THIRD_PERSON);
}
