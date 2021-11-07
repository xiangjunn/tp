package seedu.address.logic.commands.general;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.general.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.general.CommandTestUtil.EMPTY_PREFIX_CONTACT;
import static seedu.address.logic.commands.general.CommandTestUtil.EMPTY_PREFIX_DESCRIPTION;
import static seedu.address.logic.commands.general.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_INDEX_ONE;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_INDEX_TWO;
import static seedu.address.logic.commands.general.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Logic;
import seedu.address.logic.LogicStub;
import seedu.address.logic.commands.contact.CAddCommand;
import seedu.address.logic.commands.contact.CDeleteCommand;
import seedu.address.logic.commands.contact.CFindCommand;
import seedu.address.logic.commands.event.ELinkCommand;
import seedu.address.logic.commands.event.EListCommand;
import seedu.address.logic.commands.event.EUnmarkCommand;
import seedu.address.logic.commands.event.EViewCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactContainsKeywordsPredicate;
import seedu.address.model.event.Event;

class UndoRedoIntegrationTest {
    // Usual commands that do not change the display settings
    private static final String C_ADD_COMMAND = CAddCommand.COMMAND_WORD + EMAIL_DESC_AMY
        + NAME_DESC_AMY + VALID_TAG_FRIEND;
    private static final String C_DELETE_COMMAND = CDeleteCommand.COMMAND_WORD + " " + VALID_INDEX_TWO;
    private static final String E_UNMARK_COMMAND = EUnmarkCommand.COMMAND_WORD + " " + VALID_INDEX_ONE;
    private static final String E_LINK_COMMAND =
        ELinkCommand.COMMAND_WORD + " " + VALID_INDEX_TWO + EMPTY_PREFIX_CONTACT + VALID_INDEX_TWO;

    // Commands that change the display settings (predicate)
    private static final String C_FIND_COMMAND = CFindCommand.COMMAND_WORD + EMAIL_DESC_AMY;
    private static final Predicate<? super Contact> CONTACT_FIND_PREDICATE;

    // Commands that change the display settings (EventDisplaySetting)
    private static final String E_LIST_COMMAND = EListCommand.COMMAND_WORD + EMPTY_PREFIX_DESCRIPTION;
    private static final String E_VIEW_COMMAND = EViewCommand.COMMAND_WORD + " " + VALID_INDEX_TWO;

    // Undo and redo commands
    private static final String UNDO_COMMAND = UndoCommand.COMMAND_WORD;
    private static final String REDO_COMMAND = RedoCommand.COMMAND_WORD;

    // Commands that will give an error
    private static final String INVALID_COMMAND = "a";
    private static final String INVALID_C_ADD_COMMAND = CAddCommand.COMMAND_WORD + NAME_DESC_AMY;

    static {
        ContactContainsKeywordsPredicate predicate = new ContactContainsKeywordsPredicate();
        predicate.setEmailKeywords(List.of(VALID_EMAIL_AMY));
        CONTACT_FIND_PREDICATE = predicate;
    }

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Logic logicManager = new LogicStub(model);

    private static Predicate<? super Event> getEViewPredicate(Event event) {
        return curr -> curr.isSameEvent(event);
    }

    private boolean runCommandSuccessful(String command) {
        try {
            logicManager.execute(command);
            return true;
        } catch (CommandException | ParseException ignored) {
            return false;
        }
    }

    // Copies the entire model, except the history and the filtered list
    private Model copyOfModel(Model model) {
        Model result = new ModelManager(model.getAddressBook(), model.getUserPrefs());
        result.setEventDisplaySetting(model.getEventDisplaySetting());
        result.setContactDisplaySetting(model.getContactDisplaySetting());
        return result;
    }

    @Test
    public void typicalUsage_noChangeInDisplay() {
        assertFalse(runCommandSuccessful(UNDO_COMMAND));
        assertFalse(runCommandSuccessful(REDO_COMMAND));

        assertTrue(runCommandSuccessful(E_UNMARK_COMMAND));
        Model tempModel1 = copyOfModel(model);

        assertTrue(runCommandSuccessful(E_LINK_COMMAND));
        Model tempModel2 = copyOfModel(model);

        assertTrue(runCommandSuccessful(UNDO_COMMAND));
        assertEquals(model, tempModel1);

        assertTrue(runCommandSuccessful(UNDO_COMMAND));
        assertFalse(runCommandSuccessful(UNDO_COMMAND));

        assertTrue(runCommandSuccessful(REDO_COMMAND));
        assertEquals(model, tempModel1);

        assertTrue(runCommandSuccessful(REDO_COMMAND));
        assertEquals(model, tempModel2);
    }

    @Test
    public void typicalUsage_changeInDisplay() {
        assertTrue(runCommandSuccessful(C_ADD_COMMAND));
        Model tempModel1 = copyOfModel(model);
        assertEquals(tempModel1, model);

        assertTrue(runCommandSuccessful(E_LIST_COMMAND));
        Model tempModel2 = copyOfModel(model);
        assertEquals(tempModel2, model);

        assertTrue(runCommandSuccessful(C_FIND_COMMAND));
        Model tempModel3 = copyOfModel(model);
        tempModel3.updateFilteredContactList(CONTACT_FIND_PREDICATE);
        assertEquals(tempModel3, model);

        Event viewEvent = model.getFilteredEventList().get(1);

        assertTrue(runCommandSuccessful(E_VIEW_COMMAND));
        Model tempModel4 = copyOfModel(model);
        tempModel4.updateFilteredContactList(CONTACT_FIND_PREDICATE);
        tempModel4.updateFilteredEventList(getEViewPredicate(viewEvent));
        assertEquals(tempModel4, model);

        // Series of undo and redo
        assertFalse(runCommandSuccessful(REDO_COMMAND));
        assertTrue(runCommandSuccessful(UNDO_COMMAND));
        assertEquals(tempModel3, model);

        assertTrue(runCommandSuccessful(REDO_COMMAND));
        assertEquals(tempModel4, model);

        assertTrue(runCommandSuccessful(UNDO_COMMAND));
        assertEquals(tempModel3, model);

        assertTrue(runCommandSuccessful(UNDO_COMMAND));
        assertEquals(tempModel2, model);

        assertTrue(runCommandSuccessful(UNDO_COMMAND));
        assertEquals(tempModel1, model);

        assertTrue(runCommandSuccessful(UNDO_COMMAND));
        assertEquals(new ModelManager(getTypicalAddressBook(), new UserPrefs()), model);

        assertFalse(runCommandSuccessful(UNDO_COMMAND));
        assertTrue(runCommandSuccessful(REDO_COMMAND));

        assertTrue(runCommandSuccessful(C_DELETE_COMMAND));
        Model tempModel5 = copyOfModel(model);

        assertFalse(runCommandSuccessful(REDO_COMMAND));
        assertTrue(runCommandSuccessful(UNDO_COMMAND));
        assertTrue(runCommandSuccessful(REDO_COMMAND));
        assertEquals(tempModel5, model);

    }

    @Test
    public void invalidCommands() {
        assertFalse(runCommandSuccessful(INVALID_COMMAND));
        assertFalse(runCommandSuccessful(UNDO_COMMAND));
        assertFalse(runCommandSuccessful(REDO_COMMAND));
        assertTrue(runCommandSuccessful(E_LINK_COMMAND));
        Model tempModel = copyOfModel(model);
        assertFalse(runCommandSuccessful(INVALID_C_ADD_COMMAND));
        assertTrue(runCommandSuccessful(UNDO_COMMAND));
        assertEquals(model, new ModelManager(getTypicalAddressBook(), new UserPrefs()));
        assertTrue(runCommandSuccessful(REDO_COMMAND));
        assertEquals(model, tempModel);
    }
}
