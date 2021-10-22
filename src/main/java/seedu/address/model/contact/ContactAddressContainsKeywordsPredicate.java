package seedu.address.model.contact;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.event.Event;

/**
 * Tests that an {@code Address} matches any of the keywords given.
 */
public class ContactAddressContainsKeywordsPredicate implements Predicate<Event> {
    private final List<String> keywords;

    public ContactAddressContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Event event) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(event.getAddress().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContactAddressContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ContactAddressContainsKeywordsPredicate) other).keywords)); // state check
    }
}
