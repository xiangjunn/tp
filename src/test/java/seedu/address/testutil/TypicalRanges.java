package seedu.address.testutil;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import seedu.address.commons.core.range.Range;

/**
 * A utility class containing a list of {@code Range} objects to be used in tests.
 */
public class TypicalRanges {
    public static final Range RANGE_FIRST_TO_SECOND = new Range(INDEX_FIRST, INDEX_SECOND);
    public static final Range RANGE_SECOND_TO_THIRD = new Range(INDEX_SECOND, INDEX_THIRD);
    public static final Range RANGE_FIRST_TO_THIRD = new Range(INDEX_FIRST, INDEX_THIRD);
    public static final Range RANGE_FIRST_TO_FIRST = Range.convertFromIndex(INDEX_FIRST);
}
