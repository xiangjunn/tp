package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalEvents.CS2100_CONSULTATION;
import static seedu.address.testutil.TypicalEvents.CS2103_MIDTERM_MARKED;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EventBuilder;

public class EventContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        EventContainsKeywordsPredicate firstPredicate =
                new EventContainsKeywordsPredicate(firstPredicateKeywordList);
        EventContainsKeywordsPredicate secondPredicate =
                new EventContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EventContainsKeywordsPredicate firstPredicateCopy =
                new EventContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different Event -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_fieldContainsKeywords_returnsTrue() {
        // One keyword matches name field
        EventContainsKeywordsPredicate predicate =
                new EventContainsKeywordsPredicate(Arrays.asList("CS2103T", "midterms"));
        assertTrue(predicate.test(new EventBuilder().withName("CS2103T midterms").build()));

        // Multiple keywords match name field
        predicate = new EventContainsKeywordsPredicate(Arrays.asList("cs2103t", "midterms"));
        assertTrue(predicate.test(new EventBuilder().withName("cs2103t midterms").build()));

        // Only one matching keyword to name field
        predicate = new EventContainsKeywordsPredicate(Arrays.asList("consultation", "weekly"));
        assertTrue(predicate.test(new EventBuilder().withName("consultation weekly").build()));

        // Mixed-case keywords matching name field
        predicate = new EventContainsKeywordsPredicate(Arrays.asList("CONsult", "WeEek"));
        assertTrue(predicate.test(new EventBuilder().withName("CONsult WeEek").build()));

        // Keyword matches tag field
        predicate = new EventContainsKeywordsPredicate();
        predicate.setTagKeywords(Arrays.asList("SCHOOL", "EXAM"));
        // Both predicate test on CS2103T_MIDTERMS and CS2100_CONSULTATION should return true
        assertEquals(predicate.test(CS2100_CONSULTATION), predicate.test(CS2103_MIDTERM_MARKED));

        // Keywords match address and description, but does not match name
        predicate = new EventContainsKeywordsPredicate(Arrays.asList("cs2222"));
        predicate.setStartDateTimeKeywords(Arrays.asList("11-10-2021"));
        predicate.setEndDateTimeKeywords(Arrays.asList("21-10-2021"));
        predicate.setAddressKeywords(Arrays.asList("street"));
        predicate.setDescriptionKeywords(Arrays.asList("DUEEET"));
        predicate.setZoomLinkKeywords(Arrays.asList("zoom.com/12345678"));
        assertTrue(predicate.test(new EventBuilder().withName("CS2103T midterms")
                .withStartDateAndTime("01-10-2021 11:10")
                .withEndDateAndTime("01-10-2021 12:10")
                .withAddress("Main Street")
                .withDescription("i can dueeet")
                .build()));
    }

    @Test
    public void test_fieldDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EventContainsKeywordsPredicate predicate =
                new EventContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new EventBuilder().withName("CS2103T").build()));

        // Non-matching keyword for name field
        predicate = new EventContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new EventBuilder().withName("Alice Bob").build()));

        // no fields match the keyword
        predicate = new EventContainsKeywordsPredicate(Arrays.asList("cs2222"));
        predicate.setStartDateTimeKeywords(Arrays.asList("11-10-2021"));
        predicate.setEndDateTimeKeywords(Arrays.asList("21-10-2021"));
        predicate.setAddressKeywords(Arrays.asList("overseas"));
        predicate.setDescriptionKeywords(Arrays.asList("DUEEEEEET"));
        predicate.setZoomLinkKeywords(Arrays.asList("zoom.com/12345678"));
        assertFalse(predicate.test(new EventBuilder().withName("CS2103T midterms")
                .withStartDateAndTime("01-10-2021 11:10")
                .withEndDateAndTime("01-10-2021 12:10")
                .withAddress("Main Street")
                .withDescription("i can dueeet")
                .build()));

    }

}
