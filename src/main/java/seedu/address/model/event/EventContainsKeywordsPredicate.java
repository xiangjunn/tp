package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Event}'s {@code Name} matches any of the keywords given.
 */
public class EventContainsKeywordsPredicate implements Predicate<Event> {
    private final List<String> nameKeywords;
    private List<String> startDateTimeKeywords = new ArrayList<>();
    private List<String> endDateTimeKeywords = new ArrayList<>();
    private List<String> descriptionKeywords = new ArrayList<>();
    private List<String> addressKeywords = new ArrayList<>();
    private List<String> zoomLinkKeywords = new ArrayList<>();
    private List<String> tagKeywords = new ArrayList<>();

    /**
     * Creates a {@code EventContainsKeywordsPredicate} object with the name keywords.
     */
    public EventContainsKeywordsPredicate(List<String> nameKeywords) {
        requireNonNull(nameKeywords);
        this.nameKeywords = nameKeywords;
    }

    /**
     * Creates a {@code EventContainsKeywordsPredicate} object with no keywords.
     */
    public EventContainsKeywordsPredicate() {
        nameKeywords = new ArrayList<>();
    }

    public void setStartDateTimeKeywords(List<String> startDateTimeKeywords) {
        requireNonNull(startDateTimeKeywords);
        this.startDateTimeKeywords = startDateTimeKeywords;
    }
    public void setEndDateTimeKeywords(List<String> endDateTimeKeywords) {
        requireNonNull(endDateTimeKeywords);
        this.endDateTimeKeywords = endDateTimeKeywords;
    }
    public void setAddressKeywords(List<String> addressKeywords) {
        requireNonNull(addressKeywords);
        this.addressKeywords = addressKeywords;
    }
    public void setDescriptionKeywords(List<String> descriptionKeywords) {
        requireNonNull(descriptionKeywords);
        this.descriptionKeywords = descriptionKeywords;
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
    public boolean test(Event event) {
        requireNonNull(event);
        return event.nameAnyMatch(nameKeywords) || event.startTimeAnyMatch(startDateTimeKeywords)
                || event.endTimeAnyMatch(endDateTimeKeywords) || event.addressAnyMatch(addressKeywords)
                || event.descriptionAnyMatch(descriptionKeywords) || event.zoomLinkAnyMatch(zoomLinkKeywords)
                || event.anyTagsContain(tagKeywords);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventContainsKeywordsPredicate // instanceof handles nulls
                && nameKeywords.equals(((EventContainsKeywordsPredicate) other).nameKeywords)
                && startDateTimeKeywords.equals(((EventContainsKeywordsPredicate) other).startDateTimeKeywords)
                && endDateTimeKeywords.equals(((EventContainsKeywordsPredicate) other).endDateTimeKeywords)
                && addressKeywords.equals(((EventContainsKeywordsPredicate) other).addressKeywords)
                && descriptionKeywords.equals(((EventContainsKeywordsPredicate) other).descriptionKeywords)
                && zoomLinkKeywords.equals(((EventContainsKeywordsPredicate) other).zoomLinkKeywords)
                && tagKeywords.equals(((EventContainsKeywordsPredicate) other).tagKeywords)); // state check
    }
}

