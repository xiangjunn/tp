package seedu.address.logic.parser.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ZOOM;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.contact.CEditCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new CEditCommand object
 */
public class CEditCommandParser implements Parser<CEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CEditCommand
     * and returns an CEditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                    PREFIX_ZOOM, PREFIX_TELEGRAM, PREFIX_TAG, PREFIX_DELETE_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CEditCommand.MESSAGE_USAGE), pe);
        }

        CEditCommand.EditContactDescriptor editContactDescriptor = new CEditCommand.EditContactDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editContactDescriptor.setName(ParserUtil.parseContactName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editContactDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editContactDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editContactDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_ZOOM).isPresent()) {
            editContactDescriptor.setZoomLink(ParserUtil.parseZoomLink(argMultimap.getValue(PREFIX_ZOOM).get()));
        }
        if (argMultimap.getValue(PREFIX_TELEGRAM).isPresent()) {
            editContactDescriptor.setTelegramHandle(
                ParserUtil.parseTelegram(argMultimap.getValue(PREFIX_TELEGRAM).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editContactDescriptor::setTags);

        // Check for delete all
        if (argMultimap.getAllValues(PREFIX_DELETE_TAG).stream().anyMatch(arg -> arg.equals("*"))) {
            editContactDescriptor.setShouldDeleteAllTags(true);
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_DELETE_TAG)).ifPresent(editContactDescriptor::setTagsToDelete);

        if (!editContactDescriptor.isAnyFieldEdited()) {
            throw new ParseException(CEditCommand.MESSAGE_NOT_EDITED);
        }

        return new CEditCommand(index, editContactDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain an asterisk, it will be ignored.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        // To handle case for dt/*
        tags.remove("*");
        return Optional.of(ParserUtil.parseTags(tags));
    }

}
