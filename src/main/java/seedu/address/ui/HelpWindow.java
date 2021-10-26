package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
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
    public static final String CADD_COMMAND = "cadd n/NAME e/EMAIL "
            + "[p/PHONE_NUMBER] [a/ADDRESS] [th/TELEGRAM_HANDLE] [z/ZOOM] [t/TAG]…";
    public static final String CCLEAR_COMMAND = "cclear";
    public static final String CDELETE_COMMAND = "cdelete INDEX1[-INDEX2]";
    public static final String CEDIT_COMMAND = "cedit INDEX [n/NAME] [e/EMAIL] "
            + "[p/PHONE] [a/ADDRESS] [th/TELEGRAM_HANDLE] [z/ZOOM] [dt/TAG_DELETED]… [t/TAG_ADDED]…";
    public static final String CFIND_COMMAND = "cfind KEYWORD [MORE_KEYWORDS]";
    public static final String CLIST_COMMAND = "clist [e/] [p/] [a/] [th/] [z/] [t/]";
    public static final String CVIEW_COMMAND = "cview INDEX";


    public static final String EADD_COMMAND = "eadd n/NAME at/START_TIME "
            + "[end/END_TIME] [d/DESCRIPTION] [a/ADDRESS] [z/ZOOM] [t/TAG]…";
    public static final String ECLEAR_COMMAND = "eclear";
    public static final String EDELETE_COMMAND = "edelete INDEX1[-INDEX2]";
    public static final String EEDIT_COMMAND = "eedit INDEX [n/NAME] [at/START_TIME] "
            + "[end/END_TIME] [d/DESCRIPTION] [a/ADDRESS] [z/ZOOM] [dt/TAG_DELETED]… [t/TAG_ADDED]…";
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
    private Label cAdd;
    @FXML
    private Label cClear;
    @FXML
    private Label cDelete;
    @FXML
    private Label cEdit;
    @FXML
    private Label cFind;
    @FXML
    private Label cList;
    @FXML
    private Label cView;

    @FXML
    private Label eAdd;
    @FXML
    private Label eClear;
    @FXML
    private Label eDelete;
    @FXML
    private Label eEdit;
    @FXML
    private Label eFind;
    @FXML
    private Label eLink;
    @FXML
    private Label eList;
    @FXML
    private Label eSort;
    @FXML
    private Label eView;

    @FXML
    private Label calendar;
    @FXML
    private Label exit;
    @FXML
    private Label help;
    @FXML
    private Label redo;
    @FXML
    private Label undo;


    //commands
    @FXML
    private Label cAddCommand;
    @FXML
    private Label cClearCommand;
    @FXML
    private Label cDeleteCommand;
    @FXML
    private Label cEditCommand;
    @FXML
    private Label cFindCommand;
    @FXML
    private Label cListCommand;
    @FXML
    private Label cViewCommand;

    @FXML
    private Label eAddCommand;
    @FXML
    private Label eClearCommand;
    @FXML
    private Label eDeleteCommand;
    @FXML
    private Label eEditCommand;
    @FXML
    private Label eFindCommand;
    @FXML
    private Label eLinkCommand;
    @FXML
    private Label eListCommand;
    @FXML
    private Label eSortCommand;
    @FXML
    private Label eViewCommand;

    @FXML
    private Label calendarCommand;
    @FXML
    private Label exitCommand;
    @FXML
    private Label helpCommand;
    @FXML
    private Label redoCommand;
    @FXML
    private Label undoCommand;


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

        cAdd.setText(ADD);
        cClear.setText(CLEAR);
        cDelete.setText(DELETE);
        cEdit.setText(EDIT);
        cFind.setText(FIND);
        cList.setText(LIST);
        cView.setText(VIEW);

        eAdd.setText(ADD);
        eClear.setText(CLEAR);
        eDelete.setText(DELETE);
        eEdit.setText(EDIT);
        eFind.setText(FIND);
        eLink.setText(LINK);
        eList.setText(LIST);
        eSort.setText(SORT);
        eView.setText(VIEW);

        calendar.setText(CALENDAR);
        exit.setText(EXIT);
        help.setText(HELP);
        redo.setText(REDO);
        undo.setText(UNDO);

        cAddCommand.setText(CADD_COMMAND);
        cClearCommand.setText(CCLEAR_COMMAND);
        cDeleteCommand.setText(CDELETE_COMMAND);
        cEditCommand.setText(CEDIT_COMMAND);
        cFindCommand.setText(CFIND_COMMAND);
        cListCommand.setText(CLIST_COMMAND);
        cViewCommand.setText(CVIEW_COMMAND);

        eAddCommand.setText(EADD_COMMAND);
        eClearCommand.setText(ECLEAR_COMMAND);
        eDeleteCommand.setText(EDELETE_COMMAND);
        eEditCommand.setText(EEDIT_COMMAND);
        eFindCommand.setText(EFIND_COMMAND);
        eLinkCommand.setText(ELINK_COMMAND);
        eListCommand.setText(ELIST_COMMAND);
        eSortCommand.setText(ESORT_COMMAND);
        eViewCommand.setText(EVIEW_COMMAND);

        calendarCommand.setText(CALENDAR_COMMAND);
        exitCommand.setText(EXIT_COMMAND);
        helpCommand.setText(HELP_COMMAND);
        redoCommand.setText(REDO_COMMAND);
        undoCommand.setText(UNDO_COMMAND);

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
