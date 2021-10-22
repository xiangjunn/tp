package seedu.address.model.event;

import seedu.address.commons.util.StringUtil;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Event}'s {@code ZoomLink} matches any of the keywords given.
 */
public class EventZoomLinkContainsKeywordsPredicate implements Predicate<Event> {
    private final List<String> keywords;

    public EventZoomLinkContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Event event) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(event.getZoomLink().link, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventZoomLinkContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((EventZoomLinkContainsKeywordsPredicate) other).keywords)); // state check
    }

}