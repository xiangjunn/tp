package seedu.address.logic;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.general.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.general.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.general.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.general.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.general.CommandTestUtil.TELEGRAM_DESC_AMY;
import static seedu.address.logic.commands.general.CommandTestUtil.ZOOM_DESC_AMY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.AMY;
import static seedu.address.testutil.TypicalContacts.BOB;
import static seedu.address.testutil.TypicalEvents.INTERVIEW;
import static seedu.address.testutil.TypicalEvents.TEAM_MEETING;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ModelStub;
import seedu.address.logic.commands.contact.CAddCommand;
import seedu.address.logic.commands.contact.CListCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.general.CalendarCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactDisplaySetting;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDisplaySetting;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.ContactBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;
    private StorageManager storage;

    @BeforeEach
    public void setUp() {
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "cdelete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validUndoableCommand_success() throws Exception {
        String cListCommand = CListCommand.COMMAND_WORD;
        assertCommandSuccess(cListCommand, CListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_validNotUndoableCommand_success() throws Exception {
        String calendarCommand = CalendarCommand.COMMAND_WORD;
        assertCommandSuccess(calendarCommand, CalendarCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonAddressBookIoExceptionThrowingStub
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionAddressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = CAddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + TELEGRAM_DESC_AMY + ZOOM_DESC_AMY;
        Contact expectedContact = new ContactBuilder(AMY).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addContact(expectedContact);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredContactList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredContactList().remove(0));
    }

    @Test
    public void getFilteredEventList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredEventList().remove(0));
    }

    @Test
    public void test_getAddressBook() {
        Model newModel = new ModelStubWithAddressBook(new AddressBook());
        Logic newLogic = new LogicManager(newModel, storage);
        assertEquals(new AddressBook(), newLogic.getAddressBook());
        AddressBook updatedAddressBook = new AddressBook();
        updatedAddressBook.addContact(AMY);
        newModel.setAddressBook(updatedAddressBook);
        assertEquals(newModel.getAddressBook(), newLogic.getAddressBook());
    }

    @Test
    public void test_getAddressBookFilePath() {
        assertEquals(new UserPrefs().getAddressBookFilePath(), logic.getAddressBookFilePath());
    }

    @Test
    public void test_getGuiSettings() {
        Model newModel = new ModelStubWithGuiSettings(new GuiSettings());
        Logic newLogic = new LogicManager(newModel, storage);
        assertEquals(new GuiSettings(), newLogic.getGuiSettings());
        GuiSettings settings = new GuiSettings(1, 1, 1, 1);
        newModel.setGuiSettings(settings);
        assertEquals(newModel.getGuiSettings(), newLogic.getGuiSettings());
    }

    @Test
    public void test_getContactDisplaySetting() {
        assertEquals(ContactDisplaySetting.DEFAULT_SETTING, logic.getContactDisplaySetting());
        ContactDisplaySetting setting = new ContactDisplaySetting(true, true, true, false, false, false);
        model.setContactDisplaySetting(setting);
        assertEquals(setting, logic.getContactDisplaySetting());
    }

    @Test
    public void test_getEventDisplaySetting() {
        assertEquals(EventDisplaySetting.DEFAULT_SETTING, logic.getEventDisplaySetting());
        EventDisplaySetting setting = new EventDisplaySetting(true, true, true, false, false, false);
        model.setEventDisplaySetting(setting);
        assertEquals(setting, logic.getEventDisplaySetting());
    }

    @Test
    public void test_setGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 1, 1, 1);
        assertNotEquals(guiSettings, logic.getGuiSettings());
        logic.setGuiSettings(guiSettings);
        assertEquals(guiSettings, logic.getGuiSettings());
    }

    @Test
    public void test_filterContactsWithLinksToEvent() {
        ModelStubWithPredicate newModel = new ModelStubWithPredicate();
        Logic newLogic = new LogicManager(newModel, storage);
        Event event = INTERVIEW.linkTo(AMY);
        Contact c = AMY.linkTo(INTERVIEW);
        Predicate<? super Contact> predicate = contact -> contact.getLinkedEvents().contains(event.getUuid());
        newLogic.filterContactsWithLinksToEvent(event);

        assertNotEquals(predicate.test(c), newModel.contactPredicate.test(BOB));
        assertEquals(predicate.test(c), newModel.contactPredicate.test(c));
    }

    @Test
    public void test_filterEventsWithLinkToContact() {
        ModelStubWithPredicate newModel = new ModelStubWithPredicate();
        Logic newLogic = new LogicManager(newModel, storage);
        Event e = INTERVIEW.linkTo(AMY);
        Contact contact = AMY.linkTo(INTERVIEW);
        Predicate<? super Event> predicate = event -> event.getLinkedContacts().contains(contact.getUuid());
        newLogic.filterEventsWithLinkToContact(contact);

        assertNotEquals(predicate.test(e), newModel.eventPredicate.test(TEAM_MEETING));
        assertEquals(predicate.test(e), newModel.eventPredicate.test(e));
    }

    @Test
    public void test_resetFilterOfContacts() {
        ModelStubWithBoolean newModel = new ModelStubWithBoolean();
        Logic newLogic = new LogicManager(newModel, storage);
        assertFalse(newModel.contactBoolean);
        newLogic.resetFilterOfContacts();
        assertTrue(newModel.contactBoolean);
    }

    @Test
    public void test_resetFilterOfEvents() {
        ModelStubWithBoolean newModel = new ModelStubWithBoolean();
        Logic newLogic = new LogicManager(newModel, storage);
        assertFalse(newModel.eventBoolean);
        newLogic.resetFilterOfEvents();
        assertTrue(newModel.eventBoolean);
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonAddressBookIoExceptionThrowingStub extends JsonAddressBookStorage {
        private JsonAddressBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath)
                throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

    /**
     * A Model stub that contains an address book.
     */
    private class ModelStubWithAddressBook extends ModelStub {
        private ReadOnlyAddressBook addressBook;

        ModelStubWithAddressBook(AddressBook addressBook) {
            requireNonNull(addressBook);
            this.addressBook = addressBook;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return addressBook;
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook addressBook) {
            requireNonNull(addressBook);
            this.addressBook = addressBook;
        }
    }

    /**
     * A Model stub that contains a GUI settings.
     */
    private class ModelStubWithGuiSettings extends ModelStub {
        private GuiSettings guiSettings;

        ModelStubWithGuiSettings(GuiSettings guiSettings) {
            requireNonNull(guiSettings);
            this.guiSettings = guiSettings;
        }

        @Override
        public GuiSettings getGuiSettings() {
            return guiSettings;
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            requireNonNull(guiSettings);
            this.guiSettings = guiSettings;
        }
    }

    /**
     * A Model stub that contains contact and event predicates.
     */
    private class ModelStubWithPredicate extends ModelStub {
        private Predicate<? super Contact> contactPredicate;
        private Predicate<? super Event> eventPredicate;

        @Override
        public void updateFilteredContactList(Predicate<? super Contact> predicate) {
            contactPredicate = predicate;
        }

        @Override
        public void updateFilteredEventList(Predicate<? super Event> predicate) {
            eventPredicate = predicate;
        }
    }

    /**
     * A Model stub that contains two booleans.
     */
    private class ModelStubWithBoolean extends ModelStub {
        private boolean contactBoolean;
        private boolean eventBoolean;

        public ModelStubWithBoolean() {
            contactBoolean = false;
            eventBoolean = false;
        }

        @Override
        public void rerenderContactCards(boolean useBackSamePredicate) {
            contactBoolean = useBackSamePredicate;
        }

        @Override
        public void rerenderEventCards(boolean useBackSamePredicate) {
            eventBoolean = useBackSamePredicate;
        }
    }
}
