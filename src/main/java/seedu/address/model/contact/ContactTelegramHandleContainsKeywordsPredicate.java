package seedu.address.model.contact;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Contact}'s {@code TelegramHandle} matches any of the keywords given.
 */
public class ContactTelegramHandleContainsKeywordsPredicate implements Predicate<Contact> {
    private final List<String> keywords;

    public ContactTelegramHandleContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Contact contact) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(contact.getTelegramHandle().handle, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContactTelegramHandleContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ContactTelegramHandleContainsKeywordsPredicate) other).keywords)); // state check
    }

}