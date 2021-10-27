package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalEvents.CS2100_CONSULTATION;
import static seedu.address.testutil.TypicalEvents.CS2103_MIDTERM;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EventBuilder;

public class EventNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        EventNameContainsKeywordsPredicate firstPredicate =
                new EventNameContainsKeywordsPredicate(firstPredicateKeywordList);
        EventNameContainsKeywordsPredicate secondPredicate =
                new EventNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EventNameContainsKeywordsPredicate firstPredicateCopy =
                new EventNameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different Event -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        EventNameContainsKeywordsPredicate predicate =
                new EventNameContainsKeywordsPredicate(Arrays.asList("CS2103T", "midterms"));
        assertTrue(predicate.test(new EventBuilder().withName("CS2103T midterms").build()));

        // Multiple keywords
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("cs2103t", "midterms"));
        assertTrue(predicate.test(new EventBuilder().withName("cs2103t midterms").build()));

        // Only one matching keyword
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("consultation", "weekly"));
        assertTrue(predicate.test(new EventBuilder().withName("consultation weekly").build()));

        // Mixed-case keywords
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("CONsult", "WeEek"));
        assertTrue(predicate.test(new EventBuilder().withName("CONsult WeEek").build()));

        // Keyword matches multiple fields
        predicate = new EventNameContainsKeywordsPredicate();
        predicate.setTagKeywords(Arrays.asList("SCHOOL", "EXAM"));
        // Both predicate test on CS2103T_MIDTERMS and CS2100_CONSULTATION should return true
        assertEquals(predicate.test(CS2100_CONSULTATION), predicate.test(CS2103_MIDTERM));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EventNameContainsKeywordsPredicate predicate =
                new EventNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new EventBuilder().withName("CS2103T").build()));

        // Non-matching keyword
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new EventBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("cs2222"));
        predicate.setStartDateTimeKeywords(Arrays.asList("11-10-2021"));
        predicate.setEndDateTimeKeywords(Arrays.asList("21-10-2021"));
        predicate.setAddressKeywords(Arrays.asList("12th"));
        predicate.setDescriptionKeywords(Arrays.asList("DUEEEET"));
        assertFalse(predicate.test(new EventBuilder().withName("CS2103T midterms")
                .withStartDateAndTime("01-10-2021 11:10")
                .withEndDateAndTime("01-10-2021 12:10")
                .withAddress("Main Street")
                .withDescription("i can dueeet")
                .build()));
    }

}
