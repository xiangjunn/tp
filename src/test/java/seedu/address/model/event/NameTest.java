package seedu.address.model.event;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }
}
