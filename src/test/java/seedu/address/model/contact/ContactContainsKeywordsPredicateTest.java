package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalContacts.ALICE_MARKED;
import static seedu.address.testutil.TypicalContacts.FIONA;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ContactBuilder;


public class ContactContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ContactContainsKeywordsPredicate firstPredicate =
                new ContactContainsKeywordsPredicate(firstPredicateKeywordList);
        ContactContainsKeywordsPredicate secondPredicate =
                new ContactContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ContactContainsKeywordsPredicate firstPredicateCopy =
                new ContactContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different contact -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_fieldContainsKeywords_returnsTrue() {
        // One keyword matches name field
        ContactContainsKeywordsPredicate predicate =
                new ContactContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new ContactBuilder().withName("Alice Bob").build()));

        // Multiple keywords match name field
        predicate = new ContactContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new ContactBuilder().withName("Alice Bob").build()));

        // Only one matching keyword to name field
        predicate = new ContactContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new ContactBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords match name field
        predicate = new ContactContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new ContactBuilder().withName("Alice Bob").build()));

        // Keyword matches address field
        predicate = new ContactContainsKeywordsPredicate();
        predicate.setAddressKeywords(Arrays.asList("JURONG", "TOK"));
        // Both predicate test on ALICE and BENSON should return true
        assertEquals(predicate.test(ALICE_MARKED), predicate.test(FIONA));

        // keyword match phone number, address and one tag
        predicate = new ContactContainsKeywordsPredicate(Arrays.asList("Alex"));
        predicate.setPhoneKeywords(Arrays.asList("12345"));
        predicate.setEmailKeywords(Arrays.asList("alex@email.com"));
        predicate.setAddressKeywords(Arrays.asList("MAIN"));
        predicate.setTagKeywords(Arrays.asList("smart", "cca", "friend"));
        predicate.setTelegramHandleKeyword(Arrays.asList("alexx"));
        predicate.setZoomLinkKeywords(Arrays.asList("meeting.com/123456"));
        assertTrue(predicate.test(new ContactBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withTags("friends").build()));

    }

    @Test
    public void test_fieldDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ContactContainsKeywordsPredicate predicate =
                new ContactContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ContactBuilder().withName("Alice").build()));

        // Non-matching keyword to name field
        predicate = new ContactContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new ContactBuilder().withName("Alice Bob").build()));

        // no field match the keywords
        predicate = new ContactContainsKeywordsPredicate(Arrays.asList("22345"));
        predicate.setEmailKeywords(Arrays.asList("alex@email.com"));
        predicate.setTagKeywords(Arrays.asList("smart", "cca"));
        assertFalse(predicate.test(new ContactBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withTags("friends").build()));
    }
}
