package seedu.address.model.event;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Event}'s {@code StartDateTime} matches any of the keywords given.
 */
public class EventStartDateTimeContainsKeywordsPredicate implements Predicate<Event> {
    private final List<String> keywords;

    public EventStartDateTimeContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Event event) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(event.getStartDateAndTime().time.toString(),
                        keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventStartDateTimeContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((EventStartDateTimeContainsKeywordsPredicate) other).keywords)); // state check
    }
}