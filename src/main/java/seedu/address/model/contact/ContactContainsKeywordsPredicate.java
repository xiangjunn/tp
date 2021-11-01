package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Contact} contains any of the keywords given.
 */
public class ContactContainsKeywordsPredicate implements Predicate<Contact> {
    private final List<String> nameKeywords;
    private List<String> phoneKeywords = new ArrayList<>();
    private List<String> emailKeywords = new ArrayList<>();
    private List<String> telegramHandleKeywords = new ArrayList<>();
    private List<String> addressKeywords = new ArrayList<>();
    private List<String> zoomLinkKeywords = new ArrayList<>();
    private List<String> tagKeywords = new ArrayList<>();

    /**
     * Creates a {@code ContactContainsKeywordsPredicate} object with the name keywords.
     */
    public ContactContainsKeywordsPredicate(List<String> nameKeywords) {
        requireNonNull(nameKeywords);
        this.nameKeywords = nameKeywords;
    }

    /**
     * Creates a {@code ContactContainsKeywordsPredicate} object with no keywords.
     */
    public ContactContainsKeywordsPredicate() {
        nameKeywords = new ArrayList<>();
    }

    public void setPhoneKeywords(List<String> phoneKeywords) {
        requireNonNull(phoneKeywords);
        this.phoneKeywords = phoneKeywords;
    }

    public void setEmailKeywords(List<String> emailKeywords) {
        requireNonNull(emailKeywords);
        this.emailKeywords = emailKeywords;
    }

    public void setAddressKeywords(List<String> addressKeywords) {
        requireNonNull(addressKeywords);
        this.addressKeywords = addressKeywords;
    }

    public void setTelegramHandleKeyword(List<String> telegramHandleKeywords) {
        requireNonNull(telegramHandleKeywords);
        this.telegramHandleKeywords = telegramHandleKeywords;
    }

    public void setZoomLinkKeywords(List<String> zoomLinkKeywords) {
        requireNonNull(zoomLinkKeywords);
        this.zoomLinkKeywords = zoomLinkKeywords;
    }

    public void setTagKeywords(List<String> tagKeywords) {
        requireNonNull(tagKeywords);
        this.tagKeywords = tagKeywords;
    }

    @Override
    public boolean test(Contact contact) { // applied Law of Demeter, don't access contact fields' strings
        requireNonNull(contact);
        return contact.nameAnyMatch(nameKeywords) || contact.phoneAnyMatch(phoneKeywords)
                || contact.emailAnyMatch(emailKeywords) || contact.addressAnyMatch(addressKeywords)
                || contact.zoomLinkAnyMatch(zoomLinkKeywords) || contact.telegramHandleAnyMatch(telegramHandleKeywords)
                || contact.anyTagsContain(tagKeywords);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContactContainsKeywordsPredicate // instanceof handles nulls
                && nameKeywords.equals(((ContactContainsKeywordsPredicate) other).nameKeywords)
                && phoneKeywords.equals(((ContactContainsKeywordsPredicate) other).phoneKeywords)
                && emailKeywords.equals(((ContactContainsKeywordsPredicate) other).emailKeywords)
                && addressKeywords.equals(((ContactContainsKeywordsPredicate) other).addressKeywords)
                && telegramHandleKeywords.equals(((ContactContainsKeywordsPredicate) other).telegramHandleKeywords)
                && zoomLinkKeywords.equals(((ContactContainsKeywordsPredicate) other).zoomLinkKeywords)
                && tagKeywords.equals(((ContactContainsKeywordsPredicate) other).tagKeywords)); // state check
    }
}

