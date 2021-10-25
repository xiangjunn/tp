package seedu.address.model.contact;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Contact} contains any of the keywords given.
 */
public class ContactNameContainsKeywordsPredicate implements Predicate<Contact> {
    private final List<String> nameKeywords;
    private List<String> phoneKeywords = new ArrayList<>();
    private List<String> emailKeywords = new ArrayList<>();
    private List<String> telegramHandleKeywords = new ArrayList<>();
    private List<String> addressKeywords = new ArrayList<>();
    private List<String> zoomLinkKeywords = new ArrayList<>();
    private List<String> tagKeywords = new ArrayList<>();

    public ContactNameContainsKeywordsPredicate(List<String> nameKeywords) {
        this.nameKeywords = nameKeywords;
    }

    /**
     * empty class constructor if nameKeywords are not provided.
     */
    public ContactNameContainsKeywordsPredicate() {
        nameKeywords = null;
    }

    public void setPhoneKeywords(List<String> phoneKeywords) {
        this.phoneKeywords = phoneKeywords;
    }
    public void setEmailKeywords(List<String> emailKeywords) {
        this.emailKeywords = emailKeywords;
    }
    public void setAddressKeywords(List<String> addressKeywords) {
        this.addressKeywords = addressKeywords;
    }
    public void setTelegramHandleKeyword(List<String> telegramHandleKeywords) {
        this.telegramHandleKeywords = telegramHandleKeywords;
    }
    public void setZoomLinkKeywords(List<String> zoomLinkKeywords) {
        this.zoomLinkKeywords = zoomLinkKeywords;
    }
    public void setTagKeywords(List<String> tagKeywords) {
        this.tagKeywords = tagKeywords;
    }

    @Override
    public boolean test(Contact contact) { // applied Law of Demeter, dont access contact fields' strings
        boolean isMatch = false;
        if (nameKeywords != null) {
            isMatch = contact.nameAnyMatch(nameKeywords);
        }
        return isMatch || contact.phoneAnyMatch(phoneKeywords) || contact.emailAnyMatch(emailKeywords)
                || contact.addressAnyMatch(addressKeywords) || contact.zoomLinkAnyMatch(zoomLinkKeywords)
                || contact.telegramHandleAnyMatch(telegramHandleKeywords) || contact.anyTagsContain(tagKeywords);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContactNameContainsKeywordsPredicate // instanceof handles nulls
                && nameKeywords.equals(((ContactNameContainsKeywordsPredicate) other).nameKeywords)
                && phoneKeywords.equals(((ContactNameContainsKeywordsPredicate) other).phoneKeywords)
                && emailKeywords.equals(((ContactNameContainsKeywordsPredicate) other).emailKeywords)
                && addressKeywords.equals(((ContactNameContainsKeywordsPredicate) other).addressKeywords)
                && telegramHandleKeywords.equals(((ContactNameContainsKeywordsPredicate) other).telegramHandleKeywords)
                && zoomLinkKeywords.equals(((ContactNameContainsKeywordsPredicate) other).zoomLinkKeywords)
                && tagKeywords.equals(((ContactNameContainsKeywordsPredicate) other).tagKeywords)); // state check
    }
}

