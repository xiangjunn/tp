package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.contact.CAddCommand;
import seedu.address.logic.commands.contact.CClearCommand;
import seedu.address.logic.commands.contact.CDeleteCommand;
import seedu.address.logic.commands.contact.CEditCommand;
import seedu.address.logic.commands.contact.CFindCommand;
import seedu.address.logic.commands.contact.CListCommand;
import seedu.address.logic.commands.contact.CMarkCommand;
import seedu.address.logic.commands.contact.CUnmarkCommand;
import seedu.address.logic.commands.contact.CViewCommand;
import seedu.address.logic.commands.event.EAddCommand;
import seedu.address.logic.commands.event.EClearCommand;
import seedu.address.logic.commands.event.EDeleteCommand;
import seedu.address.logic.commands.event.EEditCommand;
import seedu.address.logic.commands.event.EFindCommand;
import seedu.address.logic.commands.event.ELinkCommand;
import seedu.address.logic.commands.event.EListCommand;
import seedu.address.logic.commands.event.EMarkCommand;
import seedu.address.logic.commands.event.ESortCommand;
import seedu.address.logic.commands.event.EUnlinkCommand;
import seedu.address.logic.commands.event.EUnmarkCommand;
import seedu.address.logic.commands.event.EViewCommand;
import seedu.address.logic.commands.general.CalendarCommand;
import seedu.address.logic.commands.general.ExitCommand;
import seedu.address.logic.commands.general.HelpCommand;
import seedu.address.logic.commands.general.RedoCommand;
import seedu.address.logic.commands.general.UndoCommand;
import seedu.address.logic.parser.contact.CAddCommandParser;
import seedu.address.logic.parser.contact.CDeleteCommandParser;
import seedu.address.logic.parser.contact.CEditCommandParser;
import seedu.address.logic.parser.contact.CFindCommandParser;
import seedu.address.logic.parser.contact.CListCommandParser;
import seedu.address.logic.parser.contact.CMarkCommandParser;
import seedu.address.logic.parser.contact.CUnmarkCommandParser;
import seedu.address.logic.parser.contact.CViewCommandParser;
import seedu.address.logic.parser.event.EAddCommandParser;
import seedu.address.logic.parser.event.EDeleteCommandParser;
import seedu.address.logic.parser.event.EEditCommandParser;
import seedu.address.logic.parser.event.EFindCommandParser;
import seedu.address.logic.parser.event.ELinkCommandParser;
import seedu.address.logic.parser.event.EListCommandParser;
import seedu.address.logic.parser.event.EMarkCommandParser;
import seedu.address.logic.parser.event.EUnlinkCommandParser;
import seedu.address.logic.parser.event.EUnmarkCommandParser;
import seedu.address.logic.parser.event.EViewCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case CAddCommand.COMMAND_WORD:
            return new CAddCommandParser().parse(arguments);

        case CEditCommand.COMMAND_WORD:
            return new CEditCommandParser().parse(arguments);

        case CDeleteCommand.COMMAND_WORD:
            return new CDeleteCommandParser().parse(arguments);

        case CClearCommand.COMMAND_WORD:
            return new CClearCommand();

        case CFindCommand.COMMAND_WORD:
            return new CFindCommandParser().parse(arguments);

        case CListCommand.COMMAND_WORD:
            return new CListCommandParser().parse(arguments);

        case CViewCommand.COMMAND_WORD:
            return new CViewCommandParser().parse(arguments);

        case CMarkCommand.COMMAND_WORD:
            return new CMarkCommandParser().parse(arguments);

        case CUnmarkCommand.COMMAND_WORD:
            return new CUnmarkCommandParser().parse(arguments);

        case EAddCommand.COMMAND_WORD:
            return new EAddCommandParser().parse(arguments);

        case EEditCommand.COMMAND_WORD:
            return new EEditCommandParser().parse(arguments);

        case EDeleteCommand.COMMAND_WORD:
            return new EDeleteCommandParser().parse(arguments);

        case EClearCommand.COMMAND_WORD:
            return new EClearCommand();

        case EFindCommand.COMMAND_WORD:
            return new EFindCommandParser().parse(arguments);

        case EListCommand.COMMAND_WORD:
            return new EListCommandParser().parse(arguments);

        case ELinkCommand.COMMAND_WORD:
            return new ELinkCommandParser().parse(arguments);

        case EUnlinkCommand.COMMAND_WORD:
            return new EUnlinkCommandParser().parse(arguments);

        case ESortCommand.COMMAND_WORD:
            return new ESortCommand();

        case EViewCommand.COMMAND_WORD:
            return new EViewCommandParser().parse(arguments);

        case EMarkCommand.COMMAND_WORD:
            return new EMarkCommandParser().parse(arguments);

        case EUnmarkCommand.COMMAND_WORD:
            return new EUnmarkCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case CalendarCommand.COMMAND_WORD:
            return new CalendarCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
