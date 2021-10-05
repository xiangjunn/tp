package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Name;

class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new seedu.address.model.person.Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new seedu.address.model.person.Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> seedu.address.model.person.Name.isValidName(null));

        // invalid name
        assertFalse(seedu.address.model.person.Name.isValidName("")); // empty string
        assertFalse(seedu.address.model.person.Name.isValidName(" ")); // spaces only
        assertFalse(seedu.address.model.person.Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(seedu.address.model.person.Name.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(seedu.address.model.person.Name.isValidName("group meeting")); // alphabets only
        assertTrue(seedu.address.model.person.Name.isValidName("12345")); // numbers only
        assertTrue(seedu.address.model.person.Name.isValidName("tutorial3")); // alphanumeric characters
        assertTrue(seedu.address.model.person.Name.isValidName("CCA Camp")); // with capital letters
        assertTrue(Name.isValidName("CCA meeting with teachers and friends")); // long names
    }
}
