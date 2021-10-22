package seedu.address.logic.commands.contact;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.contact.ContactAddressContainsKeywordsPredicate;
import seedu.address.model.contact.ContactNameContainsKeywordsPredicate;
import seedu.address.model.contact.ContactEmailContainsKeywordsPredicate;
import seedu.address.model.contact.ContactPhoneContainsKeywordsPredicate;
import seedu.address.model.contact.ContactTelegramHandleContainsKeywordsPredicate;
import seedu.address.model.contact.ContactZoomLinkContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class CFindCommand extends Command {

    public static final String COMMAND_WORD = "cfind";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all contacts whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final ContactNameContainsKeywordsPredicate namePredicate;
    private final ContactEmailContainsKeywordsPredicate emailPredicate;
    private final ContactPhoneContainsKeywordsPredicate phonePredicate;
    private final ContactTelegramHandleContainsKeywordsPredicate telegramHandlePredicate;
    private final ContactAddressContainsKeywordsPredicate addressPredicate;
    private final ContactZoomLinkContainsKeywordsPredicate zoomPredicate;

    public CFindCommand(ContactNameContainsKeywordsPredicate namePredicate,
                        ContactEmailContainsKeywordsPredicate emailPredicate,
                        ContactPhoneContainsKeywordsPredicate phonePredicate,
                        ContactTelegramHandleContainsKeywordsPredicate telegramHandlePredicate,
                        ContactAddressContainsKeywordsPredicate addressPredicate,
                        ContactZoomLinkContainsKeywordsPredicate zoomPredicate) {
        this.namePredicate = namePredicate;
        this.emailPredicate = emailPredicate;
        this.phonePredicate = phonePredicate;
        this.telegramHandlePredicate = telegramHandlePredicate;
        this.addressPredicate = addressPredicate;
        this.zoomPredicate = zoomPredicate;

    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredContactList(namePredicate, emailPredicate, phonePredicate, );
        return new CommandResult(
                String.format(Messages.MESSAGE_CONTACTS_LISTED_OVERVIEW, model.getFilteredContactList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CFindCommand // instanceof handles nulls
                && namePredicate.equals(((CFindCommand) other).namePredicate) // state check
                && phonePredicate.equals(((CFindCommand) other).phonePredicate)
                && emailPredicate.equals(((CFindCommand) other).emailPredicate)
                && telegramHandlePredicate.equals(((CFindCommand) other).telegramHandlePredicate)
                && addressPredicate.equals(((CFindCommand) other).addressPredicate)
                && zoomPredicate.equals(((CFindCommand) other).zoomPredicate));
    }
}
