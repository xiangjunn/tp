package seedu.address.logic.commands.event;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TypicalIndexes;

class EUnlinkCommandTest {


    @Test
    void execute() {
        EUnlinkCommand eUnlinkCommand = new EUnlinkCommand(TypicalIndexes.INDEX_FIRST_EVENT,
            Set.of(TypicalIndexes.INDEX_FIRST_PERSON));
    }

    @Test
    void testEquals() {
    }
}
