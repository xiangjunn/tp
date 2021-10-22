package seedu.address.model.contact;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Contact}'s {@code ZoomLink} matches any of the keywords given.
 */
public class ContactZoomLinkContainsKeywordsPredicate implements Predicate<Contact> {
    private final List<String> keywords;

    public ContactZoomLinkContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Contact contact) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(contact.getZoomLink().link, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContactZoomLinkContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ContactZoomLinkContainsKeywordsPredicate) other).keywords)); // state check
    }

}