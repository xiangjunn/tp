package seedu.address.logic.commands.general;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ZOOM;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.contact.CEditCommand.EditContactDescriptor;
import seedu.address.logic.commands.event.EEditCommand.EditEventDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactContainsKeywordsPredicate;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventContainsKeywordsPredicate;
import seedu.address.testutil.EditContactDescriptorBuilder;
import seedu.address.testutil.EditEventDescriptorBuilder;


/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    //common
    public static final String EMPTY_PREFIX_ADDRESS = " " + PREFIX_ADDRESS;
    public static final String EMPTY_PREFIX_ZOOM = " " + PREFIX_ZOOM;
    public static final String EMPTY_PREFIX_TAG = " " + PREFIX_TAG;
    public static final String EMPTY_PREFIX_CONTACT = " " + PREFIX_CONTACT;
    public static final String VALID_INDEX_ONE = "1";
    public static final String VALID_INDEX_TWO = "2";
    public static final String INVALID_INDEX = "0";

    //for contacts
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TELEGRAM_AMY = "amyBeeBee";
    public static final String VALID_TELEGRAM_BOB = "Bobby";
    public static final String VALID_ZOOM_AMY = "https://nus-sg.zoom.us/j/0123456789?pwd=ABCDEFG";
    public static final String VALID_ZOOM_BOB = "https://nus-sg.zoom.us/j/0123456789?pwd=ABCDEFH";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TELEGRAM_DESC_AMY = " " + PREFIX_TELEGRAM + VALID_TELEGRAM_AMY;
    public static final String TELEGRAM_DESC_BOB = " " + PREFIX_TELEGRAM + VALID_TELEGRAM_BOB;
    public static final String ZOOM_DESC_AMY = " " + PREFIX_ZOOM + VALID_ZOOM_AMY;
    public static final String ZOOM_DESC_BOB = " " + PREFIX_ZOOM + VALID_ZOOM_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String TAG_DESC_DELETEALL = " " + PREFIX_DELETE_TAG + "*";
    public static final String TAG_DESC_DELETEFRIEND = " " + PREFIX_DELETE_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_DELETEHUSBAND = " " + PREFIX_DELETE_TAG + VALID_TAG_HUSBAND;

    public static final String EMPTY_PREFIX_PHONE = " " + PREFIX_PHONE;
    public static final String EMPTY_PREFIX_EMAIL = " " + PREFIX_EMAIL;
    public static final String EMPTY_PREFIX_TELEGRAM = " " + PREFIX_TELEGRAM;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TELEGRAM_DESC = " " + PREFIX_TELEGRAM + "2103"; // Minimal 5 characters allowed
    public static final String INVALID_ZOOM_DESC = " " + PREFIX_ZOOM + ""; // empty url not allowed
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditContactDescriptor DESC_AMY;
    public static final EditContactDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditContactDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withZoomLink(VALID_ZOOM_AMY).withTelegram(VALID_TELEGRAM_AMY).withTags(VALID_TAG_FRIEND)
                .withDeleteAllTags(true).build();
        DESC_BOB = new EditContactDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withZoomLink(VALID_ZOOM_BOB).withTelegram(VALID_TELEGRAM_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).withDeleteAllTags(true).build();
    }

    //for events
    public static final String VALID_NAME_TUTORIAL = "CS2103T tutorial";
    public static final String VALID_NAME_EXAM = "CS2100 Midterms";
    public static final String VALID_START_DATE_TIME_TUTORIAL = "17-10-2021 15:00";
    public static final String VALID_END_DATE_TIME_TUTORIAL = "17-10-2021 16:00";
    public static final String VALID_START_DATE_TIME_EXAM = "20-10-2021 09:00";
    public static final String VALID_END_DATE_TIME_EXAM = "20-10-2021 11:00";
    public static final String VALID_DESCRIPTION_TUTORIAL = "Topics: Drawing UML diagrams";
    public static final String VALID_DESCRIPTION_EXAM = "I'm very unprepared";
    public static final String VALID_ADDRESS_TUTORIAL = "2-11 COM2";
    public static final String VALID_ADDRESS_EXAM = "Zoom";
    public static final String VALID_ZOOM_TUTORIAL = "https://nus-sg.zoom.us/j/0123456789?pwd=ABCDEFG";
    public static final String VALID_ZOOM_EXAM = "nus-sg.edu/123%a";
    public static final String VALID_TAG_COOL = "cool";
    public static final String VALID_TAG_EXAMS = "exams";

    public static final String NAME_DESC_TUTORIAL = " " + PREFIX_NAME + VALID_NAME_TUTORIAL;
    public static final String NAME_DESC_EXAM = " " + PREFIX_NAME + VALID_NAME_EXAM;
    public static final String START_DATE_TIME_DESC_TUTORIAL = " " + PREFIX_START_TIME + VALID_START_DATE_TIME_TUTORIAL;
    public static final String START_DATE_TIME_DESC_EXAM = " " + PREFIX_START_TIME + VALID_START_DATE_TIME_EXAM;
    public static final String END_DATE_TIME_DESC_TUTORIAL = " " + PREFIX_END_TIME + VALID_END_DATE_TIME_TUTORIAL;
    public static final String END_DATE_TIME_DESC_EXAM = " " + PREFIX_END_TIME + VALID_END_DATE_TIME_EXAM;
    public static final String DESCRIPTION_DESC_TUTORIAL = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_TUTORIAL;
    public static final String DESCRIPTION_DESC_EXAM = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_EXAM;
    public static final String ADDRESS_DESC_TUTORIAL = " " + PREFIX_ADDRESS + VALID_ADDRESS_TUTORIAL;
    public static final String ADDRESS_DESC_EXAM = " " + PREFIX_ADDRESS + VALID_ADDRESS_EXAM;
    public static final String ZOOM_DESC_TUTORIAL = " " + PREFIX_ZOOM + VALID_ZOOM_TUTORIAL;
    public static final String ZOOM_DESC_EXAM = " " + PREFIX_ZOOM + VALID_ZOOM_EXAM;
    public static final String TAG_DESC_COOL = " " + PREFIX_TAG + VALID_TAG_COOL;
    public static final String TAG_DESC_EXAMS = " " + PREFIX_TAG + VALID_TAG_EXAMS;
    public static final String DELETE_TAG_DESC_EXAMS = " " + PREFIX_DELETE_TAG + VALID_TAG_EXAMS;
    public static final String DELETE_TAG_DESC_COOL = " " + PREFIX_DELETE_TAG + VALID_TAG_COOL;

    public static final String EMPTY_PREFIX_START_DATE_TIME = " " + PREFIX_START_TIME;
    public static final String EMPTY_PREFIX_END_DATE_TIME = " " + PREFIX_END_TIME;
    public static final String EMPTY_PREFIX_DESCRIPTION = " " + PREFIX_DESCRIPTION;

    public static final String INVALID_EVENT_NAME_DESC = " " + PREFIX_NAME + "Hackathon&"; // '&' not allowed in names
    public static final String INVALID_START_DATE_TIME_DESC = " " + PREFIX_START_TIME + "911a";
    // only takes in dd-MM-yyy HH:mm format
    public static final String INVALID_END_DATE_TIME_DESC = " " + PREFIX_END_TIME + "bob!yahoo";
    // only takes in dd-MM-yyy HH:mm format


    public static final EditEventDescriptor DESC_TUTORIAL = new EditEventDescriptorBuilder()
                .withName(VALID_NAME_TUTORIAL).withStartDateTime(VALID_START_DATE_TIME_TUTORIAL)
                .withEndDateTime(VALID_END_DATE_TIME_TUTORIAL).withDescription(VALID_DESCRIPTION_TUTORIAL)
                .withAddress(VALID_ADDRESS_TUTORIAL).withZoomLink(VALID_ZOOM_TUTORIAL).withTags(VALID_TAG_COOL)
                .withDeleteAllTags(true).build();
    public static final EditEventDescriptor DESC_EXAM = new EditEventDescriptorBuilder().withName(VALID_NAME_EXAM)
                .withStartDateTime(VALID_START_DATE_TIME_EXAM).withEndDateTime(VALID_END_DATE_TIME_EXAM)
                .withDescription(VALID_DESCRIPTION_EXAM).withAddress(VALID_ADDRESS_EXAM)
                .withZoomLink(VALID_ZOOM_EXAM).withTags(VALID_TAG_EXAMS, VALID_TAG_COOL)
                .withDeleteAllTags(true).build();



    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered contact list and selected contact in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Contact> expectedFilteredList = new ArrayList<>(actualModel.getFilteredContactList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredContactList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the contact at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showContactAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredContactList().size());

        Contact contact = model.getFilteredContactList().get(targetIndex.getZeroBased());
        final String[] splitName = contact.getName().fullName.split("\\s+");
        model.updateFilteredContactList(new ContactContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredContactList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the event at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showEventAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredEventList().size());

        Event event = model.getFilteredEventList().get(targetIndex.getZeroBased());
        final String[] splitName = event.getName().fullName.split("\\s+");
        model.updateFilteredEventList(new EventContainsKeywordsPredicate(
                Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredEventList().size());
    }
}
