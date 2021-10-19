package seedu.address.testutil;

<<<<<<< HEAD
import seedu.address.commons.core.index.Index;
import seedu.address.commons.core.range.Range;

=======
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import seedu.address.commons.core.range.Range;


>>>>>>> 4e9c1cf7d9e38006f605afe1d34b5ecec2a15719
/**
 * A utility class containing a list of {@code Range} objects to be used in tests.
 */
public class TypicalRanges {
    public static final Range RANGE_FIRST_TO_FIRST = new Range(Index.fromOneBased(1), Index.fromOneBased(1));
    public static final Range RANGE_SECOND_TO_THIRD = new Range(Index.fromOneBased(2), Index.fromOneBased(3));
    public static final Range RANGE_FIRST_TO_SECOND = new Range(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
    public static final Range RANGE_SECOND_TO_THIRD = new Range(INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);
    public static final Range RANGE_FIRST_TO_THIRD = new Range(INDEX_FIRST_PERSON, INDEX_THIRD_PERSON);
    public static final Range RANGE_FIRST_TO_FIRST = Range.convertFromIndex(INDEX_FIRST_PERSON);

}
