package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2122s1-cs2103t-w15-3.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "For more help, visit SoConnect User Guide: " + USERGUIDE_URL;
    public static final String INTRODUCTION = "Here are all the commands that you can use in SoConnect:";

    //headers
    public static final String CONTACT_TITLE = "Contact Management";
    public static final String EVENT_TITLE = "Event Management";
    public static final String GENERAL_TTILE = "General";
    public static final String ACTION_HEADER = "Action";
    public static final String FORMAT = "Format";

    //actions
    public static final String ADD = "Add";
    public static final String CLEAR = "Clear";
    public static final String DELETE = "Delete";
    public static final String EDIT = "Edit";
    public static final String FIND = "Find";
    public static final String LIST = "List";
    public static final String VIEW = "View";

    public static final String LINK = "Link";
    public static final String SORT = "Sort";

    public static final String CALENDAR = "Calendar";
    public static final String EXIT = "Exit";
    public static final String HELP = "Help";
    public static final String REDO = "Redo";
    public static final String UNDO = "Undo";

    //commands
    public static final String CADD_COMMAND = "cadd n/NAME e/EMAIL " +
            "[p/PHONE_NUMBER] [a/ADDRESS] [th/TELEGRAM_HANDLE] [z/ZOOM] [t/TAG]…";
    public static final String CCLEAR_COMMAND = "cclear";
    public static final String CDELETE_COMMAND = "cdelete INDEX1[-INDEX2]";
    public static final String CEDIT_COMMAND = "cedit INDEX " +
            "[n/NAME] [e/EMAIL] [p/PHONE] [a/ADDRESS] [th/TELEGRAM_HANDLE] [z/ZOOM] [dt/TAG_DELETED]… [t/TAG_ADDED]…";
    public static final String CFIND_COMMAND = "cfind KEYWORD [MORE_KEYWORDS]";
    public static final String CLIST_COMMAND = "clist [e/] [p/] [a/] [th/] [z/] [t/]";
    public static final String CVIEW_COMMAND = "cview INDEX";


    public static final String EADD_COMMAND = "eadd n/NAME at/START_TIME " +
            "[end/END_TIME] [d/DESCRIPTION] [a/ADDRESS] [z/ZOOM] [t/TAG]…";
    public static final String ECLEAR_COMMAND = "eclear";
    public static final String EDELETE_COMMAND = "edelete INDEX1[-INDEX2]";
    public static final String EEDIT_COMMAND = "eedit INDEX " +
            "[n/NAME] [at/START_TIME] [end/END_TIME] [d/DESCRIPTION] [a/ADDRESS] [z/ZOOM] [dt/TAG_DELETED]…\u200B [t/TAG_ADDED]…\u200B";
    public static final String EFIND_COMMAND = "efind KEYWORD [MORE_KEYWORDS]";
    public static final String ELINK_COMMAND = "elink EVENT_INDEX c/CONTACT_INDEX [c/CONTACT_INDEX]…";
    public static final String ELIST_COMMAND = "elist [at/] [end/] [d/] [a/] [z/] [t/]";
    public static final String ESORT_COMMAND = "esort";
    public static final String EVIEW_COMMAND = "eview INDEX";


    public static final String CALENDAR_COMMAND = "calendar";
    public static final String EXIT_COMMAND = "exit";
    public static final String HELP_COMMAND = "help";
    public static final String REDO_COMMAND = "redo";
    public static final String UNDO_COMMAND = "undo";


    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Label introduction;

    //tables
    @FXML
    private GridPane contactTable;
    @FXML
    private GridPane eventTable;
    @FXML
    private GridPane generalTable;


    //headers
    @FXML
    private Label contactTitle;
    @FXML
    private Label eventTitle;
    @FXML
    private Label generalTitle;
    @FXML
    private Label actionHeaderContact;
    @FXML
    private Label formatContact;
    @FXML
    private Label actionHeaderEvent;
    @FXML
    private Label formatEvent;
    @FXML
    private Label actionHeaderGeneral;
    @FXML
    private Label formatGeneral;


    //actions
    @FXML
    private Label CAdd;
    @FXML
    private Label CClear;
    @FXML
    private Label CDelete;
    @FXML
    private Label CEdit;
    @FXML
    private Label CFind;
    @FXML
    private Label CList;
    @FXML
    private Label CView;

    @FXML
    private Label EAdd;
    @FXML
    private Label EClear;
    @FXML
    private Label EDelete;
    @FXML
    private Label EEdit;
    @FXML
    private Label EFind;
    @FXML
    private Label ELink;
    @FXML
    private Label EList;
    @FXML
    private Label ESort;
    @FXML
    private Label EView;

    @FXML
    private Label Calendar;
    @FXML
    private Label Exit;
    @FXML
    private Label Help;
    @FXML
    private Label Redo;
    @FXML
    private Label Undo;


    //commands
    @FXML
    private Label CAddCommand;
    @FXML
    private Label CClearCommand;
    @FXML
    private Label CDeleteCommand;
    @FXML
    private Label CEditCommand;
    @FXML
    private Label CFindCommand;
    @FXML
    private Label CListCommand;
    @FXML
    private Label CViewCommand;

    @FXML
    private Label EAddCommand;
    @FXML
    private Label EClearCommand;
    @FXML
    private Label EDeleteCommand;
    @FXML
    private Label EEditCommand;
    @FXML
    private Label EFindCommand;
    @FXML
    private Label ELinkCommand;
    @FXML
    private Label EListCommand;
    @FXML
    private Label ESortCommand;
    @FXML
    private Label EViewCommand;

    @FXML
    private Label CalendarCommand;
    @FXML
    private Label ExitCommand;
    @FXML
    private Label HelpCommand;
    @FXML
    private Label RedoCommand;
    @FXML
    private Label UndoCommand;


    //footer
    @FXML
    private Button copyButton;
    @FXML
    private Label helpMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        introduction.setText(INTRODUCTION);

        contactTable.setGridLinesVisible(true);
//        contactTable.setHgap(1.00);
        eventTable.setGridLinesVisible(true);
        generalTable.setGridLinesVisible(true);

        contactTitle.setText(CONTACT_TITLE);
        eventTitle.setText(EVENT_TITLE);
        generalTitle.setText(GENERAL_TTILE);
        actionHeaderContact.setText(ACTION_HEADER);
        formatContact.setText(FORMAT);
        actionHeaderEvent.setText(ACTION_HEADER);
        formatEvent.setText(FORMAT);
        actionHeaderGeneral.setText(ACTION_HEADER);
        formatGeneral.setText(FORMAT);

        CAdd.setText(ADD);
        CClear.setText(CLEAR);
        CDelete.setText(DELETE);
        CEdit.setText(EDIT);
        CFind.setText(FIND);
        CList.setText(LIST);
        CView.setText(VIEW);

        EAdd.setText(ADD);
        EClear.setText(CLEAR);
        EDelete.setText(DELETE);
        EEdit.setText(EDIT);
        EFind.setText(FIND);
        ELink.setText(LINK);
        EList.setText(LIST);
        ESort.setText(SORT);
        EView.setText(VIEW);

        Calendar.setText(CALENDAR);
        Exit.setText(EXIT);
        Help.setText(HELP);
        Redo.setText(REDO);
        Undo.setText(UNDO);

        CAddCommand.setText(CADD_COMMAND);
        CClearCommand.setText(CCLEAR_COMMAND);
        CDeleteCommand.setText(CDELETE_COMMAND);
        CEditCommand.setText(CEDIT_COMMAND);
        CFindCommand.setText(CFIND_COMMAND);
        CListCommand.setText(CLIST_COMMAND);
        CViewCommand.setText(CVIEW_COMMAND);

        EAddCommand.setText(EADD_COMMAND);
        EClearCommand.setText(ECLEAR_COMMAND);
        EDeleteCommand.setText(EDELETE_COMMAND);
        EEditCommand.setText(EEDIT_COMMAND);
        EFindCommand.setText(EFIND_COMMAND);
        ELinkCommand.setText(ELINK_COMMAND);
        EListCommand.setText(ELIST_COMMAND);
        ESortCommand.setText(ESORT_COMMAND);
        EViewCommand.setText(EVIEW_COMMAND);

        CalendarCommand.setText(CALENDAR_COMMAND);
        ExitCommand.setText(EXIT_COMMAND);
        HelpCommand.setText(HELP_COMMAND);
        RedoCommand.setText(REDO_COMMAND);
        UndoCommand.setText(UNDO_COMMAND);

        helpMessage.setText(HELP_MESSAGE);

    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
