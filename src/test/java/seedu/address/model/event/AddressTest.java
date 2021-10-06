package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class AddressTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Address(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new seedu.address.model.event.Address(invalidAddress));
    }

    @Test
    void isValidAddress() {
        //null address
        assertThrows(NullPointerException.class, () -> Address.isValidAddress(null));

        // invalid addresses
        assertFalse(seedu.address.model.person.Address.isValidAddress("")); // empty string
        assertFalse(seedu.address.model.person.Address.isValidAddress(" ")); // spaces only

        // valid addresses
        assertTrue(seedu.address.model.person.Address.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(seedu.address.model.person.Address.isValidAddress("-")); // one character
        assertTrue(seedu.address.model.person.Address.isValidAddress("Leng Inc; 1234 Market St; "
                + "San Francisco CA 2349879; USA")); // long address
    }
}
