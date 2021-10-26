package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
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
    public static final String FORMAT_HEADER = "Format";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    private final ObservableList<ContactCommandSummary> contactList =
            ContactCommandSummary.getContactCommandSummary();
    private final ObservableList<EventCommandSummary> eventList =
            EventCommandSummary.getEventCommandSummary();
    private final ObservableList<GeneralCommandSummary> generalList =
            GeneralCommandSummary.getGeneralCommandSummary();

    @FXML
    private Label introduction;

    //tables
    @FXML
    private TableView<ContactCommandSummary> contactTable;
    @FXML
    private TableView<EventCommandSummary> eventTable;
    @FXML
    private TableView<GeneralCommandSummary> generalTable;

    @FXML
    private TableColumn<ContactCommandSummary, String> contactAction;
    @FXML
    private TableColumn<ContactCommandSummary, String> contactFormat;

    @FXML
    private TableColumn<EventCommandSummary, String> eventAction;
    @FXML
    private TableColumn<EventCommandSummary, String> eventFormat;

    @FXML
    private TableColumn<GeneralCommandSummary, String> generalAction;
    @FXML
    private TableColumn<GeneralCommandSummary, String> generalFormat;


    //headers
    @FXML
    private Label contactTitle;
    @FXML
    private Label eventTitle;
    @FXML
    private Label generalTitle;


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

        contactTitle.setText(CONTACT_TITLE);
        eventTitle.setText(EVENT_TITLE);
        generalTitle.setText(GENERAL_TTILE);

        setCommandSummary(contactTable, contactAction, contactFormat, contactList);
        setCommandSummary(eventTable, eventAction, eventFormat, eventList);
        setCommandSummary(generalTable, generalAction, generalFormat, generalList);

        helpMessage.setText(HELP_MESSAGE);

    }


    public <S> void setCommandSummary(TableView<S> table, TableColumn<S, String> actionHeader,
                                      TableColumn<S, String> formatHeader, ObservableList<S> list) {
        table.setItems(list);

        actionHeader = new TableColumn(ACTION_HEADER);
        formatHeader = new TableColumn<>(FORMAT_HEADER);
        actionHeader.setCellValueFactory(new PropertyValueFactory(ACTION_HEADER));
        formatHeader.setCellValueFactory(new PropertyValueFactory(FORMAT_HEADER));

        table.getColumns().setAll(actionHeader, formatHeader);

        formatHeader.setCellFactory(getCallback(formatHeader));
        actionHeader.setCellFactory(getCallback(actionHeader));

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public <S> Callback<TableColumn<S, String>,
                TableCell<S, String>> getCallback(TableColumn<S, String> header) {
        return tc -> {
            TableCell<S, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(header.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        };
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
