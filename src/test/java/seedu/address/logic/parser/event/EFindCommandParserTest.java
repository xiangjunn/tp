package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.event.EFindCommand;
import seedu.address.model.event.EventContainsKeywordsPredicate;

public class EFindCommandParserTest {

    private EFindCommandParser parser = new EFindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, EFindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyPrefix_throwsParseException() {
        // empty start time prefix
        assertParseFailure(parser, " at/", String.format(EFindCommandParser.MESSAGE_MISSING_KEYWORD, "at/"));

        // empty end time prefix
        assertParseFailure(parser, " end/", String.format(EFindCommandParser.MESSAGE_MISSING_KEYWORD, "end/"));

        // empty description prefix
        assertParseFailure(parser, " d/", String.format(EFindCommandParser.MESSAGE_MISSING_KEYWORD, "d/"));

        // empty address prefix
        assertParseFailure(parser, " a/", String.format(EFindCommandParser.MESSAGE_MISSING_KEYWORD, "a/"));

        // empty zoom link prefix
        assertParseFailure(parser, " z/", String.format(EFindCommandParser.MESSAGE_MISSING_KEYWORD, "z/"));

        // empty tag prefix
        assertParseFailure(parser, " t/", String.format(EFindCommandParser.MESSAGE_MISSING_KEYWORD, "t/"));
    }

    @Test
    public void parse_prefixSyntaxButNotAPrefix_returnsEFindCommand() {
        EFindCommand expectedEFindCommand =
            new EFindCommand(new EventContainsKeywordsPredicate(Arrays.asList("w/abc")));
        assertParseSuccess(parser, " w/abc", expectedEFindCommand);
    }

    @Test
    public void parse_validArgsWithNoPrefix_returnsEFindCommand() {
        // no leading and trailing whitespaces
        EFindCommand expectedEFindCommand =
            new EFindCommand(new EventContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedEFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedEFindCommand);
    }

    @Test
    public void parse_validArgsWithStartTimePrefix_returnsEFindCommand() {
        // with one prefix
        EventContainsKeywordsPredicate predicate1 = new EventContainsKeywordsPredicate();
        predicate1.setStartDateTimeKeywords(List.of("2021"));
        EFindCommand expectedEFindCommand1 = new EFindCommand(predicate1);
        assertParseSuccess(parser, " at/2021", expectedEFindCommand1);

        //  with one prefix and two keywords before the prefix
        EventContainsKeywordsPredicate predicate2 = new EventContainsKeywordsPredicate(
            Arrays.asList("Charlie", "Darlie"));
        predicate2.setStartDateTimeKeywords(List.of("31"));
        EFindCommand expectedEFindCommand2 = new EFindCommand(predicate2);
        assertParseSuccess(parser, " Charlie Darlie at/31", expectedEFindCommand2);
    }

    @Test
    public void parse_validArgsWithEndTimePrefix_returnsEFindCommand() {
        // with one prefix
        EventContainsKeywordsPredicate predicate1 = new EventContainsKeywordsPredicate();
        predicate1.setEndDateTimeKeywords(List.of("2021"));
        EFindCommand expectedEFindCommand1 = new EFindCommand(predicate1);
        assertParseSuccess(parser, " end/2021", expectedEFindCommand1);

        //  with one prefix and two keywords before the prefix
        EventContainsKeywordsPredicate predicate2 = new EventContainsKeywordsPredicate(
            Arrays.asList("Charlie", "Darlie"));
        predicate2.setEndDateTimeKeywords(List.of("31"));
        EFindCommand expectedEFindCommand2 = new EFindCommand(predicate2);
        assertParseSuccess(parser, " Charlie Darlie end/31", expectedEFindCommand2);
    }

    @Test
    public void parse_validArgsWithDescriptionPrefix_returnsEFindCommand() {
        // with one prefix
        EventContainsKeywordsPredicate predicate1 = new EventContainsKeywordsPredicate();
        predicate1.setDescriptionKeywords(List.of("summer"));
        EFindCommand expectedEFindCommand1 = new EFindCommand(predicate1);
        assertParseSuccess(parser, " d/summer", expectedEFindCommand1);

        //  with one prefix and two keywords before the prefix
        EventContainsKeywordsPredicate predicate2 = new EventContainsKeywordsPredicate(
            Arrays.asList("Charlie", "Darlie"));
        predicate2.setDescriptionKeywords(List.of("winter"));
        EFindCommand expectedEFindCommand2 = new EFindCommand(predicate2);
        assertParseSuccess(parser, " Charlie Darlie d/winter", expectedEFindCommand2);
    }

    @Test
    public void parse_validArgsWithAddressPrefix_returnsEFindCommand() {
        // with one prefix
        EventContainsKeywordsPredicate predicate1 = new EventContainsKeywordsPredicate();
        predicate1.setAddressKeywords(List.of("sch"));
        EFindCommand expectedEFindCommand1 = new EFindCommand(predicate1);
        assertParseSuccess(parser, " a/sch", expectedEFindCommand1);

        //  with one prefix and two keywords before the prefix
        EventContainsKeywordsPredicate predicate2 = new EventContainsKeywordsPredicate(
            Arrays.asList("Charlie", "Darlie"));
        predicate2.setAddressKeywords(List.of("zoom"));
        EFindCommand expectedEFindCommand2 = new EFindCommand(predicate2);
        assertParseSuccess(parser, " Charlie Darlie a/zoom", expectedEFindCommand2);
    }

    @Test
    public void parse_validArgsWithZoomLinkPrefix_returnsEFindCommand() {
        // with one prefix
        EventContainsKeywordsPredicate predicate1 = new EventContainsKeywordsPredicate();
        predicate1.setZoomLinkKeywords(List.of("sg"));
        EFindCommand expectedEFindCommand1 = new EFindCommand(predicate1);
        assertParseSuccess(parser, " z/sg", expectedEFindCommand1);

        //  with one prefix and two keywords before the prefix
        EventContainsKeywordsPredicate predicate2 = new EventContainsKeywordsPredicate(
            Arrays.asList("Charlie", "Darlie"));
        predicate2.setZoomLinkKeywords(List.of(".com"));
        EFindCommand expectedEFindCommand2 = new EFindCommand(predicate2);
        assertParseSuccess(parser, " Charlie Darlie z/.com", expectedEFindCommand2);
    }

    @Test
    public void parse_validArgsWithTagPrefix_returnsEFindCommand() {
        // with one prefix
        EventContainsKeywordsPredicate predicate1 = new EventContainsKeywordsPredicate();
        predicate1.setTagKeywords(List.of("event"));
        EFindCommand expectedEFindCommand1 = new EFindCommand(predicate1);
        assertParseSuccess(parser, " t/event", expectedEFindCommand1);

        //  with one prefix and two keywords before the prefix
        EventContainsKeywordsPredicate predicate2 = new EventContainsKeywordsPredicate(
            Arrays.asList("Charlie", "Darlie"));
        predicate2.setTagKeywords(List.of("mid"));
        EFindCommand expectedEFindCommand2 = new EFindCommand(predicate2);
        assertParseSuccess(parser, " Charlie Darlie t/mid", expectedEFindCommand2);
    }

    @Test
    public void parse_validArgsWithAllPrefixes_returnsEFindCommand() {
        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate();
        predicate.setStartDateTimeKeywords(List.of("start"));
        predicate.setEndDateTimeKeywords(List.of("end"));
        predicate.setDescriptionKeywords(List.of("description"));
        predicate.setAddressKeywords(List.of("address"));
        predicate.setZoomLinkKeywords(List.of("zoomlink"));
        predicate.setTagKeywords(List.of("tag"));
        EFindCommand expectedEFindCommand = new EFindCommand(predicate);
        assertParseSuccess(parser, " at/start end/end d/description a/address z/zoomlink t/tag",
            expectedEFindCommand);
        // swapping around the prefixes should work as well
        assertParseSuccess(parser, " a/address end/end t/tag z/zoomlink at/start d/description",
            expectedEFindCommand);
    }

    @Test
    public void parse_allPrefixesWithNoWhitespaces_returnsEFindCommand() {
        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate();
        predicate.setStartDateTimeKeywords(List.of("end/d/a/z/t/"));
        EFindCommand expectedEFindCommand = new EFindCommand(predicate);
        assertParseSuccess(parser, " at/end/d/a/z/t/", expectedEFindCommand);
    }
}
