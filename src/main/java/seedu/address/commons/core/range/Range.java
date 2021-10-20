package seedu.address.commons.core.range;

import seedu.address.commons.core.index.Index;

public class Range {
    private Index start;
    private Index end;

    /**
     * Constructs an inclusive range from start index to end index.
     *
     * @param start An index representing the start.
     * @param end An index representing the end.
     */
    public Range(Index start, Index end) {
        assert start.getZeroBased() >= 0;
        assert end.getZeroBased() >= 0;
        if (start.isMoreThan(end)) {
            throw new IndexOutOfBoundsException();
        }
        this.start = start;
        this.end = end;
    }

    public Index getStart() {
        return start;
    }

    public Index getEnd() {
        return end;
    }

    /**
     * Creates a Range object with start and end indexes representing the same index.
     *
     * @param index The index to be converted.
     */
    public static Range convertFromIndex(Index index) {
        return new Range(index, index);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Range // instanceof handles nulls
            && start.equals(((Range) other).start)
            && (end.equals(((Range) other).end))); // state check
    }
}
