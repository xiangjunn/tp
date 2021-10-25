package seedu.address.logic.parser.event;

import static seedu.address.logic.commands.general.CommandTestUtil.EMPTY_PREFIX_CONTACT;
import static seedu.address.logic.commands.general.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_INDEX_ONE;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_INDEX_TWO;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.EUnlinkCommand;

class EUnlinkCommandParserTest {
    private EUnlinkCommandParser parser = new EUnlinkCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // whitespace only preamble
        Set<Index> contactIndexes = new HashSet<>();
        contactIndexes.add(INDEX_FIRST_PERSON);
        contactIndexes.add(INDEX_SECOND_PERSON);
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_INDEX_ONE + EMPTY_PREFIX_CONTACT
                + VALID_INDEX_ONE + EMPTY_PREFIX_CONTACT + VALID_INDEX_TWO,
            new EUnlinkCommand(INDEX_FIRST_EVENT, contactIndexes));
    }
}
