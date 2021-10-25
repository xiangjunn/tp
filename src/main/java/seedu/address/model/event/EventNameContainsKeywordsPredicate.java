package seedu.address.model.event;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Event}'s {@code Name} matches any of the keywords given.
 */
public class EventNameContainsKeywordsPredicate implements Predicate<Event> {
    private final List<String> nameKeywords;
    private List<String> startDateTimeKeywords = new ArrayList<>();
    private List<String> endDateTimeKeywords = new ArrayList<>();
    private List<String> descriptionKeywords = new ArrayList<>();
    private List<String> addressKeywords = new ArrayList<>();
    private List<String> zoomLinkKeywords = new ArrayList<>();
    private List<String> tagKeywords = new ArrayList<>();

    public EventNameContainsKeywordsPredicate(List<String> nameKeywords) {
        this.nameKeywords = nameKeywords;
    }

    /**
     * empty class constructor if nameKeywords are not provided.
     */
    public EventNameContainsKeywordsPredicate() {
        nameKeywords = null;
    }

    public void setStartDateTimeKeywords(List<String> startDateTimeKeywords) {
        this.startDateTimeKeywords = startDateTimeKeywords;
    }
    public void setEndDateTimeKeywords(List<String> endDateTimeKeywords) {
        this.endDateTimeKeywords = endDateTimeKeywords;
    }
    public void setAddressKeywords(List<String> addressKeywords) {
        this.addressKeywords = addressKeywords;
    }
    public void setDescriptionKeywords(List<String> descriptionKeywords) {
        this.descriptionKeywords = descriptionKeywords;
    }
    public void setZoomLinkKeywords(List<String> zoomLinkKeywords) {
        this.zoomLinkKeywords = zoomLinkKeywords;
    }
    public void setTagKeywords(List<String> tagKeywords) {
        this.tagKeywords = tagKeywords;
    }

    @Override
    public boolean test(Event event) {
        boolean isMatch = false;
        if (nameKeywords != null) {
            isMatch = event.nameAnyMatch(nameKeywords);
        }
        return isMatch || event.startTimeAnyMatch(startDateTimeKeywords) || event.endTimeAnyMatch(endDateTimeKeywords)
                || event.addressAnyMatch(addressKeywords) || event.descriptionAnyMatch(descriptionKeywords)
                || event.zoomLinkAnyMatch(zoomLinkKeywords) || event.anyTagsContain(tagKeywords);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventNameContainsKeywordsPredicate // instanceof handles nulls
                && nameKeywords.equals(((EventNameContainsKeywordsPredicate) other).nameKeywords)
                && startDateTimeKeywords.equals(((EventNameContainsKeywordsPredicate) other).startDateTimeKeywords)
                && endDateTimeKeywords.equals(((EventNameContainsKeywordsPredicate) other).endDateTimeKeywords)
                && addressKeywords.equals(((EventNameContainsKeywordsPredicate) other).addressKeywords)
                && descriptionKeywords.equals(((EventNameContainsKeywordsPredicate) other).descriptionKeywords)
                && zoomLinkKeywords.equals(((EventNameContainsKeywordsPredicate) other).zoomLinkKeywords)
                && tagKeywords.equals(((EventNameContainsKeywordsPredicate) other).tagKeywords)); // state check
    }
}

