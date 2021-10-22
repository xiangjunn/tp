package seedu.address.model.event;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.contact.Contact;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that an {@code Address} matches any of the keywords given.
 */
public class EventAddressContainsKeywordsPredicate implements Predicate<Contact> {
    private final List<String> keywords;

    public EventAddressContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Contact contact) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(contact.getAddress().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventAddressContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((EventAddressContainsKeywordsPredicate) other).keywords)); // state check
    }
}
