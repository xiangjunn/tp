package seedu.address.logic.commands.event;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalEvents;
import seedu.address.testutil.TypicalIndexes;

class ELinkCommandTest {

    private AddressBook sampleAddressBook = TypicalEvents.getTypicalAddressBook();
    @Test
    public void execute() {
        ELinkCommand eLinkCommand = new ELinkCommand(TypicalIndexes.INDEX_FIRST_EVENT,
            Set.of(TypicalIndexes.INDEX_FIRST_PERSON));
    }

    @Test
    public void testEquals() {
    }
}
