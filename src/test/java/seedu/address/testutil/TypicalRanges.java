package seedu.address.testutil;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.core.range.Range;

/**
 * A utility class containing a list of {@code Range} objects to be used in tests.
 */
public class TypicalRanges {
    public static final Range RANGE_FIRST_TO_FIRST = new Range(Index.fromOneBased(1), Index.fromOneBased(1));
    public static final Range RANGE_SECOND_TO_THIRD = new Range(Index.fromOneBased(2), Index.fromOneBased(3));
}
