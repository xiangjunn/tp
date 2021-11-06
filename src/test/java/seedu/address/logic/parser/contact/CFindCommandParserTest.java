package seedu.address.logic.parser.contact;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.contact.CFindCommand;
import seedu.address.model.contact.ContactContainsKeywordsPredicate;

public class CFindCommandParserTest {

    private CFindCommandParser parser = new CFindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, CFindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyPrefix_throwsParseException() {
        // empty email prefix
        assertParseFailure(parser, " e/", String.format(CFindCommandParser.MESSAGE_MISSING_KEYWORD, "e/"));

        // empty address prefix
        assertParseFailure(parser, " a/", String.format(CFindCommandParser.MESSAGE_MISSING_KEYWORD, "a/"));

        // empty phone prefix
        assertParseFailure(parser, " p/", String.format(CFindCommandParser.MESSAGE_MISSING_KEYWORD, "p/"));

        // empty zoom link prefix
        assertParseFailure(parser, " z/", String.format(CFindCommandParser.MESSAGE_MISSING_KEYWORD, "z/"));

        // empty telegram handle prefix
        assertParseFailure(parser, " th/", String.format(CFindCommandParser.MESSAGE_MISSING_KEYWORD, "th/"));

        // empty tag prefix
        assertParseFailure(parser, " t/", String.format(CFindCommandParser.MESSAGE_MISSING_KEYWORD, "t/"));
    }

    @Test
    public void parse_prefixSyntaxButNotAPrefix_returnsCFindCommand() {
        CFindCommand expectedCFindCommand =
            new CFindCommand(new ContactContainsKeywordsPredicate(Arrays.asList("w/abc")));
        assertParseSuccess(parser, " w/abc", expectedCFindCommand);
    }

    @Test
    public void parse_validArgsWithNoPrefix_returnsCFindCommand() {
        // no leading and trailing whitespaces
        CFindCommand expectedCFindCommand =
                new CFindCommand(new ContactContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedCFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedCFindCommand);
    }

    @Test
    public void parse_validArgsWithEmailPrefix_returnsCFindCommand() {
        // with one prefix
        ContactContainsKeywordsPredicate predicate1 = new ContactContainsKeywordsPredicate();
        predicate1.setEmailKeywords(List.of("Alice", "Bob"));
        CFindCommand expectedCFindCommand1 = new CFindCommand(predicate1);
        assertParseSuccess(parser, " e/Alice Bob", expectedCFindCommand1);

        //  with one prefix and two keywords before the prefix
        ContactContainsKeywordsPredicate predicate2 = new ContactContainsKeywordsPredicate(
                Arrays.asList("Charlie", "Darlie"));
        predicate2.setEmailKeywords(List.of("Alice", "Bob"));
        CFindCommand expectedCFindCommand2 = new CFindCommand(predicate2);
        assertParseSuccess(parser, " Charlie Darlie e/Alice Bob", expectedCFindCommand2);
    }

    @Test
    public void parse_validArgsWithAddressPrefix_returnsCFindCommand() {
        // with one prefix
        ContactContainsKeywordsPredicate predicate1 = new ContactContainsKeywordsPredicate();
        predicate1.setAddressKeywords(List.of("jurong"));
        CFindCommand expectedCFindCommand1 = new CFindCommand(predicate1);
        assertParseSuccess(parser, " a/jurong", expectedCFindCommand1);

        //  with one prefix and two keywords before the prefix
        ContactContainsKeywordsPredicate predicate2 = new ContactContainsKeywordsPredicate(
            Arrays.asList("Charlie", "Darlie"));
        predicate2.setAddressKeywords(List.of("changi"));
        CFindCommand expectedCFindCommand2 = new CFindCommand(predicate2);
        assertParseSuccess(parser, " Charlie Darlie a/changi", expectedCFindCommand2);
    }

    @Test
    public void parse_validArgsWithPhonePrefix_returnsCFindCommand() {
        // with one prefix
        ContactContainsKeywordsPredicate predicate1 = new ContactContainsKeywordsPredicate();
        predicate1.setPhoneKeywords(List.of("123"));
        CFindCommand expectedCFindCommand1 = new CFindCommand(predicate1);
        assertParseSuccess(parser, " p/123", expectedCFindCommand1);

        //  with one prefix and two keywords before the prefix
        ContactContainsKeywordsPredicate predicate2 = new ContactContainsKeywordsPredicate(
            Arrays.asList("Charlie", "Dar"));
        predicate2.setPhoneKeywords(List.of("999"));
        CFindCommand expectedCFindCommand2 = new CFindCommand(predicate2);
        assertParseSuccess(parser, " Charlie Dar p/999", expectedCFindCommand2);
    }

    @Test
    public void parse_validArgsWithZoomLinkPrefix_returnsCFindCommand() {
        // with one prefix
        ContactContainsKeywordsPredicate predicate1 = new ContactContainsKeywordsPredicate();
        predicate1.setZoomLinkKeywords(List.of("nus"));
        CFindCommand expectedCFindCommand1 = new CFindCommand(predicate1);
        assertParseSuccess(parser, " z/nus", expectedCFindCommand1);

        //  with one prefix and two keywords before the prefix
        ContactContainsKeywordsPredicate predicate2 = new ContactContainsKeywordsPredicate(
            Arrays.asList("Charlie", "Darlie"));
        predicate2.setZoomLinkKeywords(List.of("zoom"));
        CFindCommand expectedCFindCommand2 = new CFindCommand(predicate2);
        assertParseSuccess(parser, " Charlie Darlie z/zoom", expectedCFindCommand2);
    }

    @Test
    public void parse_validArgsWithTelegramHandlePrefix_returnsCFindCommand() {
        // with one prefix
        ContactContainsKeywordsPredicate predicate1 = new ContactContainsKeywordsPredicate();
        predicate1.setTelegramHandleKeyword(List.of("Alice", "Bob"));
        CFindCommand expectedCFindCommand1 = new CFindCommand(predicate1);
        assertParseSuccess(parser, " th/Alice Bob", expectedCFindCommand1);

        //  with one prefix and two keywords before the prefix
        ContactContainsKeywordsPredicate predicate2 = new ContactContainsKeywordsPredicate(
            Arrays.asList("Charlie", "Darlie"));
        predicate2.setTelegramHandleKeyword(List.of("Alice", "Bob"));
        CFindCommand expectedCFindCommand2 = new CFindCommand(predicate2);
        assertParseSuccess(parser, " Charlie Darlie th/Alice Bob", expectedCFindCommand2);
    }

    @Test
    public void parse_validArgsWithTagPrefix_returnsCFindCommand() {
        // with one prefix
        ContactContainsKeywordsPredicate predicate1 = new ContactContainsKeywordsPredicate();
        predicate1.setTagKeywords(List.of("friends", "tut"));
        CFindCommand expectedCFindCommand1 = new CFindCommand(predicate1);
        assertParseSuccess(parser, " t/friends tut", expectedCFindCommand1);

        //  with one prefix and two keywords before the prefix
        ContactContainsKeywordsPredicate predicate2 = new ContactContainsKeywordsPredicate(
            Arrays.asList("Charlie", "Darlie"));
        predicate2.setTagKeywords(List.of("Ali", "Bo"));
        CFindCommand expectedCFindCommand2 = new CFindCommand(predicate2);
        assertParseSuccess(parser, " Charlie Darlie t/Ali Bo", expectedCFindCommand2);
    }

    @Test
    public void parse_validArgsWithAllPrefixes_returnsCFindCommand() {
        ContactContainsKeywordsPredicate predicate = new ContactContainsKeywordsPredicate();
        predicate.setEmailKeywords(List.of("email"));
        predicate.setAddressKeywords(List.of("address"));
        predicate.setPhoneKeywords(List.of("phone"));
        predicate.setZoomLinkKeywords(List.of("zoomlink"));
        predicate.setTelegramHandleKeyword(List.of("telegramhandle"));
        predicate.setTagKeywords(List.of("tag"));
        CFindCommand expectedCFindCommand = new CFindCommand(predicate);
        assertParseSuccess(parser, " e/email a/address p/phone z/zoomlink th/telegramhandle t/tag",
            expectedCFindCommand);
        // swapping around the prefixes should work as well
        assertParseSuccess(parser, " a/address p/phone t/tag z/zoomlink e/email th/telegramhandle",
            expectedCFindCommand);
    }

    @Test
    public void parse_allPrefixesWithNoWhitespaces_returnsCFindCommand() {
        ContactContainsKeywordsPredicate predicate = new ContactContainsKeywordsPredicate();
        predicate.setEmailKeywords(List.of("a/p/z/th/t/"));
        CFindCommand expectedCFindCommand = new CFindCommand(predicate);
        assertParseSuccess(parser, " e/a/p/z/th/t/", expectedCFindCommand);
    }
}
