package seedu.address.model.event;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Event}'s {@code EndDateTime} matches any of the keywords given.
 */
public class EventEndDateTimeContainsKeywordsPredicate implements Predicate<Event> {
    private final List<String> keywords;

    public EventEndDateTimeContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Event event) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(event.getEndDateAndTime().time.toString(),
                        keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventEndDateTimeContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((EventEndDateTimeContainsKeywordsPredicate) other).keywords)); // state check
    }
}
