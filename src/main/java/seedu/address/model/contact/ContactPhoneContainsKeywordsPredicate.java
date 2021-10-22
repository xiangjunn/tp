package seedu.address.model.contact;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Contact}'s {@code Phone} matches any of the keywords given.
 */
public class ContactPhoneContainsKeywordsPredicate implements Predicate<Contact> {
    private final List<String> keywords;

    public ContactPhoneContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Contact contact) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(contact.getPhone().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContactPhoneContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ContactPhoneContainsKeywordsPredicate) other).keywords)); // state check
    }

}
