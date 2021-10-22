package seedu.address.model.contact;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Contact}'s {@code Email} matches any of the keywords given.
 */
public class ContactEmailContainsKeywordsPredicate implements Predicate<Contact> {
    private final List<String> keywords;

    public ContactEmailContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Contact contact) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(contact.getEmail().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContactEmailContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ContactEmailContainsKeywordsPredicate) other).keywords)); // state check
    }

}
