package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.contact.CAddCommand;
import seedu.address.logic.commands.contact.CClearCommand;
import seedu.address.logic.commands.contact.CDeleteCommand;
import seedu.address.logic.commands.contact.CEditCommand;
import seedu.address.logic.commands.contact.CFindCommand;
import seedu.address.logic.commands.contact.CListCommand;
import seedu.address.logic.commands.event.EClearCommand;
import seedu.address.logic.commands.event.EDeleteCommand;
import seedu.address.logic.commands.event.EFindCommand;
import seedu.address.logic.commands.general.ExitCommand;
import seedu.address.logic.commands.general.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.NameContainsKeywordsPredicate;
import seedu.address.model.event.Event;
import seedu.address.testutil.EditContactDescriptorBuilder;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_cadd() throws Exception {
        Contact contact = new PersonBuilder().build();
        CAddCommand command = (CAddCommand) parser.parseCommand(PersonUtil.getAddCommand(contact));
        assertEquals(new CAddCommand(contact), command);
    }
    
    @Test
    public void parseCommand_eadd() throws Exception {
        Event event = new EventBuilder().build();
        // TODO: 10/13/2021 implement test when eadd is ready 
    }

    @Test
    public void parseCommand_cclear() throws Exception {
        assertTrue(parser.parseCommand(CClearCommand.COMMAND_WORD) instanceof CClearCommand);
        assertTrue(parser.parseCommand(CClearCommand.COMMAND_WORD + " 3") instanceof CClearCommand);
    }
    
    @Test
    public void parseCommand_eclear() throws Exception {
        assertTrue(parser.parseCommand(EClearCommand.COMMAND_WORD) instanceof EClearCommand);
        assertTrue(parser.parseCommand(CClearCommand.COMMAND_WORD + "2") instanceof EClearCommand);
    }

    @Test
    public void parseCommand_cdelete() throws Exception {
        CDeleteCommand command = (CDeleteCommand) parser.parseCommand(
                CDeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new CDeleteCommand(INDEX_FIRST_PERSON), command);
    }
    
    @Test
    public void parseCommand_edelete() throws Exception {
        EDeleteCommand command = (EDeleteCommand) parser.parseCommand(
                EDeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new EDeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_cedit() throws Exception {
        Contact contact = new PersonBuilder().build();
        CEditCommand.EditContactDescriptor descriptor = new EditContactDescriptorBuilder(contact,
            null, false).build();
        CEditCommand command = (CEditCommand) parser.parseCommand(CEditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new CEditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }
    
    @Test
    public void parseCommand_eedit() throws Exception {
        Event event = new EventBuilder().build();
        // TODO: 10/13/2021 implement this test when eedit is completed 
    }

    @Test
    public void parseCommand_cfind() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        CFindCommand command = (CFindCommand) parser.parseCommand(
                CFindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new CFindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }
    
    @Test
    public void parseCommand_efind() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        EFindCommand command = (EFindCommand) parser.parseCommand(
                EFindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new EFindCommand(new seedu.address.model.event.NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_clist() throws Exception {
        assertTrue(parser.parseCommand(CListCommand.COMMAND_WORD) instanceof CListCommand);
        assertTrue(parser.parseCommand(CListCommand.COMMAND_WORD + " 3") instanceof CListCommand);
    }
    
    @Test
    public void parseCommand_elist() throws Exception {
        // TODO: 10/13/2021  implement when elist is ready 
    }
    
    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
