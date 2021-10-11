package seedu.address.commons.core.range;

import seedu.address.commons.core.index.Index;

public class Range {
    private Index start;
    private Index end;

    /**
     * Constructs a range from start to end, inclusive.
     *
     * @param start An index representing the start.
     * @param end An index representing the end.
     */
    public Range(Index start, Index end) {
        this.start = start;
        this.end = end;
    }

    public Index getStart() {
        return start;
    }

    public Index getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Range // instanceof handles nulls
            && start.equals(((Range) other).start)
            && (end.equals(((Range) other).end))); // state check
    }
}
