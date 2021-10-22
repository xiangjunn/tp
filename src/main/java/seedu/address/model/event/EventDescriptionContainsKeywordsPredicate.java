package seedu.address.model.event;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Event}'s {@code Description} matches any of the keywords given.
 */
public class EventDescriptionContainsKeywordsPredicate implements Predicate<Event> {
    private final List<String> keywords;

    public EventDescriptionContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Event event) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(event.getDescription().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventDescriptionContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((EventDescriptionContainsKeywordsPredicate) other).keywords)); // state check
    }
}
